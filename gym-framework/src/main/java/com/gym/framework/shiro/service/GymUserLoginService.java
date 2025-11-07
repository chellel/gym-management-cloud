package com.gym.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gym.common.constant.Constants;
import com.gym.common.exception.user.UserBlockedException;
import com.gym.common.exception.user.UserNotExistsException;
import com.gym.common.utils.MessageUtils;
import com.gym.common.utils.StringUtils;
import com.gym.framework.manager.AsyncManager;
import com.gym.framework.manager.factory.AsyncFactory;
import com.gym.system.domain.GymUser;
import com.gym.system.service.IGymUserService;

/**
 * 健身房会员登录服务
 * 
 * @author gym
 */
@Component
public class GymUserLoginService
{
    @Autowired
    private IGymUserService gymUserService;

    @Autowired
    private GymUserPasswordService gymUserPasswordService;

    /**
     * 会员登录
     */
    public GymUser login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }

        // 查询会员信息
        GymUser user = gymUserService.selectGymUserByUsername(username);

        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }

        // TODO 验证密码
//        gymUserPasswordService.validate(user, password);

        return user;
    }
}

