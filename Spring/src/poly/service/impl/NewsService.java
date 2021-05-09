package poly.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.EPLDTO;
import poly.service.INewsService;
import poly.service.impl.comm.AbstractgetUrlForJson;

@Service("NewsService")
public class NewsService extends AbstractgetUrlForJson  implements INewsService{

	private Logger log = Logger.getLogger(this.getClass());
	
	
	//skySports뉴스 크롤링 및 데이터 업데이트
	@Override
	public int skySportsNewsUpdate(List<EPLDTO> rList) throws Exception {
		
		EPLDTO pDTO = new EPLDTO();
		for(int i=0; i<rList.size(); i++) {
			pDTO = rList.get(i);
			
			//팀 이름을 활용하여 url 형식 만들기
			String team = pDTO.getTeam_name().trim();
			if(team.endsWith("FC")){
				team = team.substring(0,team.lastIndexOf("FC")-1).trim();
			}
			team = team.replaceAll(" ", "-").replace("&", "and");
			team = team.concat("-news").toLowerCase();
			
			String url = "https://www.skysports.com/"+team;
			log.info("팀이름을 활용한 url 형식 :: " + url);
			
			
			
			pDTO = null;
		}
		
		
		return 0;
	}

}
