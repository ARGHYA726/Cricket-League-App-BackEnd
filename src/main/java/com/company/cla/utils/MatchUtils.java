package com.company.cla.utils;

import com.company.cla.dtos.MatchFetchDTO;
import com.company.cla.entity.Match;
import com.company.cla.entity.Team;

public class MatchUtils {
	private MatchUtils() {
	}

	public static MatchFetchDTO convertToMatchFetchDTO(Match match) {

		Team teamOne = match.getTeams().get(0);
		Team teamTwo = match.getTeams().get(1);

		MatchFetchDTO mfd = new MatchFetchDTO(match.getMatchId(), teamOne.getTeamId(), teamOne.getTeamName(),
				teamTwo.getTeamId(), teamTwo.getTeamName(), match.getSchedule(), match.isCanceled(),
				match.getGround().getGroundId(), match.getGround().getGroundName(), match.getGround().getAddress());

		if (match.getTournament() != null) {
			mfd.setTournament_id(match.getTournament().getTournamentId());
			mfd.setTournament_name(match.getTournament().getTournamentName());
		}

		return mfd;
	}
}
