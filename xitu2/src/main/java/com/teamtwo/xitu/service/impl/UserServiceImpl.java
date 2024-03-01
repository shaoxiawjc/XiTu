package com.teamtwo.xitu.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamtwo.xitu.dto.*;
import com.teamtwo.xitu.exception.IncorrectExtensionException;
import com.teamtwo.xitu.exception.NoParamException;
import com.teamtwo.xitu.exception.NoPrivilegeException;
import com.teamtwo.xitu.exception.UnknownAccountException;
import com.teamtwo.xitu.handler.IncorrectPasswordException;
import com.teamtwo.xitu.mapper.*;
import com.teamtwo.xitu.pojo.*;
import com.teamtwo.xitu.service.UserService;
import com.teamtwo.xitu.utils.FileUtils;
import com.teamtwo.xitu.utils.JWTUtils;
import com.teamtwo.xitu.utils.PasswordUtil;
import com.teamtwo.xitu.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.service.impl
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private ImagesMapper imagesMapper;
	@Autowired
	private FCommentMapper fCommentMapper;
	@Autowired
	private SCommentMapper sCommentMapper;
	@Autowired
	private LikedAssociationMapper likedAssociationMapper;


	@Override
	public ResultResponse<LoginDTO> login(String username, String password) {
		// 数据库校验
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",username);
		User user = userMapper.selectOne(queryWrapper);
		if (Objects.isNull(user)){
			throw new UnknownAccountException("未知的账号");
		}
		System.out.println(password);
		System.out.println(user.getSalt());
		System.out.println(PasswordUtil.hashPassword(password,user.getSalt()));
		System.out.println(user.getPassword());
		System.out.println(PasswordUtil.verifyPassword(password,user.getSalt(),user.getPassword()));
		if (!PasswordUtil.verifyPassword(password,user.getSalt(),user.getPassword())){
			throw new IncorrectPasswordException("密码错误");
		}
		// satoken的登录和放回token
		StpUtil.login(user.getId(),
				SaLoginConfig.setExtra("userId", user.getId().toString())
						     .setExtra("username",username));
		SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

		return new ResultResponse(CodeMessage.SUCCESS,new LoginDTO(tokenInfo.getTokenValue(),user.getImage()));
	}

	@Override
	@Transactional
	public ResultResponse register(String username, String password) {
		log.info("-------------------------------------------");
		log.info("执行了service的register");
		log.info("-------------------------------------------");
		String newPassword = PasswordUtil.hashPassword(password, username+"teamtwo");
		System.out.println("newPassword is = "+newPassword);
		User user = new User(username, newPassword, username + "teamtwo");
		userMapper.insert(user);
		Long id = user.getId();
		System.out.println("id:"+id);
		QueryWrapper<Blog> wrapper = new QueryWrapper<>();

		LikedAssociation association = new LikedAssociation();
		association.setUserId(id);
		System.out.println(association.getId());
		likedAssociationMapper.registerUserUpdate(association,id);
		// 在Redis里添加关联数据
		log.info("-----注册时向Redis里添加关联表数据--------");
		List<Long> longs = blogMapper.selectAllBlogId();
		if (longs != null){
			for (Long blogId:longs){
				redisCache.set("liked:userId"+id+":blogId:"+blogId,0);
			}
		}
		return new ResultResponse(CodeMessage.SUCCESS_REGISTER);
	}

	@Override
	public ResultResponse logout() {
		StpUtil.checkLogin();
		StpUtil.logout();
		return new ResultResponse(CodeMessage.SUCCESS);
	}

	@Override
	public ResultResponse<UserUpdateDTO> update(HttpServletRequest request,
												String username,
												MultipartFile image) throws IOException, ClientException {
		System.out.println("----------------------update-------------------------------");
		System.out.println("输入的参数：");
		System.out.println("username: "+username);
		System.out.println("isHaveImage?: "+!image.isEmpty());

		String authorization = request.getHeader("Authorization");
		long userId = Long.parseLong(JWTUtils.verify(authorization).getClaim("userId").asString());
		System.out.println("authorization: "+authorization);
		System.out.println("userId: "+userId);

		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("id",userId);
		String imgURL = null;
		int flag = 0;
		if (!Objects.isNull(username) && !username.equals("")){
			System.out.println("username"+username);
			wrapper.set("username",username);
			flag++;
		}

		if(image != null && !image.isEmpty()){
			// 获取后缀判断
			String extension = FileUtils.getFileExtension(image.getOriginalFilename());
			System.out.println("extension: "+extension);
			if (extension.equals("jpg") && extension.equals("png")){
				throw new IncorrectExtensionException("不正确的类型"+extension);
			}
			imgURL = FileUtils.uploadFile(image, "user_images");
			wrapper.set("image",imgURL);
			flag++;
		}
		if (flag != 0) {
			userMapper.update(null,wrapper);
		}else {
			System.out.println("没有接收到参数，不进行修改");
		}
		System.out.println("------------------------------------------------------------");
		return new ResultResponse(CodeMessage.SUCCESS,new UserUpdateDTO(username,imgURL));
	}

	@Override
	@Transactional
	public ResultResponse changePassword(String token, String newPassword) throws NotLoginException {

		Long userId = Long.parseLong(StpUtil.getExtra("userId").toString());
		System.out.println(userId);
		String username =  StpUtil.getExtra("username").toString();
		if (userId == null) {
			throw new NotLoginException(NotLoginException.NOT_TOKEN_MESSAGE,null,null);
		}
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("id",userId)
				.set("password",PasswordUtil.hashPassword(newPassword,username+"teamtwo"));
		userMapper.update(null,wrapper);
		return new ResultResponse(CodeMessage.SUCCESS);
	}

	@Override
	@Transactional
	public ResultResponse addBlog(String token, String title, String content, List<MultipartFile> imageData) throws IOException, ClientException {

		if (Objects.isNull(title) || Objects.isNull(content)){
			throw new NoParamException("标题或内容不能为空");
		}
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		Blog blog = new Blog();
		blog.setTitle(title);
		blog.setContent(content);
		blog.setUserId(userId);
		blogMapper.insert(blog);
		Long blogId = blog.getId();

		if (imageData!=null && imageData.size() > 0){
			// 上传文件
			for (MultipartFile image : imageData) {
				// 获取后缀判断
				String extension = FileUtils.getFileExtension(image.getOriginalFilename());
				System.out.println("extension: "+extension);
				if (extension.equals("jpg") && extension.equals("png")){
					throw new IncorrectExtensionException("不正确的类型"+extension);
				}
				String newName = FileUtils.getNewName(extension);
				String url = FileUtils.uploadFile(image, "blog/images",newName);
				// 插入数据库
				Images images = new Images();
				images.setBlogId(blogId);
				images.setName(newName);
				images.setUrl(url);
				imagesMapper.insert(images);
			}
		}
		LikedAssociation association = new LikedAssociation();
		association.setBlogId(blogId);
		likedAssociationMapper.addBlogUpdate(association,blogId);

		// 在Redis里添加数据
		List<Long> userIdList = userMapper.selectUserId();
		for (Long id:userIdList){
			redisCache.set("liked:userId"+id+":blogId:"+blogId,0);
		}
		return new ResultResponse(CodeMessage.SUCCESS);
	}

	@Override
	@Transactional
	public ResultResponse<FCommentDTO> addFComment(String token, String blogId, String content) {
		if (content.isEmpty()){
			throw new NoParamException("评论内容不能为空");
		}
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		String username = StpUtil.getExtra(token, "username").toString();
		FComment fComment = new FComment();
		fComment.setUserId(userId);
		fComment.setContent(content);
		fComment.setBlogId(Long.parseLong(blogId));
		fCommentMapper.insert(fComment);
		FCommentDTO fCommentDTO = new FCommentDTO(fComment.getId().toString(), content, username,null, 0L,fComment.getCreatedTime(),null);
		return new ResultResponse<>(CodeMessage.SUCCESS,fCommentDTO);
	}

	@Override
	@Transactional
	public ResultResponse<LikedDTO> like(String token, String blogId,Integer isLiked) {
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		System.out.println("isLiked: "+isLiked);
		int flag = (isLiked+1)%2;
		System.out.println("flag: "+flag);
		UpdateWrapper<LikedAssociation> wrapper = new UpdateWrapper<>();
		wrapper.eq("user_id",userId)
						.eq("blog_id",blogId)
								.set("is_liked",flag);
		likedAssociationMapper.update(null,wrapper);
		System.out.println("设置Redis关联表为"+flag);
		redisCache.set("liked:userId"+userId+":blogId:"+blogId,flag);
		System.out.println("设置Redis的点赞数");
		LikedRank likedRank = (LikedRank) redisCache.get("likeNum:blogId:" + blogId);
		if (flag == 1){
			likedRank.setLiked(likedRank.getLiked()+1);
		}else if(flag == 0){
			likedRank.setLiked(likedRank.getLiked()-1);
		}
		redisCache.set("likeNum:blogId:" + blogId,likedRank);
		return new ResultResponse(CodeMessage.SUCCESS,new LikedDTO(flag));
	}


	@Override
	@Transactional
	public ResultResponse delFComment(String token, String fCommentId) {
		UpdateWrapper<FComment> wrapper = new UpdateWrapper<>();
		UpdateWrapper<SComment> wrapperS = new UpdateWrapper<>();
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		wrapper.eq("user_id",userId)
				.eq("id",fCommentId);
		wrapperS.eq("f_comment_id",fCommentId)
				.eq("user_id",userId);

		int delete = fCommentMapper.delete(wrapper);
		if (delete == 0){
			throw new NoPrivilegeException("你没有删除此评论的权限");
		}
		sCommentMapper.delete(wrapperS);
		return new ResultResponse(CodeMessage.SUCCESS);
	}

	@Override
	public ResultResponse<SCommentDTO> addSComment(String token, String fCommentId, String content) {
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		String username = StpUtil.getExtra(token, "username").toString();
		SComment sComment = new SComment();
		sComment.setUserId(userId);
		sComment.setFCommentId(Long.parseLong(fCommentId));
		sComment.setContent(content);
		sCommentMapper.insert(sComment);
		SCommentDTO sCommentDTO = new SCommentDTO(sComment.getId().toString(), content, username, null,sComment.getCreatedTime());
		return new ResultResponse<>(CodeMessage.SUCCESS,sCommentDTO);
	}

	@Override
	public ResultResponse delSComment(String token, String sCommentId) {
		UpdateWrapper<SComment> wrapper = new UpdateWrapper<>();
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		wrapper.eq("user_id",userId)
				.eq("id",sCommentId);
		int delete = sCommentMapper.delete(wrapper);
		if (delete == 0){
			throw new NoPrivilegeException("你没有删除此评论的权限");
		}
		return new ResultResponse(CodeMessage.SUCCESS);
	}

	@Override
	public ResultResponse delBlog(String token, String blogId) {
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
		wrapper.eq("id",blogId)
				.eq("user_id",userId);
		int delete = blogMapper.delete(wrapper);
		if (delete == 1){
			return new ResultResponse<>(CodeMessage.SUCCESS);
		}
		return new ResultResponse(CodeMessage.FAIL);
	}

	@Override
	public ResultResponse<UserUpdateDTO> info(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Long userId = Long.parseLong(StpUtil.getExtra(token, "userId").toString());
		User user = userMapper.selectById(userId);
		return new ResultResponse<>(CodeMessage.SUCCESS,new UserUpdateDTO(user.getUsername(),user.getImage()));
	}

}
