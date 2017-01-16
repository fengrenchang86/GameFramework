package com.frc.games.framework.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frc.games.framework.beans.CreateRoomRequest;
import com.frc.games.framework.beans.CreateRoomResponse;
import com.frc.games.framework.beans.JoinRoomRequest;
import com.frc.games.framework.beans.JoinRoomResponse;
import com.frc.games.framework.beans.ListRoomRequest;
import com.frc.games.framework.beans.ListRoomResponse;
import com.frc.games.framework.model.Player;
import com.frc.games.framework.model.Room;
import com.frc.games.framework.model.UserProperties;
import com.frc.games.framework.service.IRoomService;
import com.frc.games.framework.service.ITokenService;

@Controller
@RequestMapping("/room")
public class RoomController {

//	@Resource(name="GuessNumberTokenService")
	protected ITokenService tokenService;
	
//	@Resource(name="GuessNumberRoomService")
	protected IRoomService roomService;
	
	
	@RequestMapping(value = "/create")
	public @ResponseBody CreateRoomResponse create(
			HttpServletRequest httpReq, 
			@RequestBody CreateRoomRequest request) {
		CreateRoomResponse response = new CreateRoomResponse();
		String ip = httpReq.getRemoteAddr();
		
		String tokenId = request.getUserTokenId();
		UserProperties userProp = tokenService.queryUser(tokenId);
		
		if (userProp == null) {
			response.setCode("1");
			response.setMsg("User invalid");
			return response;
		}
		Player player = new Player(userProp.getName(), ip, tokenId);
		
		int size = 2;
		try {
			size = Integer.parseInt(request.getRoomSize());
		} catch (Exception e) {
			size = 2;
		}
		Room room = roomService.createRoom(player, request.getRoomName(), size);
		
		response.setRoomId(room.getRoomId());
		
		return response;
	}
	
	@RequestMapping(value = "/list")
	public @ResponseBody ListRoomResponse list(
			HttpServletRequest httpReq,
			@RequestBody ListRoomRequest request
			) {
		ListRoomResponse response = new ListRoomResponse();
		
		List<Room> list = null;
		if ("0".equals(request.getType())) {
			list = roomService.listAll();
		} else if ("1".equals(request.getType())) {
			list = roomService.listAllWaiting();
		} else {
			response.setCode("1");
			response.setCode("Type not defined");
		}
		response.setList(list);
		return response;
	}

	@RequestMapping(value = "/join")
	public @ResponseBody JoinRoomResponse verify(
			HttpServletRequest httpReq, 
			@RequestBody JoinRoomRequest request)
			throws Exception {
		JoinRoomResponse response = new JoinRoomResponse();
		
		String ip = httpReq.getRemoteAddr();
		String tokenId = request.getTokenId();
		UserProperties userProp = tokenService.queryUser(tokenId);
		if (userProp == null) {
			response.setCode("1");
			response.setMsg("User invalid");
			return response;
		}
		Player player = new Player(userProp.getName(), ip, tokenId);
		
		boolean result = roomService.joinRoom(player, request.getRoomId());
		if (!result) {
			response.setCode("1");
			response.setMsg("Fail!");
		}
		
		return response;
	}

	@Autowired
	public void setTokenService(ITokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Autowired
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

}
