package com.frc.games.framework.service;

import com.frc.games.framework.model.Player;
import com.frc.games.framework.model.Room;

public interface IRoomService {
	public Room createRoom(Player player, String roomName, int roomSize);
	public Room findRoom(String roomId);
	public boolean joinRoom(Player player, String roomId);
}
