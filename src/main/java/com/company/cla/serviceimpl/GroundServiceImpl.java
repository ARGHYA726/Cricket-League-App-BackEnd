package com.company.cla.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.company.cla.entity.Ground;
import com.company.cla.entity.Match;
import com.company.cla.exception.GroundNotFoundException;
import com.company.cla.repository.GroundRepository;
import com.company.cla.service.IGroundService;

@Service
public class GroundServiceImpl implements IGroundService {

	private GroundRepository groundRepository;

	final static Logger log = LoggerFactory.getLogger("Ground Logger");

	public GroundServiceImpl(GroundRepository groundRepository) {
		log.info("Retriving all ground information");
		this.groundRepository = groundRepository;
	}

	@Override
	public List<Ground> getAllGround() {
		return groundRepository.findAll();
	}

	@Override
	public Ground getGroundById(Long groundId) {
		return groundRepository.findById(groundId).orElseThrow(() -> new GroundNotFoundException("Ground not Found"));
	}

	@Override
	public Ground insertGround(Ground ground) {
		return groundRepository.save(ground);
	}

	@Override
	public Ground updateGround(Ground ground) {
		Ground existingGround = groundRepository.findById(ground.getGroundId())
				.orElseThrow(() -> new GroundNotFoundException("Ground not Found"));

		if (ground.getGroundName() != null) {
			existingGround.setGroundName(ground.getGroundName());
		}
		if (ground.getCapacity() > 0) {
			existingGround.setCapacity(ground.getCapacity());
		}

		if (ground.getMatches() != null) {
			existingGround.setMatches(ground.getMatches());
		}

		if (ground.getAddress() != null) {
			existingGround.setAddress(ground.getAddress());
		}

		groundRepository.save(existingGround);
		return existingGround;
	}

	@Override
	public Ground deleteGround(Long groundId) {
		Ground ground = groundRepository.findById(groundId)
				.orElseThrow(() -> new GroundNotFoundException("Ground not Found"));
		groundRepository.delete(ground);
		return ground;
	}

	@Override
	public List<Match> getAllMatches() {
		/*
		 * Ground ground = groundRepository.findById(groundId) .orElseThrow(() -> new
		 * GroundNotFoundException("Ground not Found")); return ground.getMatches();
		 */

		return null;
	}

	@Override
	public Match getMatch(Long matchId) {
//		return matchService.getMatch(matchId);
		return null;
	}

}
