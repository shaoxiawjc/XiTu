package com.teamtwo.xitu.dto;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.dto
 **/
public enum CodeMessage {
	SUCCESS_LOGIN(200,"登录成功"),
	SUCCESS_REGISTER(201,"注册成功"),
	UNKNOWN_ACCOUNT(401,"登录失败，用户名不存在"),
	INCORRECT_PASSWORD(402,"登录失败，密码错误"),
	ACCOUNT_ALREADY_EXIST(400,"用户已经存在");


	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private final Integer code;
	private final String message;

	CodeMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
