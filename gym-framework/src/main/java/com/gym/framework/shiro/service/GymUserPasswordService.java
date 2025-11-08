package com.gym.framework.shiro.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.gym.common.constant.Constants;
import com.gym.common.constant.ShiroConstants;
import com.gym.common.exception.user.UserPasswordNotMatchException;
import com.gym.common.exception.user.UserPasswordRetryLimitExceedException;
import com.gym.common.utils.MessageUtils;
import com.gym.framework.manager.AsyncManager;
import com.gym.framework.manager.factory.AsyncFactory;
import com.gym.system.domain.GymUser;
import jakarta.annotation.PostConstruct;
import com.gym.common.utils.security.Md5Utils;

/**
 * 健身房会员登录密码方法
 * 
 * @author gym
 */
@Component
public class GymUserPasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> userLoginRecordCache;

    @Value(value = "${user.password.maxRetryCount:5}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
    }

    /**
     * 验证会员密码
     */
    public void validate(GymUser user, String password)
    {
        String username = user.getUsername();

        AtomicInteger retryCount = userLoginRecordCache.get(username);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            userLoginRecordCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            userLoginRecordCache.put(username, retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    /**
     * 检查密码是否匹配
     */
    public boolean matches(GymUser user, String newPassword)
    {
        String username = user.getUsername();
        return user.getPassword().equals(encryptPassword(username, newPassword));
    }

    /**
     * 清除登录记录缓存
     */
    public void clearLoginRecordCache(String username)
    {
        userLoginRecordCache.remove(username);
    }

    /**
     * 加密密码（使用MD5，参考SysPasswordService的实现）
     */
    public String encryptPassword(String username, String password)
    {
        // 参考SysPasswordService的实现，使用username + password的组合
        return Md5Utils.hash(username + password);
    }

    /**
     * 验证密码强度
     */
    public boolean validatePasswordStrength(String password)
    {
        if (password == null || password.length() < 6)
        {
            return false;
        }
        
        // 检查是否包含数字
        boolean hasDigit = password.matches(".*\\d.*");
        // 检查是否包含字母
        boolean hasLetter = password.matches(".*[a-zA-Z].*");
        
        return hasDigit && hasLetter;
    }
}
