package poly.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.service.INewsService;
import poly.service.impl.comm.AbstractgetUrlFordata;
import poly.util.dateUtil;
import poly.util.BigTransUtil;

@Service("NewsService")
public class NewsService extends AbstractgetUrlFordata  implements INewsService{

	private Logger log = Logger.getLogger(this.getClass());
	
	
	//skySports뉴스 크롤링 및 데이터 업데이트
	@Override
	public int skySportsNewsUpdate(List<EPLDTO> rList) throws Exception {
		
		List<Map<String, Object>> newsList = new LinkedList<Map<String,Object>>();
		
		EPLDTO pDTO = new EPLDTO();
		for(int i=0; i<rList.size(); i++) {

			pDTO = rList.get(i);
			
			//팀 이름을 활용하여 url 형식 만들기
			String team = pDTO.getTeam_name().trim();
			if(team.endsWith("FC")){
				team = team.substring(0,team.lastIndexOf("FC")-1).trim();
			}
			
			//url 중복을 방지하기위해 저장
			HashSet<String> distinct = new HashSet<>();
			
			//db에 들어갈 팀 목록(중복된 뉴스가 없을 경우 사용)
			List<String> teams = new ArrayList<>();
			teams.add(team);
			
			//팀이름을 url의 형식에 맞춰 변경하고 합치기
			String teamForUrl = team.replaceAll(" ", "-").replace("&", "and").concat("-news").toLowerCase();
			String url = "https://www.skysports.com/"+teamForUrl;
			log.info("팀이름을 활용한 url 형식 :: " + url);
			
			//팀 뉴스 사이트 접속
			Document doc = Jsoup.connect(url).get();
			Elements element_urlGet = doc.select("a.news-list__figure");
			Iterator<Element> newsUrlList = element_urlGet.iterator();
			
			//뉴스 URL크롤링
			while(newsUrlList.hasNext()) {

				try {
					
					Map<String, Object> pMap = new LinkedHashMap<String, Object>();
					
					//크롤링할 뉴스 url
					String newsUrl = newsUrlList.next().attr("href").toString();
					
					log.info("news_url :: " + newsUrl);
					
					// 뉴스가 아닌 url 거르기
					if (newsUrl.endsWith("transfer-centre") || newsUrl.contains("/live/")
							|| newsUrl.contains("/video/") || newsUrl.contains("/story-telling/")) {
						log.info("뉴스형식이 맞지 않음");
						continue;
					}
					
					//중복 url 거르기
					if(distinct.contains(newsUrl)) {
						log.info("중복된 url 거르고 관련 팀 목록만 추가");
						for(Map<String, Object> a :newsList) {
							if(a.containsValue(newsUrl)) {
								((ArrayList<String>) a.get("teams")).add(team);
								break;
							}
						}
						continue;
					}
					distinct.add(newsUrl);

					doc = Jsoup.connect(newsUrl).get();
					
					// 날짜 크롤링
					String news_date = doc.select("p.sdc-article-date__date-time").text();
					String day = news_date.trim().split(" ")[1];
					String month = news_date.trim().split(" ")[2];
					if (news_date.trim().equals("")) {
						log.info("날짜 없어서 패스");
						continue;
					}else if( day.equals(dateUtil.today_day) && month.equals(dateUtil.today_month) ){
						news_date = dateUtil.today;
					}else if( day.equals(dateUtil.yesterday_day) && month.equals(dateUtil.yesterday_month)){
						news_date = dateUtil.yesterday;
					}else {
						log.info("최근 2일간의 기사가 아님 => 뉴스 날짜  :: " + news_date);
						break;
					}
					log.info("news_date :: " + news_date);
					
					// 제목 크롤링
					String news_title = doc.select("span.sdc-article-header__long-title").text();
					if (news_title.equals("")) {
						log.info("제목 없어서 패스");
						continue;
					}
					log.info("news_title :: " + news_title);

					// 이미지 주소 크롤링
					String img = doc.select("img.sdc-article-image__item").attr("src");
					if (img.trim().equals("")) {
						log.info("이미지 없음 => 대체 이미지 넣음");
						// 대체 이미지 입력
						img = "../resource/images/sky_sports.png";
					}
					log.info("news_img :: " + img);
					
					// 뉴스 본문 크롤링
					List<String> contents = new ArrayList<>();
					Elements element = doc.select("div.sdc-article-body.sdc-article-body--lead");
					if (element.select(
							"div.sdc-article-body.sdc-article-body--lead > p, div.sdc-article-body.sdc-article-body--lead > h3")
							.size() < 1) {
						log.info("뉴스 본문 없음 패스");
						continue;
					}else {
						log.info("뉴스 본문 1줄 이상 진행");
					}
					for (Element content : element.select(
							"div.sdc-article-body.sdc-article-body--lead > p,div.sdc-article-body.sdc-article-body--lead > h3")) {
						contents.add(content.toString());
					}
					log.info("news_body length :: " + contents.size());
					
					
					//Map에 저장
					pMap.put("url", newsUrl);
					pMap.put("title", news_title);
					pMap.put("date", news_date);
					pMap.put("img", img);
					pMap.put("contents", contents);
					pMap.put("teams", teams);
					
					//리스트에 저장
					newsList.add(pMap);
					
					pMap=null;
					
				} catch (Exception e) {
					log.info("크롤링 뉴스 형태가 맞지 않아 크롤이 종료");
				} finally {
					log.info("--------------------------------------------------------------------------------");
				}
				
				
			}
			
			pDTO = null;
			
		}
		
		log.info("SkySports 뉴스 수집 완료!! 뉴스 개수 :: " + newsList.size());
		
		return 0;
	}


