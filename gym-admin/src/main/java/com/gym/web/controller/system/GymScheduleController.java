package com.gym.web.controller.system;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.gym.common.annotation.Log;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.common.enums.BusinessType;
import com.gym.common.utils.DateUtils;
import com.gym.system.domain.GymSchedule;
import com.gym.system.service.IGymScheduleService;

/**
 * 课程排班管理
 * 
 * @author gym
 */
@Controller
@RequestMapping("/system/gymschedule")
public class GymScheduleController extends BaseController
{
    @Autowired
    private IGymScheduleService gymScheduleService;

    /**
     * 查询排班列表
     */
//    @RequiresPermissions("system:gymschedule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GymSchedule gymSchedule)
    {
        startPage();
        List<GymSchedule> list = gymScheduleService.selectGymScheduleList(gymSchedule);
        return getDataTable(list);
    }

    /**
     * 新增保存排班
     */
    @RequiresPermissions("system:gymschedule:add")
    @Log(title = "排班管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated GymSchedule gymSchedule)
    {
        // 验证时间是否合理
        if (!gymScheduleService.validateScheduleTime(gymSchedule.getStartTime(), gymSchedule.getEndTime()))
        {
            return error("排班时间设置不合理，请检查开始时间和结束时间");
        }

        // 检查教练时间冲突
        if (gymScheduleService.checkCoachTimeConflict(gymSchedule.getCoachId(), gymSchedule.getStartTime(), gymSchedule.getEndTime(), null))
        {
            return error("教练在该时间段已有其他排班，请选择其他时间");
        }

        return toAjax(gymScheduleService.insertGymSchedule(gymSchedule));
    }

    /**
     * 获取排班详情
     */
    // @RequiresPermissions("system:gymschedule:view")
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getScheduleDetail(@PathVariable("id") Long id)
    {
        GymSchedule gymSchedule = gymScheduleService.selectGymScheduleById(id);
        return success(gymSchedule);
    }

    /**
     * 修改保存排班
     */
//    @RequiresPermissions("system:gymschedule:edit")
    @Log(title = "排班管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GymSchedule gymSchedule)
    {
        // 验证时间是否合理
        if (!gymScheduleService.validateScheduleTime(gymSchedule.getStartTime(), gymSchedule.getEndTime()))
        {
            return error("排班时间设置不合理，请检查开始时间和结束时间");
        }

        // 检查教练时间冲突（排除自己）
        if (gymScheduleService.checkCoachTimeConflict(gymSchedule.getCoachId(), gymSchedule.getStartTime(), gymSchedule.getEndTime(), gymSchedule.getId()))
        {
            return error("教练在该时间段已有其他排班，请选择其他时间");
        }

        return toAjax(gymScheduleService.updateGymSchedule(gymSchedule));
    }

    /**
     * 删除排班
     */
    @RequiresPermissions("system:gymschedule:remove")
    @Log(title = "排班管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(gymScheduleService.deleteGymScheduleByIds(ids));
    }

    /**
     * 修改排班状态
     */
    @RequiresPermissions("system:gymschedule:edit")
    @Log(title = "排班管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Long id, String status)
    {
        boolean result = gymScheduleService.updateGymScheduleStatus(id, status);
        return result ? success() : error("状态修改失败");
    }

    /**
     * 检查教练时间冲突
     */
    @PostMapping("/checkCoachTimeConflict")
    @ResponseBody
    public AjaxResult checkCoachTimeConflict(Long coachId, String startTimeStr, String endTimeStr, Long excludeId)
    {
        try
        {
            Date startTime = DateUtils.parseDate(startTimeStr, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            
            boolean hasConflict = gymScheduleService.checkCoachTimeConflict(coachId, startTime, endTime, excludeId);
            return success(hasConflict ? "1" : "0");
        }
        catch (Exception e)
        {
            return error("时间格式错误");
        }
    }

    /**
     * 获取今日排班
     */
    @GetMapping("/today")
    @ResponseBody
    public AjaxResult getTodaySchedules()
    {
        List<GymSchedule> schedules = gymScheduleService.selectTodaySchedules();
        return success(schedules);
    }

    /**
     * 获取即将开始的排班
     */
    @GetMapping("/upcoming")
    @ResponseBody
    public AjaxResult getUpcomingSchedules()
    {
        List<GymSchedule> schedules = gymScheduleService.selectUpcomingSchedules();
        return success(schedules);
    }

    /**
     * 根据课程ID获取排班列表
     */
    @GetMapping("/course/{courseId}")
    @ResponseBody
    public AjaxResult getSchedulesByCourse(@PathVariable("courseId") Long courseId)
    {
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCourseId(courseId);
        return success(schedules);
    }

    /**
     * 根据教练ID获取排班列表
     */
    @GetMapping("/coach/{coachId}")
    @ResponseBody
    public AjaxResult getSchedulesByCoach(@PathVariable("coachId") Long coachId)
    {
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCoachId(coachId);
        return success(schedules);
    }

    // ========== REST API 接口 ==========

    /**
     * 根据时间范围获取排班列表
     */
    @PostMapping("/timeRange")
    @ResponseBody
    public AjaxResult getSchedulesByTimeRange(@RequestParam String startTimeStr, @RequestParam String endTimeStr)
    {
        try
        {
            Date startTime = DateUtils.parseDate(startTimeStr, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByTimeRange(startTime, endTime);
            return success(schedules);
        }
        catch (Exception e)
        {
            return error("时间格式错误");
        }
    }

    /**
     * REST API - 新增排班
     */
    @PostMapping("/api")
    @ResponseBody
    public AjaxResult addApi(@RequestBody GymSchedule gymSchedule)
    {
        // 验证时间是否合理
        if (!gymScheduleService.validateScheduleTime(gymSchedule.getStartTime(), gymSchedule.getEndTime()))
        {
            return error("排班时间设置不合理，请检查开始时间和结束时间");
        }

        // 检查教练时间冲突
        if (gymScheduleService.checkCoachTimeConflict(gymSchedule.getCoachId(), gymSchedule.getStartTime(), gymSchedule.getEndTime(), null))
        {
            return error("教练在该时间段已有其他排班，请选择其他时间");
        }

        return toAjax(gymScheduleService.insertGymSchedule(gymSchedule));
    }

    /**
     * REST API - 修改排班
     */
    @PutMapping("/api")
    @ResponseBody
    public AjaxResult editApi(@RequestBody GymSchedule gymSchedule)
    {
        // 验证时间是否合理
        if (!gymScheduleService.validateScheduleTime(gymSchedule.getStartTime(), gymSchedule.getEndTime()))
        {
            return error("排班时间设置不合理，请检查开始时间和结束时间");
        }

        // 检查教练时间冲突（排除自己）
        if (gymScheduleService.checkCoachTimeConflict(gymSchedule.getCoachId(), gymSchedule.getStartTime(), gymSchedule.getEndTime(), gymSchedule.getId()))
        {
            return error("教练在该时间段已有其他排班，请选择其他时间");
        }

        return toAjax(gymScheduleService.updateGymSchedule(gymSchedule));
    }

    /**
     * REST API - 删除排班
     */
    @DeleteMapping("/api/{ids}")
    @ResponseBody
    public AjaxResult removeApi(@PathVariable String ids)
    {
        return toAjax(gymScheduleService.deleteGymScheduleByIds(ids));
    }

    /**
     * REST API - 修改排班状态
     */
    @PutMapping("/api/status")
    @ResponseBody
    public AjaxResult changeStatusApi(@RequestParam Long id, @RequestParam String status)
    {
        boolean result = gymScheduleService.updateGymScheduleStatus(id, status);
        return result ? success() : error("状态修改失败");
    }

    /**
     * REST API - 检查教练时间冲突
     */
    @PostMapping("/api/checkConflict")
    @ResponseBody
    public AjaxResult checkCoachTimeConflictApi(@RequestParam Long coachId, @RequestParam String startTimeStr, @RequestParam String endTimeStr, @RequestParam(required = false) Long excludeId)
    {
        try
        {
            Date startTime = DateUtils.parseDate(startTimeStr, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            
            boolean hasConflict = gymScheduleService.checkCoachTimeConflict(coachId, startTime, endTime, excludeId);
            return success(hasConflict);
        }
        catch (Exception e)
        {
            return error("时间格式错误");
        }
    }

    /**
     * REST API - 验证排班时间
     */
    @PostMapping("/api/validateTime")
    @ResponseBody
    public AjaxResult validateScheduleTimeApi(@RequestParam String startTimeStr, @RequestParam String endTimeStr)
    {
        try
        {
            Date startTime = DateUtils.parseDate(startTimeStr, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtils.parseDate(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            
            boolean isValid = gymScheduleService.validateScheduleTime(startTime, endTime);
            return success(isValid);
        }
        catch (Exception e)
        {
            return error("时间格式错误");
        }
    }
}