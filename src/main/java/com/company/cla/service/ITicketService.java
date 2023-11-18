package com.company.cla.service;

import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Ticket;

public interface ITicketService {
	public Ticket getTicket(Long ticketId);

	public Ticket insertTicket(Ticket ticket);

	public Ticket cancelTicket(Long ticketId);

	public Match getMatch(Long ticketId);

	public Schedule getSchedule(Long ticketId);

	public double calculateBill(Long ticketId);

}
