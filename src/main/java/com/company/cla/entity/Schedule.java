package com.company.cla.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Embeddable;

@Embeddable
public class Schedule {

//	data validation
	private LocalDate matchDate;
	private LocalTime startTime;
	private LocalTime endTime;

	public Schedule() {
	}

	public Schedule(LocalDate matchDate, LocalTime startTime, LocalTime endTime) {
		super();
		this.matchDate = matchDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

}
