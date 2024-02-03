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
	public static String hashPassword(String password,String salt){
		ByteSource saltSource = ByteSource.Util.bytes(salt);
		return new SimpleHash("SHA-256",password,saltSource,5).toHex();
	}

	public static boolean verifyPassword(String inputPassword, String salt, String storedPassword) {
		String hashedInputPassword = hashPassword(inputPassword, salt);
		return hashedInputPassword.equals(storedPassword);
	}
}
