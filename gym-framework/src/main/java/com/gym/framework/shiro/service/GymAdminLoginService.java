package com.gym.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gym.common.constant.Constants;
import com.gym.common.exception.user.UserNotExistsException;
import com.gym.common.exception.user.UserPasswordNotMatchException;
import com.gym.common.exception.user.UserBlockedException;
import com.gym.common.utils.MessageUtils;
import com.gym.common.utils.StringUtils;
import com.gym.framework.manager.AsyncManager;
import com.gym.framework.manager.factory.AsyncFactory;
import com.gym.system.domain.GymAdmin;
import com.gym.system.service.IGymAdminService;
import com.gym.framework.shiro.service.AdminPasswordService;

/**
 * 健身房管理员登录校验方法
 * 
 * @author gym
 */
@Component
public class GymAdminLoginService
{
    @Autowired
    private IGymAdminService gymAdminService;

    @Autowired
    private AdminPasswordService adminPasswordService;

    /**
     * 管理员登录
     */
    public GymAdmin login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }

        // 查询管理员信息
        GymAdmin admin = gymAdminService.selectGymAdminByUsername(username);

         if (admin == null)
         {
             AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
             throw new UserNotExistsException();
         }

         // 检查管理员状态
         if (!"active".equals(admin.getStatus()))
         {
             AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked")));
             throw new UserBlockedException();
         }

         // 验证密码
         adminPasswordService.validate(admin, password);

         AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        return admin;
    }
}
