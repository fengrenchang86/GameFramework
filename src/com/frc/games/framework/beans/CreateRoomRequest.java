package com.frc.games.framework.beans;

public class CreateRoomRequest {
	protected String roomName;
	protected String roomSize;
	protected String userTokenId;
	protected String userName;
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String roomSize) {
		this.roomSize = roomSize;
	}
	public String getUserTokenId() {
		return userTokenId;
	}
	public void setUserTokenId(String userTokenId) {
		this.userTokenId = userTokenId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
