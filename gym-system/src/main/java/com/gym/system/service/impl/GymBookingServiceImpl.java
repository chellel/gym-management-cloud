package com.gym.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.common.core.text.Convert;
import com.gym.common.utils.DateUtils;
import com.gym.system.domain.GymBooking;
import com.gym.system.domain.GymSchedule;
import com.gym.system.mapper.GymBookingMapper;
import com.gym.system.service.IGymBookingService;
import com.gym.system.service.IGymScheduleService;

/**
 * 课程预约Service业务层处理
 * 
 * @author gym
 * @date 2025-10-19
 */
@Service
public class GymBookingServiceImpl implements IGymBookingService
{
    @Autowired
    private GymBookingMapper gymBookingMapper;

    @Autowired
    private IGymScheduleService gymScheduleService;

    /**
     * 查询课程预约
     * 
     * @param id 课程预约主键
     * @return 课程预约
     */
    @Override
    public GymBooking selectGymBookingById(Long id)
    {
        return gymBookingMapper.selectGymBookingById(id);
    }

    /**
     * 查询课程预约列表
     * 
     * @param gymBooking 课程预约
     * @return 课程预约
     */
    @Override
    public List<GymBooking> selectGymBookingList(GymBooking gymBooking)
    {
        return gymBookingMapper.selectGymBookingList(gymBooking);
    }

    /**
     * 根据排班ID查询预约列表
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 预约信息集合
     */
    @Override
    public List<GymBooking> selectGymBookingByScheduleId(Long scheduleId, Long memberId)
    {
        return gymBookingMapper.selectGymBookingByScheduleId(scheduleId, memberId);
    }

    /**
     * 根据会员ID查询预约列表
     * 
     * @param memberId 会员ID
     * @return 预约信息集合
     */
    @Override
    public List<GymBooking> selectGymBookingByMemberId(Long memberId)
    {
        return gymBookingMapper.selectGymBookingByMemberId(memberId);
    }

    /**
     * 根据排班ID和会员ID查询预约
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 预约信息
     */
    @Override
    public GymBooking selectGymBookingByScheduleAndMember(Long scheduleId, Long memberId)
    {
        return gymBookingMapper.selectGymBookingByScheduleAndMember(scheduleId, memberId);
    }

    /**
     * 查询今日预约
     * 
     * @return 预约信息集合
     */
    @Override
    public List<GymBooking> selectTodayBookings()
    {
        return gymBookingMapper.selectTodayBookings();
    }

    /**
     * 查询即将开始的预约（1小时内）
     * 
     * @return 预约信息集合
     */
    @Override
    public List<GymBooking> selectUpcomingBookings()
    {
        return gymBookingMapper.selectUpcomingBookings();
    }

    /**
     * 新增课程预约
     * 
     * @param gymBooking 课程预约
     * @return 结果
     */
    @Override
    public int insertGymBooking(GymBooking gymBooking)
    {
        gymBooking.setCreateTime(DateUtils.getNowDate());
        gymBooking.setBookingTime(DateUtils.getNowDate());
        gymBooking.setIsDeleted(0);
        return gymBookingMapper.insertGymBooking(gymBooking);
    }

    /**
     * 修改课程预约
     * 
     * @param gymBooking 课程预约
     * @return 结果
     */
    @Override
    public int updateGymBooking(GymBooking gymBooking)
    {
        gymBooking.setUpdateTime(DateUtils.getNowDate());
        return gymBookingMapper.updateGymBooking(gymBooking);
    }

    /**
     * 签到
     * 
     * @param id 预约ID
     * @return 结果
     */
    @Override
    public int checkinBooking(Long id)
    {
        return gymBookingMapper.checkinBooking(id);
    }

    /**
     * 批量删除课程预约
     * 
     * @param ids 需要删除的课程预约主键
     * @return 结果
     */
    @Override
    public int deleteGymBookingByIds(String ids)
    {
        return gymBookingMapper.deleteGymBookingByIds(Convert.toLongArray(ids));
    }

    /**
     * 删除课程预约信息
     * 
     * @param id 课程预约主键
     * @return 结果
     */
    @Override
    public int deleteGymBookingById(Long id)
    {
        return gymBookingMapper.deleteGymBookingById(id);
    }

    /**
     * 检查预约是否已存在
     * 
     * @param scheduleId 排班ID
     * @param memberId 会员ID
     * @return 是否存在
     */
    @Override
    public boolean checkBookingExists(Long scheduleId, Long memberId)
    {
        return gymBookingMapper.checkBookingExists(scheduleId, memberId) > 0;
    }

    /**
     * 统计排班的预约数量
     * 
     * @param scheduleId 排班ID
     * @return 预约数量
     */
    @Override
    public int countBookingsByScheduleId(Long scheduleId)
    {
        return gymBookingMapper.countBookingsByScheduleId(scheduleId);
    }

    /**
     * 验证预约是否有效
     * 
     * @param gymBooking 预约信息
     * @return 是否有效
     */
    @Override
    public boolean validateBooking(GymBooking gymBooking)
    {
        // 检查排班是否存在且有效
        GymSchedule schedule = gymScheduleService.selectGymScheduleById(gymBooking.getScheduleId());
        if (schedule == null)
        {
            return false;
        }

        // 检查排班状态是否为waiting
        if (!"waiting".equals(schedule.getStatus()))
        {
            return false;
        }

        // 检查排班是否已开始
        Date now = DateUtils.getNowDate();
        if (schedule.getStartTime().before(now))
        {
            return false;
        }

        // 检查是否已预约
        if (checkBookingExists(gymBooking.getScheduleId(), gymBooking.getMemberId()))
        {
            return false;
        }

        // 检查预约数量是否超过排班最大容量
        int currentBookings = countBookingsByScheduleId(gymBooking.getScheduleId());
        if (currentBookings >= schedule.getMaxCapacity())
        {
            return false;
        }

        return true;
    }
}
