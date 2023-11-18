package com.company.cla.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.dtos.MatchFetchDTO;
import com.company.cla.dtos.MatchInsertDTO;
import com.company.cla.entity.Match;
import com.company.cla.entity.Team;
import com.company.cla.service.IMatchService;

import io.swagger.annotations.ApiOperation;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/matches")
public class MatchController {

	@Autowired
	private IMatchService matchService;

	@GetMapping
	@ApiOperation(value = "Get all the matches present in the database", notes = "It returns a match list", response = Match.class)
	public ResponseEntity<List<MatchFetchDTO>> retriveAllMatches() {
		return new ResponseEntity<>(matchService.getAllMatches(), HttpStatus.OK);
	}

	@GetMapping("/{matchId}")
	@ApiOperation(value = "Get details of a match", notes = "It returns match details", response = Match.class)
	public ResponseEntity<MatchFetchDTO> retriveMatches(@PathVariable Long matchId) {
		return new ResponseEntity<>(matchService.getMatch(matchId), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "insert a match details into the database", notes = "It returns the details of the match inserted", response = Match.class)
	public ResponseEntity<MatchFetchDTO> addMatch(@RequestBody MatchInsertDTO match) {
		return new ResponseEntity<>(matchService.insertMatch(match), HttpStatus.CREATED);
	}

	@PatchMapping("/{matchId}/cancel")
	@ApiOperation(value = "Used to cancel a match or not", notes = "returns the required details of the match", response = Match.class)
	public ResponseEntity<MatchFetchDTO> cancelMatch(@PathVariable Long matchId, @RequestBody Boolean cancelStatus) {
		return new ResponseEntity<>(matchService.cancelMatch(matchId, cancelStatus), HttpStatus.OK);
	}

	@DeleteMapping("{matchId}/delete")
	@ApiOperation(value = "Used to delete a match", notes = "returns the details of the match deleted", response = Match.class)
	public ResponseEntity<MatchFetchDTO> deleteMatch(@PathVariable Long matchId) {
		throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Match can only be canceled");
	}

	@GetMapping("{matchId}/get-team1")
	@ApiOperation(value = "Get the details of the group 1 team", notes = "It returns the first team details", response = Match.class)
	public ResponseEntity<Team> getAllTeam1(@PathVariable Long matchId) {
		return new ResponseEntity<>(matchService.getTeam1(matchId), HttpStatus.OK);
	}

	@GetMapping("{matchId}/get-team2")
	@ApiOperation(value = "Get the details of the group 2 team", notes = "It returns the second team details", response = Match.class)
	public ResponseEntity<Team> getAllTeam2(@PathVariable Long matchId) {
		return new ResponseEntity<>(matchService.getTeam2(matchId), HttpStatus.OK);
	}

	@GetMapping("/get-all-matches-team-wise")
	@ApiOperation(value = "Get the details of the team combination", notes = "It returns the team details of both the teams", response = Match.class)
	public ResponseEntity<Map<String, String>> getTeamMap() {
		return new ResponseEntity<>(matchService.getTeams(), HttpStatus.OK);
	}

}
