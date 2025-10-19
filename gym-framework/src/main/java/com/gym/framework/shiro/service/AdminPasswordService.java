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
import com.gym.system.domain.GymAdmin;
import jakarta.annotation.PostConstruct;
import com.gym.common.utils.security.Md5Utils;

/**
 * 健身房管理员登录密码方法
 * 
 * @author gym
 */
@Component
public class AdminPasswordService
{
    @Autowired
    private CacheManager cacheManager;

    private Cache<String, AtomicInteger> adminLoginRecordCache;

    @Value(value = "${admin.password.maxRetryCount:5}")
    private String maxRetryCount;

    @PostConstruct
    public void init()
    {
        adminLoginRecordCache = cacheManager.getCache(ShiroConstants.ADMIN_LOGIN_RECORD_CACHE);
    }

    /**
     * 验证管理员密码
     */
    public void validate(GymAdmin admin, String password)
    {
        String username = admin.getUsername();

        AtomicInteger retryCount = adminLoginRecordCache.get(username);

        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            adminLoginRecordCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("admin.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(admin, password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("admin.password.retry.limit.count", retryCount)));
            adminLoginRecordCache.put(username, retryCount);
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
    public boolean matches(GymAdmin admin, String newPassword)
    {
        return admin.getPassword().equals(encryptPassword(admin.getUsername(), newPassword));
    }

    /**
     * 清除登录记录缓存
     */
    public void clearLoginRecordCache(String username)
    {
        adminLoginRecordCache.remove(username);
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
