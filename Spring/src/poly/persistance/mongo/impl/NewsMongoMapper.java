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

import com.mongodb.client.MongoCollection;

import poly.persistance.mongo.INewsMongoMapper;
import poly.persistance.mongo.comm.AbstractMongoDBComon;


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
}
