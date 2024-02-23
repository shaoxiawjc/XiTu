package com.teamtwo.xitu.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.dto.BlogDTO;
import com.teamtwo.xitu.mapper.BlogMapper;
import com.teamtwo.xitu.pojo.Blog;
import com.teamtwo.xitu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author shaoxiawjc
 * @ 2024/2/13
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.config
 **/
@Component
public class ScheduledTask {
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private BlogMapper blogMapper;

	// 每隔一定时间执行同步任务，单位为毫秒
	@Scheduled(fixedRate = 600000) // 每隔10分钟执行一次
	public void syncClicksToDatabase() {
		System.out.println("------------------------syncClicksToDatabase------------------------");
		String keyPattern = "blog:click:*";
		Set keys = redisTemplate.keys(keyPattern);
		System.out.println("keys: "+keys);
		List<BlogClickDTO> list = null;
		if (keys != null) {
			list = redisTemplate.opsForValue().multiGet(keys);
			System.out.println("list: "+list);
		}
		for (BlogClickDTO blogClickDTO : list) {
			UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
			String blogId = blogClickDTO.getBlogId();
			String clickNum = blogClickDTO.getClickNum().toString();
			wrapper.eq("id", blogId)
					.set("click_num", clickNum);
			blogMapper.update(null, wrapper);
			wrapper.clear();
		}
		System.out.println("-------------------END-------------------------");
	}

}
