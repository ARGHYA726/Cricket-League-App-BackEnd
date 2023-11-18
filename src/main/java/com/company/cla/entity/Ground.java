package com.company.cla.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ground {

	@Id
	@SequenceGenerator(name = "ground_seq", sequenceName = "GROUND_SEQ", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(generator = "ground_seq")
	@Column(name = "ground_id")
	private Long groundId;

	@NonNull
	@Column(name = "ground_name", nullable = true)
	private String groundName;

	@Column(name = "capacity")
	private Integer capacity;


	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "ground")
	@JsonIgnore
	private Match matches;

	@Column(name = "address", nullable = true)
	private Address address; // embedded entry

	/* constructor (default) */
	public Ground() {
	}

	public Ground(Long groundId, String groundName, Integer capacity, Match matches, Address address) {
		super();
		this.groundId = groundId;
		this.groundName = groundName;
		this.capacity = capacity;
		this.matches = matches;
		this.address = address;
	}

	public Long getGroundId() {
		return groundId;
	}

	public String getGroundName() {
		return groundName;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public Match getMatches() {
		return matches;
	}

	public Address getAddress() {
		return address;
	}

	public void setGroundId(Long groundId) {
		this.groundId = groundId;
	}

	public void setGroundName(String groundName) {
		this.groundName = groundName;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public void setMatches(Match matches) {
		this.matches = matches;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
