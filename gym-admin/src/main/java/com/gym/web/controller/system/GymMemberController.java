package com.gym.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;
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
import com.gym.common.utils.DateUtils;
import com.gym.common.utils.StringUtils;
import com.gym.common.utils.poi.ExcelUtil;
import com.gym.system.domain.GymMembership;
import com.gym.system.domain.GymUser;
import com.gym.system.domain.dto.GymMemberDTO;
import com.gym.system.service.IGymMembershipService;
import com.gym.system.service.IGymUserService;

/**
 * 会员管理
 * 
 * @author gym
 */
@Controller
@RequestMapping("/system/gymmember")
public class GymMemberController extends BaseController
{
    @Autowired
    private IGymUserService gymUserService;

    @Autowired
    private IGymMembershipService gymMembershipService;

    /**
     * 查询会员列表（联表查询）
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody GymMemberDTO gymMemberDTO, 
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, 
                             @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize)
    {
        startPage(page, pageSize);
        gymMemberDTO.setRole("member");
        List<GymMemberDTO> list = gymMembershipService.selectGymMemberList(gymMemberDTO);
        return getDataTable(list);
    }

    /**
     * 导出会员列表
     */
    @Log(title = "会员管理", businessType = BusinessType.EXPORT)
    // @RequiresPermissions("system:gymmember:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(GymMemberDTO gymMemberDTO)
    {
        List<GymMemberDTO> list = gymMembershipService.selectGymMemberList(gymMemberDTO);
        ExcelUtil<GymMemberDTO> util = new ExcelUtil<GymMemberDTO>(GymMemberDTO.class);
        return util.exportExcel(list, "会员数据");
    }

