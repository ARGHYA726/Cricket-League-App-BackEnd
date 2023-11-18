package com.company.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.cla.entity.Audience;
import com.company.cla.entity.Match;
import com.company.cla.entity.Ticket;
import com.company.cla.service.IAudienceService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/audiences")
public class AudienceController {
	@Autowired
	IAudienceService audienceService;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get an Audience Object by given id",
	  notes = "It returns an Audience object",
	  response = Audience.class)
	public ResponseEntity<Audience> getAudienceById(@PathVariable(name = "id") Long audienceId) {
		return new ResponseEntity<Audience>(audienceService.getAudience(audienceId), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "insert an audience into the database",
	  notes = "It returns an Audience object",
	  response = Audience.class)
	public ResponseEntity<Audience> insertAudience(@RequestBody Audience audience) {
		return new ResponseEntity<Audience>(audienceService.insertAudience(audience), HttpStatus.CREATED);
	}

	@PatchMapping("/{audienceId}/book-ticket/{ticketId}")
	@ApiOperation(value = "Used to book a ticket",
	  notes = "returns the updated audience",
	  response = Audience.class)
	public ResponseEntity<Audience> bookTicket(@PathVariable Long audienceId, @PathVariable Long ticketId) {
		return new ResponseEntity<Audience>(audienceService.bookTicket(ticketId, audienceId), HttpStatus.OK);
	}

	@GetMapping("/{id}/get_match")
	@ApiOperation(value = "Get the match details to be visited by a particular audience",
	  notes = "It returns a match object",
	  response = Audience.class)
	public ResponseEntity<Match> getMatch(@PathVariable(name = "id") Long audienceId) {
		return new ResponseEntity<Match>(audienceService.getMatch(audienceId), HttpStatus.OK);
	}

	@GetMapping("/{id}/get_ticket")
	@ApiOperation(value = "Get the details of ticket",
	  notes = "It returns Ticket details",
	  response = Audience.class)
	public ResponseEntity<Ticket> getTicket(@PathVariable(name = "id") Long ticketId) {
		return new ResponseEntity<Ticket>(audienceService.getTicket(ticketId), HttpStatus.OK);
	}

	@GetMapping("/{id}/get_ticket_by_audienceId")
	@ApiOperation(value = "Get get the ticket bought by a particular audience",
	  notes = "It returns Ticket details",
	  response = Audience.class)
	public ResponseEntity<Ticket> getTicketByAudienceId(@PathVariable(name = "id") Long audienceId) {
		return new ResponseEntity<>(audienceService.getTicketByAudienceId(audienceId), HttpStatus.OK);
	}
}
