package com.company.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.cla.dtos.OwnerDTO;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Player;
import com.company.cla.entity.Team;
import com.company.cla.exception.OwnerLowBudgetException;
import com.company.cla.exception.OwnerNotFoundException;
import com.company.cla.exception.PlayerNotFoundException;
import com.company.cla.exception.TeamNotFoundException;
import com.company.cla.repository.OwnerRepository;
import com.company.cla.service.IOwnerService;
import com.company.cla.service.IPlayerService;
import com.company.cla.service.ITeamService;

@Service
public class OwnerServiceImpl implements IOwnerService {
	@Autowired
	ITeamService teamService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	IPlayerService playerService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	static String ownerException = "Owner not found";

	/**
	 * This method finds an owner based on owner id and throws an exception if owner
	 * is not found.
	 * 
	 * @param Long: ownerId
	 * @return Owner : owner
	 * @see OwnerNotFoundException
	 */
	@Override
	public Owner getOwner(Long ownerId) {
		log.info("Owner Service method to get Owner by Id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			return owner.get();
		} else {
			log.warn("Owner with id {} not found", ownerId);
			throw new OwnerNotFoundException();
		}
	}

	/**
	 * This method finds owner and return the details as string and throws an
	 * exception if owner is not found.
	 * 
	 * @param Long: ownerId
	 * @return String : string returned by toString method of Owner.
	 * @see : OwnerNotFoundException
	 */
	@Override
	public String getOwnerDetails(Long ownerId) {
		log.info("Owner Service method to get Owner Details by Id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			return owner.toString();
		} else {
			log.warn("Owner with id {} not found", ownerId);
			throw new OwnerNotFoundException();
		}
	}

	/**
	 * This method find all the owners in the database and throws an exception if no
	 * owner is found.
	 * 
	 * @return List: OwnerDTO
	 * @see OwnerNotFoundException
	 */
	@Override
	public List<OwnerDTO> getAllOwners() {
		Iterator<Owner> ownerList = ownerRepository.findAll().iterator();
		List<OwnerDTO> dtoList = new ArrayList<>();
		while (ownerList.hasNext()) {
			Owner owner = ownerList.next();
			dtoList.add(new OwnerDTO(owner.getOwnerId(), owner.getOwnerName(), owner.getBudget()));
		}
		if (dtoList.isEmpty())
			throw new OwnerNotFoundException();
		return dtoList;
	}

	/**
	 * This method gives the names of all owners in database and throws an exception
	 * if no owner is found.
	 * 
	 * @return List: String
	 * @see OwnerNotFoundException
	 */
	@Override
	public List<String> getAllOwnersNames() {
		log.info("Owner Service method to get all Owners names");
		if (ownerRepository.findAll().isEmpty()) {
			log.warn(ownerException);
			throw new OwnerNotFoundException();
		} else {
			return ownerRepository.findAllOwners();
		}
	}

	/**
	 * This method is used to insert an owner in the database and throws an
	 * exceptions if name is null or budget is low.
	 * 
	 * @param Owner: owner
	 * @return Owner: owner
	 * @see OwnerNotFoundException,OwnerLowBudgetException
	 */
	@Override
	public Owner insertOwner(Owner owner) {
		log.info("Owner Service method to add Owner");
		if (owner.getOwnerName() == null)
			throw new OwnerNotFoundException("Name cannot be null");
		if (owner.getBudget() < 10000000)
			throw new OwnerLowBudgetException("Budget should be atleast 1 Crore");

		return ownerRepository.save(owner);
	}

	/**
	 * This method is used to update owner details and throws an exception if owner
	 * is not found.
	 * 
	 * @param Owner: owner
	 * @return Owner: existingOwner
	 * @see OwnerNotFoundException
	 */
	@Override
	public Owner updateOwner(Owner owner) {
		log.info("Owner Service method to update Owner");
		Owner existingOwner = ownerRepository.findById(owner.getOwnerId())
				.orElseThrow(() -> new OwnerNotFoundException());

		existingOwner.setOwnerName(owner.getOwnerName());
		existingOwner.setBudget(owner.getBudget());
		return ownerRepository.save(existingOwner);

	}

