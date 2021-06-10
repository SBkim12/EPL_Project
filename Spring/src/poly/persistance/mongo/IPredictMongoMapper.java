package poly.persistance.mongo;

import java.util.Map;

public interface IPredictMongoMapper {

	String PredictDataSave(Map<String, Object> rMap)throws Exception;
}
