package com.frc.games.guessnumber.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.frc.games.framework.model.UserProperties;
import com.frc.games.framework.service.ITokenService;

@Service("GuessNumberTokenService")
public class GuessNumberTokenService implements ITokenService {
	protected static final String[] ipWhiteList = new String[]{"127.0.0.1", "192.168.36.86"};
	
	protected static Map<String, UserProperties> userMap = new HashMap<>();
	
	@Override
	public String generateToken(UserProperties userProp) {
		if (!inWhiteList(userProp.getIpAddress())) {
			return "";
		}
		
		String token = UUID.randomUUID().toString().substring(0, 10);
		userProp.setTokenId(token);
		
		removeUser(userProp);
		userMap.put(token, userProp);
		
		return userProp.getTokenId();
	}

	@Override
	public String verifyToken(String tokenId) {
		UserProperties up = queryUser(tokenId);
		if (up == null) {
			return "NO";
		} else {
			return "YES";
		}
	}
	
	@Override
	public UserProperties queryUser(String tokenId) {
		UserProperties up = userMap.get(tokenId);
		return up;
	}

	protected boolean inWhiteList(String ip) {
		for (String white : ipWhiteList) {
			if (white.equals(ip)) {
				return true;
			}
		}
		return false;
	}
	protected void removeUser(UserProperties userProp) {
		for (Entry<String, UserProperties> entry : userMap.entrySet()) {
			UserProperties up = entry.getValue();
			if (up != null && up.getIpAddress().equals(userProp.getIpAddress()) && 
				up.getName().equals(userProp.getName()) &&
				up.getTokenId().equals(userProp.getTokenId())) {
				userMap.put(entry.getKey(), null);
			}	
		}
	}

	
}
