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
import com.company.cla.entity.Ticket;
import com.company.cla.exception.AudienceNotFoundException;
import com.company.cla.exception.MatchNotFoundException;
import com.company.cla.exception.TicketNotFoundException;
import com.company.cla.repository.AudienceRepository;
import com.company.cla.repository.MatchRepository;
import com.company.cla.repository.TicketRepository;
import com.company.cla.service.IAudienceService;
import com.company.cla.service.ITicketService;

@Service
public class AudienceServiceImpl implements IAudienceService {

	@Autowired
	AudienceRepository audienceRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ITicketService ticketService;

	Logger log = LoggerFactory.getLogger("Audience Service Logger");

	/**
	 * This method is used to find audience by audienceId.
	 * 
	 * @param audienceId Only audienceId is needed as a parameter.
	 * @return Audience This returns Audience object
	 * @see AudienceNotFoundException
	 */

	@Override
	public Audience getAudience(Long audienceId) {
		log.info("Audience Service method to get Audience by audienceId {}", audienceId);
		Optional<Audience> audience = audienceRepository.findById(audienceId);
		if (audience.isPresent()) {
			return audience.get();
		} else {
			String exceptionMessage = "Audience with audienceId " + audienceId + " does not exist.";
			log.warn(exceptionMessage);
			throw new AudienceNotFoundException(exceptionMessage);
		}
	}

	/**
	 * This method is used to insert audience.
	 * 
	 * @param audience Only audience object is needed as a parameter.
	 * @return Audience This returns Audience object
	 */

	@Override
	public Audience insertAudience(Audience audience) {
		log.info("Audience Service method to add audience");

		if (audience.getMatchId() != null) {
			Match match = matchRepository.findById(audience.getMatchId())
					.orElseThrow(() -> new MatchNotFoundException("Match not found"));
			audience.setMatch(match);

		}

		if (audience.getTicketId() != null) {
			Ticket ticket = ticketRepository.findById(audience.getTicketId())
					.orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
			audience.setTicket(ticket);

		}

		if (audience.getAmount() <= 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is negative or zero");

		return audienceRepository.save(audience);
	}

	/**
	 * This method is used to book ticket i.e, it will map the audienceId with the
	 * ticketId.
	 * 
	 * @param audienceId ticketId : audienceId and ticketId are needed as
	 *                   parameters.
	 * @return Audience: This returns Audience object
	 * @see TicketNotFoundException AudienceNotFoundException
	 */

	@Override
	public Audience bookTicket(Long ticketId, Long audienceId) {
		log.info("Audience Service method to book ticket by ticketId {} and audienceId {}", ticketId, audienceId);
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Invalid ticket id"));
		Audience audience = audienceRepository.findById(audienceId)
				.orElseThrow(() -> new AudienceNotFoundException("Invalid audience id"));
		double ticketPrice = ticketRepository.findById(ticketId).get().getTotalAmount();
		if (audience.getAmount() < ticketPrice) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Audience do not have enough amount to buy ticket");
		} else {
			audience.setTicket(ticket);
			audience.setAmount(audience.getAmount() - ticketPrice);
		}
		return audienceRepository.save(audience);

	}

	/**
	 * This method is used to get match by audienceId.
	 * 
	 * @param audienceId : Only audienceId is needed as a parameter
	 * @return Match: This returns Match object
	 * @see: AudienceNotFoundException
	 */

	@Override
	public Match getMatch(Long audienceId) {
		log.info("Audience Service method to get Match by audienceId {}", audienceId);
		Optional<Audience> audienceOptional = audienceRepository.findById(audienceId);
		if (audienceOptional.get().getTicket().getMatch() != null) {
			return audienceOptional.get().getTicket().getMatch();
		} else {
			String exceptionMessage = "Audience with audienceId " + audienceId + " not found";
			log.warn(exceptionMessage);
			throw new AudienceNotFoundException(exceptionMessage);
		}
	}

	/**
	 * This method is used to get ticket by audienceId.
	 * 
	 * @param audienceId : Only audienceId is needed as a parameter
	 * @return Ticket : This returns ticket object
	 * @see: TicketNotFoundException AudienceNotFoundException
	 */
	@Override
	public Ticket getTicketByAudienceId(Long audienceId) {
		log.info("Audience Service method to get Ticket by audienceId{}", audienceId);
		Optional<Audience> audienceOptional = audienceRepository.findById(audienceId);
		if (audienceOptional.isPresent()) {
			if (audienceOptional.get().getTicket() != null)
				return audienceOptional.get().getTicket();
			else {
				throw new TicketNotFoundException("Ticket not found!");
			}
		} else {
			throw new AudienceNotFoundException("Audience not found!");
		}
	}

	/**
	 * This method is used to get ticket by ticketId.
	 * 
	 * @param ticketId : Only ticketId is needed as a parameter
	 * @return Ticket : This returns ticket object
	 * @see: TicketNotFoundException
	 */

	@Override
	public Ticket getTicket(Long ticketId) {
		log.info("Audience Service method to get Ticket by ticketId {}", ticketId);
		Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
		if (ticketOptional.isPresent()) {
			return ticketOptional.get();
		} else {
			String exceptionMessage = "Ticket with TicketId " + ticketId + " does not exist.";
			log.warn(exceptionMessage);
			throw new TicketNotFoundException(exceptionMessage);
		}
	}

}
