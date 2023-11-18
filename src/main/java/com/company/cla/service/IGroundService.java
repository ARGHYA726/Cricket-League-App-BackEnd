package com.company.cla.service;

import java.util.List;

import com.company.cla.entity.Ground;
import com.company.cla.entity.Match;

public interface IGroundService {

	public List<Ground> getAllGround();

	public Ground getGroundById(Long groundId); 

	public Ground insertGround(Ground ground);

	public Ground updateGround(Ground ground);

	public Ground deleteGround(Long groundId);

	public List<Match> getAllMatches();

	public Match getMatch(Long matchId);

}
