package com.company.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.cla.entity.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
