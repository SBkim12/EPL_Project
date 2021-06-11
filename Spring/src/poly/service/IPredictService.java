package poly.service;

import java.util.List;
import java.util.Map;

public interface IPredictService {

	String PredictDataSave(Map<String, Object> rMap)throws Exception;

	List<Map<String, Object>> GetPredictData(String member_id)throws Exception;

	int UpdatePredictData(Map<String, String> qMap)throws Exception;

}
