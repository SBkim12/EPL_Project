package poly.persistance.mapper;

import java.util.List;

import config.Mapper;
import poly.dto.EPLDTO;

@Mapper("EPLdataMapper")
public interface IEPLdataMapper {

	int upsertlogo(EPLDTO rDTO);

	EPLDTO infoTeamLogoName(EPLDTO rDTO);

	int upsertEPLdata(EPLDTO rDTO);

	int updateSeason(EPLDTO rDTO);

	List<EPLDTO> getEPLTeam();

	EPLDTO getTeamLogo(String favorite_team);

	EPLDTO getKoname(String team);

	EPLDTO presentSeason();

}
