package poly.service;

import java.util.Map;

import poly.dto.EPLDTO;

public interface IGetEPLdataService {
	
	Map<String, Object> GetEPLSeason(EPLDTO pDTO)throws Exception;
}
