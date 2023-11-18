package com.company.cla.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.cla.dtos.PlayerDTO;
import com.company.cla.entity.Player;
import com.company.cla.entity.Skill;
import com.company.cla.entity.Team;
import com.company.cla.exception.PlayerNotFoundException;
import com.company.cla.exception.SkillNotFoundException;
import com.company.cla.exception.TeamNotFoundException;
import com.company.cla.repository.PlayerRepository;
import com.company.cla.service.IPlayerService;
import com.company.cla.service.ITeamService;

@Service
public class PlayerServiceImpl implements IPlayerService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	PlayerRepository playerRepository;

	@Autowired
	ITeamService teamService;

	public PlayerServiceImpl(PlayerRepository playerRepository) {
		log.info("Retriving all player information");
		this.playerRepository = playerRepository;
	}

	String exceptionMessage = "Player with the given ID not found";

	@Override
	public PlayerDTO getPlayer(Long playerId) {
		log.info("PlayerServiceImpl getPlayerById : {}", playerId);
		Player p = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		String skill = p.getSkill().toString();
		return new PlayerDTO(p.getPlayerId(), p.getPlayerName(), p.getSalary(), skill, p.getTeam().getTeamId());
	}

	@Override
	public List<PlayerDTO> getAllPlayers() {
		log.info("PlayerServiceImpl getAllPlayers");
		List<Player> playerList = playerRepository.findAll();
		if (playerList.isEmpty()) {
			throw new PlayerNotFoundException("No Players found");
		} else {
			List<PlayerDTO> player = new ArrayList<>();
			for (Player i : playerList) {
				player.add(new PlayerDTO(i.getPlayerId(), i.getPlayerName(), i.getSalary(), i.getSkill().toString(),
						i.getTeam().getTeamId()));
			}
			return player;
		}
	}

	@Override
	public PlayerDTO insertPlayer(PlayerDTO player) {
		log.info("PlayerServiceImpl insertPlayer");

		Skill skill = null;
		if (player.getSkill() != null) {
			try {
				skill = Skill.valueOf(player.getSkill().toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new SkillNotFoundException("Skill not found");
			}
		} else {
			skill = Skill.NONE;
		}

		Team team = null;
		if (player.getTeamId() != null) {
			try {
				log.info("inside team update");
				team = teamService.getTeam(player.getTeamId());
			} catch (TeamNotFoundException e) {
				throw new TeamNotFoundException("Team not found");
			}
		}

		Player p = new Player(player.getPlayerName(), player.getSalary(), skill, team);
		Player ply = playerRepository.save(p);
		player.setPlayerId(ply.getPlayerId());
		return player;

	}

	@Override
	public PlayerDTO updatePlayer(PlayerDTO player, Long playerId) {
		log.info("PlayerService updatePlayer {}", player.getPlayerId());
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));

		Skill skill = existingPlayer.getSkill();
		if (player.getSkill() != null) {
			try {
				skill = Skill.valueOf(player.getSkill().toUpperCase());
				existingPlayer.setSkill(skill); // skill will be updated only if a valid skill is passed, otherwise
												// remains
				// unchanged
			} catch (IllegalArgumentException e) {
				throw new SkillNotFoundException("Skill not found");
			}
		}

		/* updating the team only if teamid is present */
		Team team = existingPlayer.getTeam();
		if (player.getTeamId() != null) {
			try {
				team = teamService.getTeam(player.getTeamId());
				existingPlayer.setTeam(team);
			} catch (TeamNotFoundException e) {
				throw new TeamNotFoundException("Team not found");
			}
		}

		if (player.getPlayerName() != null) {
			existingPlayer.setPlayerName(player.getPlayerName());
		}
		if (player.getSalary() != null) {
			existingPlayer.setSalary(player.getSalary());
		}
		playerRepository.save(existingPlayer);
		return new PlayerDTO(existingPlayer.getPlayerId(), existingPlayer.getPlayerName(), existingPlayer.getSalary(),
				skill != null ? skill.toString() : null, team != null ? team.getTeamId() : null);

	}

	@Override
	public void deletePlayer(Long playerId) {
		log.info("PlayerServiceImpl deletePlayer : {}", playerId);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));

//		delete by id
		playerRepository.delete(existingPlayer);
	}

	@Override
	public Skill getSkill(Long playerId) {
		log.info("PlayerServiceimpl getSkill {}", playerId);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));

		return existingPlayer.getSkill();
	}

	@Override
	public Skill addSkill(Long playerId, String stringSkill) {
		log.info("PlayerServiceImpl addSkill : {}, {} ", playerId, stringSkill);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		Skill skill = null;
		try {
			skill = Skill.valueOf(stringSkill.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new SkillNotFoundException("Skill not found");
		}
		existingPlayer.setSkill(skill);
		playerRepository.save(existingPlayer);
		return skill;
	}

	@Override
	public Skill updateSkill(Long playerId, String stringSkill) {
		log.info("PlayerService updateSkill : {}, {}", playerId, stringSkill);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		Skill skill = null;
		try {
			skill = Skill.valueOf(stringSkill.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new SkillNotFoundException("Skill not found");
		}
		existingPlayer.setSkill(skill);
		playerRepository.save(existingPlayer);
		return skill;
	}

	@Override
	public void removeSkill(Long playerId) {
		log.info("PlayerServiceImpl deleteSkill : {}", playerId);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		existingPlayer.setSkill(Skill.NONE);
		playerRepository.save(existingPlayer);
	}

	@Override
	public Team getTeam(Long playerId) {
		log.info("PlayerServiceImpl getTeam : {}", playerId);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		Team existingTeam = null;
		try {
			existingTeam = teamService.getTeam(existingPlayer.getTeam().getTeamId());
		} catch (TeamNotFoundException e) {
			log.warn("Team was not found", e.getMessage());
		}
		return existingTeam;
	}

	@Override
	public Double getSalary(Long playerId) {
		log.info("PlayerServiceImpl getSalary : {}", playerId);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));
		return existingPlayer.getSalary();
	}

	@Override
	public void paySalary(Long playerId, Double salary) {
		log.info("PlayerServiceImpl paySalary : {} , {}", playerId, salary);
		Player existingPlayer = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException(exceptionMessage));

//		add with prev sal
		existingPlayer.setSalary(salary);
		playerRepository.save(existingPlayer);
	}

}