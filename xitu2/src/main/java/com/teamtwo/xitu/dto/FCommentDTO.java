package com.teamtwo.xitu.dto;

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
public class FCommentDTO implements Serializable {
	private String id;
	private String content;
	private String author;
	private String image;
	private Long sCommentNum;
	private Timestamp createdTime;
	private List<SCommentDTO> sComments;
}

