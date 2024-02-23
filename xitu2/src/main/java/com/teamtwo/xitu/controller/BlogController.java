package com.teamtwo.xitu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.teamtwo.xitu.dto.BlogClickDTO;
import com.teamtwo.xitu.dto.BlogDTO;
import com.teamtwo.xitu.dto.LikedRank;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.service.BlogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/6
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.controller
 **/
@RestController
public class BlogController {
	@Autowired
	private BlogService blogService;

	@SaIgnore
	@CrossOrigin
	@PostMapping("/blog/enter")
	public ResultResponse<BlogDTO> enter(HttpServletRequest request,
										 @RequestParam("blog_id") String blogId){
		String token = request.getHeader("Authorization");
		return blogService.enter(token,blogId);
	}

	@SaIgnore
	@CrossOrigin
	@GetMapping("/blog/currents")
	public ResultResponse<List<BlogDTO>> queryCurrent(HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return blogService.queryCurrent(token);
	}

	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/blog/myblogs")
	public ResultResponse<List<BlogDTO>> myBlogs(HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return blogService.myBlogs(token);
	}

	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/blog/mylike")
	public ResultResponse<List<BlogDTO>> myLike(HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return blogService.myLike(token);
	}

	@SaIgnore
	@CrossOrigin
	@GetMapping("/blog/clickrank")
	public ResultResponse<List<BlogClickDTO>> clickRank(){
		return blogService.clickRank();
	}

	@SaIgnore
	@CrossOrigin
	@GetMapping("/blog/likerank")
	public ResultResponse<List<LikedRank>> likedRank(){
		return blogService.likedRank();
	}


}
