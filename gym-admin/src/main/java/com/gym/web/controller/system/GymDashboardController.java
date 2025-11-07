package com.gym.web.controller.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gym.common.annotation.Log;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.enums.BusinessType;
import com.gym.system.service.IGymDashboardService;

/**
 * 数据看板Controller
 * 
 * @author gym
 */
@RestController
@RequestMapping("/system/dashboard")
public class GymDashboardController extends BaseController
{
    @Autowired
    private IGymDashboardService gymDashboardService;

    /**
     * 获取KPI指标数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return KPI数据
     */
    @GetMapping("/kpis")
    public AjaxResult getKpis(@RequestParam String startDate, 
                              @RequestParam String endDate)
    {
        try
        {
            Map<String, Object> kpis = gymDashboardService.getKpiData(startDate, endDate);
            return success(kpis);
        }
        catch (Exception e)
        {
            logger.error("获取KPI数据失败", e);
            return error("获取KPI数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取会员增长趋势数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员增长趋势数据
     */
    @GetMapping("/member-growth")
    public AjaxResult getMemberGrowthData(@RequestParam String startDate, 
                                         @RequestParam String endDate)
    {
        try
        {
            List<Map<String, Object>> data = gymDashboardService.getMemberGrowthData(startDate, endDate);
            return success(data);
        }
        catch (Exception e)
        {
            logger.error("获取会员增长数据失败", e);
            return error("获取会员增长数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取课程预约Top5数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程预约Top5数据
     */
    @GetMapping("/class-booking-top5")
    public AjaxResult getClassBookingTop5(@RequestParam String startDate, 
                                         @RequestParam String endDate)
    {
        try
        {
            List<Map<String, Object>> data = gymDashboardService.getClassBookingTop5(startDate, endDate);
            return success(data);
        }
        catch (Exception e)
        {
            logger.error("获取课程预约Top5数据失败", e);
            return error("获取课程预约Top5数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取训练高峰期数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 训练高峰期数据
     */
    @GetMapping("/peak-hours")
    public AjaxResult getPeakHoursData(@RequestParam String startDate, 
                                      @RequestParam String endDate)
    {
        try
        {
            Map<String, Object> data = gymDashboardService.getPeakHoursData(startDate, endDate);
            return success(data);
        }
        catch (Exception e)
        {
            logger.error("获取训练高峰期数据失败", e);
            return error("获取训练高峰期数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取会员详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员详细数据
     */
    @GetMapping("/member-details")
    public AjaxResult getMemberDetails(@RequestParam String startDate, 
                                     @RequestParam String endDate)
    {
        try
        {
            List<Map<String, Object>> data = gymDashboardService.getMemberDetails(startDate, endDate);
            return success(data);
        }
        catch (Exception e)
        {
            logger.error("获取会员详细数据失败", e);
            return error("获取会员详细数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取课程详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程详细数据
     */
    @GetMapping("/class-details")
    public AjaxResult getClassDetails(@RequestParam String startDate, 
                                    @RequestParam String endDate)
    {
        try
        {
            List<Map<String, Object>> data = gymDashboardService.getClassDetails(startDate, endDate);
            return success(data);
        }
        catch (Exception e)
        {
            logger.error("获取课程详细数据失败", e);
            return error("获取课程详细数据失败：" + e.getMessage());
        }
    }



    /**
     * 刷新数据看板缓存
     */
    @Log(title = "数据看板", businessType = BusinessType.OTHER)
    @PostMapping("/refresh-cache")
    public AjaxResult refreshCache()
    {
        try
        {
            gymDashboardService.refreshCache();
            return success("缓存刷新成功");
        }
        catch (Exception e)
        {
            logger.error("刷新缓存失败", e);
            return error("刷新缓存失败：" + e.getMessage());
        }
    }
}
