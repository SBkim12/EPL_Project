package poly.persistance.mongo.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

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
			
			//몽고 DB에서 url찾으면 continue;
			
			
			// 레코드 한개씩 저장하기
			col.insertOne(new Document(pMap));
			
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
		
		MongoCollection<Document> rCol = mongodb.getCollection(colNm);
		
		
		//MongoDB 조회 쿼리
		 BasicDBObject query = new BasicDBObject();
		 
         query.put("teams", new BasicDBObject()
                 .append("$elemMatch", new BasicDBObject()
                         .append("team_name", Pattern.compile(team))
                 )
         );
         
         BasicDBObject projection = new BasicDBObject();

         projection.put("_id", 0.0);
         projection.put("url", 1.0);
         projection.put("ko_title", 1.0);
         projection.put("date", 1.0);
         projection.put("img", 1.0);
         projection.put("ko_contents", 1.0);
         
         BasicDBObject sort = new BasicDBObject();

         sort.put("date", -1);
         
         FindIterable<Document> rs = rCol.find(query).projection(projection).sort(sort).limit(limit);
         Iterator<Document> cursor = rs.iterator();
         while (cursor.hasNext()) {
				Document doc = cursor.next();

				if (doc == null) {
					doc = new Document();
				}

				String url = CmmUtil.nvl(doc.getString("url"));
				String ko_title = CmmUtil.nvl(doc.getString("ko_title"));
				String date = CmmUtil.nvl(doc.getString("date"));
				String img = CmmUtil.nvl(doc.getString("img"));
             
				Map<String, Object> rMap = new LinkedHashMap<String, Object>();
				
				rMap.put("url", url);
				rMap.put("ko_title", ko_title);
				rMap.put("date", date);
				rMap.put("img", img);
				
				rList.add(rMap);
				
				rMap = null;
				doc = null;
         }
		
		cursor = null;
		rCol = null;
		projection = null;
		
		log.info(this.getClass().getName() + ".getNews End!");
		return rList;
	}
	
}
