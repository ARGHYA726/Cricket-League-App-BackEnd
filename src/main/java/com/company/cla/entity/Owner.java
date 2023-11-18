package com.company.cla.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "owners")
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "owner_Id")
	private Long ownerId;

	@Column(name = "owner_Name", nullable = false, length = 20)
	@NonNull
	private String ownerName;

	@Column(name = "budget")
	@NonNull
	private Double budget;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "owner")
	@JsonIgnore
	private Team team;

	public Owner(Long ownerId, String ownerName, Double budget, Team team) {
		super();
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.budget = budget;
		this.team = team;
	}

	public Owner() {
		super();
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String toString() {
		return ("Owner ID: " + ownerId + "\nOwner Name: " + ownerName + "\nBudget: " + budget + "\nTeam: "
				+ team.getTeamName());
	}

}
