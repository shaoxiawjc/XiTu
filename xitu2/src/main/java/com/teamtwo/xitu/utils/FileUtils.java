package com.teamtwo.xitu.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author shaoxiawjc
 * @ 2023/12/2
 * @ IntelliJ IDEA
 * @ CloudeMusic
 * @ com.sodagroup.utils
 **/
public class FileUtils {
	// 阿里域名
	public static final String ALI_DOMAIN = "https://xitu.oss-cn-guangzhou.aliyuncs.com/";
	// 地域节点
	public static final String endPoint = "https://oss-cn-nanjing.aliyuncs.com";
	// 账号密码
	private static final String ALIYUN_ACCESS_KEY_ID = "LTAI5t5mmLPjmdc9pGDScnW4";
	private static final String ALIYUN_ACCESS_KEY_SECRET = "ak4jRL3M93oGAahweCAU78Uq4ezx2d";
	/**
	 * @param file 文件
	 * @param folderName 对应的在oss里的文件夹的名字
	 * */
	public static String uploadFile(MultipartFile file, String folderName) throws IOException, ClientException {
		// 从环境变量获取key和secret
//		EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
//		System.out.println("Access Key ID: " + credentialsProvider.getCredentials().getAccessKeyId());
//		System.out.println("Secret Access Key: " + credentialsProvider.getCredentials().getSecretAccessKey());


		// 生成文件名
		String fileExtension = FileUtils.getFileExtension(file.getOriginalFilename());
		String newName = FileUtils.getNewName(fileExtension);

		// OSS客户端对象
		OSS build = new OSSClientBuilder().build(endPoint,ALIYUN_ACCESS_KEY_ID,ALIYUN_ACCESS_KEY_SECRET);
		String objectKey = folderName+"/"+newName;
		try{
			build.putObject(
					"xitu",// 仓库名
					objectKey, // 文件名
					file.getInputStream()
			);
		}finally {
			if (build != null){
				build.shutdown();
			}
		}
		return ALI_DOMAIN+objectKey;
	}

	public static String uploadFile(MultipartFile file, String folderName,String name) throws IOException, ClientException {
		// 从环境变量获取key和secret
//		EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
		// 生成文件名
		String fileExtension = FileUtils.getFileExtension(file.getOriginalFilename());

		// OSS客户端对象
		OSS build = new OSSClientBuilder().build(endPoint,ALIYUN_ACCESS_KEY_ID,ALIYUN_ACCESS_KEY_SECRET);
		String objectKey = folderName+"/"+name+fileExtension;
		try{
			build.putObject(
					"xitu",// 仓库名
					objectKey, // 文件名
					file.getInputStream()
			);
		}finally {
			if (build != null){
				build.shutdown();
			}
		}
		return ALI_DOMAIN+objectKey;
	}

	public static String getFileExtension(String fileName) {
		String fileExtension = "";
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			fileExtension = fileName.substring(dotIndex + 1);
		}
		return fileExtension;
	}

	public static String getNewName(String fileExtension){
		String newName = UUID.randomUUID().toString()+"."+fileExtension;
		System.out.println(newName);
		return newName;
	}
}
