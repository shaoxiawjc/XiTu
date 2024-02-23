package com.teamtwo.xitu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shaoxiawjc
 * @ 2024/2/8
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.dto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedDTO implements Serializable {
	private Integer isLiked;
}
