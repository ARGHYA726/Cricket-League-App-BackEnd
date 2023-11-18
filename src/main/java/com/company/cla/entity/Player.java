package com.company.cla.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Player entity class
 */
@Entity
@Table(name = "player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "player_id")
	private Long playerId;

	@Column(name = "player_name")
	private String playerName;

	@Column(name = "salary")
	private Double salary;

	@Column(name = "skill")
	private Skill skill;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "team_id")
	//@JsonIgnore
	private Team team;
	@Transient
	private Long teamId;
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public Player() {

	}

	public Player(Long playerId, String playerName, Double salary, Skill skill, Team team) {

		this.playerId = playerId;
		this.playerName = playerName;
		this.salary = salary;
		this.skill = skill;
		this.team = team;
	}

	public Player(String playerName, Double salary, Skill skill, Team team) {

		this.playerName = playerName;
		this.salary = salary;
		this.skill = skill;
		this.team = team;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
