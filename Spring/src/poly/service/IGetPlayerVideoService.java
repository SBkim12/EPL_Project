package poly.service;

import java.util.Map;

import poly.dto.YouTubeDTO;

public interface IGetPlayerVideoService {

	Map<String, Object> GetPlayerVideo(YouTubeDTO pDTO)throws Exception;
	
}
