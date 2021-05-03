package poly.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.persistance.mapper.IEPLdataMapper;
import poly.service.IEPLdataService;
import poly.util.CmmUtil;

@Service("EPLdataService")
public class EPLdataService implements IEPLdataService {
	
	@Resource(name="EPLdataMapper")
	private IEPLdataMapper epldataMapper;
	
	//로그생성
	private Logger log = Logger.getLogger(this.getClass());
	
	private String getUrlForJSON(String callUrl) {
		
		log.info(this.getClass().getName() + ".getUrlForJSON start!");
		
		log.info("requested URL: " +callUrl);
		
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		
		// json 결과값 저장
		String json = "";
		
		// SSL 적용된 사이트일 경우, 데이터 증명을 위해 사용
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		
		try {
			//웹 사이트 접속을 위한 URL 파싱
			URL url = new URL(callUrl);
			
			//접속
			urlConn = url.openConnection();
			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
			// 접속화면, 응답을 60초(60 * 1000ms)동안 기다림
			if(urlConn != null) {
				urlConn.setReadTimeout(60*1000);
			}
			
			if(urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8"));
				
				BufferedReader bufferedReader = new BufferedReader(in);
				
				// 주어진 문자 입력 스트림 inputStream에대해 기본 크기의 버퍼를 갖는 개체를 생성
				if(bufferedReader!=null) {
					int cp;
					while((cp = bufferedReader.read()) != -1) {
						sb.append((char)cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		}catch (Exception e){
			throw new RuntimeException("Exception URL:" + callUrl, e);
		}
		
		json = sb.toString(); //json 결과 저장
		log.info("JSON result: " + json);
		
		log.info(this.getClass().getName() + ".getUrlForJSON End !");
		
		return json;
	}
	

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
	
	
}
