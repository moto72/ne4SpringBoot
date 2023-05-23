package com.tmsps.ne4springboot.token;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TokenController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param key
     * @return
     */
    @GetMapping("/get_token")
    public String get_token(String key) {
        Assert.notBlank(key, "500:Parameter <key> can not be null.");

        // 设置token值
        String token = key + "@@" + IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set("token@@" + key, token, 60, TimeUnit.MINUTES);
        return token;
    }

}
