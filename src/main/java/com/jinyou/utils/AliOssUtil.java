package com.jinyou.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
@Component
public class AliOssUtil {
    // 静态变量，给静态方法使用
    public static String ENDPOINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String REGION;

    // 从配置文件注入（非静态）
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.region}")
    private String region;

    // 项目启动时，把注入的值赋值给静态变量
    @PostConstruct
    public void init() {
        ENDPOINT = this.endpoint;
        ACCESS_KEY_ID = this.accessKeyId;
        ACCESS_KEY_SECRET = this.accessKeySecret;
        BUCKET_NAME = this.bucketName;
        REGION = this.region;
    }
    public static String uploadFile(String objectName , InputStream in) throws Exception {

        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        // 使用静态凭证（避免环境变量问题）
        CredentialsProvider credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
                ACCESS_KEY_ID, ACCESS_KEY_SECRET
        );

        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();
        String url = "";
        try {
            // 本地文件路径（确保存在）
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, in);
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            System.out.println("上传成功！文件URL：https://" + BUCKET_NAME + "." + ENDPOINT.replace("https://","") + "/" + objectName);
            // url组成
            url = "https://" + BUCKET_NAME + "." + ENDPOINT.replace("https://","") + "/" + objectName;
        } catch (OSSException oe) {
            System.out.println("OSS错误：" + oe.getErrorMessage());
            System.out.println("错误码：" + oe.getErrorCode());
        } catch (ClientException ce) {
            System.out.println("客户端错误：" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
