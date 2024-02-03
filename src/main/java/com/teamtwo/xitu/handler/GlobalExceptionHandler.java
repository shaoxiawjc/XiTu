package com.teamtwo.xitu.handler;

import com.alibaba.fastjson.JSON;
import com.teamtwo.xitu.dto.CodeMessage;
import com.teamtwo.xitu.dto.ResultResponse;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.config
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UnknownAccountException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResultResponse handleUnknownAccountException(UnknownAccountException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("登录失败，账号不存在");
		System.out.println("-----------------------------------------------------------");
		return new ResultResponse(CodeMessage.UNKNOWN_ACCOUNT);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultResponse handleDuplicateKeyException(DuplicateKeyException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("注册失败，账号已存在");
		System.out.println("-----------------------------------------------------------");
//		String jsonString = JSON.toJSONString(new ResultResponse(CodeMessage.ACCOUNT_ALREADY_EXIST));
		return new ResultResponse(CodeMessage.ACCOUNT_ALREADY_EXIST);
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultResponse handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e){
		e.printStackTrace();
		return new ResultResponse(CodeMessage.ACCOUNT_ALREADY_EXIST);
	}
}
