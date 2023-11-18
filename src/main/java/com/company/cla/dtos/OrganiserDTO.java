/**
 * 
 */
package com.company.cla.dtos;

/**
 *
 */
public class OrganiserDTO {
	private Long organiserId;
	private String organiserName;
	private String email;
	private long phone;
	private double payment;
	private double budget;

	/**
	 * 
	 */
	public OrganiserDTO() {
	}

	/**
	 * @param organiserId
	 * @param organiserName
	 * @param email
	 * @param phone
	 * @param payment
	 * @param budget
	 */
	public OrganiserDTO(Long organiserId, String organiserName, String email, long phone, double payment,
			double budget) {
		this.organiserId = organiserId;
		this.organiserName = organiserName;
		this.email = email;
		this.phone = phone;
		this.payment = payment;
		this.budget = budget;
	}

	/**
	 * @return the organiserId
	 */
	public Long getOrganiserId() {
		return organiserId;
	}

	/**
	 * @param organiserId the organiserId to set
	 */
	public void setOrganiserId(Long organiserId) {
		this.organiserId = organiserId;
	}

	/**
	 * @return the organiserName
	 */
	public String getOrganiserName() {
		return organiserName;
	}

	/**
	 * @param organiserName the organiserName to set
	 */
	public void setOrganiserName(String organiserName) {
		this.organiserName = organiserName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public long getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}

	/**
	 * @return the payment
	 */
	public double getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(double payment) {
		this.payment = payment;
	}

	/**
	 * @return the budget
	 */
	public double getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(double budget) {
		this.budget = budget;
	}

}
