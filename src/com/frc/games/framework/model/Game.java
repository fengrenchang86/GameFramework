package com.frc.games.framework.model;

import java.util.HashMap;
import java.util.Map;

public class Game {
	/*
	 * 1 - waiting
	 * 2 - in progress
	 * 3 - end
	 * */
	protected int status = 1;
	protected String id;
	protected Map<String, Object> data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
