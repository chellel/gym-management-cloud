package com.gym.web.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody Map<String, Object> request)
    {
        // 创建查询对象
        GymSchedule gymSchedule = new GymSchedule();
        
        // 提取查询条件
        if (request.get("courseId") != null) {
            gymSchedule.setCourseId(Long.valueOf(request.get("courseId").toString()));
        }
        if (request.get("coachId") != null) {
            gymSchedule.setCoachId(Long.valueOf(request.get("coachId").toString()));
        }
        if (request.get("location") != null) {
            gymSchedule.setLocation(request.get("location").toString());
        }
        if (request.get("status") != null) {
            gymSchedule.setStatus(request.get("status").toString());
        }
        if (request.get("courseName") != null) {
            gymSchedule.setCourseName(request.get("courseName").toString());
        }
        if (request.get("coachName") != null) {
            gymSchedule.setCoachName(request.get("coachName").toString());
        }
        
        // 处理日期范围查询：startDate 和 endDate 转换为 startTime 和 endTime
        if (request.get("startDate") != null) {
            try {
                String startDateStr = request.get("startDate").toString();
                // 如果只有日期，添加时间部分
                if (startDateStr.length() == 10) {
                    startDateStr += " 00:00:00";
                }
                Date startTime = DateUtils.parseDate(startDateStr, "yyyy-MM-dd HH:mm:ss");
                gymSchedule.setStartTime(startTime);
            } catch (Exception e) {
                logger.warn("解析开始日期失败: {}", request.get("startDate"), e);
            }
        }
        if (request.get("endDate") != null) {
            try {
                String endDateStr = request.get("endDate").toString();
                // 如果只有日期，添加时间部分
                if (endDateStr.length() == 10) {
                    endDateStr += " 23:59:59";
                }
                Date endTime = DateUtils.parseDate(endDateStr, "yyyy-MM-dd HH:mm:ss");
                gymSchedule.setEndTime(endTime);
            } catch (Exception e) {
                logger.warn("解析结束日期失败: {}", request.get("endDate"), e);
            }
        }
        
        // 提取分页参数
        Integer page = 1;
        Integer pageSize = 100;
        if (request.get("page") != null) {
            page = Integer.valueOf(request.get("page").toString());
        }
        if (request.get("pageSize") != null) {
            pageSize = Integer.valueOf(request.get("pageSize").toString());
        }
        
        startPage(page, pageSize);
        List<GymSchedule> list = gymScheduleService.selectGymScheduleListWithBooking(gymSchedule);
        return getDataTable(list);
    }

    /**
     * 新增保存排班
     */
    @Log(title = "排班管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody @Validated GymSchedule gymSchedule)
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
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getScheduleDetail(@PathVariable("id") Long id)
    {
        GymSchedule gymSchedule = gymScheduleService.selectGymScheduleByIdWithBooking(id);
        return success(gymSchedule);
    }

    /**
     * 修改保存排班
     */
    @Log(title = "排班管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody @Validated GymSchedule gymSchedule)
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
    @Log(title = "排班管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map<String, Long> request)
    {
        return toAjax(gymScheduleService.deleteGymScheduleById(request.get("id")));
    }

    /**
     * 修改排班状态
     */
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
        List<GymSchedule> schedules = gymScheduleService.selectTodaySchedulesWithBooking();
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
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCourseIdWithBooking(courseId);
        return success(schedules);
    }

    /**
     * 根据教练ID获取排班列表
     */
    @GetMapping("/coach/{coachId}")
    @ResponseBody
    public AjaxResult getSchedulesByCoach(@PathVariable("coachId") Long coachId)
    {
        List<GymSchedule> schedules = gymScheduleService.selectGymScheduleByCoachIdWithBooking(coachId);
        return success(schedules);
    }

}