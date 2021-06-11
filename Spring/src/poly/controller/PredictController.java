package poly.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

import poly.dto.MemberDTO;
import poly.service.IEPLdataService;
import poly.service.IPredictService;
import poly.service.IUserService;
import poly.util.CmmUtil;

@Controller
public class PredictController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	IUserService userService;
	
	@Resource(name = "PredictService")
	IPredictService predictService;
	
	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;


	@RequestMapping(value = "PredictDataSave")
	@ResponseBody
	public String PredictDataSave(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("PredictDataSave start");
		
		String res = "0";
		String member_id = (String) session.getAttribute("member_id");
		String match_id = request.getParameter("match_id");
		String home_score = request.getParameter("home_score");
		String away_score = request.getParameter("away_score");
		String home = request.getParameter("home");
		String away = request.getParameter("away");
		String home_logo = request.getParameter("home_logo");
		String away_logo = request.getParameter("away_logo");
		String round = request.getParameter("round");
		
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
		rMap.put("round", round);
		
		res = predictService.PredictDataSave(rMap);
		return res;
	}
	
	@RequestMapping(value = "GetPredictData")
	@ResponseBody
	public List<Map<String, Object>> GetPredictData(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info(this.getClass().getName() + ".GetPredictData start");
		
		String member_id = (String) CmmUtil.nvl(request.getParameter("member_id"));
		
		List<Map<String, Object>> rList = new LinkedList<Map<String, Object>>();
		
		rList = predictService.GetPredictData(member_id);
		
		if(rList == null) {
			rList = new LinkedList<Map<String, Object>>();
		}
		
		
		log.info(this.getClass().getName() +".GetPredictData end");
		return rList;
	}
	
	@RequestMapping(value = "PredictCheck")
	@ResponseBody
	public Map<String, String> PredictCheck(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info(this.getClass().getName() + ".PredictCheck start");
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		String member_id = (String) session.getAttribute("member_id");
		int member_point = Integer.parseInt((String)session.getAttribute("member_point"));
		if(member_id == null || member_id.equals("")) {
			resultMap.put("res", "0");
			return resultMap;
		}
		
		String match_id = request.getParameter("match_id");
		int home_score = Integer.parseInt(request.getParameter("home_score"));
		int away_score =Integer.parseInt(request.getParameter("away_score"));
		
		String user_predict = "";
		if(home_score>away_score) {
			user_predict = "home";
		}else if(home_score==away_score) {
			user_predict = "draw";
		}else {
			user_predict = "away";
		}
		
		Map<String, String> rMap = epldataService.getMatchData(match_id);
		
		if(rMap == null) {
			log.info("경기가 완료되지 않았거나 잘못된 경기 아이디");
			resultMap.put("res", "1");
			return resultMap;
		}
		
		int result_home = Integer.parseInt(rMap.get("home_score"));
		int result_away = Integer.parseInt(rMap.get("away_score"));
		String result_win = "";
		if(result_home>result_away) {
			result_win = "home";
		}else if(result_home==result_away) {
			result_win = "draw";
		}else {
			result_win = "away";
		}
		
		int res =0;
		
		if(result_home==home_score) {
			res+=20;
		}
		
		if(result_away==away_score) {
			res+=20;
		}
		
		if(result_win.equals(user_predict)) {
			res+=50;
		}
		
		if(res==90) {
			res+=10;
		}
		
		int point_get = res;
		if(point_get==0) {
			point_get=-1;
		}
		
		Map<String,String> qMap = new HashMap<String, String>();
		qMap.put("member_id", member_id);
		qMap.put("point_get", Integer.toString(point_get));
		qMap.put("match_id", match_id);
		
			
		//mongodb 예측 데이터 업데이트
		int result1 = predictService.UpdatePredictData(qMap);
		
		if(result1!=1) {
			log.info("몽고 DB 업데이트 오류");
			resultMap.put("res", "2");
			return resultMap;
		}
		
		MemberDTO uDTO = new MemberDTO();
		member_point += res;
		uDTO.setMember_id(member_id);
		uDTO.setMember_point(Integer.toString(member_point));
		
		//유저 데이터 업데이트
		int result2 = userService.UserPointUpdate(uDTO);
		if(result2!=1) {
			log.info("유저 데이터 업데이트 오류");
			resultMap.put("res", "2");
			return resultMap;
		}
		
		session.setAttribute("member_point", Integer.toString(member_point));
		
		resultMap.put("res", "3");
		resultMap.put("point", Integer.toString(res));
		log.info(this.getClass().getName() +".PredictCheck end");
		return resultMap;
	}
}
