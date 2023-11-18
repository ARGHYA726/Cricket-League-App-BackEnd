/**
 * 
 */
package com.company.cla.service;

import java.util.List;

import com.company.cla.dtos.OrganiserDTO;
import com.company.cla.entity.Organiser;
import com.company.cla.entity.Owner;
import com.company.cla.entity.Tournament;
import com.company.cla.exception.OrganiserInvalidBudgetException;
import com.company.cla.exception.OrganiserInvalidEmailException;
import com.company.cla.exception.OrganiserInvalidPaymentException;
import com.company.cla.exception.OrganiserInvalidPhoneException;
import com.company.cla.exception.OrganiserNotFoundException;

/**
 * 
 */
public interface IOrganiserService {

	public Organiser getOrganiser(Long organiserId) throws OrganiserNotFoundException;

	public List<OrganiserDTO> getAllOrganisers()throws OrganiserNotFoundException;

	public Organiser insertOrganiser(Organiser organiser) throws OrganiserInvalidPhoneException,
			OrganiserInvalidPaymentException, OrganiserInvalidBudgetException, OrganiserInvalidEmailException;

	public Organiser updateOrganiser(Long id, Organiser organiser) throws OrganiserNotFoundException,OrganiserInvalidPhoneException,
	OrganiserInvalidPaymentException, OrganiserInvalidBudgetException, OrganiserInvalidEmailException;

	public List<Tournament> getTournaments(Long organiserId) throws OrganiserNotFoundException;


	public double payPrizeMoney(Long organiserId, Owner owner) throws OrganiserNotFoundException;

	public double getBudget(Long id) throws OrganiserNotFoundException;

}
