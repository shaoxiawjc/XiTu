package com.teamtwo.xitu.dto;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.dto
 **/
public enum CodeMessage {
	SUCCESS(200,"成功"),
	SUCCESS_REGISTER(201,"注册成功"),
	ACCOUNT_ALREADY_EXIST(400,"用户名已经存在"),
	UNKNOWN_ACCOUNT(401,"登录失败，用户名不存在"),
	INCORRECT_PASSWORD(402,"登录失败，密码错误"),
	INCORRECT_EXTENSION(405,"图片后缀只能为jpg或png"),
	UNAUTHENTICATED(407,"没有token或者token不合法，请先登录"),
	NO_PRIVILEGE(408," 没有权限操作"),
	NOT_NULL_PARAM(403,"参数不能为空"),
	NOT_FIND_RESOURCE(410,"未找到该资源"),
	FAIL(500,"失败"),
	UPLOAD_ERROR(501,"文件上传错误");


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
