/**
 * 
 */
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

import com.company.cla.dtos.OrganiserDTO;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.OrganiserInvalidBudgetException;
import com.company.cla.exception.OrganiserInvalidEmailException;
import com.company.cla.exception.OrganiserInvalidPaymentException;
import com.company.cla.exception.OrganiserInvalidPhoneException;
import com.company.cla.exception.OrganiserNotFoundException;
import com.company.cla.service.IOrganiserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/organisers")
public class OrganiserController {

	@Autowired
	private IOrganiserService organiserService;

	@PostMapping
	@ApiOperation(value = "save the Organiser.",
	  notes = "returns the details of the organiser if saved successfully",
	  response = Organiser.class)
	public ResponseEntity<Organiser> saveOrganiser(@RequestBody Organiser organiser)
			throws OrganiserInvalidPhoneException, OrganiserInvalidPaymentException, OrganiserInvalidBudgetException,
			OrganiserInvalidEmailException {
		return new ResponseEntity<>(organiserService.insertOrganiser(organiser), HttpStatus.CREATED);
	}
	@GetMapping
	@ApiOperation(value = "Get the list of all the organisers present",
	  notes = "It returns List of organisers",
	  response = Organiser.class)
	public List<OrganiserDTO> listAllOrganisers() {
		return organiserService.getAllOrganisers();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get an Organiser Object by given id",
	  notes = "It returns an Organiser object",
	  response = Organiser.class)
	public ResponseEntity<Organiser> getTheOrganiser(
			 @ApiParam(value = "Id for Organiser you need to retrieve", required=true)  @PathVariable("id") Long id)
			throws OrganiserNotFoundException {
		return new ResponseEntity<>(organiserService.getOrganiser(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "to update the organiser details",
	  notes = "It returns the updated details",
	  response = Organiser.class)
	public ResponseEntity<Organiser> updateTheOrganiser(@PathVariable("id") Long id, @RequestBody Organiser organiser)
			throws OrganiserNotFoundException {
		return new ResponseEntity<>(organiserService.updateOrganiser(id, organiser), HttpStatus.OK);
	}

	@GetMapping("/{id}/tournaments")
	@ApiOperation(value = "get the list of tournaments organised by the organiser",
	  notes = "It returns the list of tournaments",
	  response = Organiser.class)
	public ResponseEntity<List<Tournament>> getTournamentsOrganised(@PathVariable("id") Long id)
			throws OrganiserNotFoundException {
		return new ResponseEntity<>(organiserService.getTournaments(id), HttpStatus.OK);
	}

	@PutMapping("/{organiserId}/prizemoneyto")
	@ApiOperation(value = "used to pay the prize money to the owner",
	  notes = "It returns the prize money",
	  response = Organiser.class)
	public double payPrize(@PathVariable("organiserId") Long organiserId, @RequestBody Owner owner)
			throws OrganiserNotFoundException {
		return organiserService.payPrizeMoney(organiserId, owner);
	}

	@GetMapping("budget/{id}")
	@ApiOperation(value = "get the budget",
	  notes = "returns the present budget of the organiser",
	  response = Organiser.class)
	public double showBudget(@PathVariable("id") Long id) throws OrganiserNotFoundException {
		return organiserService.getBudget(id);
	}
}
