package com.teamtwo.xitu.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.pojo
 **/
@TableName("liked_association_user_blog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikedAssociation implements Serializable {
	@TableId
	private Long id;
	private Long userId;
	private Long blogId;
	private Integer isLiked;

	@TableField(fill = FieldFill.INSERT)
	private Timestamp createdTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Timestamp updatedTime;

	@Version
	@TableField(fill = FieldFill.INSERT)
	private Integer version;

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private Integer deleted;
}
