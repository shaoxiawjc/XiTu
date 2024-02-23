package com.teamtwo.xitu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.aliyuncs.exceptions.ClientException;
import com.teamtwo.xitu.dto.*;
import com.teamtwo.xitu.exception.NoParamException;
import com.teamtwo.xitu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.controller
 **/
@RestController
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;

	@SaIgnore
	@CrossOrigin
	@PostMapping("/user/login")
	public ResponseEntity<ResultResponse<LoginDTO>> login(@RequestParam("username") String username,
								@RequestParam("password") String password){
		log.info("-------------------------------------------");
		log.info("执行了controller的login");
		log.info("-------------------------------------------");
		return ResponseEntity.ok(userService.login(username,password));
	}

	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/user/logout")
	public ResultResponse logout(){
		return userService.logout();
	}

	@SaIgnore
	@CrossOrigin
	@PostMapping(value = "/user/register",produces = "application/json")
	public ResultResponse register(@RequestParam("username") String username,
								   @RequestParam("password") String password){
		log.info("-------------------------------------------");
		log.info("执行了controller的register");
		log.info("-------------------------------------------");
		return userService.register(username,password);
	}

	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/user/info")
	public ResultResponse<UserUpdateDTO> info(HttpServletRequest request){
		log.info("--------controller执行了info方法------");
		return userService.info(request);
	}

	@SaCheckLogin
	@CrossOrigin
	@PostMapping("/user/update")
	public ResultResponse<UserUpdateDTO> update(@RequestParam(value = "username",required = false) String username,
												@RequestParam(value = "image" ,required = false)MultipartFile image,
												HttpServletRequest request) throws IOException, ClientException {
		log.info("-------------------------------------------");
		log.info("执行了controller的update");
		log.info("-------------------------------------------");
		return userService.update(request,username,image);
	}

	@SaCheckLogin
	@CrossOrigin
	@PostMapping("/user/change_password")
	public ResultResponse changePassword(@RequestParam(value = "password") String password,
										 HttpServletRequest request){
		if (password == null||password.equals("")){
			throw new NoParamException("密码不能为空");
		}
		String token = request.getHeader("Authorization");
		return userService.changePassword(token,password);
	}

	@SaCheckLogin
	@CrossOrigin
	@PostMapping("/user/add_blog")
	public ResultResponse addBlog(@RequestParam(value = "title") String title,
								  @RequestParam(value = "content") String content,
								  @RequestParam(value = "imageData",required = false) List<MultipartFile> images,
								  HttpServletRequest request) throws IOException, ClientException {
		String token = request.getHeader("Authorization");
		return userService.addBlog(token,title,content,images);
	}

	@SaCheckLogin
	@CrossOrigin
	@DeleteMapping("/user/del_blog")
	public ResultResponse delBlog(@RequestParam("blog_id") String blogId,
								  HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return userService.delBlog(token,blogId);
	}




	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/user/add_fcomment")
	public ResultResponse<FCommentDTO> addFComment(@RequestParam(value = "content") String content,
												   @RequestParam(value = "blog_id") String blogId,
												   HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return userService.addFComment(token,blogId,content);
	}


	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/user/add_scomment")
	public ResultResponse<SCommentDTO> addSComment(@RequestParam(value = "content") String content,
												   @RequestParam(value = "fcomment_id") String fCommentId,
												   HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return userService.addSComment(token,fCommentId,content);
	}

	@SaCheckLogin
	@CrossOrigin
	@DeleteMapping("/user/del_fcomment")
	public ResultResponse delFComment(@RequestParam("fcomment_id") String fCommentId,
									  HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return userService.delFComment(token,fCommentId);
	}

	@SaCheckLogin
	@CrossOrigin
	@DeleteMapping("/user/del_scomment")
	public ResultResponse delSComment(@RequestParam("scomment_id") String sCommentId,
									  HttpServletRequest request){
		String token = request.getHeader("Authorization");
		return userService.delSComment(token,sCommentId);
	}



	@SaCheckLogin
	@CrossOrigin
	@GetMapping("/user/like")
	public ResultResponse<LikedDTO> like(HttpServletRequest request,
										 @RequestParam(value = "blog_id") String blog_id,
										 @RequestParam("is_liked") Integer isLiked){
		String token = request.getHeader("Authorization");
		return userService.like(token,blog_id,isLiked);
	}




}
