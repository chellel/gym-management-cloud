package com.gym.system.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gym.system.mapper.GymDashboardMapper;
import com.gym.system.service.IGymDashboardService;

/**
 * 数据看板Service业务层处理
 * 
 * @author gym
 */
@Service
public class GymDashboardServiceImpl implements IGymDashboardService
{
    @Autowired
    private GymDashboardMapper gymDashboardMapper;

    /**
     * 获取KPI指标数据
     */
    @Override
    @Cacheable(value = "dashboard:kpis", key = "#startDate + '_' + #endDate")
    public Map<String, Object> getKpiData(String startDate, String endDate)
    {
        Map<String, Object> result = new HashMap<>();
        
        // 获取总会员数
        Long totalMembers = gymDashboardMapper.getTotalMembersCount();
        Long previousTotalMembers = gymDashboardMapper.getTotalMembersCountByDate(getPreviousPeriodStartDate(startDate, endDate), getPreviousPeriodEndDate(startDate, endDate));
        Double memberGrowth = calculateGrowthRate(totalMembers, previousTotalMembers);
        
        // 获取课程签到数
        Long totalCheckIns = gymDashboardMapper.getTotalCheckInsCount(startDate, endDate);
        Long previousCheckIns = gymDashboardMapper.getTotalCheckInsCount(getPreviousPeriodStartDate(startDate, endDate), getPreviousPeriodEndDate(startDate, endDate));
        Double checkInGrowth = calculateGrowthRate(totalCheckIns, previousCheckIns);
        
        // 获取活跃率
        Long activeMembers = gymDashboardMapper.getActiveMembersCount(startDate, endDate);
        Double activityRate = totalMembers > 0 ? (activeMembers.doubleValue() / totalMembers.doubleValue()) * 100 : 0.0;
        Long previousActiveMembers = gymDashboardMapper.getActiveMembersCount(getPreviousPeriodStartDate(startDate, endDate), getPreviousPeriodEndDate(startDate, endDate));
        Double previousActivityRate = previousTotalMembers > 0 ? (previousActiveMembers.doubleValue() / previousTotalMembers.doubleValue()) * 100 : 0.0;
        Double activityGrowth = activityRate - previousActivityRate;
        
        result.put("totalMembers", totalMembers);
        result.put("memberGrowth", roundToTwoDecimals(memberGrowth));
        result.put("totalCheckIns", totalCheckIns);
        result.put("checkInGrowth", roundToTwoDecimals(checkInGrowth));
        result.put("activityRate", roundToTwoDecimals(activityRate));
        result.put("activityGrowth", roundToTwoDecimals(activityGrowth));
        
        return result;
    }

    /**
     * 获取会员增长趋势数据
     */
    @Override
    @Cacheable(value = "dashboard:memberGrowth", key = "#startDate + '_' + #endDate")
    public List<Map<String, Object>> getMemberGrowthData(String startDate, String endDate)
    {
        return gymDashboardMapper.getMemberGrowthData(startDate, endDate);
    }

    /**
     * 获取课程预约Top5数据
     */
    @Override
    @Cacheable(value = "dashboard:classBookingTop5", key = "#startDate + '_' + #endDate")
    public List<Map<String, Object>> getClassBookingTop5(String startDate, String endDate)
    {
        return gymDashboardMapper.getClassBookingTop5(startDate, endDate);
    }

    /**
     * 获取训练高峰期数据
     */
    @Override
    @Cacheable(value = "dashboard:peakHours", key = "#startDate + '_' + #endDate")
    public Map<String, Object> getPeakHoursData(String startDate, String endDate)
    {
        Map<String, Object> result = new HashMap<>();
        
        // 工作日数据
        List<Map<String, Object>> weekdayData = gymDashboardMapper.getPeakHoursDataByType(startDate, endDate, "weekday");
        result.put("weekday", weekdayData);
        
        // 周末数据
        List<Map<String, Object>> weekendData = gymDashboardMapper.getPeakHoursDataByType(startDate, endDate, "weekend");
        result.put("weekend", weekendData);
        
        // 早晨数据
        List<Map<String, Object>> morningData = gymDashboardMapper.getPeakHoursDataByType(startDate, endDate, "morning");
        result.put("morning", morningData);
        
        // 晚上数据
        List<Map<String, Object>> eveningData = gymDashboardMapper.getPeakHoursDataByType(startDate, endDate, "evening");
        result.put("evening", eveningData);
        
        return result;
    }

    /**
     * 获取会员详细数据
     */
    @Override
    @Cacheable(value = "dashboard:memberDetails", key = "#startDate + '_' + #endDate")
    public List<Map<String, Object>> getMemberDetails(String startDate, String endDate)
    {
        return gymDashboardMapper.getMemberDetails(startDate, endDate);
    }

    /**
     * 获取课程详细数据
     */
    @Override
    @Cacheable(value = "dashboard:classDetails", key = "#startDate + '_' + #endDate")
    public List<Map<String, Object>> getClassDetails(String startDate, String endDate)
    {
        return gymDashboardMapper.getClassDetails(startDate, endDate);
    }



    /**
     * 刷新缓存
     */
    @Override
    public void refreshCache()
    {
        // 这里可以实现缓存刷新逻辑
        // 由于使用了@Cacheable注解，可以通过CacheManager来清除缓存
    }

    /**
     * 计算增长率
     */
    private Double calculateGrowthRate(Long current, Long previous)
    {
        if (previous == null || previous == 0)
        {
            return current != null && current > 0 ? 100.0 : 0.0;
        }
        if (current == null)
        {
            return -100.0;
        }
        return ((current.doubleValue() - previous.doubleValue()) / previous.doubleValue()) * 100;
    }

    /**
     * 保留两位小数
     */
    private Double roundToTwoDecimals(Double value)
    {
        if (value == null)
        {
            return 0.0;
        }
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 获取上一周期的开始日期
     */
    private String getPreviousPeriodStartDate(String startDate, String endDate)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            
            long diffInMillies = end.getTime() - start.getTime();
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.DAY_OF_MONTH, -(int)diffInDays - 1);
            
            return sdf.format(cal.getTime());
        }
        catch (Exception e)
        {
            return startDate;
        }
    }

    /**
     * 获取上一周期的结束日期
     */
    private String getPreviousPeriodEndDate(String startDate, String endDate)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            
            long diffInMillies = end.getTime() - start.getTime();
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            
            return sdf.format(cal.getTime());
        }
        catch (Exception e)
        {
            return endDate;
        }
    }
}
