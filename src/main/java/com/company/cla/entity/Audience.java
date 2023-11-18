package com.company.cla.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "audience")
public class Audience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long audienceId;

	@NonNull
	@Column(name = "audience_name", nullable = false)
	private String audienceName;

	@NonNull
	@Column(name = "amount")
	private double amount;

	@Transient
	private Long ticketId;

	@Transient
	private Long matchId;

	@OneToOne(cascade = CascadeType.ALL)
	private Ticket ticket;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "match_id")
	@JsonIgnore
	private Match match;

	/* Default Constructor */

	public Audience() {
		super();
	}

	/* Parameterized Constructor */
	public Audience(Long audienceId, String audienceName, double amount, Long ticketId, Long matchId, Ticket ticket,
			Match match) {
		super();
		this.audienceId = audienceId;
		this.audienceName = audienceName;
		this.amount = amount;
		this.ticketId = ticketId;
		this.matchId = matchId;
		this.ticket = ticket;
		this.match = match;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Long audienceId) {
		this.audienceId = audienceId;
	}

	public String getAudienceName() {
		return audienceName;
	}

	public void setAudienceName(String audienceName) {
		this.audienceName = audienceName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}