package com.teamtwo.xitu;

import com.teamtwo.xitu.utils.PasswordUtil;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XituApplicationTests {

	@Test
	void contextLoads() {
	}

//	@Test
//	void test01(){
//		System.out.println(PasswordUtil.verifyPassword("1234567",
//				new ,
//				"4e65ec7b047535c89ab1c24fb5e736b21637727dc17ce271857fbd091c57b716"));
//	}
//
//	@Test
//	void  test02(){
//		System.out.println(PasswordUtil.verifyPassword("1234567",
//				"cd25a6e9-adb2-404d-966d-880856f375b9",
//				"513a1233ea9b36abe9ae8a1ca05a5c16d251675bb0a0933b148a1bda3bb9a18f"));
//	}

	@Test
	void test03(){
		System.out.println(PasswordUtil.verifyPassword("123456",
				ByteSource.Util.bytes("YzBhZTE5NGMtYjQyMS00ZjkzLWI3NmItZGZmOWM4NGZhYzU5"),
				"ae0db5f58de1f7b4408d19140295642c39f300da57537451048b9f3c56d986b5"));
	}

	@Test
	void test04(){
		// 原来的盐值
		String generateRandomSalt = PasswordUtil.generateRandomSalt();

		ByteSource ByteSource666 = ByteSource.Util.bytes(generateRandomSalt);
		System.out.println("ByteSource666: "+ByteSource666);
		String base64DB = ByteSource666.toBase64();
		System.out.println("base64DB: "+base64DB);
		System.out.println("对base64DB弄回去: "+ByteSource.Util.bytes(base64DB.replace("\\s","")));
		System.out.println("是否相等： "+ByteSource.Util.bytes(base64DB).equals(ByteSource666));


		String inputPWD = "1234567";
		// 存储在数据库的密码
		String newPassword = PasswordUtil.hashPassword(inputPWD, ByteSource.Util.bytes(base64DB));

		System.out.println(base64DB);
		System.out.println(ByteSource.Util.bytes(base64DB));
		System.out.println(PasswordUtil.verifyPassword(inputPWD,ByteSource.Util.bytes(base64DB),newPassword));

		System.out.println(ByteSource.Util.bytes(base64DB).equals(ByteSource.Util.bytes(generateRandomSalt)));
	}

	@Test
	void test05(){
		// 原来的盐值
		String s = "sx";
		// 这是用来加密的ByteSource
		ByteSource bytes = ByteSource.Util.bytes(s);
		System.out.println(bytes);
		// 生成加密后的新密码
		String newPassword = PasswordUtil.hashPassword("1234567", bytes);
		// 把原来的ByteSource对象变成toBase64存储在数据库
		String DBSalt = bytes.toBase64();

		// 从数据库里获取盐
		ByteSource sbytes = ByteSource.Util.bytes(DBSalt);
		System.out.println(sbytes);
		// 判断
		System.out.println(PasswordUtil.verifyPassword("1234567",sbytes,newPassword));
	}

	@Test
	void test(){
		String password = PasswordUtil.hashPassword("1234567",
				ByteSource.Util.bytes("shaoxia07teamtwo"));
		System.out.println(password);
	}
}
