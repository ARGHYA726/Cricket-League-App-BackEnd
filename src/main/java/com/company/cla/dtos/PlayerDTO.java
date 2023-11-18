package com.company.cla.dtos;

public class PlayerDTO {

	private Long playerId;

	private String playerName;

	private Double salary;

	private String skill;

	private Long teamId;

	public PlayerDTO(Long playerId, String playerName, Double salary, String skill, Long team_id) {
		super();
		this.playerId = playerId;
		this.playerName = playerName;
		this.salary = salary;
		this.skill = skill;
		this.teamId = team_id;
	}

	public PlayerDTO() {
		super();
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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

}
