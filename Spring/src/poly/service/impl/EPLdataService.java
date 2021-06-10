package poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.persistance.mapper.IEPLdataMapper;
import poly.service.IEPLdataService;
import poly.service.impl.comm.AbstractgetUrlFordata;
import poly.util.CmmUtil;
import poly.util.SportsDataUtil;
import poly.util.TranslateUtil;

@Service("EPLdataService")
public class EPLdataService	extends AbstractgetUrlFordata implements IEPLdataService {
	
	@Resource(name="EPLdataMapper")
	private IEPLdataMapper epldataMapper;
	
	private String key = SportsDataUtil.APIKey;
	
	//로그생성
	private Logger log = Logger.getLogger(this.getClass());
	

	@Override
	public int updateLogo(EPLDTO pDTO) throws Exception {
		log.info(this.getClass().getName() + ".getEPLSeason start!");
		
		// JSON 읽은 값을 Controller에 전달하기 위한 결과 변수
		Map<String, Object> rMap = new HashMap<>();
		
		// JSON 결과 받아오기
		String json = getUrlForJSON(CmmUtil.nvl(pDTO.getUrl()));
		
		//String 변수의 문자열을 json 형태의 데이터  구조로 변경하기 위한 객체를 메모리에 올림
		JSONParser parser = new JSONParser();
		
		//String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		Object obj = parser.parse(json);
		
		// 변환된 object 객체를 json 데이터 구조로 변경
		JSONObject jsonObject = (JSONObject) obj;
		
		//요청한 파라미터 가져오기
		JSONArray dataArr = (JSONArray)jsonObject.get("data");
		
		//JSON 배열에 저장된 데이터를 List<EPLdataDTO> 구조로 변경하기 위해 메모리에 올림
		List<EPLDTO> rList = new ArrayList<EPLDTO>();
		
		//각 레코드마다 DTO로 저장
		EPLDTO rDTO = null;
		
		//England 팀의 수
		int hap = 0;
		for(int i =0; i<dataArr.size(); i++) {
			JSONObject result = (JSONObject) dataArr.get(i);
			
			rDTO = new EPLDTO();
			log.info((String)result.toString());
			log.info("team_id :: " + CmmUtil.nvl(result.get("team_id").toString()));
			log.info("name :: " + CmmUtil.nvl(result.get("name").toString()));
			log.info("short_code :: " + CmmUtil.nvl(result.get("short_code").toString()));
			log.info("logo :: " + CmmUtil.nvl(result.get("logo").toString()));
			
			rDTO.setTeam_id(CmmUtil.nvl(result.get("team_id").toString()));
			rDTO.setName(CmmUtil.nvl(result.get("name").toString()));
			rDTO.setShort_code(CmmUtil.nvl(result.get("short_code").toString()));
			rDTO.setLogo(CmmUtil.nvl(result.get("logo").toString()));
			
			int res = epldataMapper.upsertlogo(rDTO);
			if(res==0) {
				log.info("데이터 입력 실패");
			}else {
				hap++;
			}
			rDTO = null;
		}	
		
		log.info(hap+"개의 팀 로고 확보!");
		log.info(this.getClass().getName() + ".getEPLSeason end!");
		
		if(dataArr.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	//최근 시즌으로 순위 업데이트
	@Override
	public List<EPLDTO> updateSeasonRank(String url) throws Exception {
		
		log.info(this.getClass().getName()+ ".UpdateSeasonRank start!");
		
		// JSON 읽은 값을 Controller에 전달하기 위한 결과 변수
		Map<String, Object> rMap = new HashMap<>();

		// JSON 결과 받아오기
		String json = getUrlForJSON(url);

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위한 객체를 메모리에 올림
		JSONParser parser = new JSONParser();

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		Object obj = parser.parse(json);

		// 변환된 object 객체를 json 데이터 구조로 변경
		JSONObject jsonObject = (JSONObject) obj;

		// 요청한 파라미터 가져오기
		JSONArray dataArr = (JSONArray) jsonObject.get("data");
		
		//가장 최근 시즌 가져오기
		int recent_season= 0;
		String recent_season_id="";
		String recent_season_name="";
		for(int i =0; i<dataArr.size(); i++) {
			JSONObject result = (JSONObject) dataArr.get(i);
			
			String[] seasonName = result.get("name").toString().split("\\/");
			int season = Integer.parseInt(seasonName[0]);
			
			if(season>recent_season){
				recent_season=season;
				recent_season_id=result.get("season_id").toString();
				recent_season_name=result.get("name").toString();
			}
		}
//		EPLDTO  sDTO = epldataMapper.presentSeason();
//		
//		String recent_season_name = sDTO.getSeason();
//		String recent_season_id = sDTO.getSeason_id();
		
		log.info("최신 시즌 :: " + recent_season_name);
		log.info("최신 시즌 id :: " + recent_season_id);
		
		url="https://app.sportdataapi.com/api/v1/soccer/standings?apikey="+key+"&season_id="+recent_season_id;
		
		// JSON 결과 받아오기
		json = getUrlForJSON(url);

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위한 객체를 메모리에 올림
		parser = new JSONParser();

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		obj = parser.parse(json);

		// 변환된 object 객체를 json 데이터 구조로 변경
		jsonObject = (JSONObject) obj;

		// 요청한 파라미터 가져오기
		JSONObject data = (JSONObject) jsonObject.get("data");
		
		JSONArray teams = (JSONArray) data.get("standings");
		
		// JSON 배열에 저장된 데이터를 List<EPLdataDTO> 구조로 변경하기 위해 메모리에 올림
		List<EPLDTO> rList = new ArrayList<EPLDTO>();

		// 각 레코드마다 DTO로 저장
		EPLDTO rDTO = null;
		
		for(int i =0; i<teams.size(); i++) {
			JSONObject result = (JSONObject) teams.get(i);
			
			rDTO = new EPLDTO();
			
			int recent_rank = i+1;
			String team_id = result.get("team_id").toString();
			
			int points = Integer.parseInt(result.get("points").toString());
			
			JSONObject overall = (JSONObject) result.get("overall");
			int recent_win = Integer.parseInt(overall.get("won").toString());
			int recent_draw = Integer.parseInt(overall.get("draw").toString());
			int recent_lost = Integer.parseInt(overall.get("lost").toString());
			int goals_scored = Integer.parseInt(overall.get("goals_scored").toString());
			int goals_agianst = Integer.parseInt(overall.get("goals_against").toString());
			
			JSONObject home = (JSONObject) result.get("home");
			int home_win = Integer.parseInt(home.get("won").toString());
			int home_draw = Integer.parseInt(home.get("draw").toString());
			int home_lost = Integer.parseInt(home.get("lost").toString());
			
			rDTO.setTeam_id(team_id);
			
			EPLDTO qDTO = epldataMapper.infoTeamLogoName(rDTO);
			
			String ko_name =TranslateUtil.trans(qDTO.getName());

			log.info("TEAM_NAME :: "+ko_name);
			
			rDTO.setTeam_id(team_id);
			rDTO.setTeam_name(qDTO.getName());
			rDTO.setLogo(qDTO.getLogo());
			rDTO.setKo_name(ko_name);
			rDTO.setRecent_rank(recent_rank);
			rDTO.setRecent_won(recent_win);
			rDTO.setRecent_draw(recent_draw);
			rDTO.setRecent_lost(recent_lost);
			rDTO.setRecent_points(points);
			rDTO.setGoals_scored(goals_scored);
			rDTO.setGoals_against(goals_agianst);
			rDTO.setHome_win(home_win);
			rDTO.setHome_draw(home_draw);
			rDTO.setHome_lost(home_lost);
			rDTO.setSeason(recent_season_name);
			
			int res = epldataMapper.upsertEPLdata(rDTO);
			if(res==0) {
				log.info("데이터 입력 실패");
			}else {
				log.info("데이터 입력 성공");
			}
			rList.add(rDTO);
			rDTO = null;
		}
		log.info(this.getClass().getName()+ ".UpdateSeasonRank End!");
		return rList;
	}

	//진행중인 시즌DB 업데이트
	@Override
	public int updateSeason(String url) throws Exception {
		
		log.info(this.getClass().getName()+ ".UpdateSeason start!");
		
		// JSON 읽은 값을 Controller에 전달하기 위한 결과 변수
		Map<String, Object> rMap = new HashMap<>();

		// JSON 결과 받아오기
		String json = getUrlForJSON(url);

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위한 객체를 메모리에 올림
		JSONParser parser = new JSONParser();

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		Object obj = parser.parse(json);

		// 변환된 object 객체를 json 데이터 구조로 변경
		JSONObject jsonObject = (JSONObject) obj;

		// 요청한 파라미터 가져오기
		JSONArray dataArr = (JSONArray) jsonObject.get("data");
		
		//진행중인 시즌이 있다면 update or insert
		for(int i =0; i<dataArr.size(); i++) {
			JSONObject result = (JSONObject) dataArr.get(i);
			
			String season = result.get("name").toString();
			String season_id = result.get("season_id").toString();
			int is_current = Integer.parseInt(result.get("is_current").toString());
			String start_date = result.get("start_date").toString();
			String end_date = result.get("end_date").toString();
			
			if(is_current>0) {
				EPLDTO rDTO = new EPLDTO();
				
				rDTO.setIs_current(is_current);
				rDTO.setSeason(season);
				rDTO.setStart_date(start_date);
				rDTO.setEnd_date(end_date);
				rDTO.setSeason_id(season_id);
				
				int res = epldataMapper.updateSeason(rDTO);
				if(res==0) {
					log.info("데이터 입력 실패");
				}else {
					log.info("데이터 입력 성공");
				}
				break;
			}
		}
		
		log.info(this.getClass().getName()+ ".UpdateSeason end!");
		
		return 0;
	}


	//EPL1부리그에 참여 중인 20팀 목록 불러오기
	@Override
	public List<EPLDTO> getEPLteam() throws Exception {
		log.info(this.getClass().getName() + ".getEPLteam start");
		
		//현재시즌 EPL팀 정보 가져오기
		List<EPLDTO> rList = epldataMapper.getEPLTeam();

		return rList;
	}

	@Override
	public EPLDTO getTeamLogo(String favorite_team) throws Exception {
		log.info(this.getClass().getName() + ".getTeamLogo start");
		
		//favorite_Team의 로고 가져오기
		EPLDTO EPL = epldataMapper.getTeamLogo(favorite_team);
		
		log.info(this.getClass().getName() + ".getTeamLogo end!");
		return EPL;
	}

	@Override
	public List<Map<String, Object>> getEPLteamPlayer(String team) throws Exception {
		
		log.info(this.getClass().getName() +".getEPLteamPlayer start");
		
		//시즌 아이디 구하기
		String season_get_url = "https://www.premierleague.com/stats";
		Document doc = Jsoup.connect(season_get_url).timeout(30000).get();
		String season_url = doc.select("#mainContent > div.hasSideNav > nav > div > ul > li:nth-child(2) > a").attr("href").toString();
		String season = season_url.substring(season_url.lastIndexOf("se="));
		
		String url = "https://www.premierleague.com/clubs";
		
		log.info(team);
		//팀 목록 페이지 접속
		doc = Jsoup.connect(url).timeout(30000).get();
		
		url = doc.select("div.indexSection > div > ul > li > a:contains("+team+")").get(0).attr("href").toString();
		
		url ="https://www.premierleague.com"+url.replace("overview", "squad");
		
		log.info(url);
		
		//팀 페이지 접속
		doc = Jsoup.connect(url).timeout(30000).get();
		
		String team_url = doc.select("#mainContent > header > div.wrapper > div > div > div.clubDetails > div.website > a").attr("href").toString();
		log.info(team_url);
		
		Elements elem = doc.select("#mainContent > div.wrapper.col-12 > div > ul > li");
		Iterator<Element> it = elem.iterator();
		
		//Map를 담을 리스트
		List<Map<String, Object>> players = new LinkedList<Map<String, Object>>();
		
		while (it.hasNext()) {
			
			//정보 담을 Map
			Map<String, Object> pMap = new LinkedHashMap<String, Object>();
			
			Element player = it.next();
			
			
			String player_url ="https://www.premierleague.com"+player.select("a").attr("href").toString();
			player_url = player_url.replace("overview", "stats");
			player_url += "?"+season;
			
			String player_id = player.select("img").attr("data-player").toString();
			String player_size = player.select("img").attr("data-size").toString();
			String player_img = "https://resources.premierleague.com/premierleague/photos/players/"+player_size+"/"+player_id+".png";
			
			String player_no = player.select("span.number").text().trim();
			String player_name = player.select("h4.name").text().trim();
			String player_position = player.select("span.position").text().trim();
			String player_country = player.select("span.playerCountry").text().trim();
			
			log.info("player_url :: " + player_url);
			log.info("player_img :: " + player_img);
			log.info("player_no :: " + player_no);
			log.info("player_name :: " + player_name);
			log.info("player_position :: " + player_position);
			log.info("player_country :: " + player_country);
			
			pMap.put("player_url" , player_url);
			pMap.put("player_img", player_img);
			pMap.put("player_no", player_no);
			pMap.put("player_name", player_name);
			pMap.put("player_position", player_position);

			Elements infoes = player.select("dl");
			
			Iterator<Element> Infoes = infoes.iterator();
			int i=0;
			while(Infoes.hasNext()) {
				Element info = Infoes.next();
				
				String name = info.select("dt.label").text().replaceAll(" ", "_").trim();
				
				String value = info.select("dd.info").text().trim();
				
				log.info(name + " :: " +value);
				pMap.put(name, value);
				i++;
				if(i>=4) {
					break;
				}
			}
			
			log.info("---------------------------------------------------------------------------------");
			players.add(pMap);
			
			pMap=null;
		}
		
		//Elements elem = doc.select("#mainContent > div.wrapper.col-12 > div > ul > li");
		//List<Map<String, Object>> players = new LinkedList<Map<String, Object>>();
		
		doc =null;

		log.info(this.getClass().getName() + ".getEPLteamPlayer End!");
		return players;
	}

	@Override
	public EPLDTO getkoname(String team) throws Exception {
		log.info(this.getClass().getName() + ".getkoname start!");

		EPLDTO qDTO = epldataMapper.getKoname(team);

		log.info(this.getClass().getName() + ".getkoname start!");
		return qDTO;
	}

	@Override
	public String presentSeason() throws Exception {
		EPLDTO  qDTO = epldataMapper.presentSeason();
		
		String recent_season_id = qDTO.getSeason_id();
		
		return recent_season_id;
	}


	@Override
	public List<Map<String, String>> getUpcomingGame(String team, String seasonId) throws Exception {
		
		//String date = poly.util.dateUtil.now();
		
		String date = "2021-05-01";
		
		String url="https://app.sportdataapi.com/api/v1/soccer/matches?apikey="+key+"&season_id="+seasonId+"&date_from="+date;
		// JSON 결과 받아오기
		String json = getUrlForJSON(url);

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위한 객체를 메모리에 올림
		JSONParser parser = new JSONParser();

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		Object obj = parser.parse(json);

		// 변환된 object 객체를 json 데이터 구조로 변경
		JSONObject jsonObject = (JSONObject) obj;
				
		// 요청한 파라미터 가져오기
		JSONArray dataArr = (JSONArray) jsonObject.get("data");
		
		//경기 정보 담을 리스트
		List<Map<String, String>> matches = new LinkedList<Map<String, String>>();
		Map<String, String> pMap = new HashMap<String, String>();
		
		int limit = 3;
		for(int i =0; i<dataArr.size(); i++) {
			pMap = new HashMap<String, String>();
					
			JSONObject match = (JSONObject) dataArr.get(i);
			
			JSONObject homes = (JSONObject) match.get("home_team");
			JSONObject aways = (JSONObject) match.get("away_team");
			
			String home = (String) homes.get("name");
			
			String away = (String) aways.get("name");
			
			
			//선택 팀 아닐경우 패스
			if( !( team.equals(home) || team.equals(away) ) ){
				continue;
			}
			log.info(home);
			log.info(away);
			
			String home_logo = (String) homes.get("logo");
			log.info(home_logo);
			String away_logo = (String) aways.get("logo");
			log.info(away_logo);
			String match_id =  match.get("match_id").toString();
			log.info(match_id);
			String status_code = match.get("status_code").toString();
			log.info(status_code);
			String minute = match.get("minute").toString();
			log.info(minute);
			String match_start = match.get("match_start").toString();
			log.info(match_start);
			
			//라운드 이름
			JSONObject rounds = (JSONObject) match.get("round");
			String round = (String) rounds.get("name").toString();
			log.info(round);
			
			JSONObject stats = (JSONObject) match.get("stats");
			String home_score = (String) stats.get("home_score").toString();
			String away_score = (String) stats.get("away_score").toString();
			
			pMap.put("home", home);
			pMap.put("away", away);
			pMap.put("home_logo", home_logo);
			pMap.put("away_logo", away_logo);
			pMap.put("match_id", match_id);
			pMap.put("status_code", status_code);
			pMap.put("minute", minute);
			pMap.put("match_start", match_start);
			pMap.put("round", round);
			pMap.put("home_score", home_score);
			pMap.put("away_score", home_score);
			
			matches.add(pMap);
			
			limit--;
			if(limit==0) {
				break;
			}
			pMap = null;
		}
		return matches;
	}


}
