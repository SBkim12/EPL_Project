package poly.persistance.mongo;

import java.util.List;
import java.util.Map;

public interface INewsMongoMapper {

	int newsInsert(List<Map<String, Object>> newsList, String news)throws Exception;
	
	List<Map<String, Object>> getNews(String colNm, int i, String team)throws Exception;

	List<Map<String, Object>> getTeamNews(String colNm, String team)throws Exception;
}
