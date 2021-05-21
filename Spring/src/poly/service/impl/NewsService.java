package poly.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.persistance.mongo.INewsMongoMapper;
import poly.service.INewsService;
import poly.service.impl.comm.AbstractgetUrlFordata;
import poly.util.BigTransUtil;
import poly.util.TranslateUtil;
import poly.util.dateUtil;

@Service("NewsService")
public class NewsService extends AbstractgetUrlFordata  implements INewsService{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "NewsMongoMapper")
	private INewsMongoMapper newsMongoMapper; 

	// skySports뉴스 크롤링 및 데이터 업데이트
	@Override
	public int skySportsNewsUpdate(List<EPLDTO> rList) throws Exception {

		log.info(this.getClass().getName() + ".theGuardianNewsUpdate start!");

		List<Map<String, Object>> newsList = new LinkedList<Map<String, Object>>();

		// url 중복을 방지하기위해 저장
		HashSet<String> distinct = new HashSet<>();

		EPLDTO pDTO = new EPLDTO();

		// 각 축구팀 뉴스 사이트 접속 및 URL 획득
		for (int i = 0; i < rList.size(); i++) {

			pDTO = rList.get(i);

			// 팀 이름을 활용하여 url 형식 만들기
			String team = pDTO.getTeam_name().trim();
			if (team.endsWith("FC")) {
				team = team.substring(0, team.lastIndexOf("FC") - 1).trim();
			}

			// db에 들어갈 팀 목록(중복된 뉴스가 없을 경우 사용)
			Map<String, String> team_map = new HashMap<String, String>();
			List<Map<String, String>> teams = new ArrayList<Map<String, String>>();
			team_map.put("team_name", team);
			teams.add(team_map);

			// 팀이름을 url의 형식에 맞춰 변경하고 합치기
			String teamForUrl = team.replaceAll(" ", "-").replace("&", "and").concat("-news").toLowerCase();
			String url = "https://www.skysports.com/" + teamForUrl;
			log.info("팀이름을 활용한 url 형식 :: " + url);

			// 팀 뉴스 사이트 접속 및
			Document doc = Jsoup.connect(url).timeout(30000).get();
			Elements element_urlGet = doc.select("a.news-list__figure");
			Iterator<Element> newsUrlList = element_urlGet.iterator();

			// 뉴스 기사 크롤링 및 기사 저장
			while (newsUrlList.hasNext()) {

				try {

					Map<String, Object> pMap = new LinkedHashMap<String, Object>();

					// 크롤링할 뉴스 url
					String newsUrl = newsUrlList.next().attr("href").toString();

					log.info("news_url :: " + newsUrl);

					// 뉴스가 아닌 url 거르기
					if (newsUrl.endsWith("transfer-centre") || newsUrl.contains("/live/") || newsUrl.contains("/video/")
							|| newsUrl.contains("/story-telling/")) {
						log.info("뉴스형식이 맞지 않음");
						continue;
					}

					// 중복 url 거르기
					if (distinct.contains(newsUrl)) {
						log.info("중복된 url 거르고 관련 팀 목록만 추가");
						for (Map<String, Object> a : newsList) {
							if (a.containsValue(newsUrl)) {
								((ArrayList<Map<String, String>>) a.get("teams")).add(team_map);
								break;
							}
						}
						continue;
					}
					distinct.add(newsUrl);

					doc = Jsoup.connect(newsUrl).timeout(30000).get();

					// 날짜 크롤링
					String news_date = doc.select("p.sdc-article-date__date-time").text();
					String day = news_date.trim().split(" ")[1];
					String month = news_date.trim().split(" ")[2];
					if (news_date.trim().equals("")) {
						log.info("날짜 없어서 패스");
						continue;
					} else if (day.equals(dateUtil.today_day) && month.equals(dateUtil.today_month)) {
						news_date = dateUtil.today;
					} else {
						log.info("오늘 기사 아님 => 뉴스 날짜  :: " + news_date);
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

					// 번역해서 넣기
					String ko_title = "";
					try {
						ko_title = TranslateUtil.trans(news_title);
					}catch (Exception e) {
						log.info("파파고 번역 제한 or 다른 문제");
					}finally{
						if(ko_title.equals("")) {
							log.info("카카오번역기 이용");
							try {
							ko_title = TranslateUtil.kakaotrans(ko_title);
							}
							catch(Exception e){
								log.info("카카오 번역도 끝");
							}
						}
						log.info("ko_title :: "+ko_title);
					}

					// 이미지 주소 크롤링
					String img = doc.select("img.sdc-article-image__item").attr("src").toString();
					if (img.trim().equals("")) {
						log.info("이미지 없음 => 대체 이미지 넣음");
						// 대체 이미지 입력
						img = "../resource/images/sky_sports.png";
					}
					log.info("news_img :: " + img);

					// 뉴스 본문 크롤링
					List<String> contents = new ArrayList<>();
					Elements element = doc.select("div.sdc-article-body");
					if (element.select(
							"div.sdc-article-body.sdc-article-body--lead > p, div.sdc-article-body.sdc-article-body--lead > h3")
							.size() < 1) {
						log.info("뉴스 본문 없음 패스");
						continue;
					} else {
						log.info("뉴스 본문 1줄 이상 진행");
					}
					for (Element content : element.select(
							"div.sdc-article-body.sdc-article-body--lead > p,div.sdc-article-body.sdc-article-body--lead > h3")) {
						contents.add(content.text());
					}
					log.info("news_body length :: " + contents.size());

					// 한국
					String ko_content = BigTransUtil.transNews(contents);

					log.info("한국 번역 : " + ko_content);

					String[] ko_contents_String = ko_content.split("\n");
					List ko_contents = new ArrayList(Arrays.asList(ko_contents_String));

					log.info("번역 리스트 길이 :: " + ko_contents.size());

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
					log.info("크롤링 뉴스 형태가 맞지 않아 크롤이 종료");
				} finally {
					log.info("--------------------------------------------------------------------------------");
				}
				
				doc = null;

			}

			pDTO = null;
			teams = null;
			team_map = null;
		}

		log.info("SkySports 뉴스 수집 완료!! 뉴스 개수 :: " + newsList.size());

		// 컬렉션 명 설정
		String colNm = dateUtil.today_year_month + "_Sky_Sports";

		log.info("몽고DB 뉴스 입력");
		int res = 0;
		if (newsList.size() > 0) {
			res = newsMongoMapper.newsInsert(newsList, colNm);
		}

		log.info(this.getClass().getName() + ".theGuardianNewsUpdate end!");

		return res;
	}

	@Override
	public int theGuardianNewsUpdate() throws Exception {

		log.info(this.getClass().getName() + ".theGuardianNewsUpdate start!");

		List<Map<String, Object>> newsList = new LinkedList<Map<String, Object>>();

		// 가디언즈 사이트(팀 뉴스 사이트 주소 보유한 페이지)git
		String url = "https://www.theguardian.com/football/teams";

		Document doc = Jsoup.connect(url).timeout(30000).get();

		Elements element_urlGet = doc.select("#premier-league > div > div > div > ul a");

		Iterator<Element> newsHomeList = element_urlGet.iterator();

		// url 중복을 방지하기위해 저장
		HashSet<String> distinct = new HashSet<>();

		// 각각의 팀 뉴스 홈으로 이동
		while (newsHomeList.hasNext()) {
			String teamNewsHome = newsHomeList.next().attr("href").toString();
			doc = Jsoup.connect(teamNewsHome).timeout(30000).get();

			// db에 들어갈 팀 목록(중복된 뉴스가 없을 경우 사용)
			String team = doc.select("h1.index-page-header__title").text().trim();
			Map<String, String> team_map = new HashMap<String, String>();
			List<Map<String, String>> teams = new ArrayList<Map<String, String>>();
			team_map.put("team_name", team);
			teams.add(team_map);

			element_urlGet = doc.select("div.fc-item__content a");

			Iterator<Element> newsUrlList = element_urlGet.iterator();

			// 뉴스 페이지로 이동해서 크롤링
			while (newsUrlList.hasNext()) {
				try {

					// 뉴스를 담을 맵 생성
					Map<String, Object> pMap = new LinkedHashMap<String, Object>();

					String newsUrl = newsUrlList.next().attr("href").toString();

					if (newsUrl.contains("/live/") || newsUrl.contains("/video/")) {
						continue;
					}

					log.info("news_url :: " + newsUrl);

					// 중복 url 거르기
					if (distinct.contains(newsUrl)) {
						log.info("중복된 url 거르고 관련 팀 목록만 추가");
						for (Map<String, Object> a : newsList) {
							if (a.containsValue(newsUrl)) {
								((ArrayList<Map<String, String>>) a.get("teams")).add(team_map);
								break;
							}
						}
						continue;
					}
					distinct.add(newsUrl);

					// 뉴스기사 접속
					doc = Jsoup.connect(newsUrl).timeout(30000).get();

					// 날짜 크롤링
					String news_date = doc.select("div.css-dcy86h > label").text().trim();
					String day = news_date.trim().split(" ")[1];
					String month = news_date.trim().split(" ")[2];
					if (news_date.equals("")) {
						log.info("날짜 없어서 패스");
						continue;
					} else if (day.equals(dateUtil.today_day) && month.equals(dateUtil.today_month)) {
						news_date = dateUtil.today;
					} else {
						log.info("오늘 기사 아님 => 뉴스 날짜  :: " + news_date);
						break;
					}
					log.info("news_date :: " + news_date);

					// 뉴스 제목 크롤링
					String news_title = doc.select("h1").text().trim();
					if (news_title.equals("")) {
						log.info("제목 없어서 패스");
						continue;
					}
					log.info("news_title :: " + news_title);

					// 번역해서 넣기
					String ko_title = "";
					try {
						ko_title = TranslateUtil.trans(news_title);
					}catch (Exception e) {
						log.info("파파고 번역 제한 or 다른 문제");
					}finally{
						if(ko_title.equals("")) {
							log.info("카카오번역기 이용");
							try {
								ko_title = TranslateUtil.kakaotrans(news_title);
							}
							catch(Exception e){
								log.info("카카오 번역도 끝");
							}
						}
						log.info("ko_title :: "+ko_title);
					}
					
					
					// 뉴스 이미지 크롤링
					String img = doc.select("img").attr("src").toString();
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
					String ko_content = BigTransUtil.transNews(contents);

					log.info("한국 번역 : " + ko_content);

					String[] ko_contents_String = ko_content.split("\n");
					List ko_contents = new ArrayList(Arrays.asList(ko_contents_String));

					log.info("한국 번역 리스트 길이 :: " + ko_contents.size());

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

				doc = null;

			}

			team_map = null;
			teams = null;

		}

		distinct = null;

		log.info("The Guardians 뉴스 수집 완료!! 뉴스 개수 :: " + newsList.size());

		// 컬렉션 명 설정
		String colNm = dateUtil.today_year_month + "_The_Guardian";

		log.info("몽고DB 뉴스 입력");
		int res = 0;
		if (newsList.size() > 0) {
			res = newsMongoMapper.newsInsert(newsList, colNm);
		}

		log.info(this.getClass().getName() + ".theGuardianNewsUpdate end!");

		return res;
	}

	@Override
	public List<Map<String, Object>> getMainNews(String team, String news) throws Exception {
		log.info(this.getClass().getName() + ".getMainNews start!");
		
		//collection 이름
		String colNm = dateUtil.today_year_month+news;
		//뉴스개수
		int no = 3;
		
		List<Map<String, Object>> rList = newsMongoMapper.getNews(colNm, no, team);
		
		if(rList.size()<1) {
			colNm = dateUtil.month_ago()+news;
			rList = newsMongoMapper.getNews(colNm, no, team);
		}
		
		if(rList == null) {
			rList = new LinkedList<Map<String, Object>>();
		}
		
		log.info("뉴스 개수 :: "+rList.size());
		
		log.info(this.getClass().getName() + ".getMainNews end!");
		return rList;
	}

}
