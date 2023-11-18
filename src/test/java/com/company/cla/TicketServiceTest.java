package com.company.cla;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.cla.entity.Audience;
import com.company.cla.entity.Match;
import com.company.cla.entity.Ticket;
import com.company.cla.exception.TicketNotFoundException;
import com.company.cla.repository.TicketRepository;
import com.company.cla.serviceimpl.TicketServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceTest {

	private static List<Ticket> tickets;
	private static Ticket ticket;

	@Autowired
	private TicketServiceImpl ticketServiceImpl;

//	********************************************************* injecting ticket repository *************************************************************************
	@MockBean(name = "ticketRepository")
	private TicketRepository ticketRepository;

	static Optional<Ticket> ticket1 = Optional.of(new Ticket(1L, "Ticket1", 100D, 10, new Match(), new Audience()));
	static Optional<Ticket> ticket2 = Optional.of(new Ticket(2L, "Ticket2", 200D, 11, new Match(), new Audience()));

	@BeforeAll
	public static void setUp() {
		tickets = List.of(ticket1.get(), ticket2.get());
	}

//	********************************************************* get ticket *************************************************************************
	@Test
	@DisplayName("TicketService getTicketTest (Positive Testcase)")
	void getTicketTest() {
		when(ticketRepository.findById(ticket1.get().getTicketId())).thenReturn(ticket1);
		Optional<Ticket> to = Optional.of(ticketServiceImpl.getTicket(ticket1.get().getTicketId()));
		assertAll("Tickets in Database", () -> assertEquals(to.get(), ticket1.get()));
	}

	@Test
	@DisplayName("TicketService getTicketTestN(Negative TestCase)")
	void getTicketTestN() {
		when(ticketRepository.findById(ticket1.get().getTicketId())).thenReturn(ticket1);
		assertThrows(TicketNotFoundException.class, () -> ticketServiceImpl.getTicket(ticket2.get().getTicketId()));
	}

//	********************************************************* calculate bill *************************************************************************
	@Test
	@DisplayName("TicketService calculateBillTest (Positive Testcase)")
	void calculateBillTest() {
		when(ticketRepository.existsById(1L)).thenReturn(true);
		when(ticketRepository.findById(1L)).thenReturn(ticket1);
		double bill = ticketServiceImpl.calculateBill(1L);
		assertEquals(100D, bill);
	}

	@Test
	@DisplayName("TicketService calculateBillTestN (Positive Testcase)")
	void calculateBillTestN() {
		when(ticketRepository.existsById(1L)).thenReturn(false);
		assertThrows(TicketNotFoundException.class, () -> ticketServiceImpl.calculateBill(1L));
	}

}
