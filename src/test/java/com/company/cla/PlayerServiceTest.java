package com.company.cla;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.cla.dtos.PlayerDTO;
import com.company.cla.entity.*;
import com.company.cla.exception.PlayerNotFoundException;
import com.company.cla.repository.PlayerRepository;
import com.company.cla.serviceimpl.PlayerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class PlayerServiceTest {
	
	private static List<Player> players;
	
	@Autowired
	private PlayerServiceImpl playerServiceImpl;
	
	@MockBean(name = "playerRepository")
	private PlayerRepository playerRepository;
	static Team t = new Team();
	static Optional<Player> player1 = Optional.of(new Player(1L, "Player1", 1D, Skill.BATSMAN, t));
	static Optional<Player> player2 = Optional.of(new Player(2L, "Player2", 1D, Skill.BATSMAN, t));
	
	@BeforeAll
	public static void setUp() {
		players = List.of(player1.get(), player2.get());
	}

	
	@Test
	@DisplayName("PlayerService getAllPlayersTest (Postive TestCase)")
	void getAllPlayersTest() {
		when(playerRepository.findAll()).thenReturn(players);
		List<PlayerDTO> ply = playerServiceImpl.getAllPlayers();
		assertAll("Players in Database", () -> assertEquals(1L, ply.get(0).getPlayerId()),
				() -> assertEquals(2L, ply.get(1).getPlayerId()));
	}
	
	
	@Test
	@DisplayName("PlayerService getAllPlayersTest (Negative TestCase)")
	void getAllPlayersTestN() {
		when(playerRepository.findAll()).thenReturn(Collections.emptyList());
		assertThrows(PlayerNotFoundException.class, () -> playerServiceImpl.getAllPlayers());
	}
	
	
	@Test
	@DisplayName("PlayerService getPlayerTest (Postive TestCase)")
	void getPlayerTest() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		Optional<PlayerDTO> ply = Optional.of(playerServiceImpl.getPlayer(player1.get().getPlayerId()));
		Skill skill = Skill.valueOf(ply.get().getSkill().toString());
		Player p = new Player(ply.get().getPlayerId(),ply.get().getPlayerName(),
				ply.get().getSalary(),skill,t);
		assertAll("Players in Database", () -> assertEquals(player1.get().getPlayerId(),p.getPlayerId()));
	}

	
	@Test
	@DisplayName("PlayerService getPlayerTest (Negative TestCase)")
	void getPlayerTestN() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		assertThrows(PlayerNotFoundException.class, () -> playerServiceImpl.getPlayer(player2.get().getPlayerId()));
	}
	
	
	@Test
	@DisplayName("PlayerService getSkillTest (Postive TestCase)")
	void getSkillTest() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		Skill skill = playerServiceImpl.getSkill(player1.get().getPlayerId());
		assertAll("Players in Database", () -> assertEquals(player1.get().getSkill(), skill));
	}

	
	@Test
	@DisplayName("PlayerService getSkillTest (Negative TestCase)")
	void getSkillTestN() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		assertThrows(PlayerNotFoundException.class, () -> playerServiceImpl.getSkill(player2.get().getPlayerId()));
	}
	
	
	@Test
	@DisplayName("PlayerService getSalaryTest (Postive TestCase)")
	void getSalaryTest() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		Double salary = playerServiceImpl.getSalary(player1.get().getPlayerId());
		assertAll("Players in Database", () -> assertEquals(player1.get().getSalary(), salary));
	}

	
	@Test
	@DisplayName("PlayerService getSalaryTest (Negative TestCase)")
	void getSalaryTestN() {
		when(playerRepository.findById(player1.get().getPlayerId())).thenReturn(player1);
		assertThrows(PlayerNotFoundException.class, () -> playerServiceImpl.getSalary(player2.get().getPlayerId()));
	}
}