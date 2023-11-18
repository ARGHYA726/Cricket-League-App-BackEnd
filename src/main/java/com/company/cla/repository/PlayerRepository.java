package com.company.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.cla.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	
}
