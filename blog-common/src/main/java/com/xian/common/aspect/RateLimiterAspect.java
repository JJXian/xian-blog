package com.xian.common.aspect;

import com.xian.common.annotation.RateLimiter;
import com.xian.common.enums.LimitType;
import com.xian.common.exception.CustomException;
import com.xian.common.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 用于拦截和处理带有 @RateLimiter 注解的方法。
 * 其核心功能是实现基于 Redis 的接口限流
 */
@Aspect
@Component
public class RateLimiterAspect {
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> limitScript;

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (number == null || number.intValue() > count) {
                throw new CustomException("5001", "访问过于频繁，请稍候再试");
            }
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    /**
     * 方法生成Redis键，结合了注解中的基础键、请求的IP以及方法的全名，确保每个方法的限流数据是唯一的。
     *
     * @param rateLimiter
     * @param point
     * @return
     */
    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(IpUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest())).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

    /**
     * 基于滑动窗口的限流
     */
    // 核心限流方法，判断请求是否允许
    /*
    public boolean isAllowed(String userId) {
        String key = "rate_limit:" + userId;

        // 当前时间戳（秒）
        long currentTime = Instant.now().getEpochSecond();
        long windowStart = currentTime - windowSize;

        // Redis ZSet操作
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        // Step 1: 清理时间窗口之外的请求记录
        zSetOps.removeRangeByScore(key, 0, windowStart);

        // Step 2: 获取当前窗口内的请求数量
        Long requestCount = zSetOps.zCard(key);

        if (requestCount != null && requestCount < maxRequests) {
            // Step 3: 如果没有超过限制，则记录当前请求，并允许通过
            zSetOps.add(key, String.valueOf(currentTime), currentTime);

            // Step 4: 设置键的过期时间，略大于窗口期，比如窗口期 + 1 秒
            redisTemplate.expire(key, windowSize + 1, TimeUnit.SECONDS);

            return true;
        } else {
            // 超过限流，拒绝请求
            return false;
        }
    }

     */
}