package com.frc.games.framework.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frc.games.framework.model.UserProperties;
import com.frc.games.framework.service.ITokenService;

@Controller
@RequestMapping("/token")
public class TokenController {

	@Resource(name="GuessNumberTokenService")
	protected ITokenService tokenService;
	
	@RequestMapping(value = "/create")
	public @ResponseBody String create(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		UserProperties userProp = new UserProperties();
		userProp.setIpAddress(ip);
		
		String token = tokenService.generateToken(userProp);
		
		return token;
	}

	@RequestMapping(value = "/verify")
	public @ResponseBody String verify(HttpServletRequest request, String tokenId)
			throws Exception {
		String result = tokenService.verifyToken(tokenId);
		
		return result;
	}

}
