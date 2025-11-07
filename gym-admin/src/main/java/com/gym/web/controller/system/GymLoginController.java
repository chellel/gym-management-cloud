package com.gym.web.controller.system;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.utils.ServletUtils;
import com.gym.common.utils.StringUtils;
import com.gym.framework.shiro.service.GymUserLoginService;
import com.gym.system.domain.GymUser;

import java.util.Map;

/**
 * 健身房会员登录验证
 * 
 * @author gym
 */
@Controller
public class GymLoginController extends BaseController
{
    @Autowired
    private GymUserLoginService gymUserLoginService;

    /**
     * 健身房会员登录
     */
    @PostMapping("/system/gymLogin")
    @ResponseBody
    public AjaxResult gymLogin(@RequestBody Map<String, String> request)
    {
        try
        {
            GymUser user = gymUserLoginService.login(request.get("username"), request.get("password"));
            
            // 创建响应对象，只包含必要的字段
            GymUserResponse response = new GymUserResponse(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getGender(),
                user.getRole(),
                user.getStatus(),
                user.getCreateTime()
            );
            
            return success(response);
        }
        catch (Exception e)
        {
            String msg = "会员用户名或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    /**
     * 会员登出
     */
    @PostMapping("/system/gymLogout")
    @ResponseBody
    public AjaxResult gymLogout()
    {
        try
        {
            // 这里可以清除session或其他登录状态
            return success("会员登出成功");
        }
        catch (Exception e)
        {
            return error("登出失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前登录的会员信息
     */
    @GetMapping("/gymUserInfo")
    @ResponseBody
    public AjaxResult getGymUserInfo()
    {
        try
        {
            // 这里可以从session或其他地方获取当前登录的会员信息
            // 暂时返回一个示例
            return success(null);
        }
        catch (Exception e)
        {
            return error("获取会员信息失败：" + e.getMessage());
        }
    }

}

