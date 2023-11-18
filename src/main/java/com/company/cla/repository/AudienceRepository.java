package com.company.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.cla.entity.Audience;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

//	Optional<Audience> getAudience(Long audienceId);
}
