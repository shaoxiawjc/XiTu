package com.teamtwo.xitu.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.teamtwo.xitu.dto.FCommentDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.dto.SCommentDTO;
import com.teamtwo.xitu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/8
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.controller
 **/
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;

	@SaIgnore
	@CrossOrigin
	@GetMapping("/fcomment")
	public ResultResponse<List<FCommentDTO>> fComment(@RequestParam("blog_id") String blogId){
		return commentService.fComment(blogId);
	}

	@SaIgnore
	@CrossOrigin
	@GetMapping("/scomment")
	public ResultResponse<List<SCommentDTO>> scomment(@RequestParam("fcomment_id") String fCommentId){
		return commentService.sComment(fCommentId);
	}
}
