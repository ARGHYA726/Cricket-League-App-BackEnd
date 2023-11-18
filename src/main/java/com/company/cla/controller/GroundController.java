package com.company.cla.controller;

import java.util.List;

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

import com.company.cla.entity.Ground;
import com.company.cla.service.IGroundService;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/grounds")
public class GroundController {
	private IGroundService groundService;

	public GroundController(IGroundService groundService) {
		this.groundService = groundService;
	}

	@GetMapping
	public ResponseEntity<List<Ground>> retriveAllGrounds() {
		return new ResponseEntity<>(groundService.getAllGround(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ground> retriveGroundById(@PathVariable Long id) {
		return new ResponseEntity<>(groundService.getGroundById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Ground> addGround(@RequestBody Ground ground) {
		return new ResponseEntity<>(groundService.insertGround(ground), HttpStatus.CREATED);
	}

	@PatchMapping("/{groundId}")
	public ResponseEntity<Ground> updateGround(@PathVariable Long groundId, @RequestBody Ground ground) {
		ground.setGroundId(groundId);
		return new ResponseEntity<Ground>(groundService.updateGround(ground), HttpStatus.OK);
	}

	@DeleteMapping("/{groundId}")
	public ResponseEntity<Ground> deleteGround(@PathVariable Long groundId) {
		return new ResponseEntity<>(groundService.deleteGround(groundId), HttpStatus.OK);
	}

}
