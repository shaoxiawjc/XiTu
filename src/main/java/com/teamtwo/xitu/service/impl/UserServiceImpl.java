package com.teamtwo.xitu.service.impl;

import com.teamtwo.xitu.dto.CodeMessage;
import com.teamtwo.xitu.dto.LoginDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.mapper.UserMapper;
import com.teamtwo.xitu.pojo.User;
import com.teamtwo.xitu.service.UserService;
import com.teamtwo.xitu.utils.JWTUtils;
import com.teamtwo.xitu.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.service.impl
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;


	@Override
	public ResultResponse<LoginDTO> login(String username, String password) {
		// 登录
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		subject.login(usernamePasswordToken);
		User user = (User) subject.getPrincipal();
		HashMap<String, String> map = new HashMap<>();
		map.put("userId",user.getId().toString());
		String token = JWTUtils.getToken(map);
		return new ResultResponse(CodeMessage.SUCCESS_LOGIN,new LoginDTO(token));
	}

	@Override
	public ResultResponse register(String username, String password) {
		log.info("-------------------------------------------");
		log.info("执行了service的register");
		log.info("-------------------------------------------");
		ByteSource byteSource = ByteSource.Util.bytes(username+"teamtwo");
		System.out.println("byteSource.toBase64: "+byteSource.toBase64());
		String newPassword = new SimpleHash("SHA-256",password,byteSource,1024).toString();
		System.out.println("newPassword is = "+newPassword);
		userMapper.insert(new User(username, newPassword, username+"teamtwo"));
		return new ResultResponse(CodeMessage.SUCCESS_REGISTER);
	}
}
