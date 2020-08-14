package com.arjun.datastructure;

import com.microsoft.azure.storage.table.TableServiceEntity;

public class EnvironmentDataHolder extends TableServiceEntity {

	private String postgreHostname;
	private String postgrePort;
	private String postgreUsername;
	private String postgrepassword;

	public String getPostgreHostname() {
		return postgreHostname;
	}

	public void setPostgreHostname(String postgreHostname) {
		this.postgreHostname = postgreHostname;
	}

	public String getPostgrePort() {
		return postgrePort;
	}

	public void setPostgrePort(String postgrePort) {
		this.postgrePort = postgrePort;
	}

	public String getPostgreUsername() {
		return postgreUsername;
	}

	public void setPostgreUsername(String postgreUsername) {
		this.postgreUsername = postgreUsername;
	}

	public String getPostgrepassword() {
		return postgrepassword;
	}

	public void setPostgrepassword(String postgrepassword) {
		this.postgrepassword = postgrepassword;
	}

}
