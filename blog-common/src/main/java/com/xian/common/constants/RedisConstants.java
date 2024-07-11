package com.xian.common.constants;

/**
 * @Author: jjxian
 */
public class RedisConstants {

    public static final Long CACHE_NULL_TTL = 2L;
    public static final String CACHE_LOCK_KEY = "lock:";

//    博客缓存
    public static final Long CACHE_BLOG_TTL = 30L;
    public static final String CACHE_BLOG_KEY = "cache:blog:";
//      互斥锁
    public static  final  String LOCK_BLOG_KEY="lock:blog:";

//    邮箱验证
    public static final String REGISTER_MAIL_CODE_KEY = "user:register:emailCode:";
    public static final Long REGISTER_MAIL_CODE_TTL = 300L;

}
