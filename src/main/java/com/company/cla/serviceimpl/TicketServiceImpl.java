package com.company.cla.serviceimpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.entity.Audience;
import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Ticket;
import com.company.cla.exception.TicketNotFoundException;
import com.company.cla.repository.AudienceRepository;
import com.company.cla.repository.TicketRepository;
import com.company.cla.service.IMatchService;
import com.company.cla.service.ITicketService;

@Service
public class TicketServiceImpl implements ITicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	AudienceRepository audienceRepository;

	@Autowired
	IMatchService matchService;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * This method is used to find ticket by ticketId.
	 * 
	 * @param ticketId Only ticketId is needed as a parameter.
	 * @return Audience This returns Ticket object
	 * @see TicketNotFoundException
	 */

	@Override
	public Ticket getTicket(Long ticketId) {
		log.info("Audience Service method to get Ticket by ticketId {}", ticketId);
		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isPresent()) {
			return ticketOptional.get();
		} else {
			String exceptionMessage = "Ticket with ticketId " + ticketId + " not found";
			log.warn(exceptionMessage);
			throw new TicketNotFoundException(exceptionMessage);
		}
	}

	/**
	 * This method is used to insert ticket.
	 * 
	 * @param ticket Only ticket object is needed as a parameter.
	 * @return Ticket This returns ticket object
	 */
	@Override
	public Ticket insertTicket(Ticket ticket) {
		log.info("Audience Service method to add Ticket");
		if (ticket.getTotalAmount() < 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket Price can not be 0 or negative! ");
		else {
			return ticketRepository.save(ticket);
		}
	}

	/**
	 * This method is used to cancel ticket.
	 * 
	 * @param ticketId Only ticketId is needed as a parameter.
	 * @return Ticket This returns Ticket object
	 * @see TicketNotFoundException
	 */

	@Override
	public Ticket cancelTicket(Long ticketId) {
		log.info("Audience Service method to cancel Ticket by ticketId {}", ticketId);
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Invalid ticket id"));
		Audience audience = ticket.getAudience();

		if (audience != null) {
			audience.setAmount(audience.getAmount() + ticket.getTotalAmount());
			audience.setTicket(null);
			audienceRepository.save(audience);
		}
		ticketRepository.delete(ticket);
		return ticket;
	}

	/**
	 * This method is used to get match by ticketId.
	 * 
	 * @param ticketId Only ticketId is needed as a parameter.
	 * @return Match This returns Match object
	 * @see TicketNotFoundException
	 */
	@Override
	public Match getMatch(Long ticketId) {
		log.info("Audience Service method to get Match by ticketId {}", ticketId);
		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isPresent()) {
			return ticketOptional.get().getMatch();
		} else {
			String exceptionMessage = " Ticket not found for the ticketId " + ticketId;
			log.warn(exceptionMessage);
			throw new TicketNotFoundException(exceptionMessage);
		}
	}

	/**
	 * This method is used to get schedule by ticketId.
	 * 
	 * @param ticketId Only ticketId is needed as a parameter.
	 * @return Schedule This returns schedule object
	 * @see TicketNotFoundException
	 */

	@Override
	public Schedule getSchedule(Long ticketId) {
		log.info("Audience Service method to get Schedule by ticketId {}", ticketId);
		Schedule schedule = null;

		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isPresent()) {

			if (ticketOptional.get().getAudience() != null) {
				Audience audience = ticketOptional.get().getAudience();
				schedule = audience.getMatch().getSchedule();
			}

		} else {
			String exceptionMessage = " Ticket not found for the ticketId " + ticketId;
			log.warn(exceptionMessage);
			throw new TicketNotFoundException(exceptionMessage);
		}

		return schedule;
	}

	/**
	 * This method is used to calculate bill for ticketId.
	 * 
	 * @param ticketId Only ticketId is needed as a parameter.
	 * @return double This returns double value
	 * @see TicketNotFoundException
	 */

	@Override
	public double calculateBill(Long ticketId) {
		log.info("Audience Service method to calculate ticket for ticketId {}", ticketId);
		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isPresent()) {
			return ticketOptional.get().getTotalAmount();
		} else {
			String exceptionMessage = "Ticket not found with ticketId " + ticketId;
			log.warn(exceptionMessage);
			throw new TicketNotFoundException(exceptionMessage);
		}
	}
}
