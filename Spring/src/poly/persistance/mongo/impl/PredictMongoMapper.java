package poly.persistance.mongo.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
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


	@Override
	public List<Map<String, Object>> GetPredictData(String member_id) throws Exception {
		
		log.info(this.getClass().getName() + ".GetPredictData End!");
		
		List<Map<String, Object>> rList = new LinkedList<Map<String,Object>>();
		
		String colNm = member_id+"_Predict";
		
		log.info("colNm :: " + colNm);
		
		MongoCollection<Document> col = mongodb.getCollection(colNm);
		
		Document query = new Document();

        query.append("point_get", 0L);
		
        FindIterable<Document> rs = col.find(query);
        
        Iterator<Document> cursor = rs.iterator();
        
        while(cursor.hasNext()) {
        	Document doc = cursor.next();
        	
        	if(doc==null) {
        		doc = new Document();
        	}
        	
        	String match_id = CmmUtil.nvl(doc.getString("match_id"));
        	String home = CmmUtil.nvl(doc.getString("home"));
        	String away = CmmUtil.nvl(doc.getString("away"));
        	String home_score = CmmUtil.nvl(doc.getString("home_score"));
        	String away_score = CmmUtil.nvl(doc.getString("away_score"));
        	String home_logo = CmmUtil.nvl(doc.getString("home_logo"));
        	String away_logo = CmmUtil.nvl(doc.getString("away_logo"));
        	String round = CmmUtil.nvl(doc.getString("round"));
        	
        	Map<String, Object> rMap = new LinkedHashMap<String, Object>();
        	
        	rMap.put("match_id", match_id);
        	rMap.put("home", home);
        	rMap.put("away", away);
        	rMap.put("home_score", home_score);
        	rMap.put("away_score", away_score);
        	rMap.put("home_logo", home_logo);
        	rMap.put("away_logo", away_logo);
        	rMap.put("round", round);
        	
        	rList.add(rMap);
        	
        	rMap=null;
        	doc=null;
        }
        
        col = null;
        rs = null;
        cursor = null;
        query = null;
        
        log.info(this.getClass().getName() +".GetPredictData end");
		return rList;
	}


	@Override
	public int UpdatePredictData(Map<String, String> qMap) throws Exception {
		
		log.info(this.getClass().getName() + ".UpdatePredictData start!");
		
		String colNm = qMap.get("member_id")+"_Predict";
		
		log.info("colNm :: " + colNm);
		
		MongoCollection<Document> col = mongodb.getCollection(colNm);
		
		col.updateOne(new Document("match_id", qMap.get("match_id").toString()),new Document("$set", new Document("point_get", Integer.parseInt(qMap.get("point_get")))) );
		
		log.info(this.getClass().getName() + ".UpdatePredictData End!");
		return 1;
	}
	
}
