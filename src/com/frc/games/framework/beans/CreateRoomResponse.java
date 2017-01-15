package com.frc.games.framework.beans;

public class CreateRoomResponse extends BaseResponse {
	protected String roomId;

	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
