package com.frc.games.framework.model;

import java.util.HashMap;
import java.util.Map;

import com.frc.games.framework.common.GameStatus;

public class Game {
	/*
	 * 1 - waiting
	 * 2 - in progress
	 * 3 - end
	 * */
	protected GameStatus status = GameStatus.WAITING;
	protected String id;
	protected Map<String, Object> data;
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, Object> getData() {
		if (data == null) {
			data = new HashMap<>();
		}
		return data;
	}
	public void putData(String key, Object val) {
		getData().put(key, val);
	}
	public Object getData(String key) {
		return getData().get(key);
	}
}
