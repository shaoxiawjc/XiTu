package com.teamtwo.xitu.handler;

/**
 * @author shaoxiawjc
 * @ 2024/2/5
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.handler
 **/
public class IncorrectPasswordException extends RuntimeException{
	public IncorrectPasswordException(String message) {
		super(message);
	}
}
