package poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.persistance.mapper.IEPLdataMapper;
import poly.service.IEPLdataService;
import poly.service.impl.comm.AbstractgetUrlForJson;
import poly.util.CmmUtil;
import poly.util.SportsDataUtil;
import poly.util.TranslateUtil;

@Service("EPLdataService")
public class EPLdataService	extends AbstractgetUrlForJson implements IEPLdataService {
	
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
			rDTO.setSeason_name(recent_season_name);
			
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
			
			String season_name = result.get("name").toString();
			int is_current = Integer.parseInt(result.get("is_current").toString());
			String start_date = result.get("start_date").toString();
			String end_date = result.get("end_date").toString();
			
			if(is_current>0) {
				EPLDTO rDTO = new EPLDTO();
				
				rDTO.setIs_current(is_current);
				rDTO.setSeason_name(season_name);
				rDTO.setStart_date(start_date);
				rDTO.setEnd_date(end_date);
				
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
		log.info(this.getClass().getName() + ".PresentEPLteam start");
		
		EPLDTO qDTO = new EPLDTO();
		
		qDTO = epldataMapper.presentSeason();

		List<EPLDTO> rList = epldataMapper.getEPLTeams(qDTO);

		qDTO = null;

		return rList;
	}
}
