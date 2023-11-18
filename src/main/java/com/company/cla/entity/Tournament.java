package com.company.cla.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

@Entity
public class Tournament implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tournament_id")
	private Long tournamentId;

	private String tournamentName;
	@NonNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORGANISER_ID")
	private Organiser organiser;

	@Transient
	private Long organiserId;

	public Long getOrganiserId() {
		return organiserId;
	}

	public void setOrganiserId(Long organiserId) {
		this.organiserId = organiserId;
	}

	@Transient
	private List<Long> matchIds;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tournament")
	private List<Match> matches;

	private boolean cancel;

	public Tournament() {

	}

	public Tournament(Long tournamentId, String tournamentName, Organiser organiser, Long organiserId,
			List<Match> matches, boolean cancel) {
		super();
		this.tournamentId = tournamentId;
		this.tournamentName = tournamentName;
		this.organiser = organiser;
		this.organiserId = organiserId;
		this.matches = matches;
		this.cancel = cancel;
	}

	public void setMatchIds(List<Long> matchIds) {
		this.matchIds = matchIds;
	}

	public List<Long> getMatchIds() {
		return this.matchIds;
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

	public Organiser getOrganiser() {
		return organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

}
