/**
 * 
 */
package com.company.cla.serviceimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.cla.dtos.OrganiserDTO;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.OrganiserInvalidBudgetException;
import com.company.cla.exception.OrganiserInvalidEmailException;
import com.company.cla.exception.OrganiserInvalidPaymentException;
import com.company.cla.exception.OrganiserInvalidPhoneException;
import com.company.cla.exception.OrganiserNotFoundException;
import com.company.cla.repository.OrganiserRepository;
import com.company.cla.service.IOrganiserService;
import com.company.cla.service.IOwnerService;

/**
 * This is an implementation of the IOrganiserService interface
 *
 */
@Service
public class OrganiserServiceImpl implements IOrganiserService {

	@Autowired
	private OrganiserRepository organiserRepository;

	@Autowired
	private IOwnerService ownerService;

	/**
	 * This is used to find the organiser by looking for the id number in the
	 * database
	 * 
	 * @param organiserId : the id number of the organiser to be searched.
	 * @return Organiser : the organiser found
	 * @throws OrganiserNotFoundException
	 */

	@Override
	public Organiser getOrganiser(Long organiserId) throws OrganiserNotFoundException {
		if (organiserRepository.existsById(organiserId)) {
			return organiserRepository.findById(organiserId).get();
		}
		throw new OrganiserNotFoundException("No such organisers found");
	}

	/**
	 * it returns the list of all organisers present in the database.
	 * 
	 * @return List: List of all the organisers
	 * @throws OrganiserNotFoundException : if the database is empty.
	 */
	@Override
	public List<OrganiserDTO> getAllOrganisers() throws OrganiserNotFoundException {
		List<Organiser> organiserList = organiserRepository.findAll();
		if (organiserList.isEmpty())
			throw new OrganiserNotFoundException("No organisers found");
		Iterator<Organiser> organiserIterator = organiserList.iterator();
		List<OrganiserDTO> dtoList = new ArrayList<>();
		while (organiserIterator.hasNext()) {
			Organiser organiser = organiserIterator.next();
			dtoList.add(new OrganiserDTO(organiser.getOrganiserId(), organiser.getOrganiserName(), organiser.getEmail(),
					organiser.getPhone(), organiser.getPayment(), organiser.getBudget()));
		}
		return dtoList;
	}

	/**
	 * This function is used to insert the organiser into the database.
	 * 
	 * @param organiser: the organiser to be inserted.
	 * @return organiser: The organiser if the insertion is performed successfully.
	 * @throws OrganiserInvalidPhoneException
	 * @throws OrganiserInvalidPaymentException
	 * @throws OrganiserInvalidEmailException
	 * @throws OrganiserInvalidBudgetException
	 */
	@Override
	public Organiser insertOrganiser(Organiser organiser) throws OrganiserInvalidPhoneException,
			OrganiserInvalidPaymentException, OrganiserInvalidBudgetException, OrganiserInvalidEmailException {

		if (String.valueOf(organiser.getPhone()).length() != 10) {
			throw new OrganiserInvalidPhoneException("Phone number not valid");
		} else if (organiser.getPayment() <= 0) {
			throw new OrganiserInvalidPaymentException("Payment is negative or zero");
		} else if (organiser.getBudget() <= 0) {
			throw new OrganiserInvalidBudgetException("Budget is negative or zero");
		} else if (organiser.getBudget() <= organiser.getPayment()) {
			throw new OrganiserInvalidPaymentException("Payment should be less than the budget");
		} else if (!organiser.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
			throw new OrganiserInvalidEmailException("Email address not valid");
		} else
			return organiserRepository.save(organiser);
	}

	/**
	 * This function is used to update the organiser into the database.
	 * 
	 * @param id:        the organiser id to be updated
	 * @param organiser: the organiser details to be updated.
	 * @return organiser: The organiser if the update is performed successfully.
	 * @throws OrganiserInvalidPhoneException
	 * @throws OrganiserInvalidPaymentException
	 * @throws OrganiserInvalidEmailException
	 * @throws OrganiserInvalidBudgetException
	 */
	@Override
	public Organiser updateOrganiser(Long id, Organiser organiser)
			throws OrganiserNotFoundException, OrganiserInvalidPhoneException, OrganiserInvalidPaymentException,
			OrganiserInvalidBudgetException, OrganiserInvalidEmailException {

		if (organiserRepository.existsById(id)) {
			Organiser existingOrganiser = organiserRepository.findById(id).get();
			if (String.valueOf(organiser.getPhone()).length() != 10) {
				throw new OrganiserInvalidPhoneException("Phone number not valid");
			} else if (organiser.getPayment() <= 0) {
				throw new OrganiserInvalidPaymentException("Payment is negative or zero");
			} else if (organiser.getBudget() <= 0) {
				throw new OrganiserInvalidBudgetException("Budget is negative or zero");
			} else if (organiser.getBudget() <= organiser.getPayment()) {
				throw new OrganiserInvalidPaymentException("Payment should be less than the budget");
			} else if (!organiser.getEmail().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				throw new OrganiserInvalidEmailException("Email address not valid");
			} else {
				existingOrganiser.setOrganiserName(organiser.getOrganiserName());
				existingOrganiser.setEmail(organiser.getEmail());

				existingOrganiser.setPhone(organiser.getPhone());

				existingOrganiser.setPayment(organiser.getPayment());

				existingOrganiser.setBudget(organiser.getBudget());
				return organiserRepository.save(existingOrganiser);
			}
		}
		throw new OrganiserNotFoundException("Not Found");
	}

	/**
	 * it returns the current budget of the organiser
	 * 
	 * @param id: the id of the organiser
	 * @return budget: current budget
	 * @throws OrganiserNotFoundException : if the organiser is not present.
	 */
	@Override
	public double getBudget(Long id) throws OrganiserNotFoundException {
		if (organiserRepository.existsById(id))
			return organiserRepository.findById(id).get().getBudget();
		throw new OrganiserNotFoundException("No such organisers found");
	}

	/**
	 * shows all the tournaments hosted by the organiser.
	 * 
	 * @param id : the id of the organiser
	 * @return List: List of all the tournaments hosted by the organiser
	 * @throws OrganiserNotFoundException : if the database is empty.
	 */
	@Override
	public List<Tournament> getTournaments(Long organiserId) throws OrganiserNotFoundException {
		if (organiserRepository.existsById(organiserId))
			return organiserRepository.findById(organiserId).get().getTournaments();
		throw new OrganiserNotFoundException("No such organisers found");
	}

	/**
	 * pays the prize money to the team through the owner
	 * 
	 * @param id    : the organiser id.
	 * @param owner : the owner whose team has won the prize.
	 * @return the prize money
	 * @throws OrganiserNotFoundException : if the database is empty.
	 */
	@Override
	public double payPrizeMoney(Long organiserId, Owner owner) throws OrganiserNotFoundException {
		if (organiserRepository.existsById(organiserId)) {
			Organiser organiser = organiserRepository.findById(organiserId).get();
			owner = ownerService.getOwner(owner.getOwnerId());
			organiser.setBudget(organiser.getBudget() - organiser.getPayment());
			organiserRepository.save(organiser);
			owner.setBudget(owner.getBudget() + organiser.getPayment());
			ownerService.updateOwner(owner);
			return organiser.getPayment();
		}
		throw new OrganiserNotFoundException("Organiser does not exists");
	}

}
