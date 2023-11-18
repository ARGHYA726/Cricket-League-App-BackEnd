/**
 * 
 */
package com.company.cla.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Organiser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORGANISER_ID")
	private Long organiserId;
	@NonNull
	private String organiserName;
	@NonNull
	private String email;
	@NonNull
	@Column(unique = true)
	private long phone;
	@NonNull
	private double payment;
	@NonNull
	private double budget;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organiser", cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JsonIgnore
	private List<Tournament> tournaments;

	/**
	 * 
	 */
	public Organiser() {
	}

	/**
	 * @param organiserId
	 * @param organiserName
	 * @param email
	 * @param phone
	 * @param payment
	 * @param budget
	 * @param tournaments
	 */
	public Organiser(Long organiserId, String organiserName, String email, long phone, double payment, double budget,
			List<Tournament> tournaments) {
		this.organiserId = organiserId;
		this.organiserName = organiserName;
		this.email = email;
		this.phone = phone;
		this.payment = payment;
		this.budget = budget;
		this.tournaments = tournaments;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return budget;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the organiserId
	 */
	public Long getOrganiserId() {
		return organiserId;
	}

	/**
	 * @return the organiserName
	 */
	public String getOrganiserName() {
		return organiserName;
	}

	/**
	 * @return the payment
	 */
	public double getPayment() {
		return payment;
	}

	/**
	 * @return the phone
	 */
	public long getPhone() {
		return phone;
	}

	/**
	 * @return the tournaments
	 */
	public List<Tournament> getTournaments() {
		return tournaments;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param organiserId the organiserId to set
	 */
	public void setOrganiserId(Long organiserId) {
		this.organiserId = organiserId;
	}

	/**
	 * @param organiserName the organiserName to set
	 */
	public void setOrganiserName(String organiserName) {
		this.organiserName = organiserName;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}

	/**
	 * @param tournaments the tournaments to set
	 */
	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
