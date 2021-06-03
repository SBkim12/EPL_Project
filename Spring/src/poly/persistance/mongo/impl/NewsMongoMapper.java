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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;

import poly.persistance.mongo.INewsMongoMapper;
import poly.persistance.mongo.comm.AbstractMongoDBComon;
import poly.util.CmmUtil;


@Component("NewsMongoMapper")
public class NewsMongoMapper extends AbstractMongoDBComon implements INewsMongoMapper{

	@Autowired
	private MongoTemplate mongodb;
	
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public int newsInsert(List<Map<String, Object>> newsList, String colNm) throws Exception {
		log.info(this.getClass().getName() + ".insertNews Start!");

		int res = 0;
		
		if (newsList == null) {
			newsList = new LinkedList<Map<String, Object>>();
		}
		
		
		// 데이터를 저장할 컬렉션 생성
		super.createCollection(colNm, "collect_month");
		
		// 저장할 컬렉션 객체 생성
		MongoCollection<Document> col = mongodb.getCollection(colNm);

		Iterator<Map<String, Object>> it = newsList.iterator();

		while (it.hasNext()) {
			Map<String, Object> pMap = it.next();

			if (pMap == null) {
				continue;
			}

			// 레코드 한개씩 저장하기
			/*
			 * col.updateOne(new Document("$url", pMap.get("url").toString()),new
			 * Document(pMap), new UpdateOptions().upsert(true));
			 */			
			col.replaceOne(new Document("url", pMap.get("url").toString()),new Document(pMap), new ReplaceOptions().upsert(true));
			pMap=null;
		}

		col = null;

		res = 1;

		log.info(this.getClass().getName() + ".insertSong End!");
		
		return res;
	}
	
	@Override
	public List<Map<String, Object>> getNews(String colNm, int limit, String team) throws Exception {
		
		log.info(this.getClass().getName() + ".getNews End!");
		
		List<Map<String, Object>> rList = new LinkedList<Map<String,Object>>();
		
		
		
		//몽고DB 조회 쿼리
		List<? extends Bson> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("teams.team_name", team)
                        ), 
                new BasicDBObject()
                        .append("$project", new BasicDBObject()
                                .append("_id", 0.0)
                                .append("url", 1.0)
                                .append("ko_title", 1.0)
                                .append("date", 1.0)
                                .append("img", 1.0)
                                .append("ko_contents", 1.0)
                        ), 
                new BasicDBObject()
                        .append("$sort", new BasicDBObject()
                                .append("date", -1.0)
                        ), 
                new BasicDBObject()
                        .append("$limit", limit)
        );
		
		MongoCollection<Document> col = mongodb.getCollection(colNm);
		AggregateIterable<Document> rs = col.aggregate(pipeline).allowDiskUse(true);
		Iterator<Document> cursor = rs.iterator();
		
         while (cursor.hasNext()) {
				Document doc = cursor.next();

				if (doc == null) {
					doc = new Document();
				}
				
				List<String> list = new ArrayList<>();
				list.add("abc");
				
				String url = CmmUtil.nvl(doc.getString("url"));
				String ko_title = CmmUtil.nvl(doc.getString("ko_title"));
				String date = CmmUtil.nvl(doc.getString("date"));
				String img = CmmUtil.nvl(doc.getString("img"));
				List<String> ko_contents = (List<String>)doc.get("ko_contents");
				
				Map<String, Object> rMap = new LinkedHashMap<String, Object>();
				
				rMap.put("url", url);
				rMap.put("ko_title", ko_title);
				rMap.put("date", date);
				rMap.put("img", img);
				rMap.put("ko_contents", ko_contents);
				
				rList.add(rMap);
				
				rMap = null;
				doc = null;
         }
		
		cursor = null;
		rs = null;
		col = null;
		pipeline= null;
		
		log.info(this.getClass().getName() + ".getNews End!");
		return rList;
	}

	@Override
	public List<Map<String, Object>> getTeamNews(String colNm, String team) throws Exception {
log.info(this.getClass().getName() + ".getNews End!");
		
		List<Map<String, Object>> rList = new LinkedList<Map<String,Object>>();
		
		
		
		//몽고DB 조회 쿼리
		List<? extends Bson> pipeline = Arrays.asList(
                new BasicDBObject()
                        .append("$match", new BasicDBObject()
                                .append("teams.team_name", team)
                        ), 
                new BasicDBObject()
                        .append("$project", new BasicDBObject()
                                .append("_id", 0.0)
                                .append("url", 1.0)
                                .append("ko_title", 1.0)
                                .append("date", 1.0)
                                .append("img", 1.0)
                                .append("ko_contents", 1.0)
                        ), 
                new BasicDBObject()
                        .append("$sort", new BasicDBObject()
                                .append("date", -1.0)
                        )
        );
		
		MongoCollection<Document> col = mongodb.getCollection(colNm);
		AggregateIterable<Document> rs = col.aggregate(pipeline).allowDiskUse(true);
		Iterator<Document> cursor = rs.iterator();
		
         while (cursor.hasNext()) {
				Document doc = cursor.next();

				if (doc == null) {
					doc = new Document();
				}
				
				List<String> list = new ArrayList<>();
				list.add("abc");
				
				String url = CmmUtil.nvl(doc.getString("url"));
				String ko_title = CmmUtil.nvl(doc.getString("ko_title"));
				String date = CmmUtil.nvl(doc.getString("date"));
				String img = CmmUtil.nvl(doc.getString("img"));
				List<String> ko_contents = (List<String>)doc.get("ko_contents");
				
				Map<String, Object> rMap = new LinkedHashMap<String, Object>();
				
				rMap.put("url", url);
				rMap.put("ko_title", ko_title);
				rMap.put("date", date);
				rMap.put("img", img);
				rMap.put("ko_contents", ko_contents);
				
				rList.add(rMap);
				
				rMap = null;
				doc = null;
         }
		
		cursor = null;
		rs = null;
		col = null;
		pipeline= null;
		
		log.info(this.getClass().getName() + ".getNews End!");
		return rList;
	}
	
}
