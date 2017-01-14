package com.frc.games.framework.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
	protected int roomSize;
	protected String name;
	protected String roomId;
	protected List<Player> playerList;
	protected Game game;
	public int getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(int roomSize) {
		this.roomSize = roomSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public List<Player> getPlayerList() {
		if (playerList == null) {
			playerList = new ArrayList<Player>();
		}
		return playerList;
	}
	public void addPlayer(Player player) {
		getPlayerList().add(player);
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
}
