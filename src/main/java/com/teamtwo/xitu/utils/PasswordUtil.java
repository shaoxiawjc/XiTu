package com.teamtwo.xitu.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.UUID;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.utils
 **/
public class PasswordUtil {

	public static String generateRandomSalt() {
		return UUID.randomUUID().toString();
	}
	public static String hashPassword(String password,ByteSource salt){
		return new SimpleHash("SHA-256",password,salt,1024).toString();
	}



	public static boolean verifyPassword(String inputPassword, ByteSource salt, String storedPassword) {
		System.out.println(inputPassword+" "+salt.toString());
		String hashedInputPassword = hashPassword(inputPassword, salt);
		return hashedInputPassword.equals(storedPassword);
	}

}
