/**
 * 
 */
package com.company.cla;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.cla.dtos.OrganiserDTO;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Team;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.OrganiserInvalidEmailException;
import com.company.cla.exception.OrganiserInvalidPaymentException;
import com.company.cla.exception.OrganiserInvalidPhoneException;
import com.company.cla.exception.OrganiserNotFoundException;
import com.company.cla.repository.OrganiserRepository;
import com.company.cla.repository.OwnerRepository;
import com.company.cla.serviceimpl.OrganiserServiceImpl;
import com.company.cla.serviceimpl.OwnerServiceImpl;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganiserServiceTest {

	@Mock
	private OwnerServiceImpl ownerService;

	@MockBean(name = "organiserRepository")
	private OrganiserRepository organiserRepository;

	@MockBean(name = "ownerRepository")
	private OwnerRepository ownerRepository;

	@InjectMocks
	@Autowired
	private OrganiserServiceImpl organiserService;

	static List<Organiser> organiserList;
	static Team team = new Team();
	static Owner owner = new Owner(1L, "Owner", 10000000D, team);
	static Optional<Owner> ownerOptional = Optional.of(owner);

	static Tournament tournament = new Tournament();
	static Tournament tournamentTwo = new Tournament();
	static List<Tournament> tournamentList = List.of(tournament, tournamentTwo);
	static Optional<Organiser> organiserOne = Optional
			.of(new Organiser(1L, "OrganiserOne", "abc@gmail.com", 9903381234L, 10, 40, tournamentList));
	static Optional<Organiser> organiserTwo = Optional
			.of(new Organiser(2L, "OrganiserTwo", "abcd@gmail.com", 9903381235L, 20, 30, tournamentList));

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		organiserList = List.of(organiserOne.get(), organiserTwo.get());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testGetOrganiser() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);

		Organiser organiser = organiserService.getOrganiser(1L);

		assertEquals(1L, organiser.getOrganiserId());
	}

	@Test
	void testGetOrganiserNegative() {
		when(organiserRepository.existsById(1L)).thenReturn(false);

		assertThrows(OrganiserNotFoundException.class, () -> organiserService.getOrganiser(1L));
	}

	@Test
	void testGetAllOrganisers() {
		when(organiserRepository.findAll()).thenReturn(organiserList);

		List<OrganiserDTO> dtoListTest = organiserService.getAllOrganisers();

		assertAll("Organisers in database", () -> assertEquals(1L, dtoListTest.get(0).getOrganiserId()),
				() -> assertEquals(2L, dtoListTest.get(1).getOrganiserId()));
	}

	@Test
	void testGetAllOrganisersNegative() {
		when(organiserRepository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(OrganiserNotFoundException.class, () -> organiserService.getAllOrganisers());
	}

	@Test
	void testUpdateOrganiser() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);
		when(organiserRepository.save(organiserOne.get())).thenReturn(organiserOne.get());
		Organiser organiser = organiserService.updateOrganiser(1L, organiserOne.get());

		assertEquals(1L, organiser.getOrganiserId());
	}

	@Test
	void testUpdateOrganiserPhoneException() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abc@gmail.com", 99033812343L, 10, 20, tournamentList);
		assertThrows(OrganiserInvalidPhoneException.class, () -> organiserService.updateOrganiser(1L, organiser));
	}

	@Test
	void testUpdateOrganiserEmailException() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abab", 9903381234L, 10, 20, tournamentList);
		assertThrows(OrganiserInvalidEmailException.class, () -> organiserService.updateOrganiser(1L, organiser));
	}

	@Test
	void testUpdateOrganiserPaymentException() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abc@gmail.com", 9903381234L, 30, 20, tournamentList);
		assertThrows(OrganiserInvalidPaymentException.class, () -> organiserService.updateOrganiser(1L, organiser));
	}

	@Test
	void testInsertOrganiser() {
		when(organiserRepository.save(organiserOne.get())).thenReturn(organiserOne.get());
		Organiser organiser = organiserService.insertOrganiser(organiserOne.get());

		assertEquals(1L, organiser.getOrganiserId());
	}

	@Test
	void testInsertOrganiserPhoneException() {
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abc@gmail.com", 99033812343L, 10, 20, tournamentList);
		assertThrows(OrganiserInvalidPhoneException.class, () -> organiserService.insertOrganiser(organiser));
	}

	@Test
	void testInsertOrganiserEmailException() {
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abab", 9903381234L, 10, 20, tournamentList);
		assertThrows(OrganiserInvalidEmailException.class, () -> organiserService.insertOrganiser(organiser));
	}

	@Test
	void testInsertOrganiserPaymentException() {
		Organiser organiser = new Organiser(1L, "OrganiserOne", "abc@gmail.com", 9903381234L, 30, 20, tournamentList);
		assertThrows(OrganiserInvalidPaymentException.class, () -> organiserService.insertOrganiser(organiser));
	}

	@Test
	void testGetTournaments() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);

		List<Tournament> tournaments = organiserService.getTournaments(1L);
		assertNotNull(tournaments);
	}

	@Test
	void testPayPrizeMoney() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);
		when(organiserRepository.save(organiserOne.get())).thenReturn(organiserOne.get());
		when(ownerRepository.findById(owner.getOwnerId())).thenReturn(ownerOptional);
		when(ownerService.updateOwner(owner)).thenReturn(owner);

		double payment = organiserService.payPrizeMoney(1L, owner);
		assertEquals(10, payment);
	}

	@Test
	void testPayPrizeMoneyNegative() {
		when(organiserRepository.existsById(1L)).thenReturn(false);
		assertThrows(OrganiserNotFoundException.class, () -> organiserService.payPrizeMoney(1L, owner));
	}

	@Test
	void testGetBudget() {
		when(organiserRepository.existsById(1L)).thenReturn(true);
		when(organiserRepository.findById(1L)).thenReturn(organiserOne);

		double budget = organiserService.getBudget(1L);
		assertEquals(30, budget);
	}

	@Test
	void testGetBudgetNegative() {
		when(organiserRepository.existsById(1L)).thenReturn(false);
		assertThrows(OrganiserNotFoundException.class, () -> organiserService.getBudget(1L));
	}

}
