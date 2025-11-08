package com.gym.web.controller.system;

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
import com.gym.system.domain.GymBooking;
import com.gym.system.service.IGymBookingService;

/**
 * 课程预约Controller
 * 
 * @author gym
 * @date 2025-10-19
 */
@Controller
@RequestMapping("/system/gymbooking")
public class GymBookingController extends BaseController
{
    @Autowired
    private IGymBookingService gymBookingService;

    /**
     * 查询课程预约列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody(required = false) GymBooking gymBooking,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, 
                             @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize,
                             @RequestParam(value = "memberId", required = false) Long memberId)
    {
        if (gymBooking == null) {
            gymBooking = new GymBooking();
        }
        // 如果请求参数中有 memberId，优先使用请求参数
        if (memberId != null) {
            gymBooking.setMemberId(memberId);
        }
        startPage(page, pageSize);
        List<GymBooking> list = gymBookingService.selectGymBookingList(gymBooking);
        return getDataTable(list);
    }

    /**
     * 新增保存课程预约
     */
    @Log(title = "课程预约", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestBody @Validated GymBooking gymBooking)
    {
        //  gymBooking.setMemberId(getUserId());

        // 验证预约是否有效
        if (!gymBookingService.validateBooking(gymBooking))
        {
            return error("预约无效，请检查排班状态、时间或容量限制");
        }
        // if(gymBookingService.checkBookingExists(gymBooking.getScheduleId(), gymBooking.getMemberId()))
        // {
        //     gymBooking.setIsDeleted(0);
        //     return editSave(gymBooking);
        // } 
        // gymBooking.setCreateBy(getLoginName());
        // gymBooking.setCreateTime(DateUtils.getNowDate());
        return toAjax(gymBookingService.insertGymBooking(gymBooking));
    }

    /**
     * 获取预约详情
     */
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getBookingDetail(@PathVariable("id") Long id)
    {
        GymBooking gymBooking = gymBookingService.selectGymBookingById(id);
        return success(gymBooking);
    }


    /**
     * 修改保存课程预约
     */
    @Log(title = "课程预约", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@RequestBody @Validated GymBooking gymBooking)
    {
        return toAjax(gymBookingService.updateGymBooking(gymBooking));
    }

    /**
     * 删除课程预约
     */
    @Log(title = "课程预约", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map<String, Long> request)
    {
        return toAjax(gymBookingService.deleteGymBookingById(request.get("id")));
    }

    /**
     * 签到
     */
    @Log(title = "课程预约", businessType = BusinessType.UPDATE)
    @PostMapping("/checkin")
    @ResponseBody
    public AjaxResult checkin(@RequestBody Map<String, Long> request)
    {
        return toAjax(gymBookingService.checkinBooking(request.get("id")));
    }


    /**
     * 根据排班ID查询预约列表（使用请求体）
     */
    @PostMapping("/schedule")
    @ResponseBody
    public AjaxResult getBookingsBySchedule(@RequestBody Map<String, Long> request)
    {
        Long scheduleId = request.get("scheduleId");
        Long memberId = request.get("memberId");
        List<GymBooking> bookings = gymBookingService.selectGymBookingByScheduleId(scheduleId, memberId);
        return success(bookings);
    } 

    /**
     * 查询今日预约
     */
    @PostMapping("/today")
    @ResponseBody
    public AjaxResult getTodayBookings()
    {
        List<GymBooking> bookings = gymBookingService.selectTodayBookings();
        return success(bookings);
    }

    /**
     * 查询即将开始的预约
     */
    @PostMapping("/upcoming")
    @ResponseBody
    public AjaxResult getUpcomingBookings()
    {
        List<GymBooking> bookings = gymBookingService.selectUpcomingBookings();
        return success(bookings);
    }

    /**
     * 检查预约是否已存在
     */
    @PostMapping("/checkExists")
    @ResponseBody
    public AjaxResult checkBookingExists(@RequestParam Long scheduleId, @RequestParam Long memberId)
    {
        boolean exists = gymBookingService.checkBookingExists(scheduleId, memberId);
        return success(exists);
    }

    /**
     * 统计排班的预约数量
     */
    @PostMapping("/count/{scheduleId}")
    @ResponseBody
    public AjaxResult countBookingsBySchedule(@PathVariable Long scheduleId)
    {
        int count = gymBookingService.countBookingsByScheduleId(scheduleId);
        return success(count);
    }

    /**
     * 验证预约是否有效
     */
    @PostMapping("/validate")
    @ResponseBody
    public AjaxResult validateBooking(@RequestBody GymBooking gymBooking)
    {
        boolean isValid = gymBookingService.validateBooking(gymBooking);
        return success(isValid);
    }
}
