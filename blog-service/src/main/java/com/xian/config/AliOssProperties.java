package com.xian.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云oss的配置属性类
 */
@Component
@ConfigurationProperties(prefix = "alioss")
@Data
public class AliOssProperties {
 
//    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
//    private String accessKeyId = "LTAI5tQ3atsyXyYHvPFE8iTp";
//    private String accessKeySecret = "Fu3LpH2IjQYhQ5jFghcIIoPl8TRKry";
//    private String bucketName = "xian-blog";

    private String endpoint ;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}

