package com.teamtwo.xitu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shaoxiawjc
 * @ 2024/2/13
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.dto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedRank implements Serializable,Comparable<LikedRank>{
	private String blogId;
	private String title;
	private Long liked; 
	@Override
	public int compareTo(LikedRank o) {
		return Long.compare(o.liked,this.liked);
	}
}
