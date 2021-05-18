package poly.persistance.mongo;

import java.util.List;
import java.util.Map;

public interface INewsMongoMapper {

	int newsInsert(List<Map<String, Object>> newsList, String news)throws Exception;

}
