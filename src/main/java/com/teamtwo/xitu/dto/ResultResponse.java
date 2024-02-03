package com.teamtwo.xitu.dto;

import java.io.Serializable;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.dto
 **/
public class ResultResponse<T> implements Serializable {
	private Integer code;
	private String message;
	private T t;

	public ResultResponse(CodeMessage codeMessage) {
		this.code = codeMessage.getCode();
		this.message = codeMessage.getMessage();
	}

	public ResultResponse(CodeMessage codeMessage, T t){
		this.code = codeMessage.getCode();
		this.message = codeMessage.getMessage();
		this.t = t;
	}

	public ResultResponse(Integer code, String message, T t) {
		this.code = code;
		this.message = message;
		this.t = t;
	}
}
