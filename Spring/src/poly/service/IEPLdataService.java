package poly.service;

import java.util.List;

import poly.dto.EPLDTO;

public interface IEPLdataService {

	int updateLogo(EPLDTO pDTO)throws Exception;

	List<EPLDTO> updateSeasonRank(String url)throws Exception;

	int updateSeason(String url)throws Exception;

	List<EPLDTO> getEPLteam()throws Exception;

	EPLDTO getTeamLogo(String favorite_team)throws Exception;

}
