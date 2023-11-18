package com.company.cla.dtos;

import java.util.List;

import org.springframework.lang.NonNull;

import com.company.cla.entity.Match;

public class TournamentDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long tournamentId;
	@NonNull
	private String tournamentName;
//	@NonNull

	private Long organiser_id;
//	@NonNull

	private List<Match> matches;
	private boolean cancel;

	public TournamentDTO() {

	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public Long getOrganiser_id() {
		return organiser_id;
	}

	public void setOrganiser_id(Long organiser_id) {
		this.organiser_id = organiser_id;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

}
