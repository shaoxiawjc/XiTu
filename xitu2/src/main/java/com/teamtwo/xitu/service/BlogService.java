package com.teamtwo.xitu.service;

import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.dto.BlogDTO;
import com.teamtwo.xitu.dto.LikedRank;
import com.teamtwo.xitu.dto.ResultResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/6
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.service
 **/
@Service
public interface BlogService {

	ResultResponse<List<BlogDTO>> queryCurrent(String token);

	ResultResponse<List<BlogDTO>> myBlogs(String token);

	ResultResponse<List<BlogDTO>> myLike(String token);

	ResultResponse<BlogDTO> enter(String token, String blogId);

	ResultResponse<List<BlogClickDTO>> clickRank();

	ResultResponse<List<LikedRank>> likedRank();
}
