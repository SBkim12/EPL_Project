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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import poly.dto.YouTubeDTO;
import poly.service.IGetPlayerVideoService;
import poly.service.impl.comm.AbstractgetUrlForJson;
import poly.util.CmmUtil;

@Service("GetPlayerVideoService")
public class GetPlayerVideoService extends AbstractgetUrlForJson  implements IGetPlayerVideoService {

	// 로그생성
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Map<String, Object> GetPlayerVideo(YouTubeDTO pDTO) throws Exception {
		log.info(this.getClass().getName() + ".getPlayerVideo start!");

		// JSON 읽은 값을 Controller에 전달하기 위한 결과 변수
		Map<String, Object> rMap = new HashMap<>();

		// JSON 결과 받아오기
		String json = getUrlForJSON(CmmUtil.nvl(pDTO.getUrl()));

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위한 객체를 메모리에 올림
		JSONParser parser = new JSONParser();

		// String 변수의 문자열을 json 형태의 데이터 구조로 변경하기 위해 자바 최상위 Object 변환
		Object obj = parser.parse(json);

		// 변환된 object 객체를 json 데이터 구조로 변경
		JSONObject jsonObject = (JSONObject) obj;
		
		// 요청한 파라미터 가져오기
		JSONArray dataArr = (JSONArray) jsonObject.get("items");

		// JSON 배열에 저장된 데이터를 List<EPLdataDTO> 구조로 변경하기 위해 메모리에 올림
		List<YouTubeDTO> rList = new ArrayList<YouTubeDTO>();

		// 각 레코드마다 DTO로 저장
		YouTubeDTO rDTO = null;

		for (int i = 0; i < dataArr.size(); i++) {
			JSONObject result = (JSONObject) dataArr.get(i);
			log.info((String) result.toString());
			
			rDTO = new YouTubeDTO();
			
			JSONObject id = (JSONObject) result.get("id");
			
			log.info("video_id :: " + CmmUtil.nvl(id.get("videoId").toString()) );
			rDTO.setVideo_id((CmmUtil.nvl(id.get("videoId").toString())));
			
			JSONObject snip = (JSONObject) result.get("snippet");
			
			log.info("video_title ::" + CmmUtil.nvl(snip.get("title").toString()));
			rDTO.setVideo_title(CmmUtil.nvl(snip.get("title").toString()));
			
			JSONObject thumb = (JSONObject) snip.get("thumbnails");
			JSONObject medium = (JSONObject) thumb.get("medium");
			
			log.info("video_thumbnals :: " + CmmUtil.nvl(medium.get("url").toString()));
			rDTO.setVideo_thumbnails(CmmUtil.nvl(medium.get("url").toString()));
			
			// 저장된 DTO를 리스트에 저장
			rList.add(rDTO);
		}
		// Controller에 저장할 데이터 저장
		rMap.put("res", rList);

		log.info(this.getClass().getName() + ".getPlayerVideo end!");

		return rMap;
	}

}
