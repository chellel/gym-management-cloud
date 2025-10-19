package com.gym.system.service;

import java.util.Date;
import java.util.List;
import com.gym.system.domain.GymSchedule;

/**
 * 健身房课程排班 业务层
 * 
 * @author gym
 */
public interface IGymScheduleService
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
    public List<GymSchedule> selectGymScheduleByTimeRange(Date startTime, Date endTime);

    /**
     * 检查教练时间冲突
     * 
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param excludeId 排除的排班ID（用于编辑时排除自己）
     * @return 是否有冲突
     */
    public boolean checkCoachTimeConflict(Long coachId, Date startTime, Date endTime, Long excludeId);

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
    public boolean updateGymScheduleStatus(Long id, String status);

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
    public int deleteGymScheduleByIds(String ids);

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

    /**
     * 验证排班时间是否合理
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否合理
     */
    public boolean validateScheduleTime(Date startTime, Date endTime);
}
