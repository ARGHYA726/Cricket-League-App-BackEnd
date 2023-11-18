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

import com.company.cla.dtos.OwnerDTO;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Team;
import com.company.cla.service.IOwnerService;
import com.company.cla.service.ITeamService;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/owner")
public class OwnerController {
	@Autowired
	IOwnerService ownerService;

	@Autowired
	ITeamService teamService;

	@GetMapping("/getOwnerById/{id}")
	public ResponseEntity<Owner> getOwnerById(@PathVariable(name = "id") Long ownerId) {
		return new ResponseEntity<>(ownerService.getOwner(ownerId), HttpStatus.OK);
	}

	@GetMapping("/getOwnerDetailsById/{id}")
	public ResponseEntity<String> getOwnerDetailsById(@PathVariable(name = "id") Long ownerId) {
		return new ResponseEntity<>(ownerService.getOwnerDetails(ownerId), HttpStatus.OK);
	}

	@GetMapping("/getAllOwners")
	public ResponseEntity<List<OwnerDTO>> getAllOwners() {
		return new ResponseEntity<>(ownerService.getAllOwners(), HttpStatus.OK);
	}

	@GetMapping("/getAllOwnersNames")
	public ResponseEntity<List<String>> getAllOwnersNames() {
		return new ResponseEntity<>(ownerService.getAllOwnersNames(), HttpStatus.OK);
	}

	@PostMapping("/addOwner")
	public ResponseEntity<Owner> insertOwner(@RequestBody Owner owner) {
		return new ResponseEntity<>(ownerService.insertOwner(owner), HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteById/{ownerId}")
	public String deleteOwnerById(@PathVariable("ownerId") Long ownerId) {
		return ownerService.deleteOwnerById(ownerId);
	}

	@PutMapping("/updateOwner/{ownerId}")
	public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner,@PathVariable("ownerId") Long teamId) {
	return new ResponseEntity<>(ownerService.updateOwner(owner), HttpStatus.OK);
	}

	@GetMapping("/getTeamByOwnerId/{id}")
	public ResponseEntity<Team> getTeam(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<>(ownerService.getTeam(id), HttpStatus.OK);
	}

	@PutMapping("/{ownerId}/paySalaryto/{playerId}/amount/{salary}")
	public ResponseEntity<Double> paySalary(@PathVariable(name = "ownerId") Long ownerId,
			@PathVariable(name = "playerId") Long playerId, @PathVariable(name = "salary") Double salary) {
		return new ResponseEntity<>(ownerService.paySalary(ownerId, playerId, salary), HttpStatus.OK);
	}

	@GetMapping("/getBudget/{ownerId}")
	public ResponseEntity<Double> getBudget(@PathVariable(name = "ownerId") Long ownerId) {
		return new ResponseEntity<>(ownerService.getBudget(ownerId), HttpStatus.OK);
	}

	@GetMapping("/getTotalSalary/{ownerId}")
	public ResponseEntity<Double> getTotalSalary(@PathVariable(name = "ownerId") Long ownerId) {
		return new ResponseEntity<>(ownerService.getTotalSalary(ownerId), HttpStatus.OK);
	}

	@PostMapping("/addTeam")
	public ResponseEntity<Team> addTeam(@RequestBody Team team) {
		return new ResponseEntity<>(teamService.insertTeam(team), HttpStatus.OK);
	}
}
