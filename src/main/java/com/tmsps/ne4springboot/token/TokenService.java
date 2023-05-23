package com.tmsps.ne4springboot.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Token 相关工具类
 *
 * @author 冯晓东 398479251@qq.com
 */
@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 同步获取token值并移除
     *
     * @param key
     * @return
     */
    public String getAsyncToken(String key) {
        // 压测代码
		/*
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
        // 无需上锁, 获取的时候直接移除.
        String token = redisTemplate.opsForValue().getAndSet(key, "");
        redisTemplate.delete(key);
        return token;
    }

}
