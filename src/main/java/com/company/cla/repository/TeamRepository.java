package com.company.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.cla.entity.Team;


public interface TeamRepository extends JpaRepository<Team, Long> {
	 
}
