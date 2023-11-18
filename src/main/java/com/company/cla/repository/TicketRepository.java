package com.company.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.cla.entity.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
}
