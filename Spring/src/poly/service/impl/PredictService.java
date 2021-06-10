package poly.service.impl;

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

}
