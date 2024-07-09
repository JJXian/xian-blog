package com.xian.common.redis.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: jjxian
 */
@Data
public class RedisData {

    LocalDateTime expireTime;

    Object data;
}
