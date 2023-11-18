package com.company.cla;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
import com.company.cla.exception.AudienceNotFoundException;
import com.company.cla.exception.TicketNotFoundException;
import com.company.cla.repository.AudienceRepository;
import com.company.cla.repository.TicketRepository;
import com.company.cla.serviceimpl.AudienceServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AudienceServiceTest {

	@Autowired
	private AudienceServiceImpl audienceServiceImpl;

//	********************************************************* injecting audience repository *************************************************************************
	@MockBean(name = "audienceRepository")
	private AudienceRepository audienceRepository;

//	********************************************************* injecting ticket repository *************************************************************************
	@MockBean(name = "ticketRepository")
	private TicketRepository ticketRepository;

	static Optional<Ticket> ticket1 = Optional.of(new Ticket(1L, "Ticket1", 1D, 10, new Match(), new Audience()));
	static Optional<Ticket> ticket2 = Optional.of(new Ticket(2L, "Ticket2", 2D, 11, new Match(), new Audience()));
	static Optional<Audience> audience1 = Optional
			.of(new Audience(1L, "Audience1", 1D, 5L, 10L, new Ticket(), new Match()));
	static Optional<Audience> audience2 = Optional
			.of(new Audience(2L, "Audience2", 2D, 6L, 11L, new Ticket(), new Match()));

	@BeforeAll
	public static void setUp() {
		List.of(audience1.get(), audience2.get());
	}

//	********************************************************* get audience *************************************************************************
	@Test
	@DisplayName("AudienceService getAudienceTest (Positive Testcase)")
	public void getAudienceTest() {
		when(audienceRepository.findById(audience1.get().getAudienceId())).thenReturn(audience1);
		Optional<Audience> audienceOptional = Optional
				.of(audienceServiceImpl.getAudience(audience1.get().getAudienceId()));
		assertAll("Audience in Database", () -> assertEquals(audience1.get(), audienceOptional.get()));
	}

	@Test
	@DisplayName("AudienceService getAudienceTest (Negative TestCase)")
	void getAudienceTestN() {
		when(audienceRepository.findById(audience1.get().getAudienceId())).thenReturn(audience1);
		assertThrows(AudienceNotFoundException.class,
				() -> audienceServiceImpl.getAudience(audience2.get().getAudienceId()));
	}

//	********************************************************* get ticket *************************************************************************
	@Test
	@DisplayName("AudienceService getTicketTest (Positive Testcase)")
	public void getTicketTest() {
		when(ticketRepository.findById(1L)).thenReturn(ticket1);
		Optional<Ticket> ticketOptional = Optional.of(audienceServiceImpl.getTicket(ticket1.get().getTicketId()));
		assertAll("Ticket in Database", () -> assertEquals(1L, ticketOptional.get().getTicketId()));
	}

	@Test
	@DisplayName("AudienceService getTicketTest (Negative Testcase)")
	void getTicketTestN() {
		when(ticketRepository.findById(1L)).thenReturn(ticket1);
		assertThrows(TicketNotFoundException.class, () -> audienceServiceImpl.getTicket(ticket2.get().getTicketId()));
	}

}
