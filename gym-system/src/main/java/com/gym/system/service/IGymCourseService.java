package com.gym.system.service;

import java.util.List;
import com.gym.system.domain.GymCourse;

/**
 * 健身房课程 业务层
 * 
 * @author gym
 */
public interface IGymCourseService
{
    /**
     * 根据条件分页查询课程列表
     * 
     * @param gymCourse 课程信息
     * @return 课程信息集合信息
     */
    public List<GymCourse> selectGymCourseList(GymCourse gymCourse);

    /**
     * 通过课程ID查询课程
     * 
     * @param id 课程ID
     * @return 课程对象信息
     */
    public GymCourse selectGymCourseById(Long id);

    /**
     * 通过课程名称查询课程
     * 
     * @param name 课程名称
     * @return 课程对象信息
     */
    public GymCourse selectGymCourseByName(String name);

    /**
     * 新增课程信息
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    public int insertGymCourse(GymCourse gymCourse);

    /**
     * 修改课程信息
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    public int updateGymCourse(GymCourse gymCourse);

    /**
     * 通过课程ID删除课程
     * 
     * @param id 课程ID
     * @return 结果
     */
    public int deleteGymCourseById(Long id);

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGymCourseByIds(String ids);

    /**
     * 校验课程名称是否唯一
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    public boolean checkCourseNameUnique(GymCourse gymCourse);

    /**
     * 查询所有有效课程
     * 
     * @return 课程信息集合
     */
    public List<GymCourse> selectAllValidCourses();
}
