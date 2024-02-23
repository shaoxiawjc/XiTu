package com.teamtwo.xitu;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamtwo.xitu.config.ScheduledTask;
import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.mapper.BlogMapper;
import com.teamtwo.xitu.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

@SpringBootTest
class XituApplicationTests {


	@Test
	void contextLoads() {

	}
}
