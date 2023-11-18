package com.company.cla.dtos;

import com.company.cla.entity.Address;
import com.company.cla.entity.Schedule;

public class MatchFetchDTO {
	private Long match_id;
	private Long team_one_id;
	private String team_one_name;
	private Long team_two_id;
	private String team_two_name;
	private Schedule schedule;
	private boolean status;

//	ground details
	private Long ground_id;
	private String ground_name;
	private Address ground_address;

//	tournament details

	private Long tournament_id;
	private String tournament_name;

	public MatchFetchDTO() {
		super();
	}

	public MatchFetchDTO(Long match_id, Long team_one_id, String team_one_name, Long team_two_id, String team_two_name,
			Schedule schedule, boolean status, Long ground_id, String ground_name, Address ground_address) {
		super();
		this.match_id = match_id;
		this.team_one_id = team_one_id;
		this.team_one_name = team_one_name;
		this.team_two_id = team_two_id;
		this.team_two_name = team_two_name;
		this.schedule = schedule;
		this.ground_id = ground_id;
		this.ground_name = ground_name;
		this.ground_address = ground_address;
		this.status = status;
	}

	public Long getMatch_id() {
		return match_id;
	}

	public Long getTeam_one_id() {
		return team_one_id;
	}

	public String getTeam_one_name() {
		return team_one_name;
	}

	public Long getTeam_two_id() {
		return team_two_id;
	}

	public String getTeam_two_name() {
		return team_two_name;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public boolean isStatus() {
		return status;
	}

	public Long getGround_id() {
		return ground_id;
	}

	public String getGround_name() {
		return ground_name;
	}

	public Address getGround_address() {
		return ground_address;
	}

	public Long getTournament_id() {
		return tournament_id;
	}

	public String getTournament_name() {
		return tournament_name;
	}

	public void setMatch_id(Long match_id) {
		this.match_id = match_id;
	}

	public void setTeam_one_id(Long team_one_id) {
		this.team_one_id = team_one_id;
	}

	public void setTeam_one_name(String team_one_name) {
		this.team_one_name = team_one_name;
	}

	public void setTeam_two_id(Long team_two_id) {
		this.team_two_id = team_two_id;
	}

	public void setTeam_two_name(String team_two_name) {
		this.team_two_name = team_two_name;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setGround_id(Long ground_id) {
		this.ground_id = ground_id;
	}

	public void setGround_name(String ground_name) {
		this.ground_name = ground_name;
	}

	public void setGround_address(Address ground_address) {
		this.ground_address = ground_address;
	}

	public void setTournament_id(Long tournament_id) {
		this.tournament_id = tournament_id;
	}

	public void setTournament_name(String tournament_name) {
		this.tournament_name = tournament_name;
	}

}
