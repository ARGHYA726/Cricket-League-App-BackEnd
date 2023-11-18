package com.company.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.cla.entity.Tournament;
import com.company.cla.service.ITournamentService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/tournaments")
public class TournamentController {
	@Autowired
	private ITournamentService tournamentService;

	@GetMapping
	public List<Tournament> listAllTournament() {
		return tournamentService.getAlTournaments();
	}

	@GetMapping("/{tournamentId}")
	public ResponseEntity<Tournament> retriveTournamentById(@PathVariable Long tournamentId) {
		return new ResponseEntity<Tournament>(tournamentService.getTournamentById(tournamentId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Tournament> addTournament(@RequestBody Tournament tournament) {
		return new ResponseEntity<>(tournamentService.insertTorunament(tournament), HttpStatus.CREATED);
	}

	@PutMapping("/{tournamentId}")
	public ResponseEntity<Tournament> updateTournament(@RequestBody Tournament tournament,
			@PathVariable Long tournamentId) {
		return new ResponseEntity<Tournament>(tournamentService.updateTournament(tournament), HttpStatus.OK);
	}

}
