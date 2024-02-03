package com.teamtwo.xitu.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamtwo.xitu.mapper.UserMapper;
import com.teamtwo.xitu.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author shaoxiawjc
 * @ 2024/2/1
 * @ IntelliJ IDEA
 * @ shirolearn
 * @ com.shaoxia.shirolearn.config
 **/
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private UserMapper userMapper;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("执行了=============================>doGetAuthorizationInfo方法");
		return null;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("执行了=============================>doGetAuthenticationInfo认证方法");
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 通过数据库查用户username
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("name",token.getUsername());
		User user = userMapper.selectOne(wrapper);
		// 判断，如果为空就return null ，这样在后面会抛出账号不存在异常
		if (Objects.isNull(user)){
			return null;
		}
		// 这里判断密码，把数据库的密码传过去
		return new SimpleAuthenticationInfo(user,
				user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()),
				getName());
	}
}
