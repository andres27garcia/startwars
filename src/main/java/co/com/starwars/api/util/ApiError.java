package co.com.starwars.api.util;

import java.io.Serializable;

public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messageUser;

	private String messageDeveloper;

	private String code;

	public ApiError() {
	}

	public ApiError(String messageUser, String messageDeveloper, String code) {
		super();
		this.messageUser = messageUser;
		this.messageDeveloper = messageDeveloper;
		this.code = code;
	}

	public String getMessageUser() {
		return messageUser;
	}

	public void setMessageUser(String messageUser) {
		this.messageUser = messageUser;
	}

	public String getMessageDeveloper() {
		return messageDeveloper;
	}

	public void setMessageDeveloper(String messageDeveloper) {
		this.messageDeveloper = messageDeveloper;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return new com.google.gson.Gson().toJson(this);
	}

}
