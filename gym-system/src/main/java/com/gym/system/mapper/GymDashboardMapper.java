package com.gym.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 数据看板Mapper接口
 * 
 * @author gym
 */
public interface GymDashboardMapper
{
    /**
     * 获取总会员数
     * 
     * @return 总会员数
     */
    Long getTotalMembersCount();

    /**
     * 根据日期范围获取总会员数
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 总会员数
     */
    Long getTotalMembersCountByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取课程签到总数
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程签到总数
     */
    Long getTotalCheckInsCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取活跃会员数
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 活跃会员数
     */
    Long getActiveMembersCount(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取会员增长趋势数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员增长趋势数据
     */
    List<Map<String, Object>> getMemberGrowthData(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取课程预约Top5数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程预约Top5数据
     */
    List<Map<String, Object>> getClassBookingTop5(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 根据类型获取训练高峰期数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param type 类型（weekday/weekend/morning/evening）
     * @return 训练高峰期数据
     */
    List<Map<String, Object>> getPeakHoursDataByType(@Param("startDate") String startDate, 
                                                      @Param("endDate") String endDate, 
                                                      @Param("type") String type);

    /**
     * 获取会员详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 会员详细数据
     */
    List<Map<String, Object>> getMemberDetails(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 获取课程详细数据
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 课程详细数据
     */
    List<Map<String, Object>> getClassDetails(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
