package com.company.cla.service;

import java.util.List;

import com.company.cla.entity.Match;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.MatchNotFoundException;
import com.company.cla.exception.TournamentNotFoundException;

public interface ITournamentService {

	public Tournament getTournamentById(Long tournamentId) throws TournamentNotFoundException;

	public List<Tournament> getAlTournaments();

	public Tournament insertTorunament(Tournament tournament);

	public Tournament updateTournament(Tournament tournament) throws TournamentNotFoundException;

	public List<Match> getAllMatches(Long tournamentId) throws TournamentNotFoundException;

	public Match getMatch(Long tournamentId, Long matchId) throws MatchNotFoundException, TournamentNotFoundException;
}
