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
@TableName("user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	@TableId(type = IdType.ID_WORKER)
	private Long id;
	private String username;
	private String password;
	@TableField(fill = FieldFill.INSERT)
	private String image;
	private String salt;
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

	public User(String username, String password, String salt) {
		this.username = username;
		this.password = password;
		this.salt = salt;
	}
}
