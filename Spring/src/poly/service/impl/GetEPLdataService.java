package poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.service.IGetEPLdataService;
import poly.service.impl.comm.AbstractgetUrlFordata;
import poly.util.CmmUtil;

@Service("GetEPLdataService")
public class GetEPLdataService extends AbstractgetUrlFordata  implements IGetEPLdataService {
	
	//로그생성
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Map<String, Object> GetEPLSeason(EPLDTO pDTO) throws Exception {
		
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
		
		for(int i =0; i<dataArr.size(); i++) {
			JSONObject result = (JSONObject) dataArr.get(i);
			
			rDTO = new EPLDTO();
			log.info((String)result.toString());
			log.info("season_id :: " + CmmUtil.nvl(result.get("season_id").toString()));
			log.info("season_name :: " + CmmUtil.nvl(result.get("name").toString()));
			log.info("start_date :: " + CmmUtil.nvl(result.get("start_date").toString()));
			log.info("end_date :: " + CmmUtil.nvl(result.get("end_date").toString()));
			
			rDTO.setSeason_id(CmmUtil.nvl(result.get("season_id").toString()));
			rDTO.setSeason(CmmUtil.nvl(result.get("name").toString()));
			rDTO.setStart_date(CmmUtil.nvl(result.get("start_date").toString()));
			rDTO.setEnd_date(CmmUtil.nvl(result.get("end_date").toString()));
			
			//저장된 DTO를 리스트에 저장
			rList.add(rDTO);
		}	
		//Controller에 저장할 데이터 저장
		rMap.put("res", rList);
		
		log.info(this.getClass().getName() + ".getEPLSeason end!");
		
		return rMap;
	}
	
	
}
