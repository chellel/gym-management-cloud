package com.gym.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.gym.common.annotation.Log;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.common.enums.BusinessType;
import com.gym.common.utils.poi.ExcelUtil;
import com.gym.common.utils.StringUtils;
import com.gym.system.domain.GymUser;
import com.gym.system.service.IGymUserService;

/**
 * 健身房会员信息
 * 
 * @author gym
 */
@Controller
@RequestMapping("/system/gymuser")
public class GymUserController extends BaseController
{
    @Autowired
    private IGymUserService gymUserService;

    /**
     * 查询会员列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GymUser gymUser)
    {
        startPage();
        List<GymUser> list = gymUserService.selectGymUserList(gymUser);
        return getDataTable(list);
    }

    /**
     * 导出会员列表
     */
    @Log(title = "会员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GymUser gymUser)
    {
        List<GymUser> list = gymUserService.selectGymUserList(gymUser);
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        return util.exportExcel(list, "会员数据");
    }

    /**
     * 导入会员数据
     */
    @Log(title = "会员管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:gymuser:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        List<GymUser> gymUserList = util.importExcel(file.getInputStream());
        String message = gymUserService.importGymUser(gymUserList, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    /**
     * 新增保存会员
     */
    @RequiresPermissions("system:gymuser:add")
    @Log(title = "会员管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated GymUser gymUser)
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
     * 获取会员详情
     */
    // @RequiresPermissions("system:gymcourse:view")
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getUserDetail(@PathVariable("id") Long id)
    {
        GymUser gymUser = gymUserService.selectGymUserById(id);
        return success(gymUser);
    }

    /**
     * 修改保存会员
     */
    @RequiresPermissions("system:gymuser:edit")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GymUser gymUser)
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
    @RequiresPermissions("system:gymuser:remove")
    @Log(title = "会员管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(gymUserService.deleteGymUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 重置会员密码
     */
    @RequiresPermissions("system:gymuser:resetPwd")
    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("gymUser", gymUserService.selectGymUserById(id));
        return "system/gymuser/resetPwd";
    }

    /**
     * 重置会员密码
     */
    @RequiresPermissions("system:gymuser:resetPwd")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(GymUser gymUser)
    {
        return toAjax(gymUserService.resetGymUserPwd(gymUser));
    }

    /**
     * 修改会员状态
     */
    @RequiresPermissions("system:gymuser:edit")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(GymUser gymUser)
    {
        return toAjax(gymUserService.updateGymUserStatus(gymUser.getId(), gymUser.getStatus()));
    }

    /**
     * 校验用户编号
     */
    @PostMapping("/checkUserIdUnique")
    @ResponseBody
    public boolean checkUserIdUnique(GymUser gymUser)
    {
        return gymUserService.checkUserIdUnique(gymUser);
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public boolean checkPhoneUnique(GymUser gymUser)
    {
        return gymUserService.checkPhoneUnique(gymUser);
    }

    /**
     * 校验email
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public boolean checkEmailUnique(GymUser gymUser)
    {
        return gymUserService.checkEmailUnique(gymUser);
    }

    // ========== REST API 接口 ==========

    /**
     * 根据用户编号获取详细信息
     */
    @GetMapping("/userId/{userId}")
    @ResponseBody
    public AjaxResult getInfoByUserId(@PathVariable("userId") String userId)
    {
        return success(gymUserService.selectGymUserByUserId(userId));
    }

    /**
     * 根据手机号获取详细信息
     */
    @GetMapping("/phone/{phone}")
    @ResponseBody
    public AjaxResult getInfoByPhone(@PathVariable("phone") String phone)
    {
        return success(gymUserService.selectGymUserByPhone(phone));
    }

    /**
     * 根据邮箱获取详细信息
     */
    @GetMapping("/email/{email}")
    @ResponseBody
    public AjaxResult getInfoByEmail(@PathVariable("email") String email)
    {
        return success(gymUserService.selectGymUserByEmail(email));
    }

    /**
     * REST API - 新增会员
     */
    @PostMapping("/api")
    @ResponseBody
    public AjaxResult addApi(@RequestBody GymUser gymUser)
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
     * REST API - 修改会员
     */
    @PutMapping("/api")
    @ResponseBody
    public AjaxResult editApi(@RequestBody GymUser gymUser)
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
     * REST API - 删除会员
     */
    @PostMapping("/api/remove")
    @ResponseBody
    public AjaxResult removeApi(@RequestBody Long[] ids)
    {
        try
        {
            String idsStr = String.join(",", java.util.Arrays.stream(ids).map(String::valueOf).toArray(String[]::new));
            return toAjax(gymUserService.deleteGymUserByIds(idsStr));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * REST API - 修改会员状态
     */
    @PostMapping("/api/changeStatus")
    @ResponseBody
    public AjaxResult changeStatusApi(@RequestBody GymUser gymUser)
    {
        return toAjax(gymUserService.updateGymUserStatus(gymUser.getId(), gymUser.getStatus()));
    }

    /**
     * REST API - 重置会员密码
     */
    @PostMapping("/api/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdApi(@RequestBody GymUser gymUser)
    {
        return toAjax(gymUserService.resetGymUserPwd(gymUser));
    }

    /**
     * REST API - 校验用户编号是否唯一
     */
    @PostMapping("/api/checkUserIdUnique")
    @ResponseBody
    public AjaxResult checkUserIdUniqueApi(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkUserIdUnique(gymUser));
    }

    /**
     * REST API - 校验手机号码是否唯一
     */
    @PostMapping("/api/checkPhoneUnique")
    @ResponseBody
    public AjaxResult checkPhoneUniqueApi(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkPhoneUnique(gymUser));
    }

    /**
     * REST API - 校验邮箱是否唯一
     */
    @PostMapping("/api/checkEmailUnique")
    @ResponseBody
    public AjaxResult checkEmailUniqueApi(@RequestBody GymUser gymUser)
    {
        return success(gymUserService.checkEmailUnique(gymUser));
    }
}