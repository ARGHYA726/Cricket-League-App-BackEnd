package com.company.cla.dtos;

public class OwnerDTO {
	private Long ownerId;
	private String ownerName;
	private Double budget;

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

	public OwnerDTO() {
		super();
	}

	public OwnerDTO(Long ownerId, String ownerName, Double budget) {
		super();
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.budget = budget;
	}

}
