package com.gym.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.system.domain.GymUser;
import com.gym.common.utils.StringUtils;
import com.gym.system.service.IGymUserService;

/**
 * 健身房会员 REST API
 * 
 * @author gym
 */
@RestController
@RequestMapping("/api/gymuser")
public class GymUserApiController extends BaseController
{
    @Autowired
    private IGymUserService gymUserService;

    /**
     * 获取会员列表
     */
    @PostMapping("/list")
    public TableDataInfo list(GymUser gymUser)
    {
        startPage();
        List<GymUser> list = gymUserService.selectGymUserList(gymUser);
        return getDataTable(list);
    }

    /**
     * 根据会员ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gymUserService.selectGymUserById(id));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping(value = "/userId/{userId}")
    public AjaxResult getInfoByUserId(@PathVariable("userId") String userId)
    {
        return success(gymUserService.selectGymUserByUserId(userId));
    }

    /**
     * 根据手机号获取详细信息
     */
    @GetMapping(value = "/phone/{phone}")
    public AjaxResult getInfoByPhone(@PathVariable("phone") String phone)
    {
        return success(gymUserService.selectGymUserByPhone(phone));
    }

    /**
     * 根据邮箱获取详细信息
     */
    @GetMapping(value = "/email/{email}")
    public AjaxResult getInfoByEmail(@PathVariable("email") String email)
    {
        return success(gymUserService.selectGymUserByEmail(email));
    }

    /**
     * 新增会员
     */
    @PostMapping
    public AjaxResult add(@RequestBody GymUser gymUser)
    {
        if (!gymUserService.checkUserIdUnique(gymUser))
        {
            return error("新增会员'" + gymUser.getUserId() + "'失败，用户编号已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getPhone()) && !gymUserService.checkPhoneUnique(gymUser))
        {
            return error("新增会员'" + gymUser.getUserId() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getEmail()) && !gymUserService.checkEmailUnique(gymUser))
        {
            return error("新增会员'" + gymUser.getUserId() + "'失败，邮箱账号已存在");
        }
        gymUser.setCreateBy(getLoginName());
        return toAjax(gymUserService.insertGymUser(gymUser));
    }

    /**
     * 修改会员
     */
    @PutMapping
    public AjaxResult edit(@RequestBody GymUser gymUser)
    {
        if (!gymUserService.checkUserIdUnique(gymUser))
        {
            return error("修改会员'" + gymUser.getUserId() + "'失败，用户编号已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getPhone()) && !gymUserService.checkPhoneUnique(gymUser))
        {
            return error("修改会员'" + gymUser.getUserId() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getEmail()) && !gymUserService.checkEmailUnique(gymUser))
        {
            return error("修改会员'" + gymUser.getUserId() + "'失败，邮箱账号已存在");
        }
        gymUser.setUpdateBy(getLoginName());
        return toAjax(gymUserService.updateGymUser(gymUser));
    }

    /**
     * 删除会员
     */
    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody Long[] ids)
    {
        String idsStr = String.join(",", java.util.Arrays.stream(ids).map(String::valueOf).toArray(String[]::new));
        return toAjax(gymUserService.deleteGymUserByIds(idsStr));
    }

    /**
     * 修改会员状态
     */
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody GymUser gymUser)
    {
        return toAjax(gymUserService.updateGymUserStatus(gymUser.getId(), gymUser.getStatus()));
    }

    /**
     * 重置会员密码
     */
    @PostMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody GymUser gymUser)
    {
        return toAjax(gymUserService.resetGymUserPwd(gymUser));
    }

    /**
     * 校验用户编号是否唯一
     */
    @PostMapping("/checkUserIdUnique")
    public AjaxResult checkUserIdUnique(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkUserIdUnique(gymUser));
    }

    /**
     * 校验手机号码是否唯一
     */
    @PostMapping("/checkPhoneUnique")
    public AjaxResult checkPhoneUnique(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkPhoneUnique(gymUser));
    }

    /**
     * 校验邮箱是否唯一
     */
    @PostMapping("/checkEmailUnique")
    public AjaxResult checkEmailUnique(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkEmailUnique(gymUser));
    }
}
