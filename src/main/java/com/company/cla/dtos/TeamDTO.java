package com.company.cla.dtos;

import java.util.List;

import com.company.cla.entity.Match;
import com.company.cla.entity.Player;

public class TeamDTO {
	private Long teamId;
	private String teamName;
	private Match match;
	private List<Player> players;

	public TeamDTO() {
		super();
	}

	public TeamDTO(Long teamId, String teamName, Match match, List<Player> players) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.match = match;
		this.players = players;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
