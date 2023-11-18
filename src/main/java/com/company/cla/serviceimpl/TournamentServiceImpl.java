package com.company.cla.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.entity.Match;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.MatchNotFoundException;
import com.company.cla.exception.OrganiserNotFoundException;
import com.company.cla.exception.TournamentNotFoundException;
import com.company.cla.repository.MatchRepository;
import com.company.cla.repository.TournamentRepository;
import com.company.cla.service.IOrganiserService;
import com.company.cla.service.ITournamentService;

@Service
public class TournamentServiceImpl implements ITournamentService {

	private final Logger log = LoggerFactory.getLogger("Tournament Logger");

	@Autowired
	private TournamentRepository tournamentRepositry;

	@Autowired
	private MatchRepository matchRepository;

	private Tournament existingTournament;

	@Autowired
	private IOrganiserService org;

	/**
	 * This method finds Tournament based on tournament id and throws an exception
	 * if tournament is not found.
	 *
	 * @param Long: tournamentId
	 * @return Tournament : tournament
	 * @see TournamentNotFoundException
	 */

	@Override
	public Tournament getTournamentById(Long tournamentId) throws TournamentNotFoundException {
		if (tournamentRepositry.existsById(tournamentId))
			return tournamentRepositry.findById(tournamentId).get();
		throw new TournamentNotFoundException("No Tournament is Exist with this id");

	}

	/**
	 * This method find all the Tournaments in the database
	 *
	 * @return List: Tournament
	 */
	@Override
	public List<Tournament> getAlTournaments() {
		return tournamentRepositry.findAll();
	}

	/* inserting a new tournament */

	/**
	 * This method is used to insert Tournament in the database and throws an
	 * exceptions if match is null or Organiser is null.
	 *
	 * @param Tournament: tournament
	 * @return Tournament: tournament
	 * @see OrganiserNotFoundException,MatchNotFoundException
	 */
	@Override
	public Tournament insertTorunament(Tournament tournament) {
		try {
			Organiser o = org.getOrganiser(tournament.getOrganiserId());
			System.err.println("ORGANISER DEBUGGING=" + o);
			tournament.setOrganiser(o);

			/* get the matchids from list and set the matches in list */
			if (tournament.getMatchIds().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one match should exist");
			}

			tournament
					.setMatches(tournament.getMatchIds().stream().filter(id -> matchRepository.findById(id).isPresent())
							.map(id -> matchRepository.findById(id)
									.orElseThrow(() -> new MatchNotFoundException("Match not found")))
							.collect(Collectors.toList()));

			/* setting tournament in match */

			tournament.getMatches().forEach(match -> {

//				System.err.println(match.getTournament());
				match.setTournament(tournament);
//				System.err.println(match.getTournament());
//				matchRepository.save(match);
			});

		} catch (OrganiserNotFoundException o) {
			log.warn(o.getMessage() + "Exception thrown");
		}
//		tournament.setTournamentId(tournament.getMatches().get(0).getTournament().getTournamentId());
		return tournamentRepositry.save(tournament);
	}

	/**
	 * This method is used to update tournament details and throws an exception if
	 * tournament is not found.
	 *
	 * @param Tournament: tournament
	 * @return Tournament: existingTournament
	 * @see TournamentNotFoundException
	 */
	@Override
	public Tournament updateTournament(Tournament tournament) throws TournamentNotFoundException {
		log.info("Update started");
		if (tournamentRepositry.existsById(tournament.getTournamentId())) {
			existingTournament = tournamentRepositry.findById(tournament.getTournamentId()).get();
			existingTournament.setTournamentName(tournament.getTournamentName());

			try {
				Organiser o = org.getOrganiser(tournament.getOrganiserId());
				System.err.println("ORGANISER DEBUGGING=" + o);
				existingTournament.setOrganiser(o);
			} catch (OrganiserNotFoundException o) {
				log.warn(o.getMessage());
			}

			return tournamentRepositry.save(existingTournament);
		}
		throw new TournamentNotFoundException("No Tournament is Found to Update");
	}

	/**
	 * This method find all the matches in the database
	 *
	 * @return List: Match
	 */
	@Override
	public List<Match> getAllMatches(Long tournamentId) throws TournamentNotFoundException {
		if (tournamentRepositry.existsById(tournamentId))
			return tournamentRepositry.findById(tournamentId).get().getMatches();
		throw new TournamentNotFoundException("Tournament is Not Found");
	}

	/**
	 * This method find the match in the database and throws Exception if not found
	 *
	 * @param Long: tournamentId
	 * @param Long: matchId
	 * @return Match: Match
	 * @see TournamentNotFoundException, MatchNotFoundException
	 */
	@Override
	public Match getMatch(Long tournamentId, Long matchId) throws MatchNotFoundException, TournamentNotFoundException {
		if (tournamentRepositry.existsById(tournamentId)) {
			existingTournament = tournamentRepositry.findById(tournamentId).get();
			List<Match> match = existingTournament.getMatches().stream().filter(p -> p.getMatchId() == matchId)
					.collect(Collectors.toList());
			if (match.isEmpty())
				throw new MatchNotFoundException("Match Not Found");
			return match.get(0);
		}

		throw new TournamentNotFoundException("Tournament Not Found");
	}

}
