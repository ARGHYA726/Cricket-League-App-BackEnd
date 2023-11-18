package com.company.cla;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
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

import com.company.cla.dtos.OwnerDTO;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Team;
import com.company.cla.exception.OwnerNotFoundException;
import com.company.cla.repository.OwnerRepository;
import com.company.cla.service.IPlayerService;
import com.company.cla.service.ITeamService;
import com.company.cla.serviceimpl.OwnerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerServiceImplTests {

	private static List<Owner> owners;
	private static Owner owner;

	@Autowired
	private OwnerServiceImpl ownerServiceImpl;

	@Autowired
	ITeamService teamService;

	@Autowired
	IPlayerService playerService;

//	injecting the application repository
	@MockBean(name = "ownerRepository")
	private OwnerRepository ownerRepository;
	static Optional<Owner> owner1 = Optional.of(new Owner(1L, "Owner1", (double) 100, new Team()));
	static Optional<Owner> owner2 = Optional.of(new Owner(2L, "Owner2", (double) 200, new Team()));

	@BeforeAll
	public static void setUp() {
		owners = List.of(owner1.get(), owner2.get());
	}

	@Test
	@DisplayName("Find all owners(Postive TestCase)")
	public void getAllOwnersTest() {
		when(ownerRepository.findAll()).thenReturn(owners);

		List<OwnerDTO> os = ownerServiceImpl.getAllOwners();

		assertAll("Owners in Database", () -> assertEquals(1, os.get(0).getOwnerId()),
				() -> assertEquals(2, os.get(1).getOwnerId()));
	}

	@Test
	@DisplayName("Find all owners (Negative TestCase)")
	public void getAllOwnersTestN() {
		when(ownerRepository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(OwnerNotFoundException.class, () -> ownerServiceImpl.getAllOwners());
	}

	@Test
	@DisplayName("Find owner by id(Postive TestCase)")
	public void getOwnerTest() {
		when(ownerRepository.findById(owner1.get().getOwnerId())).thenReturn(owner1);

		Optional<Owner> os = Optional.of(ownerServiceImpl.getOwner(owner1.get().getOwnerId()));

		assertAll("Owners in Database", () -> assertEquals(os.get(), owner1.get()));
	}

	@Test
	@DisplayName("Find owner by id (Negative TestCase)")
	public void getOwnerTestN() {
		when(ownerRepository.findById(owner1.get().getOwnerId())).thenReturn(owner1);
		assertThrows(OwnerNotFoundException.class, () -> ownerServiceImpl.getOwner(owner2.get().getOwnerId()));
	}

}
