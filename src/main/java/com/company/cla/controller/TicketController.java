package com.company.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Ticket;
import com.company.cla.service.ITicketService;
@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/tickets")
public class TicketController {
	@Autowired
	ITicketService ticketService;

	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable(name = "id") Long ticketId) {
		return new ResponseEntity<Ticket>(ticketService.getTicket(ticketId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Ticket> insertTicket(@RequestBody Ticket ticket) {
		return new ResponseEntity<Ticket>(ticketService.insertTicket(ticket), HttpStatus.CREATED);
	}

	@DeleteMapping("/{ticketId}")
	public ResponseEntity<String> cancelTicket(@PathVariable("ticketId") Long ticketId) {
		ticketService.cancelTicket(ticketId);
		return new ResponseEntity<String>("Ticket cancelled successfully!", HttpStatus.OK);
	}

	@GetMapping("/{id}/get_match")
	public ResponseEntity<Match> getMatchtByTicketId(@PathVariable(name = "id") Long ticketId) {
		return new ResponseEntity<Match>(ticketService.getMatch(ticketId), HttpStatus.OK);
	}

	@GetMapping("/{id}/get_schedule")
	public ResponseEntity<Schedule> getScheduleByTicketId(@PathVariable(name = "id") Long ticketId) {
		return new ResponseEntity<>(ticketService.getSchedule(ticketId), HttpStatus.OK);
	}

	@GetMapping("/{id}/bill")
	public ResponseEntity<Double> calculateBill(@PathVariable(name = "id") Long ticketId) {
		return new ResponseEntity<Double>(ticketService.calculateBill(ticketId), HttpStatus.OK);
	}
}
