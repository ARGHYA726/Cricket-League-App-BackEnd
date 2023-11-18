package com.company.cla.service;

import java.util.List;

import com.company.cla.dtos.PlayerDTO;
import com.company.cla.entity.Skill;
import com.company.cla.entity.Team;

public interface IPlayerService {

	PlayerDTO getPlayer(Long playerId);

	List<PlayerDTO> getAllPlayers();

	PlayerDTO insertPlayer(PlayerDTO player);

	PlayerDTO updatePlayer(PlayerDTO player, Long playerId);

	void deletePlayer(Long playerId);

	Team getTeam(Long playerId);

	Skill getSkill(Long playerId);

	Skill addSkill(Long playerId, String stringSkill);

	Skill updateSkill(Long playerId, String stringSkill);

	void removeSkill(Long playerId);

	Double getSalary(Long playerId);

	void paySalary(Long playerId, Double salary);
}
