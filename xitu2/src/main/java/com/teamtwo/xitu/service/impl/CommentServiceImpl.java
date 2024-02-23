package com.teamtwo.xitu.service.impl;

import com.teamtwo.xitu.dto.CodeMessage;
import com.teamtwo.xitu.dto.FCommentDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.dto.SCommentDTO;
import com.teamtwo.xitu.mapper.FCommentMapper;
import com.teamtwo.xitu.mapper.SCommentMapper;
import com.teamtwo.xitu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/8
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.service.impl
 **/
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private FCommentMapper fCommentMapper;
	@Autowired
	private SCommentMapper sCommentMapper;

	@Override
	public ResultResponse<List<FCommentDTO>> fComment(String blogId) {
		List<FCommentDTO> fCommentDTOS = fCommentMapper.fComment(blogId);
		return new ResultResponse<>(CodeMessage.SUCCESS,fCommentDTOS);
	}

	@Override
	public ResultResponse<List<SCommentDTO>> sComment(String fCommentId) {
		List<SCommentDTO> sCommentDTOS = sCommentMapper.sComment(fCommentId);
		return new ResultResponse<>(CodeMessage.SUCCESS,sCommentDTOS);
	}
}
