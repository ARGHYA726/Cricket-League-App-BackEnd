package com.company.cla.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.company.cla.entity.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE TOURNAMENT SET ORGANISER_ID=:ORGANISER_ID WHERE TOURNAMENT_ID=:TOURNAMENT_ID", nativeQuery=true)
	public void addOrganiserId(@Param("ORGANISER_ID") Long organiser_id, @Param("TOURNAMENT_ID") Long tournament_id);
}
