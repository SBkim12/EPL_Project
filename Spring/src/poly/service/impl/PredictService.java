package poly.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import poly.persistance.mongo.IPredictMongoMapper;
import poly.service.IPredictService;

@Service("PredictService")
public class PredictService implements IPredictService{
	
	@Resource(name = "PredictMongoMapper")
	private IPredictMongoMapper predictMongoMapper;
	
	@Override
	public String PredictDataSave(Map<String, Object> rMap) throws Exception {
		
		String res = predictMongoMapper.PredictDataSave(rMap);
		
		return res;
	}

	@Override
	public List<Map<String, Object>> GetPredictData(String member_id) throws Exception {
		
		List<Map<String, Object>> rList = predictMongoMapper.GetPredictData(member_id);
		
		return rList;
	}

	@Override
	public int UpdatePredictData(Map<String, String> qMap) throws Exception {

		int res = predictMongoMapper.UpdatePredictData(qMap);
		
		return res;
	}

}
