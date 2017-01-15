package com.frc.games.guessnumber.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.frc.games.framework.model.Player;
import com.frc.games.framework.service.ILoginService;

@Service("GuessNumberLoginService")
public class GuessNumberLoginService implements ILoginService {
	protected static final String[] ipWhiteList = new String[]{"127.0.0.1", "192.168.36.86"};
	
	protected static Map<String, Player> playerMap = new HashMap<>();
	
	@Override
	public String login(Map<String, String> userInfo) {
		//In SimpleLoginService, we only care about the IP address, but don't care about username nor password
		String ip = userInfo.get("ip");
		String username = userInfo.get("username");
		if (!inWhiteList(ip)) {
			return "";
		}
		
		String token = UUID.randomUUID().toString().substring(0, 10);
		
		Player player = new Player();
		player.setIp(ip);
		player.setName(username);
		player.setToken(token);
		
		playerMap.put(token, player);
		
		return token;
	}

	protected boolean inWhiteList(String ip) {
		for (String white : ipWhiteList) {
			if (white.equals(ip)) {
				return true;
			}
		}
		return false;
	}
}
