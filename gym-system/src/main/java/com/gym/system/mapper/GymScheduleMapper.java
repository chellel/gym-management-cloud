package com.gym.system.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.gym.system.domain.GymSchedule;

/**
 * 健身房课程排班表 数据层
 * 
 * @author gym
 */
public interface GymScheduleMapper
{
    /**
     * 根据条件分页查询排班列表
     * 
     * @param gymSchedule 排班信息
     * @return 排班信息集合信息
     */
    public List<GymSchedule> selectGymScheduleList(GymSchedule gymSchedule);

    /**
     * 通过排班ID查询排班
     * 
     * @param id 排班ID
     * @return 排班对象信息
     */
    public GymSchedule selectGymScheduleById(Long id);

    /**
     * 通过课程ID查询排班列表
     * 
     * @param courseId 课程ID
     * @return 排班信息集合
     */
    public List<GymSchedule> selectGymScheduleByCourseId(Long courseId);

    /**
     * 通过教练ID查询排班列表
     * 
     * @param coachId 教练ID
     * @return 排班信息集合
     */
    public List<GymSchedule> selectGymScheduleByCoachId(Long coachId);

    /**
     * 查询指定时间范围内的排班
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 排班信息集合
     */
    public List<GymSchedule> selectGymScheduleByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询教练在指定时间是否有冲突的排班
     * 
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的排班ID（用于编辑时排除自己）
     * @return 冲突的排班数量
     */
    public int checkCoachTimeConflict(@Param("coachId") Long coachId, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("excludeId") Long excludeId);

    /**
     * 新增排班信息
     * 
     * @param gymSchedule 排班信息
     * @return 结果
     */
    public int insertGymSchedule(GymSchedule gymSchedule);

    /**
     * 修改排班信息
     * 
     * @param gymSchedule 排班信息
     * @return 结果
     */
    public int updateGymSchedule(GymSchedule gymSchedule);

    /**
     * 修改排班状态
     * 
     * @param id 排班ID
     * @param status 状态
     * @return 结果
     */
    public int updateGymScheduleStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 通过排班ID删除排班
     * 
     * @param id 排班ID
     * @return 结果
     */
    public int deleteGymScheduleById(Long id);

    /**
     * 批量删除排班信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGymScheduleByIds(Long[] ids);

    /**
     * 查询今日排班
     * 
     * @return 排班信息集合
     */
    public List<GymSchedule> selectTodaySchedules();

    /**
     * 查询即将开始的排班（未来1小时内）
     * 
     * @return 排班信息集合
     */
    public List<GymSchedule> selectUpcomingSchedules();
}
