package com.frc.games.guessnumber.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.frc.games.framework.common.GameStatus;
import com.frc.games.framework.model.Game;
import com.frc.games.framework.model.Player;
import com.frc.games.framework.model.Room;
import com.frc.games.framework.service.IRoomService;

@Service("GuessNumberRoomService")
public class GuessNumberRoomService implements IRoomService {
	public static Map<String, Room> roomMap = new HashMap<>();
	
	public Room createRoom(Player player, String roomName, int roomSize) {
		Room room = new Room();
		room.setName(roomName);
		room.setRoomSize(roomSize);
		room.addPlayer(player);
		room.setRoomId(UUID.randomUUID().toString());
		
		Game game = new Game();
		game.setId(UUID.randomUUID().toString());
		room.setGame(game);
		
		roomMap.put(room.getRoomId(), room);
		return room;
	}
	
	public Room findRoom(String roomId) {
		return roomMap.get(roomId);
	}
	
	public boolean joinRoom(Player player, String roomId) {
		Room room = findRoom(roomId);
		if (canJoin(room) && !inRoom(player.getToken(), roomId)) {
			room.addPlayer(player);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean inRoom(String userTokenId, String roomId) {
		if (userTokenId == null || userTokenId.trim().length() == 0) {
			return false;
		}
		if (roomId == null || roomId.trim().length() == 0) {
			return false;
		}
		Room room = findRoom(roomId);
		List<Player> list = room.getPlayerList();
		for (Player player : list) {
			if (player.getToken().equals(userTokenId)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Room> listAll() {
		List<Room> list = new ArrayList<>();
		for (Entry<String, Room> entry : roomMap.entrySet()) {
			Room val = entry.getValue();
			list.add(val);
		}		
		return list;
	}

	@Override
	public List<Room> listAllWaiting() {
		List<Room> list = new ArrayList<>();		
		for (Entry<String, Room> entry : roomMap.entrySet()) {
			Room val = entry.getValue();
			if (val != null && val.getGame() != null && val.getGame().getStatus() == GameStatus.WAITING) {
				list.add(val);	
			}			
		}		
		return list;
	}
	
	protected boolean canJoin(Room room) {
		if (room == null) {
			return false;
		} else if (room.getGame() == null) {
			return false;
		} else if (room.getGame().getStatus() != GameStatus.WAITING) {
			return false;
		} else if (room.getPlayerList().size() >= room.getRoomSize()) {
			return false;
		}else {
			return true;
		}
	}


	
}
