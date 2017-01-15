package com.frc.games.framework.beans;

public class ListRoomRequest {
	/*
	 * 0 - list all
	 * 1 - list room of "waiting"
	 * */
	protected String type;
	protected String tokenId;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
}
