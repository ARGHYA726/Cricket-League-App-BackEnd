package com.company.cla.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Match {

	@Id
	@SequenceGenerator(name = "match_seq", sequenceName = "MATCH_SEQ", initialValue = 1100, allocationSize = 1)
	@GeneratedValue(generator = "match_seq", strategy = GenerationType.SEQUENCE)
	@Column(name = "match_id")
	public Long matchId;

	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Team> teams;

	private Schedule schedule;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ground_fk", referencedColumnName = "ground_id")
	private Ground ground;

	@Column(name = "is_canceled", nullable = false)
	private boolean isCanceled;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "tournament_fk")
	@JsonIgnore
	private Tournament tournament;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "match")
	private List<Audience> audience;

	/* =========================default constructor============================= */

	public Match() {
		super();
	}

	/*
	 * =========================parameterized constructor==================
	 */

	public Match(Long matchId, List<Team> teams, Schedule schedule, Ground ground, boolean isCanceled,
			Tournament tournament, List<Audience> audience) {
		super();
		this.matchId = matchId;
		this.teams = teams;
		this.schedule = schedule;
		this.ground = ground;
		this.isCanceled = isCanceled;
		this.tournament = tournament;
		this.audience = audience;
	}

	/*
	 * =========================getter and setter methods=======================
	 */

	public Long getMatchId() {
		return matchId;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public Ground getGround() {
		return ground;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public List<Audience> getAudience() {
		return audience;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public void setAudience(List<Audience> audience) {
		this.audience = audience;
	}

}
