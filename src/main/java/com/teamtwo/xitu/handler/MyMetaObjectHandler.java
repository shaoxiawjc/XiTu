package com.teamtwo.xitu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author shaoxiawjc
 * @ 2024/1/28
 * @ IntelliJ IDEA
 * @ warmup
 * @ com.teamtwo.warmup.handler
 **/
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("insert fill .............");
		this.setFieldValByName("createdTime",new Timestamp(System.currentTimeMillis()),metaObject);
		this.setFieldValByName("gmtModified",new Timestamp(System.currentTimeMillis()),metaObject);
		this.setFieldValByName("version",new Integer(1),metaObject);
		this.setFieldValByName("deleted",new Integer(0),metaObject);
		this.setFieldValByName("image","https://xitu.oss-cn-nanjing.aliyuncs.com/666.png",metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("insert fill .............");
		this.setFieldValByName("gmtModified",new Timestamp(System.currentTimeMillis()),metaObject);
	}
}
