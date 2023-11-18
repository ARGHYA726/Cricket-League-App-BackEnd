package com.company.cla.service;

import com.company.cla.entity.Audience;
import com.company.cla.entity.Match;
import com.company.cla.entity.Ticket;

public interface IAudienceService {
	public Audience getAudience(Long audienceId);

	public Audience insertAudience(Audience audience);

	public Match getMatch(Long audienceId);

	public Ticket getTicket(Long ticketId);
	
	public Audience bookTicket(Long ticketId, Long audienceId); // updates the ticket and match of audience

	Ticket getTicketByAudienceId(Long audienceId);
}
