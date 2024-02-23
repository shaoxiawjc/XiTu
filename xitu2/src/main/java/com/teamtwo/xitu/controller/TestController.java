package com.teamtwo.xitu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shaoxiawjc
 * @ 2024/2/5
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.controller
 **/
@RestController
public class TestController {
	@SaCheckLogin
	@GetMapping("/test01")
	public String test01(){
		return "test01";
	}
}
