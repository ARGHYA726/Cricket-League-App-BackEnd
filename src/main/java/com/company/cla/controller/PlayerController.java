package com.company.cla.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.company.cla.entity.Skill;
import com.company.cla.service.IPlayerService;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private IPlayerService service;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	String playerText = "Player ";
	String msg = "message";
	String foundSuccessfully = " was found successfully.";
	String pController = "PlayerController ";

	@PostMapping("/insert-player")
	public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO player) {
		return new ResponseEntity<>(service.insertPlayer(player), HttpStatus.OK);
	}
	
	@PutMapping("/{playerId}")
	public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO player, @PathVariable Long playerId) {
		return new ResponseEntity<>(service.updatePlayer(player, playerId), HttpStatus.OK);
	}
	
	@GetMapping("/get-player-by-id/{playerId}")
	public ResponseEntity<PlayerDTO> getPlayerByPlayerId(@PathVariable("playerId") Long playerId) {
		return new ResponseEntity<>(service.getPlayer(playerId), HttpStatus.OK);
	}

	@DeleteMapping("/delete-player/{playerId}")
	public ResponseEntity<String> deletePlayer(@PathVariable(name = "playerId") Long playerId) {
		service.deletePlayer(playerId);
		return new ResponseEntity<>("Player deleted successfully!.", HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json",path="/get-all-players")
	public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
		log.info("{} getAllPlayers: ", pController);
		HttpHeaders headers = new HttpHeaders();
		headers.add(msg, "Players" + foundSuccessfully);
		return new ResponseEntity<>(service.getAllPlayers(), headers, HttpStatus.OK);
	}

	@GetMapping("/get-skill-by-id/{pid}")
	public ResponseEntity<Skill> getSkillById(@PathVariable(name = "pid") Long playerId) {
		log.info("{} getSkillById: {}", pController, playerId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(msg, "Skill of player " + playerId + foundSuccessfully);
		return new ResponseEntity<>(service.getSkill(playerId), headers, HttpStatus.OK);
	}

	@PostMapping("/add-skill-by-id/{pid}/{skill}")
	public ResponseEntity<Skill> addSkillById(@PathVariable(name = "pid") Long playerId,
			@PathVariable(name = "skill") String skillName) {
		log.info("{} addSkillById: {}", pController, playerId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(msg, "Skill: " + skillName + " added to player " + playerId + " successfully.");
		return new ResponseEntity<>(service.addSkill(playerId, skillName), headers, HttpStatus.CREATED);
	}

	@PutMapping("/update-skill-by-id/{pid}/{skill}")
	public ResponseEntity<Skill> updateSkillById(@PathVariable(name = "pid") Long playerId,
			@PathVariable(name = "skill") String skillName) {
		log.info("{} updateSkillById: {}", pController, playerId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(msg, "Skill: " + skillName + " updated of player " + playerId + " successfully.");
		return new ResponseEntity<>(service.updateSkill(playerId, skillName), headers, HttpStatus.OK);
	}

	@DeleteMapping("/remove-skill-by-id/{pid}")
	public ResponseEntity<String> removeSkillById(@PathVariable(name = "pid") Long playerId) {
		log.info("{} removeSkillById: {}", pController, playerId);
		service.removeSkill(playerId);
		return new ResponseEntity<>("Skill deleted successfully!.", HttpStatus.OK);
	}

	@GetMapping("/get-salary-by-id/{pid}")
	public ResponseEntity<Double> getSalaryById(@PathVariable(name = "pid") Long playerId) {
		log.info("{} getSalaryById: {}", pController, playerId);
		HttpHeaders headers = new HttpHeaders();
		headers.add(msg, playerText + playerId + foundSuccessfully);
		return new ResponseEntity<>(service.getSalary(playerId), headers, HttpStatus.OK);
	}
}


//@GetMapping("/{pid}")
//public ResponseEntity<PlayerDTO> getPlayer(@PathVariable(name = "pid") Long playerId) {
//	log.info("{} getPlayer: {}", pController, playerId);
//	HttpHeaders headers = new HttpHeaders();
//	headers.add(msg, "Player " + playerId + foundSuccessfully);
//	return new ResponseEntity<>(service.getPlayer(playerId), headers, HttpStatus.OK);
//}
//
//@PostMapping
//public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO player) {
//	return new ResponseEntity<>(service.insertPlayer(player), HttpStatus.OK);
//}
//
//@PutMapping("/{playerId}")
//public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO player, @PathVariable Long playerId) {
//	return new ResponseEntity<>(service.updatePlayer(player, playerId), HttpStatus.OK);
//}