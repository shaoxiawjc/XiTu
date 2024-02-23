package com.teamtwo.xitu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamtwo.xitu.pojo.LikedAssociation;
import com.teamtwo.xitu.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.mapper
 **/
@Repository
@Mapper
public interface LikedAssociationMapper extends BaseMapper<LikedAssociation> {
	int registerUserUpdate(@Param("et") LikedAssociation likedAssociation,@Param("userId") Long uerId);

	int addBlogUpdate(@Param("et")LikedAssociation likedAssociation,@Param("blogId") Long blogId);
}
