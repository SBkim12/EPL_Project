package poly.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.service.IPredictService;

@Controller
public class PredictController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "PredictService")
	IPredictService predictService;


	@RequestMapping(value = "PredictDataSave")
	@ResponseBody
	public String PredictDataSave(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("login start");
		
		String res = "0";
		String member_id = (String) session.getAttribute("member_id");
		String match_id = request.getParameter("match_id");
		String home_score = request.getParameter("home_score");
		String away_score = request.getParameter("away_score");
		String home = request.getParameter("home");
		String away = request.getParameter("away");
		String home_logo = request.getParameter("home_logo");
		String away_logo = request.getParameter("away_logo");
		
		log.info(member_id);
		log.info(match_id);
		log.info(home_score);
		log.info(away_score);
		
		
		if(member_id ==null || match_id ==null || home_score == null || away_score == null) {
			return res;
		}
		
		Map<String, Object> rMap = new HashMap<>();
		
		rMap.put("member_id", member_id);
		rMap.put("match_id", match_id);
		rMap.put("home_score", home_score);
		rMap.put("away_score", away_score);
		rMap.put("home", home);
		rMap.put("away", away);
		rMap.put("home_logo", home_logo);
		rMap.put("away_logo", away_logo);
		rMap.put("point_get", 0);
		
		res = predictService.PredictDataSave(rMap);
		
		return res;
	}
}
