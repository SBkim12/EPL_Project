  
package poly.service;

import java.util.List;

import poly.dto.YouTubeDTO;

public interface IGetYoutubeDataService {

	List<YouTubeDTO> GetTeamVideo(String url)throws Exception;
	
}