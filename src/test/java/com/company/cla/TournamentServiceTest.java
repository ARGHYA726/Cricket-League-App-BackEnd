package com.company.cla;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.entity.Match;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.TournamentNotFoundException;
import com.company.cla.repository.MatchRepository;
import com.company.cla.repository.TournamentRepository;
import com.company.cla.service.IOrganiserService;
import com.company.cla.service.ITournamentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TournamentServiceTest {

	@Autowired
	private ITournamentService tournamentService;

	@MockBean
	private TournamentRepository tournamentRepositry;

	@MockBean
	private MatchRepository matchRepository;

	@MockBean
	private IOrganiserService organiserService;

	private Tournament tournament;
	private Tournament tournament2;

	private List<Tournament> tournaments;
	private Match match;

	private Organiser o;
	private Optional<Match> optional;

	@BeforeEach
	void setUp() throws Exception {
		match = new Match();
		match.setMatchId(1l);

		optional = Optional.of(match);

		o = new Organiser();
		o.setOrganiserId(1l);

		tournament = new Tournament(1l, "Tourn1", o, 2L, List.of(match), false);
		tournament.setMatchIds(List.of(1l));
		tournament2 = new Tournament(2l, "Tourn2", o, 2L, Collections.emptyList(), true);
		tournaments = List.of(tournament, tournament2);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Get Tournament by Id (Positive Testcase)")
	void testGetTournamentById() {
		when(tournamentRepositry.existsById(1l)).thenReturn(true);
		when(tournamentRepositry.findById(1l)).thenReturn(Optional.of(tournament));

		Tournament t = tournamentService.getTournamentById(1l);
		assertEquals(1l, t.getTournamentId());

	}

	@Test
	@DisplayName("Get Tournament by Id (Negative Testcase)")
	void testGetTournamentByIdN() {
		when(tournamentRepositry.existsById(1l)).thenReturn(false);
		when(tournamentRepositry.findById(1l)).thenReturn(Optional.of(tournament));

		assertThrows(TournamentNotFoundException.class, () -> tournamentService.getTournamentById(1l));

	}

	@Test
	@DisplayName("Get all tournaments (Positive)")
	void testGetAlTournaments() {
		when(tournamentRepositry.findAll()).thenReturn(tournaments);

		assertEquals(1l, tournamentService.getAlTournaments().get(0).getTournamentId());
		assertEquals(2l, tournamentService.getAlTournaments().get(1).getTournamentId());
	}

	@Test
	@DisplayName("Get all tournaments (Negative)")
	void testGetAlTournamentsN() {
		when(tournamentRepositry.findAll()).thenReturn(Collections.emptyList());
		assertTrue(tournamentService.getAlTournaments().size() == 0);
	}

	@Test
	@DisplayName("Insert Tournament (Positive)")
	void testInsertTorunament() {
		when(tournamentRepositry.save(tournament)).thenReturn(tournament);
		when(organiserService.getOrganiser(1l)).thenReturn(o);
		when(matchRepository.findById(1l)).thenReturn(optional);
		assertEquals(1l, tournamentService.insertTorunament(tournament).getTournamentId());
	}

	@Test
	@DisplayName("Insert Tournament (Negative)")
	void testInsertTorunamentN() {
		tournament.setMatchIds(List.of());
		when(tournamentRepositry.save(tournament)).thenReturn(tournament);
		when(organiserService.getOrganiser(1l)).thenReturn(o);
		when(matchRepository.findById(1l)).thenReturn(optional);
		assertThrows(ResponseStatusException.class,
				() -> tournamentService.insertTorunament(tournament).getTournamentId());
	}

	@Test
	@DisplayName("Update Tournament (Positive)")
	void testUpdateTournament() {
		when(tournamentRepositry.existsById(1l)).thenReturn(true);
		when(tournamentRepositry.findById(1l)).thenReturn(Optional.of(tournament));
		when(tournamentRepositry.save(tournament)).thenReturn(tournament);
		when(organiserService.getOrganiser(1l)).thenReturn(o);
		tournament.setTournamentName("T_updated");

		assertEquals("T_updated", tournamentService.updateTournament(tournament).getTournamentName());

	}

	@Test
	@DisplayName("Update Tournament (Negative)")
	void testUpdateTournamentN() {
		when(tournamentRepositry.existsById(1l)).thenReturn(false);
		when(tournamentRepositry.findById(1l)).thenReturn(Optional.of(tournament));
		when(tournamentRepositry.save(tournament)).thenReturn(tournament);
		when(organiserService.getOrganiser(1l)).thenReturn(o);
		tournament.setTournamentName("T_updated");

		assertThrows(TournamentNotFoundException.class, () -> tournamentService.updateTournament(tournament));

	}

}
