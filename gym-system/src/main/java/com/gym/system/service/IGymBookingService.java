package com.gym.system.service;

import java.util.List;
import com.gym.system.domain.GymBooking;

/**
 * 课程预约Service接口
 * 
 * @author gym
 * @date 2025-10-19
 */
public interface IGymBookingService 
{
    /**
     * 查询课程预约
     * 
     * @param id 课程预约主键
     * @return 课程预约
     */
    public GymBooking selectGymBookingById(Long id);

    /**
     * 查询课程预约列表
     * 
     * @param gymBooking 课程预约
     * @return 课程预约集合
     */
    public List<GymBooking> selectGymBookingList(GymBooking gymBooking);

    /**
     * 根据排班ID查询预约列表
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 预约信息集合
     */
    public List<GymBooking> selectGymBookingByScheduleId(Long scheduleId, Long memberId);

    /**
     * 根据会员ID查询预约列表
     * 
     * @param memberId 会员ID
     * @return 预约信息集合
     */
    public List<GymBooking> selectGymBookingByMemberId(Long memberId);

    /**
     * 根据排班ID和会员ID查询预约
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 预约信息
     */
    public GymBooking selectGymBookingByScheduleAndMember(Long scheduleId, Long memberId);

    /**
     * 查询今日预约
     * 
     * @return 预约信息集合
     */
    public List<GymBooking> selectTodayBookings();

    /**
     * 查询即将开始的预约（1小时内）
     * 
     * @return 预约信息集合
     */
    public List<GymBooking> selectUpcomingBookings();

    /**
     * 新增课程预约
     * 
     * @param gymBooking 课程预约
     * @return 结果
     */
    public int insertGymBooking(GymBooking gymBooking);

    /**
     * 修改课程预约
     * 
     * @param gymBooking 课程预约
     * @return 结果
     */
    public int updateGymBooking(GymBooking gymBooking);

    /**
     * 签到
     * 
     * @param id 预约ID
     * @return 结果
     */
    public int checkinBooking(Long id);

    /**
     * 批量删除课程预约
     * 
     * @param ids 需要删除的课程预约主键集合
     * @return 结果
     */
    public int deleteGymBookingByIds(String ids);

    /**
     * 删除课程预约信息
     * 
     * @param id 课程预约主键
     * @return 结果
     */
    public int deleteGymBookingById(Long id);

    /**
     * 检查预约是否已存在
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 是否存在
     */
    public boolean checkBookingExists(Long scheduleId, Long memberId);

    /**
     * 统计排班的预约数量
     * 
     * @param scheduleId 排班ID
     * @return 预约数量
     */
    public int countBookingsByScheduleId(Long scheduleId);

    /**
     * 验证预约是否有效
     * 
     * @param gymBooking 预约信息
     * @return 是否有效
     */
    public boolean validateBooking(GymBooking gymBooking);
}
