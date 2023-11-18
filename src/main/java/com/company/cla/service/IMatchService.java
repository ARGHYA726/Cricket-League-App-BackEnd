package com.company.cla.service;

import java.util.List;
import java.util.Map;

import com.company.cla.dtos.MatchFetchDTO;
import com.company.cla.dtos.MatchInsertDTO;
import com.company.cla.entity.Audience;
import com.company.cla.entity.Match;
import com.company.cla.entity.Schedule;
import com.company.cla.entity.Team;
import com.company.cla.entity.Tournament;

public interface IMatchService {

	public List<MatchFetchDTO> getAllMatches();

	public MatchFetchDTO getMatch(Long matchId);

	public MatchFetchDTO insertMatch(MatchInsertDTO match);

	public Match updateMatch(Match match);

	public MatchFetchDTO cancelMatch(Long matchId, Boolean cancelStatus);

	public Team getTeam1(Long matchId);

	public Team getTeam2(Long matchId);

	public Map<String, String> getTeams();

	public Schedule getSchedule(Long matchId);

	public Tournament getTournament(Long matchId);

	public List<Audience> getAllAudience(Long matchId);

	public Audience getAudience(Long audienceId);

}