    /**
     * 新增保存会员
     */
    // @RequiresPermissions("system:gymmember:add")
    @Log(title = "会员管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated GymMemberDTO gymMemberDTO)
    {
        // 检查用户是否存在
        GymUser existingUser = gymUserService.selectGymUserByUserId(gymMemberDTO.getUserCode());
        if (existingUser == null)
        {
            return error("用户编号'" + gymMemberDTO.getUserCode() + "'不存在，请先创建用户");
        }

        // 检查是否已有有效会籍
        if (gymMembershipService.checkUserHasActiveMembership(existingUser.getId()))
        {
            return error("用户'" + gymMemberDTO.getUserCode() + "'已有有效会籍，不能重复创建");
        }

        // 创建会籍记录
        GymMembership gymMembership = new GymMembership();
        gymMembership.setUserId(existingUser.getId());
        gymMembership.setMembershipType(gymMemberDTO.getMembershipType());
        gymMembership.setStartDate(gymMemberDTO.getMembershipStartDate());
        gymMembership.setExpireDate(gymMemberDTO.getMembershipExpireDate());
        gymMembership.setStatus("active");
        gymMembership.setRemark(gymMemberDTO.getRemark());
        gymMembership.setCreateBy(getLoginName());
        gymMembership.setCreateTime(DateUtils.getNowDate());
        return toAjax(gymMembershipService.insertGymMembership(gymMembership));
    }

    /**
     * 修改保存会员
     */
    // @RequiresPermissions("system:gymmember:edit")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GymMemberDTO gymMemberDTO)
    {
        GymMembership gymMembership = gymMembershipService.selectGymMembershipById(gymMemberDTO.getMembershipId());
        if (gymMembership == null)
        {
            return error("会籍记录不存在");
        }

        gymMembership.setMembershipType(gymMemberDTO.getMembershipType());
        gymMembership.setStartDate(gymMemberDTO.getMembershipStartDate());
        gymMembership.setExpireDate(gymMemberDTO.getMembershipExpireDate());
        gymMembership.setStatus(gymMemberDTO.getMembershipStatus());
        gymMembership.setRemark(gymMemberDTO.getRemark());
        gymMembership.setUpdateBy(getLoginName());

        return toAjax(gymMembershipService.updateGymMembership(gymMembership));
    }

    /**
     * 删除会员
     */
    @RequiresPermissions("system:gymmember:remove")
    @Log(title = "会员管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(gymMembershipService.deleteGymMembershipByIds(ids));
    }

    /**
     * 修改会员状态
     */
    // @RequiresPermissions("system:gymmember:edit")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(GymMembership gymMembership)
    {
        return toAjax(gymMembershipService.updateGymMembershipStatus(gymMembership.getId(), gymMembership.getStatus()));
    }

    /**
     * 续费会员
     */
    // @RequiresPermissions("system:gymmember:edit")
    @Log(title = "会员续费", businessType = BusinessType.UPDATE)
    @PostMapping("/renew")
    @ResponseBody
    public AjaxResult renew(@RequestBody GymMemberDTO gymMemberDTO)
    {
        GymMembership gymMembership = gymMembershipService.selectGymMembershipById(gymMemberDTO.getMembershipId());
        if (gymMembership == null)
        {
            return error("会籍记录不存在");
        }

        // 更新到期日期
        gymMembership.setExpireDate(gymMemberDTO.getMembershipExpireDate());
        gymMembership.setStatus("active");
        gymMembership.setRemark(gymMemberDTO.getRemark());
        gymMembership.setUpdateBy(getLoginName());

        return toAjax(gymMembershipService.updateGymMembership(gymMembership));
    }

    /**
     * 查询即将过期的会员
     */
    // @RequiresPermissions("system:gymmember:list")
    @PostMapping("/expiring")
    @ResponseBody
    public TableDataInfo expiring(@RequestParam(defaultValue = "7") int days)
    {
        startPage();
        List<GymMembership> list = gymMembershipService.selectExpiringMemberships(days);
        return getDataTable(list);
    }

    /**
     * 查询已过期的会员
     */
    // @RequiresPermissions("system:gymmember:list")
    @PostMapping("/expired")
    @ResponseBody
    public TableDataInfo expired()
    {
        startPage();
        List<GymMembership> list = gymMembershipService.selectExpiredMemberships();
        return getDataTable(list);
    }

    /**
     * 根据会籍ID获取详细信息
     */
    // @RequiresPermissions("system:gymmember:list")
    @GetMapping(value = "/{membershipId}")
    @ResponseBody
    public AjaxResult getInfo(@PathVariable("membershipId") Long membershipId)
    {
        GymMembership gymMembership = gymMembershipService.selectGymMembershipById(membershipId);
        if (gymMembership == null)
        {
            return error("会籍记录不存在");
        }
        
        GymUser gymUser = gymUserService.selectGymUserById(gymMembership.getUserId());
        GymMemberDTO gymMemberDTO = new GymMemberDTO();
        gymMemberDTO.setUserId(gymUser.getUserId());
        gymMemberDTO.setUserCode(gymUser.getUserId());
        gymMemberDTO.setUserName(gymUser.getName());
        gymMemberDTO.setPhone(gymUser.getPhone());
        gymMemberDTO.setEmail(gymUser.getEmail());
        gymMemberDTO.setGender(gymUser.getGender());
        gymMemberDTO.setBirthDate(gymUser.getBirthDate());
        gymMemberDTO.setRole(gymUser.getRole());
        gymMemberDTO.setUserStatus(gymUser.getStatus());
        gymMemberDTO.setMembershipId(gymMembership.getId());
        gymMemberDTO.setMembershipType(gymMembership.getMembershipType());
        gymMemberDTO.setMembershipStartDate(gymMembership.getStartDate());
        gymMemberDTO.setMembershipExpireDate(gymMembership.getExpireDate());
        gymMemberDTO.setMembershipStatus(gymMembership.getStatus());
        gymMemberDTO.setRemark(gymMembership.getRemark());
        
        return success(gymMemberDTO);
    }

    /**
     * 根据用户ID获取会籍列表
     */
    // @RequiresPermissions("system:gymmember:list")
    @GetMapping(value = "/user/{userId}")
    @ResponseBody
    public AjaxResult getMembershipsByUserId(@PathVariable("userId") Long userId)
    {
        List<GymMembership> list = gymMembershipService.selectGymMembershipByUserId(userId);
        return success(list);
    }

    /**
     * 根据用户编号获取当前有效会籍
     */
    // @RequiresPermissions("system:gymmember:list")
    @GetMapping(value = "/active/{userCode}")
    @ResponseBody
    public AjaxResult getActiveMembershipByUserCode(@PathVariable("userCode") String userCode)
    {
        GymUser gymUser = gymUserService.selectGymUserByUserId(userCode);
        if (gymUser == null)
        {
            return error("用户不存在");
        }
        
        GymMembership gymMembership = gymMembershipService.selectActiveGymMembershipByUserId(gymUser.getId());
        return success(gymMembership);
    }

    /**
     * 检查用户是否有有效会籍
     */
    // @RequiresPermissions("system:gymmember:list")
    @PostMapping("/checkActiveMembership")
    @ResponseBody
    public AjaxResult checkActiveMembership(@RequestParam Long userId)
    {
        boolean hasActive = gymMembershipService.checkUserHasActiveMembership(userId);
        return success(hasActive);
    }

    /**
     * 获取会员统计数据
     */
    // @RequiresPermissions("system:gymmember:list")
    @GetMapping("/stats/summary")
    @ResponseBody
    public AjaxResult getMemberStatistics()
    {
        java.util.Map<String, Object> stats = gymMembershipService.selectMemberStatistics();
        return success(stats);
    }
}
