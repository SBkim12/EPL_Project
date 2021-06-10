package poly.service;

import java.util.List;
import java.util.Map;

import poly.dto.EPLDTO;

public interface IEPLdataService {

	int updateLogo(EPLDTO pDTO)throws Exception;

	List<EPLDTO> updateSeasonRank(String url)throws Exception;

	int updateSeason(String url)throws Exception;

	List<EPLDTO> getEPLteam()throws Exception;

	EPLDTO getTeamLogo(String favorite_team)throws Exception;

	List<Map<String, Object>> getEPLteamPlayer(String team)throws Exception;

	EPLDTO getkoname(String team)throws Exception;

	String presentSeason()throws Exception;

	List<Map<String, String>> getUpcomingGame(String team, String seasonId)throws Exception;

}
