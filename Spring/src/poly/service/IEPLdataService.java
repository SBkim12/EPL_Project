package poly.service;

import java.util.List;

import poly.dto.EPLDTO;

public interface IEPLdataService {

	int updateLogo(EPLDTO pDTO)throws Exception;

	List<EPLDTO> updateSeasonRank(String url)throws Exception;
	
}
