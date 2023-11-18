package com.company.cla;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.dtos.MatchFetchDTO;
import com.company.cla.dtos.MatchInsertDTO;
import com.company.cla.entity.Address;
import com.company.cla.entity.Audience;
import com.company.cla.entity.Ground;
import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Team;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.MatchNotFoundException;
import com.company.cla.repository.MatchRepository;
import com.company.cla.repository.TeamRepository;
import com.company.cla.service.IGroundService;
import com.company.cla.service.IMatchService;
import com.company.cla.service.ITeamService;

@RunWith(SpringRunner.class)
@SpringBootTest
class MatchServiceTest {

	private static List<Match> matches;

	private Team teamOne;
	private Team teamTwo;
	private Tournament tournament;
	private Schedule schedule;
	private Ground ground;
	private List<Audience> audiences;

	private MatchFetchDTO dto1;
	private MatchFetchDTO dto2;

	private MatchInsertDTO iDto;

	private Match match1;
	private Match match2;

	@MockBean
	private MatchRepository matchRepository;

	@MockBean
	private ITeamService teamService;

	@MockBean
	private IGroundService groundService;

	@MockBean
	private TeamRepository teamRepository;

	@Autowired
	private IMatchService matchService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		teamOne = new Team();
		teamOne.setTeamId(1l);
		teamTwo = new Team();
		teamTwo.setTeamId(2l);

		tournament = new Tournament();
		tournament.setTournamentId(1L);

		schedule = new Schedule();
		ground = new Ground();
		ground.setGroundId(1L);

		audiences = Collections.emptyList();

		dto1 = new MatchFetchDTO(1L, 2L, "Team1", 3L, "Team2", schedule, false, 4l, "Ground1", new Address());
		dto2 = new MatchFetchDTO(1L, 2L, "Team1", 3L, "Team2", schedule, false, 5l, "Ground2", new Address());

		List<Team> teams = List.of(teamOne, teamTwo);
		List<Team> teams1 = List.of(teamTwo, teamOne);

		match1 = new Match(1L, teams, schedule, ground, false, tournament, audiences);
		match2 = new Match(2L, teams1, schedule, ground, false, tournament, audiences);

		iDto = new MatchInsertDTO(1l, 2l, schedule, 3l);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Get all Matches (Positive TestCase)")
	void testGetAllMatches() {
		when(matchRepository.findAll()).thenReturn(List.of(match1, match2));

		List<MatchFetchDTO> matchDtos = matchService.getAllMatches();

		assertAll("Getting all matches", () -> assertEquals(1l, matchDtos.get(0).getMatch_id()),
				() -> assertEquals(2L, matchDtos.get(1).getMatch_id()), () -> assertTrue(matchDtos.size() == 2),
				() -> assertEquals(1L, matchDtos.get(0).getGround_id()));
	}

	@Test
	@DisplayName("Get all Matches (Negative Testcase")
	void testGetAllMatchesN() {
		when(matchRepository.findAll()).thenReturn(Collections.emptyList());

		List<MatchFetchDTO> matchDtos = matchService.getAllMatches();

		assertTrue(matchDtos.isEmpty());
	}

	@Test
	@DisplayName("Get Match (Positive TestCase)")
	void testGetMatch() {
		when(matchRepository.findById(1L)).thenReturn(Optional.of(match1));

		MatchFetchDTO dto = matchService.getMatch(1L);

		assertAll("Getting match by id", () -> assertEquals(1l, dto.getMatch_id()),
				() -> assertEquals(2L, dto.getTeam_two_id()));
	}

	@Test
	@DisplayName("Get Match (Negative TestCase)")
	void testGetMatchN() {
		when(matchRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(MatchNotFoundException.class, () -> matchService.getMatch(1L));
	}

	@Test
	@DisplayName("Insert Match (Positive)")
	void testInsertMatch() {

		when(matchRepository.save(match1)).thenReturn(match1);
		when(teamService.getTeam(1l)).thenReturn(teamOne);
		when(teamService.getTeam(2l)).thenReturn(teamTwo);
		when(groundService.getGroundById(3l)).thenReturn(ground);
		when(teamRepository.save(teamOne)).thenReturn(teamOne);
		when(teamRepository.save(teamTwo)).thenReturn(teamTwo);

		MatchFetchDTO m = matchService.insertMatch(iDto);
		Long teamOneId = m.getTeam_one_id();
		Long teamTwoId = m.getTeam_two_id();

		assertAll("Insert Match", () -> assertEquals(1l, teamOneId), () -> assertEquals(2L, teamTwoId),
				() -> assertEquals(1l, m.getGround_id()));
	}

	@Test
	@DisplayName("Insert Match (Negative)")
	void testInsertMatchN() {

		iDto.setTeam_one_id(1l);
		iDto.setTeam_two_id(1l);

		assertThrows(ResponseStatusException.class, () -> matchService.insertMatch(iDto));

	}

	@Test
	@DisplayName("Update Match (Positive)")
	void testUpdateMatchN() {
		when(matchRepository.findById(1L)).thenReturn(Optional.of(match1));
		when(matchRepository.save(match1)).thenReturn(match1);

		match1.setTeams(List.of(new Team(3l, null, match1, null, null), match1.getTeams().get(1)));
		Match updatedMatch = matchService.updateMatch(match1);
		assertAll("Updated Match", () -> assertEquals(3l, updatedMatch.getTeams().get(0).getTeamId()));
	}

	@Test
	@DisplayName("Update Match (Negative)")
	void testUpdateMatch() {
		when(matchRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(MatchNotFoundException.class, () -> matchService.updateMatch(match2));

	}

	@Test
	void testCancelMatch() {
//		fail("Not yet implemented");
	}
}
