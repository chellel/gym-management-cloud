package com.gym.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.common.core.text.Convert;
import com.gym.common.utils.DateUtils;
import com.gym.system.domain.GymSchedule;
import com.gym.system.mapper.GymScheduleMapper;
import com.gym.system.service.IGymScheduleService;

/**
 * 健身房课程排班 业务层处理
 * 
 * @author gym
 */
@Service
public class GymScheduleServiceImpl implements IGymScheduleService
{
    @Autowired
    private GymScheduleMapper gymScheduleMapper;

    /**
     * 根据条件分页查询排班列表
     * 
     * @param gymSchedule 排班信息
     * @return 排班信息集合信息
     */
    @Override
    public List<GymSchedule> selectGymScheduleList(GymSchedule gymSchedule)
    {
        return gymScheduleMapper.selectGymScheduleList(gymSchedule);
    }

    /**
     * 通过排班ID查询排班
     * 
     * @param id 排班ID
     * @return 排班对象信息
     */
    @Override
    public GymSchedule selectGymScheduleById(Long id)
    {
        return gymScheduleMapper.selectGymScheduleById(id);
    }

    /**
     * 通过课程ID查询排班列表
     * 
     * @param courseId 课程ID
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectGymScheduleByCourseId(Long courseId)
    {
        return gymScheduleMapper.selectGymScheduleByCourseId(courseId);
    }

    /**
     * 通过教练ID查询排班列表
     * 
     * @param coachId 教练ID
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectGymScheduleByCoachId(Long coachId)
    {
        return gymScheduleMapper.selectGymScheduleByCoachId(coachId);
    }

    /**
     * 查询指定时间范围内的排班
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectGymScheduleByTimeRange(Date startTime, Date endTime)
    {
        return gymScheduleMapper.selectGymScheduleByTimeRange(startTime, endTime);
    }

    /**
     * 检查教练时间冲突
     * 
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的排班ID（用于编辑时排除自己）
     * @return 是否有冲突
     */
    @Override
    public boolean checkCoachTimeConflict(Long coachId, Date startTime, Date endTime, Long excludeId)
    {
        int conflictCount = gymScheduleMapper.checkCoachTimeConflict(coachId, startTime, endTime, excludeId);
        return conflictCount > 0;
    }

    /**
     * 新增排班信息
     * 
     * @param gymSchedule 排班信息
     * @return 结果
     */
    @Override
    public int insertGymSchedule(GymSchedule gymSchedule)
    {
        gymSchedule.setCreateTime(DateUtils.getNowDate());
        gymSchedule.setIsDeleted(0);
        if (gymSchedule.getStatus() == null || gymSchedule.getStatus().isEmpty())
        {
            gymSchedule.setStatus("waiting");
        }
        return gymScheduleMapper.insertGymSchedule(gymSchedule);
    }

    /**
     * 修改排班信息
     * 
     * @param gymSchedule 排班信息
     * @return 结果
     */
    @Override
    public int updateGymSchedule(GymSchedule gymSchedule)
    {
        gymSchedule.setUpdateTime(DateUtils.getNowDate());
        return gymScheduleMapper.updateGymSchedule(gymSchedule);
    }

    /**
     * 修改排班状态
     * 
     * @param id 排班ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public boolean updateGymScheduleStatus(Long id, String status)
    {
        int result = gymScheduleMapper.updateGymScheduleStatus(id, status);
        return result > 0;
    }

    /**
     * 通过排班ID删除排班
     * 
     * @param id 排班ID
     * @return 结果
     */
    @Override
    public int deleteGymScheduleById(Long id)
    {
        return gymScheduleMapper.deleteGymScheduleById(id);
    }

    /**
     * 批量删除排班信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGymScheduleByIds(String ids)
    {
        return gymScheduleMapper.deleteGymScheduleByIds(Convert.toLongArray(ids));
    }

    /**
     * 查询今日排班
     * 
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectTodaySchedules()
    {
        return gymScheduleMapper.selectTodaySchedules();
    }

    /**
     * 查询即将开始的排班（未来1小时内）
     * 
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectUpcomingSchedules()
    {
        return gymScheduleMapper.selectUpcomingSchedules();
    }

    /**
     * 验证排班时间是否合理
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否合理
     */
    @Override
    public boolean validateScheduleTime(Date startTime, Date endTime)
    {
        if (startTime == null || endTime == null)
        {
            return false;
        }
        
        // 结束时间必须晚于开始时间
        if (endTime.before(startTime) || endTime.equals(startTime))
        {
            return false;
        }
        
        // 排班时长不能超过8小时
        long duration = endTime.getTime() - startTime.getTime();
        long maxDuration = 8 * 60 * 60 * 1000; // 8小时
        if (duration > maxDuration)
        {
            return false;
        }
        
        return true;
    }

    /**
     * 根据条件分页查询排班列表（包含预约人数）
     * 
     * @param gymSchedule 排班信息
     * @return 排班信息集合信息
     */
    @Override
    public List<GymSchedule> selectGymScheduleListWithBooking(GymSchedule gymSchedule)
    {
        return gymScheduleMapper.selectGymScheduleListWithBooking(gymSchedule);
    }

    /**
     * 通过排班ID查询排班（包含预约人数）
     * 
     * @param id 排班ID
     * @return 排班对象信息
     */
    @Override
    public GymSchedule selectGymScheduleByIdWithBooking(Long id)
    {
        return gymScheduleMapper.selectGymScheduleByIdWithBooking(id);
    }

    /**
     * 通过课程ID查询排班列表（包含预约人数）
     * 
     * @param courseId 课程ID
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectGymScheduleByCourseIdWithBooking(Long courseId)
    {
        return gymScheduleMapper.selectGymScheduleByCourseIdWithBooking(courseId);
    }

    /**
     * 通过教练ID查询排班列表（包含预约人数）
     * 
     * @param coachId 教练ID
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectGymScheduleByCoachIdWithBooking(Long coachId)
    {
        return gymScheduleMapper.selectGymScheduleByCoachIdWithBooking(coachId);
    }

    /**
     * 查询今日排班（包含预约人数）
     * 
     * @return 排班信息集合
     */
    @Override
    public List<GymSchedule> selectTodaySchedulesWithBooking()
    {
        return gymScheduleMapper.selectTodaySchedulesWithBooking();
    }
}
