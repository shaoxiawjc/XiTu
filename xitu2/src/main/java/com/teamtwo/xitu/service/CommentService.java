package com.teamtwo.xitu.service;

import com.teamtwo.xitu.dto.FCommentDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import com.teamtwo.xitu.dto.SCommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/8
 * @ IntelliJ IDEA
 * @ xitu2
 * @ com.teamtwo.xitu.service
 **/
@Service
public interface CommentService {
	ResultResponse<List<FCommentDTO>> fComment(String blogId);

	ResultResponse<List<SCommentDTO>> sComment(String fCommentId);
}
