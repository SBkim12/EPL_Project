package poly.persistance.mapper;

import config.Mapper;
import poly.dto.EPLDTO;

@Mapper("EPLdataMapper")
public interface IEPLdataMapper {

	int upsertlogo(EPLDTO rDTO);
	
}
