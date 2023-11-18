package com.company.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.cla.dtos.PlayerDTO;
import com.company.cla.dtos.TeamDTO;
import com.company.cla.entity.Player;
import com.company.cla.entity.Team;
import com.company.cla.service.IPlayerService;
import com.company.cla.service.ITeamService;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/teams")
public class TeamController {
	@Autowired
	private IPlayerService playerService;
	private ITeamService teamService;

	public TeamController(ITeamService teamService) {
		super();
		this.teamService = teamService;
	}

	@GetMapping("{teamId}")
	public ResponseEntity<Team> getTeam(@PathVariable("teamId") Long teamId) {
		return new ResponseEntity<Team>(teamService.getTeam(teamId), HttpStatus.OK);
	}

	@GetMapping
	public List<Team> getAllTeams() {
		return teamService.getAllTeams();
	}

	@GetMapping("/dto")
	public List<TeamDTO> getAllTeamsDTO() {
		return teamService.getAllTeamsDTO();
	}

	@PostMapping
	public ResponseEntity<Team> insertTeam(@RequestBody Team team) {
		return new ResponseEntity<>(teamService.insertTeam(team), HttpStatus.CREATED);
	}

	@PutMapping("/{teamId}")
	public ResponseEntity<Team> updateTeam(@PathVariable("teamId") Long teamId, @RequestBody Team team) {
		return new ResponseEntity<>(teamService.updateTeam(teamId, team), HttpStatus.OK);
	}

	@DeleteMapping("{teamId}")
	public ResponseEntity<String> deleteTeam(@PathVariable("teamId") Long teamId) {
		teamService.deleteTeam(teamId);
		return new ResponseEntity<>("Team deleted successfully!", HttpStatus.OK);
	}

	@GetMapping("/{id}/players")
	public ResponseEntity<List<Player>> getPlayers(@PathVariable("id") Long id) {
		return new ResponseEntity<>(teamService.getPlayers(id), HttpStatus.OK);
	}
	
	@GetMapping("/get-player-by-player-id/{teamId}/{playerId}")
	public ResponseEntity<PlayerDTO> getPlayerByPlayerId(@PathVariable("teamId") Long teamId,
			@PathVariable("playerId") Long playerId) {
		return new ResponseEntity<>(playerService.getPlayer(playerId), HttpStatus.OK);
	}

	@GetMapping("/get-team-by-player-id/{playerId}")
	public ResponseEntity<Team> getTeamByPlayerId(@PathVariable("playerId") Long playerId) {
		return new ResponseEntity<>(playerService.getTeam(playerId), HttpStatus.OK);
	}

	@PostMapping("/insert-player")
	public ResponseEntity<PlayerDTO> insertPlayer(@RequestBody PlayerDTO player) {
		return new ResponseEntity<>(playerService.insertPlayer(player), HttpStatus.CREATED);

	}

	@PutMapping("/update-player")
	public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO player) {
		return new ResponseEntity<>(playerService.updatePlayer(player, player.getPlayerId()), HttpStatus.OK);
	}

	@DeleteMapping("/delete-player/{playerId}")
	public ResponseEntity<String> deletePlayer(@PathVariable(name = "playerId") Long playerId) {
		playerService.deletePlayer(playerId);
		return new ResponseEntity<>("Player deleted successfully!.", HttpStatus.OK);
	}
}
