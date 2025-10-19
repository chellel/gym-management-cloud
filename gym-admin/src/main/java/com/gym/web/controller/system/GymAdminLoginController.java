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
import com.gym.framework.shiro.service.GymAdminLoginService;
import com.gym.system.domain.GymAdmin;

import java.util.Map;

/**
 * 健身房管理员登录验证
 * 
 * @author gym
 */
@Controller
public class GymAdminLoginController extends BaseController
{
    @Autowired
    private GymAdminLoginService gymAdminLoginService;

    /**
     * 健身房管理员登录
     */
    @PostMapping("/system/gymAdminLogin")
    @ResponseBody
    public AjaxResult gymAdminLogin(@RequestBody Map<String, String> request)
    {
        try
        {
            GymAdmin admin = gymAdminLoginService.login(request.get("username"), request.get("password"));
            
            // 创建响应对象，只包含必要的字段
            GymAdminResponse response = new GymAdminResponse(
                admin.getId(),
                admin.getUsername(),
                admin.getName(),
                admin.getPhone(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreateTime()
            );
            
            return success(response);
        }
        catch (Exception e)
        {
            String msg = "管理员用户名或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }

    /**
     * 管理员登出
     */
    @PostMapping("/system/gymAdminLogout")
    @ResponseBody
    public AjaxResult gymAdminLogout()
    {
        try
        {
            // 这里可以清除session或其他登录状态
            return success("管理员登出成功");
        }
        catch (Exception e)
        {
            return error("登出失败：" + e.getMessage());
        }
    }

    /**
     * 获取当前登录的管理员信息
     */
    @GetMapping("/gymAdminInfo")
    @ResponseBody
    public AjaxResult getGymAdminInfo()
    {
        try
        {
            // 这里可以从session或其他地方获取当前登录的管理员信息
            // 暂时返回一个示例
            return success(null);
        }
        catch (Exception e)
        {
            return error("获取管理员信息失败：" + e.getMessage());
        }
    }

}
