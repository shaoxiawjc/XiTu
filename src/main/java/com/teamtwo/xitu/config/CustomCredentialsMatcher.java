package com.teamtwo.xitu.config;

import com.teamtwo.xitu.pojo.User;
import com.teamtwo.xitu.utils.PasswordUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class CustomCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取用户输入的密码
        String submittedPassword = new String((char[]) token.getCredentials());
        // 获取数据库中存储的密码和 salt
        String storedPassword = (String) info.getCredentials();
        System.out.println("\n-----------------------doCredentialsMatch------------------------");
        System.out.println("提交的密码 ================================>"+submittedPassword);
        System.out.println("数据库的密码 ======================>"+storedPassword);
        ByteSource salt = ((SaltedAuthenticationInfo) info).getCredentialsSalt();
        System.out.println("数据库转化后的盐的Base64 ====================================>"+salt.toBase64());
        System.out.println("经过上交的密码和盐重新加密 =======>"+PasswordUtil.hashPassword(submittedPassword, salt));
        System.out.println("----------------------------------------------------------------");
        return PasswordUtil.verifyPassword(submittedPassword,salt,storedPassword);
    }
}
