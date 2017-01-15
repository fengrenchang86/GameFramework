package com.frc.games.framework.service;

import com.frc.games.framework.model.UserProperties;

public interface ITokenService {
	public String generateToken(UserProperties userProp);
	public String verifyToken(String tokenId);
	public UserProperties queryUser(String tokenId);
}
