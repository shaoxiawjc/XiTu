package com.teamtwo.xitu.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.teamtwo.xitu.dto.LoginDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.pojo.User;
import com.teamtwo.xitu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.controller
 **/
@RestController
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/user/login")
	public ResponseEntity<ResultResponse<LoginDTO>> login(@RequestParam("username") String username,
								@RequestParam("password") String password){
		return ResponseEntity.ok(userService.login(username,password));
	}

	@PostMapping(value = "/user/register",produces = "application/json")
	public ResultResponse register(@RequestParam("username") String username,
								   @RequestParam("username") String password){
		log.info("-------------------------------------------");
		log.info("执行了controller的register");
		log.info("-------------------------------------------");
		return userService.register(username,password);
	}

	@PostMapping("/hello")
	public ResponseEntity<User> hello(){
		if (true){
			throw new UnknownAccountException();
		}
		return ResponseEntity.ok(new User("111","222","333"));
	}
}
