package poly.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.service.INewsService;
import poly.service.impl.comm.AbstractgetUrlFordata;

@Service("NewsService")
public class NewsService extends AbstractgetUrlFordata  implements INewsService{

	private Logger log = Logger.getLogger(this.getClass());
	
	
	//skySports뉴스 크롤링 및 데이터 업데이트
	@Override
	public int skySportsNewsUpdate(List<EPLDTO> rList) throws Exception {
		
		EPLDTO pDTO = new EPLDTO();
		for(int i=0; i<rList.size(); i++) {
			pDTO = rList.get(i);
			
			//팀 이름을 활용하여 url 형식 만들기
			String team = pDTO.getTeam_name().trim();
			if(team.endsWith("FC")){
				team = team.substring(0,team.lastIndexOf("FC")-1).trim();
			}
			team = team.replaceAll(" ", "-").replace("&", "and");
			team = team.concat("-news").toLowerCase();
			
			String url = "https://www.skysports.com/"+team;
			log.info("팀이름을 활용한 url 형식 :: " + url);
			
			Document doc = Jsoup.connect(url).get();
			
			Elements element_urlGet = doc.select("a.news-list__figure");
			
			Iterator<Element> newsUrlList = element_urlGet.iterator();
			
			while(newsUrlList.hasNext()) {
				
				try {
				String newsUrl = newsUrlList.next().attr("href").toString();
				
				if(newsUrl.endsWith("transfer-centre") || newsUrl.contains("/live/")) {
					continue;
				}
				
				log.info("news_url :: " + newsUrl);
				
				doc = Jsoup.connect(newsUrl).get();
				
				String news_title = doc.select("span.sdc-article-header__long-title").text();
				
				String news_date = doc.select("p.sdc-article-date__date-time").text();
				
				log.info("news_title :: " + news_title);
				log.info("news_date :: " + news_date);
				
				String img = "";
				try {
					img = doc.select("img.sdc-article-image__item").attr("src");
				}catch (Exception e) {
					log.info("이미지 없음");
				}finally {
					log.info("news_img :: " + img);
				}
				
				log.info("news_body :: ");
				Elements element = doc.select(
						"body > div.section-wrap > div.sdc-site-layout-wrap.site-wrap.site-wrap-padding > div > div.sdc-site-layout__col.sdc-site-layout__col1 > div.sdc-article-body.sdc-article-body--lead");

				for (Element contents : element.select("div.sdc-article-body.sdc-article-body--lead > p,div.sdc-article-body.sdc-article-body--lead > h3")) {

					log.info(contents.text());

					contents = null;

				}
				
//				Elements element_newsBody = doc.select("div.sdc-article-body sdc-article-body--lead").first().children().select("p");
				
//				Iterator<Element> newsBody = element_newsBody.select("div.sdc-article-body sdc-article-body--lead > p,h3").iterator();
//				
//				log.info("news_body :: ");
//				while(newsBody.hasNext()) {
//					
//					String contents = newsBody.next().text();
//					log.info(contents);
//				}
				
				}catch (Exception e){
					log.info("크롤링 뉴스 형태가 맞지 않음(크롤링 하지 않음)");	
				}finally {
					log.info("--------------------------------------------------------------------------------");
				}
			}
			
			pDTO = null;
			
			
			//너무 많아서 일단 한바퀴만 테스트용 끝나면 지워
			break;
		}
		
		
		
		return 0;
	}


	@Override
	public int theGuardianNewsUpdate() throws Exception {
		
		//가디언즈 사이트(팀 뉴스 사이트 주소 보유한 페이지)git 
		String url = "https://www.theguardian.com/football/teams";
		
		Document doc = Jsoup.connect(url).get();
		
		Elements element_urlGet = doc.select("#premier-league > div > div > div > ul a");
		
		Iterator<Element> newsHomeList = element_urlGet.iterator();
		
		//각각의 팀 뉴스 홈으로 이동 
		while(newsHomeList.hasNext()) {
			String teamNewsHome = newsHomeList.next().attr("href").toString();
			doc = Jsoup.connect(teamNewsHome).get();
			
			element_urlGet = doc.select("div.fc-item__content a");
			 
			Iterator<Element> newsUrlList = element_urlGet.iterator();
			
			//뉴스 페이지로 이동해서 크롤링
			while(newsUrlList.hasNext()) {
				try {
					String newsUrl = newsUrlList.next().attr("href").toString();
					
					if(newsUrl.contains("/live/")) {
						continue;
					}
					
					log.info("news_url :: " + newsUrl);
					
					doc = Jsoup.connect(newsUrl).get();
					
					String news_title = doc.select("h1").text();
					if(news_title=="") {
						log.info("제목 없어서 패스");
						continue;
					}
					
					
					String news_date = doc.select("div.css-dcy86h > label").text();
					if(news_date=="") {
						log.info("날짜 없어서 패스");
						continue;
					}
					
					String img = "";
					try {
						img = doc.select("div.css-1nfcn93 > picture > img").attr("src");
					}catch (Exception e) {
						log.info("이미지 없음");
					}finally {
						log.info("news_img :: " + img);
					}
					
					
					List<String> news_body = new ArrayList<>();
					Elements element = doc.select("p.css-19dnnol");
					for (Element contents : element) {

						log.info(contents.text());

						news_body.add(contents.text());
						
						contents = null;
						
					}
					
					if(news_body.size()<1) {
						log.info("뉴스 본문 없어서 패스");
						continue;
					}
					
					log.info("news_title :: " + news_title);
					log.info("news_date :: " + news_date);
					log.info("news_body length :: " + news_body.size());
					
				}catch (Exception e) {
					
				}finally{
					
				}
				
			}
			
			//일단 팀 한개만
			break;
		}
		
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
