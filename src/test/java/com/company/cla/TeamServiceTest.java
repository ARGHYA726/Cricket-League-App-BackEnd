package com.company.cla;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.cla.dtos.TeamDTO;
import com.company.cla.entity.Match;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Player;
import com.company.cla.entity.Team;
import com.company.cla.exception.TeamNotFoundException;
import com.company.cla.repository.TeamRepository;
import com.company.cla.serviceimpl.TeamServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {
	@MockBean(name = "teamRepository")
	private TeamRepository teamRepository;
	@InjectMocks
	@Autowired
	private TeamServiceImpl teamService;

	static Match match = new Match();
	static Owner owner = new Owner();
	static List<Team> teamList;
	static Player playerOne = new Player();
	static Player playerTwo = new Player();
	static List<Player> playerList = List.of(playerOne, playerTwo);
	static Optional<Team> teamOne = Optional.of(new Team(1L, "TeamOne", match, playerList, owner));
	static Optional<Team> teamTwo = Optional.of(new Team(2L, "TeamTwo", match, playerList, owner));

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		teamList = List.of(teamOne.get(), teamTwo.get());
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testGetTeam() {
		when(teamRepository.existsById(1L)).thenReturn(true);
		when(teamRepository.findById(1L)).thenReturn(teamOne);

		Team team = teamService.getTeam(1L);

		assertEquals(1L, team.getTeamId());
	}

	@Test
	void testGetTeamNegative() {
		when(teamRepository.existsById(1L)).thenReturn(false);

		assertThrows(TeamNotFoundException.class, () -> teamService.getTeam(1L));
	}

	@Test
	void testGetAllTeams() {
		when(teamRepository.findAll()).thenReturn(teamList);

		List<TeamDTO> dtoListTest = teamService.getAllTeamsDTO();

		assertAll("Teams in database", () -> assertEquals(1L, dtoListTest.get(0).getTeamId()),
				() -> assertEquals(2L, dtoListTest.get(1).getTeamId()));
	}

	@Test
	void testGetAllTeamsNegative() {
		when(teamRepository.findAll()).thenReturn(Collections.emptyList());

		assertThrows(TeamNotFoundException.class, () -> teamService.getAllTeamsDTO());
	}
}
