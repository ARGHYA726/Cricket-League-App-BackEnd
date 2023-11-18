package com.company.cla.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.core.lang.NonNull;

@Entity
@Table(name = "team")
public class Team {
	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long teamId;

	@NonNull
	@Column(name = "teamname", nullable = false)
	private String teamName;

	@ManyToOne(targetEntity = Match.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "match_fk")
	private Match match;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Player> players;

	@Transient
	private Long ownerId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_Id")
	private Owner owner;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Team() {
		super();
	}

	public Team(Long teamId, String teamName, Match matches, List<Player> players, Owner owner) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.match = matches;
		this.players = players;
		this.owner = owner;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Match getMatches() {
		return match;
	}

	public void setMatches(Match matches) {
		this.match = matches;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