	/**
	 * This method is used to delete owner from database and throws an exception if
	 * owner is not found.
	 * 
	 * @param Long: ownerId
	 * @return String: "Owner deleted"
	 * @see OwnerNotFoundException
	 */
	@Transactional
	@Override
	public String deleteOwnerById(Long ownerId) {
		log.info("Owner Service method to delete Owner with id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			ownerRepository.deleteById(ownerId);
			return "Owner deleted";
		} else {
			log.warn(ownerException);
			throw new OwnerNotFoundException();
		}
	}

	/**
	 * This method is used to get the team owned by a owner and throws an exception
	 * if owner is not found.
	 * 
	 * @param Long: ownerId
	 * @return Team
	 * @see OwnerNotFoundException
	 */
	@Override
	public Team getTeam(Long ownerId) {
		log.info("Owner Service method to get team with owner id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			return owner.get().getTeam();
		} else {
			log.warn(ownerException);
			throw new OwnerNotFoundException();
		}
	}

	/**
	 * This method is used to pay salary to player in an owner's team and throws an
	 * exception if owner or player is not found.
	 * 
	 * @param Long: ownerId, playerId Double: salary
	 * @return Double: salary
	 * @see OwnerNotFoundException,PlayerNotFoundException,TeamNotFoundException
	 */
	@Override
	public Double paySalary(Long ownerId, Long playerId, Double salary) {
		log.info("Owner id {} pay to player id {} amount {}", ownerId, playerId, salary);

		// check if owner exist
		Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException());

		Team team = owner.getTeam();
		// check if team exists
		if (team != null) {
			Player player = null;
			try {
				player = teamService.getPlayerById(team.getTeamId(), playerId);
			} catch (TeamNotFoundException e) {
				throw new TeamNotFoundException("no such player in team");
			}
			if (player != null) {
				if (salary < getBudget(ownerId)) {
					try {
						// pay salary to player
						playerService.paySalary(playerId, salary);
					} catch (PlayerNotFoundException e) {
						throw new PlayerNotFoundException(e);
					}
					// deduct the amount from owner's budget
					owner.setBudget(getBudget(ownerId) - salary);
					ownerRepository.save(owner);
					return salary;
				} else {
					log.warn("Owner's budget is low");
					throw new OwnerLowBudgetException("Owner's budget is low to pay salary");
				}
			} else {
				log.warn("Player not found");
				throw new PlayerNotFoundException();
			}
		} else {
			log.warn("Owner has no team");
			throw new TeamNotFoundException("Team not found");
		}
	}

	/**
	 * This method is used to get an owner's budget and throws an exception if owner
	 * is not found.
	 * 
	 * @param Long: ownerId
	 * @return Double: budget
	 * @see OwnerNotFoundException
	 */
	@Override
	public Double getBudget(Long ownerId) {
		log.info("Owner Service method to get owner's budget with id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			return owner.get().getBudget();
		} else {
			log.warn(ownerException);
			throw new OwnerNotFoundException();
		}
	}

	/**
	 * This method is used to get total salary of all players in an owner's team and
	 * throws an exception if owner is not found.
	 * 
	 * @param Long: ownerId
	 * @return Double: sum of all salaries
	 * @see OwnerNotFoundException
	 */
	@Override
	public Double getTotalSalary(Long ownerId) {
		log.info("Owner Service method to get owner's total salary with id {}", ownerId);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			List<Player> pList = owner.get().getTeam().getPlayers();
			double sum = 0;
			for (Player p : pList) {
				sum += p.getSalary();
			}
			return sum;
		} else {
			log.warn(ownerException);
			throw new OwnerNotFoundException();
		}

	}
}
