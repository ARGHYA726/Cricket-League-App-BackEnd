package com.company.cla.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.core.lang.NonNull;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long ticketId;

	@NonNull
	@Column(name = "ticket_name", nullable = false)
	private String ticketName;

	@NonNull
	@Column(name = "total_amount")
	private double totalAmount;

	@NonNull
	@Column(name = "seat_no")
	private int seatNo;

	@Transient
	@JsonIgnore
	private Match match;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE }, mappedBy = "ticket")
	@JsonIgnore
	private Audience audience;

	public Ticket() {
		super();
	}

	public Ticket(Long ticketId, String ticketName, double totalAmount, int seatNo, Match match, Audience audience) {
		super();
		this.ticketId = ticketId;
		this.ticketName = ticketName;
		this.totalAmount = totalAmount;
		this.seatNo = seatNo;
		this.match = match;
		this.audience = audience;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

}
