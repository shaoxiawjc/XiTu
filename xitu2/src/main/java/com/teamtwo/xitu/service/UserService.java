package com.teamtwo.xitu.service;

import cn.dev33.satoken.exception.NotLoginException;
import com.aliyuncs.exceptions.ClientException;
import com.teamtwo.xitu.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.service
 **/
@Service
public interface UserService {
	ResultResponse<LoginDTO> login(String username, String password);

	ResultResponse register(String username,String password);

	ResultResponse logout();

	ResultResponse update(HttpServletRequest request, String username, MultipartFile image) throws IOException, ClientException;

	ResultResponse changePassword(String token,String newPassword) throws NotLoginException;

	ResultResponse addBlog(String token, String title, String content, List<MultipartFile> imageData) throws IOException, ClientException;

	ResultResponse<FCommentDTO> addFComment(String token, String blogId, String content);

	ResultResponse<LikedDTO> like(String token, String blogId,Integer isLiked);


	ResultResponse delFComment(String token, String fCommentId);

	ResultResponse<SCommentDTO> addSComment(String token, String fCommentId, String content);

	ResultResponse delSComment(String token, String sCommentId);

	ResultResponse delBlog(String token, String blogId);

	ResultResponse<UserUpdateDTO> info(HttpServletRequest request);

}
