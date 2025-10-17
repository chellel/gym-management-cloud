package com.gym.web.controller.system;

import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
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
import com.gym.common.core.text.Convert;
import com.gym.common.enums.BusinessType;
import com.gym.common.utils.StringUtils;
import com.gym.common.utils.poi.ExcelUtil;
import com.gym.common.utils.security.Md5Utils;
import com.gym.common.constant.UserConstants;
import com.gym.system.domain.GymUser;
import com.gym.system.service.IGymUserService;

/**
 * 教练管理
 * 
 * @author gym
 */
@Controller
@RequestMapping("/system/gymcoach")
public class GymCoachController extends BaseController
{
    private String prefix = "system/gymcoach";

    @Autowired
    private IGymUserService gymUserService;

    @RequiresPermissions("system:gymcoach:view")
    @GetMapping()
    public String gymcoach()
    {
        return prefix + "/gymcoach";
    }

    /**
     * 查询教练列表
     */
    @RequiresPermissions("system:gymcoach:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GymUser gymUser)
    {
        // 只查询教练角色的用户
        gymUser.setRole("coach");
        startPage();
        List<GymUser> list = gymUserService.selectGymUserList(gymUser);
        return getDataTable(list);
    }

    /**
     * 导出教练列表
     */
    @Log(title = "教练管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:gymcoach:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GymUser gymUser)
    {
        gymUser.setRole("coach");
        List<GymUser> list = gymUserService.selectGymUserList(gymUser);
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        return util.exportExcel(list, "教练数据");
    }

    /**
     * 新增教练
     */
    @RequiresPermissions("system:gymcoach:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存教练
     */
    @RequiresPermissions("system:gymcoach:add")
    @Log(title = "教练管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated GymUser gymUser)
    {
        if (!gymUserService.checkUserIdUnique(gymUser))
        {
            return error("新增教练'" + gymUser.getUserId() + "'失败，教练编号已存在");
        }
        else if (!gymUserService.checkPhoneUnique(gymUser))
        {
            return error("新增教练'" + gymUser.getUserId() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getEmail())
                && !gymUserService.checkEmailUnique(gymUser))
        {
            return error("新增教练'" + gymUser.getUserId() + "'失败，邮箱账号已存在");
        }
        gymUser.setCreateBy(getSysUser().getLoginName());
        gymUser.setPassword(Md5Utils.hash(gymUser.getPassword()));
        gymUser.setRole("coach"); // 设置为教练角色
        return toAjax(gymUserService.insertGymUser(gymUser));
    }

    /**
     * 修改教练
     */
    @RequiresPermissions("system:gymcoach:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        GymUser gymUser = gymUserService.selectGymUserById(id);
        mmap.put("gymUser", gymUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存教练
     */
    @RequiresPermissions("system:gymcoach:edit")
    @Log(title = "教练管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GymUser gymUser)
    {
        if (!gymUserService.checkUserIdUnique(gymUser))
        {
            return error("修改教练'" + gymUser.getUserId() + "'失败，教练编号已存在");
        }
        else if (!gymUserService.checkPhoneUnique(gymUser))
        {
            return error("修改教练'" + gymUser.getUserId() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(gymUser.getEmail())
                && !gymUserService.checkEmailUnique(gymUser))
        {
            return error("修改教练'" + gymUser.getUserId() + "'失败，邮箱账号已存在");
        }
        gymUser.setUpdateBy(getSysUser().getLoginName());
        gymUser.setRole("coach"); // 确保角色为教练
        return toAjax(gymUserService.updateGymUser(gymUser));
    }

    /**
     * 删除教练
     */
    @RequiresPermissions("system:gymcoach:remove")
    @Log(title = "教练管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        Long[] userIds = Convert.toLongArray(ids);
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return error("当前教练不能删除");
        }
        return toAjax(gymUserService.deleteGymUserByIds(ids));
    }

    /**
     * 重置教练密码
     */
    @RequiresPermissions("system:gymcoach:resetPwd")
    @Log(title = "教练管理", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{id}")
    public String resetPwd(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("gymUser", gymUserService.selectGymUserById(id));
        return prefix + "/resetPwd";
    }

    /**
     * 重置教练密码
     */
    @RequiresPermissions("system:gymcoach:resetPwd")
    @Log(title = "教练管理", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(GymUser gymUser)
    {
        gymUser.setPassword(Md5Utils.hash(gymUser.getPassword()));
        gymUser.setUpdateBy(getSysUser().getLoginName());
        return toAjax(gymUserService.resetGymUserPwd(gymUser));
    }

    /**
     * 教练状态修改
     */
    @RequiresPermissions("system:gymcoach:edit")
    @Log(title = "教练管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(GymUser gymUser)
    {
        gymUser.setUpdateBy(getSysUser().getLoginName());
        return toAjax(gymUserService.updateGymUserStatus(gymUser.getId(), gymUser.getStatus()));
    }

    /**
     * 教练导入
     */
    @RequiresPermissions("system:gymcoach:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        List<GymUser> gymUserList = util.importExcel(file.getInputStream());
        String operName = getSysUser().getLoginName();
        String message = gymUserService.importGymUser(gymUserList, updateSupport, operName);
        return success(message);
    }

    /**
     * 下载导入模板
     */
    @RequiresPermissions("system:gymcoach:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        return util.importTemplateExcel("教练数据");
    }
}