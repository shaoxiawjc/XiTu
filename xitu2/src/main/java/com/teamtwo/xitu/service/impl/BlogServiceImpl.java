package com.teamtwo.xitu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.teamtwo.xitu.dto.*;
import com.teamtwo.xitu.mapper.BlogMapper;
import com.teamtwo.xitu.pojo.LikedAssociation;
import com.teamtwo.xitu.service.BlogService;
import com.teamtwo.xitu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author shaoxiawjc
 * @ 2024/2/6
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.service.impl
 **/
@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public ResultResponse<List<BlogDTO>> queryCurrent(String token) {
		Long userId = -1L;
		if (!(token == null || token.equals(""))){
			userId = Long.parseLong(StpUtil.getExtra(token,"userId").toString());
			System.out.println(userId);
		}
		String userIdString = userId.toString();
		List<BlogDTO> blogDTOS = blogMapper.queryCurrent(userIdString);

		for (BlogDTO blogDTO:blogDTOS){
			Integer isLiked =(Integer) redisCache.get("liked:userId:" + userId+ ":blogId:" + blogDTO.getId().toString());
			System.out.println("liked:userId:" + userId+ ":blogId:" + blogDTO.getId().toString()+isLiked);
			blogDTO.setIsLiked(isLiked);
		}

		for (BlogDTO blogDTO:blogDTOS){
			BlogClickDTO blogClickDTO = (BlogClickDTO) redisCache.get("blog:click:" + blogDTO.getId());
			System.out.println(blogClickDTO);
			blogDTO.setClickNum(blogClickDTO.getClickNum());
		}

		return new ResultResponse<>(CodeMessage.SUCCESS,blogDTOS);
	}

	@Override
	public ResultResponse<List<BlogDTO>> myBlogs(String token) {
		Long userId = Long.parseLong(StpUtil.getExtra(token,"userId").toString());
		List<BlogDTO> blogDTOS = blogMapper.myBlogs(userId);
		for (BlogDTO blogDTO:blogDTOS){
			blogDTO.setIsLiked((Integer) redisCache.get("liked:userId" + userId.toString() + ":blogId:" + blogDTO.getId().toString()));
		}
		return new ResultResponse<>(CodeMessage.SUCCESS,blogDTOS);
	}

	@Override
	public ResultResponse<List<BlogDTO>> myLike(String token) {
		Long userId = Long.parseLong(StpUtil.getExtra(token,"userId").toString());
		List<BlogDTO> blogDTOS = blogMapper.myLike(userId);
		return new ResultResponse<>(CodeMessage.SUCCESS,blogDTOS);
	}

	@Override
	public ResultResponse<BlogDTO> enter(String token, String blogId) {
		System.out.println("blogId: "+blogId);
		Long userId = -1L;
		Integer isLiked = 0;
		if (!(token == null || token.equals(""))){
			userId = Long.parseLong(StpUtil.getExtra(token,"userId").toString());
			// 如果用户登录了，就去Redis查询是否点赞
			isLiked = (Integer) redisCache.get("liked:userId"+ userId +":blogId:" +blogId);
		}

		// 创建传输对象
		BlogDTO blogDTO = blogMapper.selectBlogByBlogId(userId, Long.parseLong(blogId));

		// 设置isLiked
		blogDTO.setIsLiked(isLiked);

		// 点击量键
		String key = "blog:click:"+blogId;
		// 获取Redis里的点击量关联
		BlogClickDTO o = (BlogClickDTO)redisCache.get(key);
		System.out.println("BlogClickDTO: "+o);
		if (o != null){
			o.setClickNum(o.getClickNum()+1);
			redisCache.set(key,o);
		}else {
			// 如果没有获取到，就重新创建一个
			o = new BlogClickDTO();
			o.setBlogId(blogId);
			o.setTitle(blogDTO.getTitle());
			o.setClickNum(1L);
			redisCache.set(key,o);
		}

		blogDTO.setClickNum(o.getClickNum());

		return new ResultResponse<>(CodeMessage.SUCCESS,blogDTO);
	}

	@Override
	public ResultResponse<List<BlogClickDTO>> clickRank() {
		String keyPattern = "blog:click:*";
		Set keys = redisTemplate.keys(keyPattern);
		List<BlogClickDTO> list = null;
		if (keys != null) {
			list = redisTemplate.opsForValue().multiGet(keys);
		}
		Collections.sort(list);
		List<BlogClickDTO> top10Click = list.subList(0, Math.min(10, list.size()));
		return new ResultResponse<>(CodeMessage.SUCCESS,top10Click);
	}

	@Override
	public ResultResponse<List<LikedRank>> likedRank() {
		String keyPattern= "likeNum:blogId:*";
		Set keys = redisTemplate.keys(keyPattern);
		System.out.println("keys: "+keys);
		List<LikedRank> list = null;
		if (keys != null){
			list = redisTemplate.opsForValue().multiGet(keys);
			Collections.sort(list);
		}
		List<LikedRank> top10Liked = list.subList(0, Math.min(10, list.size()));
		System.out.println("top10Liked: "+top10Liked);
		return new ResultResponse<>(CodeMessage.SUCCESS,top10Liked);
	}
}
