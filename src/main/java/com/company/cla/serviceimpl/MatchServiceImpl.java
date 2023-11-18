package com.company.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.company.cla.dtos.MatchFetchDTO;
import com.company.cla.dtos.MatchInsertDTO;
import com.company.cla.entity.Audience;
import com.company.cla.entity.Ground;
import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Team;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.GroundNotFoundException;
import com.company.cla.exception.MatchNotFoundException;
import com.company.cla.exception.TeamNotFoundException;
import com.company.cla.repository.MatchRepository;
import com.company.cla.repository.TeamRepository;
import com.company.cla.service.IGroundService;
import com.company.cla.service.IMatchService;
import com.company.cla.service.ITeamService;
import com.company.cla.utils.MatchUtils;

/**
 * Implementation of IMatchService interface
 *
 */
@Service
public class MatchServiceImpl implements IMatchService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ITeamService teamService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private IGroundService groundService;

	/**
	 * gets all the matches in the database.
	 * 
	 * @return List : list of match dtos
	 */
	@Override
	public List<MatchFetchDTO> getAllMatches() {
		log.info("Inside getAllMatches function");

		return matchRepository.findAll().stream().map(match -> MatchUtils.convertToMatchFetchDTO(match))
				.collect(Collectors.toList());
	}

	/**
	 * used to get the match of a given id number.
	 * 
	 * @param matchId : the id of the match to be found
	 * @return MatchFetchDTO : the dto of the match.
	 * @throws MatchNotFoundException
	 */
	@Override
	public MatchFetchDTO getMatch(Long matchId) {

		log.info("Inside getAllMatches function");
		MatchFetchDTO dto;

		Optional<Match> match = matchRepository.findById(matchId);
		if (match.isPresent()) {
			dto = MatchUtils.convertToMatchFetchDTO(match.get());
		} else {
			log.info("Inside getAllMatches function");
			throw new MatchNotFoundException("Match not found");
		}

		return dto;
	}

	/**
	 * used to insert a match detail in the database.
	 * 
	 * @param matchDto : the dto of the match to be inserted.
	 * @return match : the match which is inserted successfully.
	 * @throws ResponseStatusException
	 */
	@Override
	public MatchFetchDTO insertMatch(MatchInsertDTO matchDto) {

		log.info("Inside insertMatch function");
//		creating new match object
		Match match = new Match();
		match.setTeams(new ArrayList<>(2));

//		checking if both team ids are different or not
		if (matchDto.getTeam_one_id() == matchDto.getTeam_two_id()) {

			log.warn("ResponseStatusException thrown");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BOTH TEAMS SHOULD BE DIFFERENT");
		}

		List<Team> teams = match.getTeams();

//		retrive team_one by id
		try {
			Team teamOne = teamService.getTeam(matchDto.getTeam_one_id());
			teamOne.setMatches(match);
			teams.add(teamOne);

			Team teamTwo = teamService.getTeam(matchDto.getTeam_two_id());
			teamTwo.setMatches(match);
			teams.add(teamTwo);

			match.setTeams(teams);

			match.setSchedule(matchDto.getSchedule());
			match.setCanceled(matchDto.isCanceled());
			Ground ground = groundService.getGroundById(matchDto.getGround_id());
			match.setGround(ground);

			matchRepository.save(match);

		} catch (TeamNotFoundException | GroundNotFoundException e) {
			log.warn("TeamNotFoundException or GroundNotFoundException thrown");
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, e.getMessage());
		}

		return MatchUtils.convertToMatchFetchDTO(match);

	}

	/**
	 * Updates the available match.
	 * 
	 * @param match : the match details to be updated.
	 * @return match : the updated match details
	 * @throws MatchNotFoundException
	 */
	@Override
	public Match updateMatch(Match match) {

		log.info("Inside updateMatch function");

		Team teamOne = match.getTeams().get(0);
		Team teamTwo = match.getTeams().get(1);
		List<Team> teams = new ArrayList<>(2);

		Optional<Match> existingMatch = matchRepository.findById(match.getMatchId());
		if (existingMatch.isPresent()) {
			if (teamOne != null) {
				teams.add(teamOne);
			}
			if (teamTwo != null) {
				teams.add(teamTwo);
				existingMatch.get().setTeams(teams);
			}
			if (match.getTournament() != null) {
				existingMatch.get().setTournament(match.getTournament());
			}

			if (match.getSchedule() != null) {
				existingMatch.get().setSchedule(match.getSchedule());
			}
			if (match.getGround() != null) {
				existingMatch.get().setGround(match.getGround());
			}

			if (match.getAudience() != null) {
				existingMatch.get().setAudience(match.getAudience());
			}
		} else {
			log.warn("MatchNotFoundException thrown");
			throw new MatchNotFoundException("Match not found");
		}

		return matchRepository.save(existingMatch.get());
	}

	/**
	 * used to cancel a match.
	 * 
	 * @param matchId      : the id of the match to be cancelled or not.
	 * @param cancelStatus : the boolean status of the match.
	 * @return MatchFetchDto : the updated details of the match.
	 * @throws MatchNotFoundException
	 */
	@Override
	public MatchFetchDTO cancelMatch(Long matchId, Boolean cancelStatus) {

		log.info("Inside cancelMatch function");

		Match existingMatch = matchRepository.findById(matchId)
				.orElseThrow(() -> new MatchNotFoundException("Match not found"));
		existingMatch.setCanceled(cancelStatus);
		matchRepository.save(existingMatch);
		return MatchUtils.convertToMatchFetchDTO(existingMatch);
	}

	/**
	 * used to get teams of 1st group.
	 * 
	 * @return List : list of the 1st group teams
	 */
	@Override
	public Team getTeam1(Long matchId) {
		Team team = null;
		Optional<Match> match = matchRepository.findById(matchId);
		if (match.isPresent()) {
			team = match.get().getTeams().get(0);
		} else {
			log.warn("Match not found exception");
			throw new MatchNotFoundException("Invalid match id");
		}
		return team;
	}

	/**
	 * used to get teams of 2nd group.
	 * 
	 * @return List : list of the 2nd group teams
	 */
	@Override
	public Team getTeam2(Long matchId) {
		Team team = null;
		Optional<Match> match = matchRepository.findById(matchId);
		if (match.isPresent()) {
			team = match.get().getTeams().get(1);
		} else {
			log.warn("Match not found exception");
			throw new MatchNotFoundException("Invalid match id");
		}
		return team;
	}

	/**
	 * used to get the team match combinations
	 * 
	 * @return Map: the team combinations of the match.
	 */
	@Override
	public Map<String, String> getTeams() {
		Map<String, String> teams = new LinkedHashMap<>();
		Iterator<Team> teamOnes = matchRepository.findAll().stream().map(match -> match.getTeams().get(0))
				.collect(Collectors.toList()).iterator();
		Iterator<Team> teamTwos = matchRepository.findAll().stream().map(match -> match.getTeams().get(1))
				.collect(Collectors.toList()).iterator();

		while (teamOnes.hasNext() && teamTwos.hasNext()) {
			teams.put(teamOnes.next().getTeamName(), teamTwos.next().getTeamName());
		}

		return teams;

	}

	/**
	 * used to get the schedule of a given match.
	 * 
	 * @param matchId : the match id whose schedule needs to be retrieved.
	 * @return Schedule: the schedule of the match.
	 * @throws MAtchNotFoundException
	 */
	@Override
	public Schedule getSchedule(Long matchId) {
		return matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"))
				.getSchedule();
	}

	/**
	 * get the tournament details by the match.
	 * 
	 * @param matchId : the id of the match whose tournament details are needed.
	 * @return tournament :the tournament details.
	 * @throws MatchNotFoundException
	 */
	@Override
	public Tournament getTournament(Long matchId) {
		return matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"))
				.getTournament();
	}

	/**
	 * get the audience list of the match.
	 * 
	 * @param matchId : the id of the match whose audience list is needed.
	 * @return List :the list of audiences.
	 * @throws MatchNotFoundException
	 */
	@Override
	public List<Audience> getAllAudience(Long matchId) {
		return matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"))
				.getAudience();
	}

	@Override
	public Audience getAudience(Long audienceId) {
		return null;
	}

}
