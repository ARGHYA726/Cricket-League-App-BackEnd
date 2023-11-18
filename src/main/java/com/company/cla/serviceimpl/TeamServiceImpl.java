package com.company.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.cla.dtos.PlayerDTO;
import com.company.cla.dtos.TeamDTO;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Player;
import com.company.cla.entity.Team;
import com.company.cla.exception.OwnerNotFoundException;
import com.company.cla.exception.PlayerNotFoundException;
import com.company.cla.exception.TeamNotFoundException;
import com.company.cla.repository.TeamRepository;
import com.company.cla.service.IOwnerService;
import com.company.cla.service.IPlayerService;
import com.company.cla.service.ITeamService;

@Service
public class TeamServiceImpl implements ITeamService {

	@Autowired
	private IPlayerService playerService;

	@Autowired
	private IOwnerService ownerService;

	private TeamRepository teamRepository;

	public TeamServiceImpl(TeamRepository teamRepository) {
		super();
		this.teamRepository = teamRepository;
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * This method finds a team based on team id and throws an exception if team is
	 * not found with the given teamId.
	 * 
	 * @param Long: teamId
	 * @return Team : team
	 * @see TeamNotFoundException
	 */
	@Override
	public Team getTeam(Long teamId) {
		log.info("Team Service method to get Team by Id {}", teamId);
		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isPresent()) {
			return team.get();
		} else {
			log.warn("Team with id {} not found", teamId);
			throw new TeamNotFoundException("Team not found with the given teamId");
		}
	}

	/**
	 * This method returns all the teams and throws an exception if No teams found.
	 * 
	 * @return Team : List<TeamDTO>
	 * @see TeamNotFoundException
	 */
	@Override
	public List<TeamDTO> getAllTeamsDTO() {
		Iterator<Team> teamList = teamRepository.findAll().iterator();
		List<TeamDTO> dtoList = new ArrayList<>();
		while (teamList.hasNext()) {
			Team team = teamList.next();
			dtoList.add(new TeamDTO(team.getTeamId(), team.getTeamName(), team.getMatches(), team.getPlayers()));
		}
		if (dtoList.isEmpty()) {
			throw new TeamNotFoundException("No teams found");
		}
		return dtoList;
	}

	/**
	 * This method returns all the teams and throws an exception if No teams found.
	 * 
	 * @return Team : List<Team>
	 * @see TeamNotFoundException
	 */
	@Override
	public List<Team> getAllTeams() {
		log.info("Team Service method to get all Teams");
		if (teamRepository.findAll().isEmpty()) {
			log.warn("Teams not found");
			throw new TeamNotFoundException("Teams not found");
		} else {
			return teamRepository.findAll();
		}
	}

	/**
	 * This method inserts the team and throws an exception if owner not found.
	 * 
	 * @param Team: team
	 * @return Team : team
	 * @see OwnerNotFoundException
	 */
	@Override
	public Team insertTeam(Team team) {
		log.info("Team Service method to insert team");

		if (team.getOwnerId() != null) {
			try {
				Owner o = ownerService.getOwner(team.getOwnerId());
				team.setOwner(o);
			} catch (OwnerNotFoundException e) {
				log.warn(e.getMessage());
				throw e;
			}
		}
		return teamRepository.save(team);

	}

	/**
	 * This method updates the team and throws an exception if team not found.
	 * 
	 * @param Long: teamId, Team: team
	 * @return Team : existingTeam
	 * @see TeamNotFoundException
	 */
	@Override
	public Team updateTeam(Long teamId, Team team) {
		log.info("Team Service method to update team by teamId {}", teamId);
		Team existingTeam = teamRepository.findById(teamId)
				.orElseThrow(() -> new TeamNotFoundException("Team not found with the given teamId"));
		existingTeam.setTeamName(team.getTeamName());
		existingTeam.setMatches(team.getMatches());
		existingTeam.setPlayers(team.getPlayers());
		existingTeam.setOwner(team.getOwner());
		teamRepository.save(existingTeam);
		return existingTeam;
	}

	/**
	 * This method deletes the team and throws an exception if team not found with
	 * the given teamId.
	 * 
	 * @param Long: teamId
	 * @see TeamNotFoundException
	 */

	@Override
	public void deleteTeam(Long teamId) {
		log.info("Team Service method to delete team with teamId {}", teamId);
		if (teamRepository.findById(teamId).isPresent()) {
			teamRepository.deleteById(teamId);
		} else {
			log.warn("Team not found with the given teamId {}", teamId);
			throw new TeamNotFoundException("Team not found with the given teamId");
		}
	}

	/**
	 * This method finds all the players with the given teamId and throws an
	 * exception if team not found.
	 * 
	 * @param Long: teamId
	 * @return Player: List<Player>
	 * @see TeamNotFoundException
	 */

	@Override
	public List<Player> getPlayers(Long teamId) {
		log.info("Team Service method to get all players by teamId {}", teamId);
		if (teamRepository.existsById(teamId))
        	return teamRepository.findById(teamId).get().getPlayers();
	    throw new TeamNotFoundException("No teams found");
	}

	/**
	 * This method finds the player with the given teamId and playerId and throws an
	 * exception if player not found in the given team.
	 * 
	 * @param Long: teamId, Long: playerId
	 * @return Player: player
	 * @see PlayerNotFoundException
	 */

	@Override
	public Player getPlayerById(Long teamId, Long playerId) {
		log.info("Team Service method to get Player by PlayerId {}", playerId);

		List<Player> player = teamRepository.findById(teamId)
				.orElseThrow(() -> new TeamNotFoundException("Team not found")).getPlayers().stream()
				.filter(p -> Long.compare(p.getPlayerId(), playerId) == 0).collect(Collectors.toList());

		if (player.isEmpty()) {
			log.warn("Player not found with the given playerId {}", playerId);
			throw new PlayerNotFoundException("player not found in the given team");
		}
		return player.get(0);
	}

	/**
	 * This method finds the team with the given playerId.
	 * 
	 * @param Long: playerId
	 * @return Team: team
	 */
	@Override
	public Team getTeamByPlayerId(Long playerId) {
		log.info("Team Service method to get Team by PlayerId {}", playerId);
		try {

			PlayerDTO player = playerService.getPlayer(playerId);
			return playerService.getTeam(player.getPlayerId());
		} catch (PlayerNotFoundException p) {
			log.warn("Player not found with the given playerId {}", playerId);
			throw p;
		}

	}

}