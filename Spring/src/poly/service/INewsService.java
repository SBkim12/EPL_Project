package poly.service;

import java.util.List;
import java.util.Map;

import poly.dto.EPLDTO;

public interface INewsService {

	int skySportsNewsUpdate(List<EPLDTO> rList)throws Exception;

	int theGuardianNewsUpdate()throws Exception;

	List<Map<String, Object>> getMainNews(String team, String news)throws Exception;

}
