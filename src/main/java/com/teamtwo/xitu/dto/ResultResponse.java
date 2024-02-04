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
	private T data;

	public ResultResponse(CodeMessage codeMessage){
		this.code = codeMessage.getCode();
		this.message = codeMessage.getMessage();
	}

	public ResultResponse(CodeMessage codeMessage,T data){
		this.code = codeMessage.getCode();
		this.message = codeMessage.getMessage();
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
