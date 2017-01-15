package com.frc.games.framework.model;

public class Player {
	protected String name;
	protected String ip;
	protected String token;
	public Player() {
		
	}
	public Player(String name, String ip, String token) {
		super();
		this.name = name;
		this.ip = ip;
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
