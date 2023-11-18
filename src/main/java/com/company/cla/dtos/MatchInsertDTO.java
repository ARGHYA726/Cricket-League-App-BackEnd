package com.company.cla.dtos;

import com.company.cla.entity.Schedule;

public class MatchInsertDTO {

	/* Database */
	private Long team_one_id;
	private Long team_two_id;
	private Schedule schedule;
	private Long ground_id;
	private boolean isCanceled;

	/* default constructor */
	public MatchInsertDTO() {
		super();
	}

	/* parameterized constructor */

	public MatchInsertDTO(Long team_one_id, Long team_two_id, Schedule schedule, Long ground_id) {
		super();
		this.team_one_id = team_one_id;
		this.team_two_id = team_two_id;
		this.schedule = schedule;
		this.ground_id = ground_id;
	}

	/* getters and setters */

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public Long getTeam_one_id() {
		return team_one_id;
	}

	public Long getTeam_two_id() {
		return team_two_id;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public Long getGround_id() {
		return ground_id;
	}

	public void setTeam_one_id(Long team_one_id) {
		this.team_one_id = team_one_id;
	}

	public void setTeam_two_id(Long team_two_id) {
		this.team_two_id = team_two_id;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void setGround_id(Long ground_id) {
		this.ground_id = ground_id;
	}
}
