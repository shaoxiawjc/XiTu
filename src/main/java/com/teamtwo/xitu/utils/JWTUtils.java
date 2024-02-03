package com.teamtwo.xitu.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author shaoxiawjc
 * @ 2024/1/31
 * @ IntelliJ IDEA
 * @ MySpringSecurity
 * @ com.shaoxia.demo01.utils
 **/
public class JWTUtils {
	// 密匙
	private static final String SIGNATURE = "teamtwo";

	/**
	 * token的生成
	 * */
	public static String getToken(Map<String,String> map){
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.DATE,14); // 设置14天过期

		JWTCreator.Builder builder = JWT.create();

		// 设置声明里的信息
		map.forEach((k,v)->{
			builder.withClaim(k,v);
		});

		// 设置过期时间和签名的算法
		String token = builder.withExpiresAt(instance.getTime())
				.sign(Algorithm.HMAC256(SIGNATURE));

		return token;
	}

	/**
	 * 验证token并返回token信息
	 * token要是不合法该方法就会抛出异常
	 * */
	public static DecodedJWT verify(String token){
		return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
	}
}
