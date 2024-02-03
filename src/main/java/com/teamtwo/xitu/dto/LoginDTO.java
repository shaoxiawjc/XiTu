package com.teamtwo.xitu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.dto
 **/
@Data
@NoArgsConstructor
public class LoginDTO {
	private String token;

	public LoginDTO(String token) {
		this.token = token;
	}
}
