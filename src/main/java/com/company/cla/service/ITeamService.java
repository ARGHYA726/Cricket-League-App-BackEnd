package com.company.cla.service;

import java.util.List;

import com.company.cla.dtos.TeamDTO;
import com.company.cla.entity.Player;
import com.company.cla.entity.Team;

public interface ITeamService {

	public Team getTeam(Long teamId);

	public List<Team> getAllTeams();

	public Team insertTeam(Team team);

	public Team updateTeam(Long teamId, Team team);

	public void deleteTeam(Long teamId);
	
	public List<Player> getPlayers(Long teamId);

	public Player getPlayerById(Long teamId, Long playerId);

	public Team getTeamByPlayerId(Long playerId);

	List<TeamDTO> getAllTeamsDTO();

}
