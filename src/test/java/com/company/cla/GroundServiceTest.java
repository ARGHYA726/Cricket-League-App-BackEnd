package com.company.cla;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.cla.entity.Address;
import com.company.cla.entity.Ground;
import com.company.cla.entity.Match;
import com.company.cla.repository.GroundRepository;
import com.company.cla.service.IGroundService;

@RunWith(SpringRunner.class)
@SpringBootTest
class GroundServiceTest {

	private static List<Ground> grounds;

	private static Address address = new Address(6547, "City1", "state1", "country1");
	private static Ground g1 = new Ground(1001l, "Ground1", 3, new Match(), address);
	private static Ground g2 = new Ground(1002l, "Ground2", 4, new Match(), address);

	@Autowired
	private IGroundService groundService;

	@MockBean(name = "groundRepository")
	private GroundRepository groundRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		grounds = List.of(g1, g2);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGroundServiceImpl() {
//		constructor test
	}

	@Test
	@DisplayName("Get all grounds (Positive Case)")
	void testGetAllGroundP() {
		when(groundRepository.findAll()).thenReturn(grounds);

		List<Ground> g = groundService.getAllGround();

		assertAll("Getting all grounds", () -> assertEquals(g.get(0).getGroundId(), grounds.get(0).getGroundId()),
				() -> assertEquals(g.get(0).getGroundName(), grounds.get(0).getGroundName()));
	}

	@Test
	@DisplayName("Get all grounds (Negative Case)")
	void testGetAllGroundN() {
		when(groundRepository.findAll()).thenReturn(Collections.emptyList());

		List<Ground> g = groundService.getAllGround();

		assertAll("Getting empty ground list", () -> assertTrue(g.size() == 0));
	}
}
