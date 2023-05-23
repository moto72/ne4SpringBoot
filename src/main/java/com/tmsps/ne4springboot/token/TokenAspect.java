package com.tmsps.ne4springboot.token;

import cn.hutool.core.lang.Assert;
import com.tmsps.ne4springboot.util.WebUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 重复提交aop
 *
 * @author 冯晓东
 */
@Aspect
@Component
public class TokenAspect {

    private static final Logger logger = LoggerFactory.getLogger(TokenAspect.class);

    @Autowired
    private TokenService tokenService;

    /**
     * @param jp 经测试,会按照单个浏览器,并行执行. 无需担心同步问题.
     */
    @Before("@annotation(com.tmsps.ne4springboot.token.TokenCheck)")
    public void before(JoinPoint jp) throws Throwable {
        String token = WebUtil.getRequest().getParameter("token");
        logger.info("token --> {}", token);
        Assert.notBlank(token, "500:Parameter <token> can not be null.");
        Assert.isTrue(token.contains("@@"), "500:Parameter <token> is invalid key.");

        String key = token.split("@@")[0];
        String snToken = tokenService.getAsyncToken("token@@" + key);

        logger.info("session token --> {}", snToken);
        if (!token.equals(snToken)) {
            throw new RuntimeException("500:Token is invalid.");
        }

    }

}
