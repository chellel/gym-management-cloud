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
    private String prefix = "system/gymuser";

    @Autowired
    private IGymUserService gymUserService;

    // @RequiresPermissions("system:gymuser:view")
    @GetMapping()
    public String gymuser()
    {
        return prefix + "/gymuser";
    }

    /**
     * 查询会员列表
     */
    // @RequiresPermissions("system:gymuser:list")
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
    // @RequiresPermissions("system:gymuser:export")
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

    @RequiresPermissions("system:gymuser:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<GymUser> util = new ExcelUtil<GymUser>(GymUser.class);
        return util.importTemplateExcel("会员数据");
    }

    /**
     * 新增会员
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
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
     * 修改会员
     */
    @RequiresPermissions("system:gymuser:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("gymUser", gymUserService.selectGymUserById(id));
        return prefix + "/edit";
    }

    /**
     * 查询会员详细
     */
    @RequiresPermissions("system:gymuser:list")
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("gymUser", gymUserService.selectGymUserById(id));
        return prefix + "/view";
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
        return prefix + "/resetPwd";
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
}
