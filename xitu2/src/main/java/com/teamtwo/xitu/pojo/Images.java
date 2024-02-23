package com.teamtwo.xitu.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author shaoxiawjc
 * @ 2024/2/6
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.pojo
 **/
@TableName("images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Images implements Serializable {
	@TableId(type = IdType.ID_WORKER)
	private Long id;
	private Long blogId;
	private String url;
	private String name;
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
