package com.teamtwo.xitu.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.dto.LikedRank;
import com.teamtwo.xitu.mapper.BlogMapper;
import com.teamtwo.xitu.mapper.LikedAssociationMapper;
import com.teamtwo.xitu.pojo.LikedAssociation;
import com.teamtwo.xitu.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/13
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.config
 **/
@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private RedisCache redisCache;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private LikedAssociationMapper likedAssociationMapper;

	@Override
	public void run(String... args) throws Exception {
		initializeClickNum();
		initializeLiked();
	}

	private void initializeClickNum(){
		List<BlogClickDTO> blogClickDTOS = blogMapper.selectClick();
		for (BlogClickDTO blogClickDTO : blogClickDTOS) {
			redisCache.set("blog:click:" + blogClickDTO.getBlogId(), blogClickDTO);
		}
	}

	private void initializeLiked(){
		QueryWrapper<LikedAssociation> wrapper = new QueryWrapper<>();
		List<LikedAssociation> likedAssociations = likedAssociationMapper.selectList(wrapper);
		for (LikedAssociation likedAssociation:likedAssociations) {
			redisCache.set("liked:userId:"+likedAssociation.getUserId().toString()+":blogId:"+likedAssociation.getBlogId().toString(),
					likedAssociation.getIsLiked());
		}
		List<LikedRank> likedRanks = blogMapper.selectLikedNum();
		for (LikedRank likedRank:likedRanks){
			redisCache.set("likeNum:blogId:"+likedRank.getBlogId(),likedRank);
		}
	}




}
