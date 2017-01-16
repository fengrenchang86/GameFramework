package com.frc.games.framework.model;

public class UserProperties {
	protected String name;
	protected String tokenId;
	protected String ipAddress;
	public UserProperties() {
		name = "";
		tokenId = "";
		ipAddress = "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
