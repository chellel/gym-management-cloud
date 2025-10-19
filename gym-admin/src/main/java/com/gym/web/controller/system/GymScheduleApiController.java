package com.gym.web.controller.system;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.common.utils.DateUtils;
import com.gym.system.domain.GymSchedule;
import com.gym.system.service.IGymScheduleService;

/**
 * 健身房课程排班 REST API
 * 
 * @author gym
 */
@RestController
@RequestMapping("/api/gymschedule")
public class GymScheduleApiController extends BaseController
{
    @Autowired
    private IGymScheduleService gymScheduleService;

    /**
     * 获取排班列表
     */
    @PostMapping("/list")
    public TableDataInfo list(GymSchedule gymSchedule)
    {
        startPage();
        List<GymSchedule> list = gymScheduleService.selectGymScheduleList(gymSchedule);
        return getDataTable(list);
    }

    /**
     * 根据排班ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gymScheduleService.selectGymScheduleById(id));
    }

    /**
     * 根据课程ID获取排班列表
     */
    @GetMapping(value = "/course/{courseId}")
    public AjaxResult getSchedulesByCourse(@PathVariable("courseId") Long courseId)
    {
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCourseId(courseId);
        return success(schedules);
    }

    /**
     * 根据教练ID获取排班列表
     */
    @GetMapping(value = "/coach/{coachId}")
    public AjaxResult getSchedulesByCoach(@PathVariable("coachId") Long coachId)
    {
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCoachId(coachId);
        return success(schedules);
    }

    /**
     * 根据时间范围获取排班列表
     */
    @PostMapping("/timeRange")
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
     * 新增排班
     */
    @PostMapping
    public AjaxResult add(@RequestBody GymSchedule gymSchedule)
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
     * 修改排班
     */
    @PutMapping
    public AjaxResult edit(@RequestBody GymSchedule gymSchedule)
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
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        return toAjax(gymScheduleService.deleteGymScheduleByIds(ids));
    }

    /**
     * 修改排班状态
     */
    @PutMapping("/status")
    public AjaxResult changeStatus(@RequestParam Long id, @RequestParam String status)
    {
        boolean result = gymScheduleService.updateGymScheduleStatus(id, status);
        return result ? success() : error("状态修改失败");
    }

    /**
     * 检查教练时间冲突
     */
    @PostMapping("/checkConflict")
    public AjaxResult checkCoachTimeConflict(@RequestParam Long coachId, @RequestParam String startTimeStr, @RequestParam String endTimeStr, @RequestParam(required = false) Long excludeId)
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
     * 获取今日排班
     */
    @GetMapping("/today")
    public AjaxResult getTodaySchedules()
    {
        List<GymSchedule> schedules = gymScheduleService.selectTodaySchedules();
        return success(schedules);
    }

    /**
     * 获取即将开始的排班
     */
    @GetMapping("/upcoming")
    public AjaxResult getUpcomingSchedules()
    {
        List<GymSchedule> schedules = gymScheduleService.selectUpcomingSchedules();
        return success(schedules);
    }

    /**
     * 验证排班时间
     */
    @PostMapping("/validateTime")
    public AjaxResult validateScheduleTime(@RequestParam String startTimeStr, @RequestParam String endTimeStr)
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
