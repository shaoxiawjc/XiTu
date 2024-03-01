package com.teamtwo.xitu.dto;

import com.teamtwo.xitu.pojo.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/6
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.dto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO implements Serializable {
	private String id;
	private String author;
	private String title;
	private String content;
	private List<ImageDTO> imageDTOS;
	private Integer isLiked;
	private Timestamp createTime;
	private Long liked;
	private Long clickNum;
	private Long fCommentNum;
}
