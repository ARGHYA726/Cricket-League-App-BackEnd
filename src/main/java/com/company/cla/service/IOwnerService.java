package com.company.cla.service;

import java.util.List;

import com.company.cla.dtos.OwnerDTO;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Team;

public interface IOwnerService {
	public Owner getOwner(Long ownerId);

	public String getOwnerDetails(Long ownerId);

	public List<OwnerDTO> getAllOwners();

	public List<String> getAllOwnersNames();

	public Owner insertOwner(Owner owner);

	public String deleteOwnerById(Long ownerId);

	public Owner updateOwner(Owner owner);

	public Team getTeam(Long ownerId);

	public Double paySalary(Long ownerId, Long playerId, Double salary);

	public Double getBudget(Long ownerId);

	public Double getTotalSalary(Long ownerId);

}
