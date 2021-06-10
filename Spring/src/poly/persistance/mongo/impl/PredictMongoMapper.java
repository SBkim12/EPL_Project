package poly.persistance.mongo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;

import poly.persistance.mongo.IPredictMongoMapper;
import poly.persistance.mongo.comm.AbstractMongoDBComon;
import poly.util.CmmUtil;


@Component("PredictMongoMapper")
public class PredictMongoMapper extends AbstractMongoDBComon implements IPredictMongoMapper{

	@Autowired
	private MongoTemplate mongodb;
	
	private Logger log = Logger.getLogger(this.getClass());


	@Override
	public String PredictDataSave(Map<String, Object> rMap)throws Exception {
		
		log.info(this.getClass().getName() + ".PredictDataSave Start!");

		String res = "0";
		
		String colNm = rMap.get("member_id").toString().trim();
		colNm += "_Predict";
		
		rMap.remove("member_id");
		
		// 데이터를 저장할 컬렉션 생성
		super.createCollection(colNm, "predict_user");
		
		// 저장할 컬렉션 객체 생성
		MongoCollection<Document> col = mongodb.getCollection(colNm);//복합키는 좀 찾아봐야 될거같음
		col.replaceOne(new Document("match_id", rMap.get("match_id").toString()),new Document(rMap), new ReplaceOptions().upsert(true));
		
		res="1";
		
		return res;
	}
	
}
