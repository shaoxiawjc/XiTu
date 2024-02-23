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
public class BlogClickDTO implements Serializable, Comparable<BlogClickDTO> {
	private String blogId;
	private String title;
	private Long clickNum;

	@Override
	public int compareTo(BlogClickDTO o) {
		return Long.compare(o.clickNum,this.clickNum);
	}
}
