package poly.service;

import java.util.List;

import poly.dto.EPLDTO;

public interface INewsService {

	int skySportsNewsUpdate(List<EPLDTO> rList)throws Exception;

	int theGuardianNewsUpdate()throws Exception;

}
