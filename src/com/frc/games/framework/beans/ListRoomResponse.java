package com.frc.games.framework.beans;

import java.util.List;

import com.frc.games.framework.model.Room;

public class ListRoomResponse extends BaseResponse {
	protected List<Room> list;

	public List<Room> getList() {
		return list;
	}
	public void setList(List<Room> list) {
		this.list = list;
	}
}
