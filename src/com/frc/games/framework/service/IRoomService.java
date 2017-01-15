package com.frc.games.framework.service;

import java.util.List;

import com.frc.games.framework.model.Player;
import com.frc.games.framework.model.Room;

public interface IRoomService {
	public Room createRoom(Player player, String roomName, int roomSize);
	public Room findRoom(String roomId);
	public boolean joinRoom(Player player, String roomId);
	public boolean inRoom(String userTokenId, String roomId);
	
	public List<Room> listAll();
	public List<Room> listAllWaiting();
}
