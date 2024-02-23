package com.teamtwo.xitu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamtwo.xitu.dto.BlogDTO;
import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.dto.LikedRank;
import com.teamtwo.xitu.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.mapper
 **/
@Repository
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
	List<BlogDTO> queryCurrent(@Param("userId") Long userId);

	List<BlogDTO> myBlogs(Long userId);

	List<BlogDTO> myLike(Long userId);

	List<BlogClickDTO> selectClick();

	List<LikedRank> selectLikedNum();

	List<Long> selectAllBlogId();

	BlogDTO selectBlogByBlogId(@Param("userId") Long userId,
							   @Param("id") Long id);
}
