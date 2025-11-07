package com.gym.system.service;

import java.util.List;
import java.util.Map;

/**
 * 数据看板Service接口
 * 
 * @author gym
 */
public interface IGymDashboardService
{
    /**
     * 获取KPI指标数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return KPI数据
     */
    Map<String, Object> getKpiData(String startDate, String endDate);

    /**
     * 获取会员增长趋势数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员增长趋势数据
     */
    List<Map<String, Object>> getMemberGrowthData(String startDate, String endDate);

    /**
     * 获取课程预约Top5数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程预约Top5数据
     */
    List<Map<String, Object>> getClassBookingTop5(String startDate, String endDate);

    /**
     * 获取训练高峰期数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 训练高峰期数据
     */
    Map<String, Object> getPeakHoursData(String startDate, String endDate);

    /**
     * 获取会员详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员详细数据
     */
    List<Map<String, Object>> getMemberDetails(String startDate, String endDate);

    /**
     * 获取课程详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程详细数据
     */
    List<Map<String, Object>> getClassDetails(String startDate, String endDate);



    /**
     * 刷新缓存
     */
    void refreshCache();
}
