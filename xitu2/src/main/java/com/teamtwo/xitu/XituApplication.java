package com.teamtwo.xitu;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.teamtwo.xitu.mapper")
public class XituApplication {

	public static void main(String[] args) {
		SpringApplication.run(XituApplication.class, args);
	}

}
