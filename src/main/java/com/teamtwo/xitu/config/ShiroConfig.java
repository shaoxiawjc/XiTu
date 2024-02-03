package com.teamtwo.xitu.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shaoxiawjc
 * @ 2024/2/1
 * @ IntelliJ IDEA
 * @ shirolearn
 * @ com.shaoxia.shirolearn.config
 **/
@Configuration
public class ShiroConfig {
	// 第三步：ShiroFilterFactoryBean
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		return bean;
	}
	// 第二步：DefaultWebSecurityManager
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager(@Qualifier("userRealm")
																UserRealm userRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 关联userRealm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	// 第一步：创建realm对象
	@Bean
	public UserRealm userRealm(){
		return new UserRealm();
	}
}