	@Override
	public int theGuardianNewsUpdate() throws Exception {
		
		List<Map<String, Object>> newsList = new LinkedList<Map<String,Object>>();
		
		//가디언즈 사이트(팀 뉴스 사이트 주소 보유한 페이지)git 
		String url = "https://www.theguardian.com/football/teams";
		
		Document doc = Jsoup.connect(url).get();
		
		Elements element_urlGet = doc.select("#premier-league > div > div > div > ul a");
		
		Iterator<Element> newsHomeList = element_urlGet.iterator();
		
		//각각의 팀 뉴스 홈으로 이동 
		while(newsHomeList.hasNext()) {
			String teamNewsHome = newsHomeList.next().attr("href").toString();
			doc = Jsoup.connect(teamNewsHome).get();
			
			//url 중복을 방지하기위해 저장
			HashSet<String> distinct = new HashSet<>();
			
			//db에 들어갈 팀 목록(중복된 뉴스가 없을 경우 사용)
			String team = doc.select("h1.index-page-header__title").text().trim();
			List<String> teams = new ArrayList<>();
			teams.add(team);
			
			element_urlGet = doc.select("div.fc-item__content a");
			 
			Iterator<Element> newsUrlList = element_urlGet.iterator();
			
			//뉴스 페이지로 이동해서 크롤링
			while(newsUrlList.hasNext()) {
				try {

					// 뉴스를 담을 맵 생성
					Map<String, Object> pMap = new LinkedHashMap<String, Object>();

					String newsUrl = newsUrlList.next().attr("href").toString();

					if (newsUrl.contains("/live/") || newsUrl.contains("/video/")) {
						continue;
					}

					log.info("news_url :: " + newsUrl);

					if (distinct.contains(newsUrl)) {
						log.info("중복된 url 거르고 관련 팀 목록만 추가");
						for (Map<String, Object> a : newsList) {
							if (a.containsValue(newsUrl)) {
								((ArrayList<String>) a.get("teams")).add(team);
								break;
							}
						}
						continue;
					}
					distinct.add(newsUrl);

					// 뉴스기사 접속
					doc = Jsoup.connect(newsUrl).get();

					// 날짜 크롤링
					String news_date = doc.select("div.css-dcy86h > label").text().trim();
					String day = news_date.trim().split(" ")[1];
					String month = news_date.trim().split(" ")[2];
					if (news_date.equals("")) {
						log.info("날짜 없어서 패스");
						continue;
					} else if (day.equals(dateUtil.today_day) && month.equals(dateUtil.today_month)) {
						news_date = dateUtil.today;
					} else if (day.equals(dateUtil.yesterday_day) && month.equals(dateUtil.yesterday_month)) {
						news_date = dateUtil.yesterday;
					} else {
						log.info("최근 2일간의 기사가 아님 => 뉴스 날짜  :: " + news_date);
						break;
					}
					log.info("news_date :: " + news_date);

					// 뉴스 제목 크롤링
					String news_title = doc.select("h1").text();
					if (news_title.trim().equals("")) {
						log.info("제목 없어서 패스");
						continue;
					}
					log.info("news_title :: " + news_title);

					// 번역해서 넣기
					String ko_title = "";

					// 뉴스 이미지 크롤링
					String img = doc.select("div.css-1nfcn93 > picture > img").attr("src");
					if (img.trim().equals("")) {
						log.info("이미지 없음 => 대체 이미지 넣음");
						// 대체 이미지 입력
						img = "../resource/images/the_guardian.png";
						continue;
					}
					log.info("news_img :: " + img);

					List<String> contents = new ArrayList<>();
					Elements element = doc.select("p.css-19dnnol, div.article-body-viewer-selector > h2");
					if (element.size() < 1) {
						log.info("뉴스 본문 없음 패스");
						continue;
					} else {
						log.info("뉴스 본문 1줄 이상 진행");
					}
					for (Element content : element) {

						contents.add(content.text());

					}
					log.info("news_contents length :: " + contents.size());

					// 한국
					String ko_contents = BigTransUtil.transNews(contents);
					
					log.info("한국 번역 : "+ ko_contents);

					// Map에 저장
					pMap.put("url", newsUrl);
					pMap.put("title", news_title);
					pMap.put("ko_title", ko_title);
					pMap.put("date", news_date);
					pMap.put("img", img);
					pMap.put("contents", contents);
					pMap.put("teams", teams);
					pMap.put("ko_contents", ko_contents);

					// 리스트에 저장
					newsList.add(pMap);

					pMap = null;

				} catch (Exception e) {
					log.info("크롤링 뉴스 형태가 맞지 않음(크롤링 하지 않음)");
				} finally {
					log.info("--------------------------------------------------------------------------------");
				}
				
			}
		}
		
		log.info("The Guardians 뉴스 수집 완료!! 뉴스 개수 :: " + newsList.size());
		
		return 0;
	}
	
	
	
//	public List<String> Selenium_skySports(String url) {
//		
//		List<String> rList = new ArrayList<>();
//		
//		// WebDriver 경로 설정
//		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
//		
//		// WebDriver 옵션 설정
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--start-maximized"); // 전체화면으로 실행
//		options.addArguments("headless");
//		options.addArguments("--disable-gpu");
//		options.addArguments("--disable-popup-blocking"); // 팝업 무시
//		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
//
//		// WebDriver 객체 생성
//		ChromeDriver driver = new ChromeDriver(options);
//		// 페이지 대기 시간 최대 5초 설정
//		WebDriverWait wait = new WebDriverWait(driver, 5);
//		
//		try {
//			// 웹페이지 요청
//			driver.get(url);
//			
//			String html_content = driver.getPageSource();
//			
//			Document doc = Jsoup.parse(html_content);
//			
//			Elements element = doc.select("div#widgetLite-9 > div > div > a");
//			#widgetLite-9 > div > div:nth-child(11) > a
//			
//			
////			Elements element = doc.select("span.sdc-article-header__long-title");
////			
////			Iterator<Element> newsUrl = element.select
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			driver.close();
//		}
//		
//		
//		return rList;
//	}
	
	

}
