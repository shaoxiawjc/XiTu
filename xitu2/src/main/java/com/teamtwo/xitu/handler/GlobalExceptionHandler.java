package com.teamtwo.xitu.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.aliyuncs.exceptions.ClientException;
import com.teamtwo.xitu.dto.CodeMessage;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.exception.IncorrectExtensionException;
import com.teamtwo.xitu.exception.NoParamException;
import com.teamtwo.xitu.exception.NoPrivilegeException;
import com.teamtwo.xitu.exception.UnknownAccountException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

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
		System.out.println("用户名已存在");
		System.out.println("-----------------------------------------------------------");
//		String jsonString = JSON.toJSONString(new ResultResponse(CodeMessage.ACCOUNT_ALREADY_EXIST));
		return new ResultResponse(CodeMessage.ACCOUNT_ALREADY_EXIST);
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultResponse handleIncorrectCredentialsException(IncorrectPasswordException e){
		e.printStackTrace();
		System.out.println("-----------------------------------------------------------");
		System.out.println("登录失败，密码错误");
		System.out.println("-----------------------------------------------------------");
		return new ResultResponse(CodeMessage.INCORRECT_PASSWORD);
	}

	@ExceptionHandler(ClientException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultResponse handleClientException(ClientException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("oss客户端错误");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse((CodeMessage.UPLOAD_ERROR));
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultResponse handleIOException(ClientException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("IO异常");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse((CodeMessage.UPLOAD_ERROR));
	}

	@ExceptionHandler(IncorrectExtensionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResultResponse handleIncorrectExtensionException(IncorrectExtensionException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("文件后缀错误");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse(CodeMessage.INCORRECT_EXTENSION);
	}

	@ExceptionHandler(NotLoginException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResultResponse handleNotLoginException(NotLoginException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("无token");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse(CodeMessage.UNAUTHENTICATED);
	}

	@ExceptionHandler(NoParamException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResultResponse handleNoParamException(NoParamException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("参数不能为空");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse(CodeMessage.NOT_NULL_PARAM);
	}

	@ExceptionHandler(NoPrivilegeException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResultResponse handleNoPrivilegeException(NoPrivilegeException e){
		System.out.println("-----------------------------------------------------------");
		System.out.println("你没有删除此评论的权限");
		System.out.println("-----------------------------------------------------------");
		e.printStackTrace();
		return new ResultResponse(CodeMessage.NO_PRIVILEGE);
	}
}
