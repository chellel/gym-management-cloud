package com.gym.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.common.core.text.Convert;
import com.gym.common.utils.DateUtils;
import com.gym.system.domain.GymCourse;
import com.gym.system.mapper.GymCourseMapper;
import com.gym.system.service.IGymCourseService;

/**
 * 健身房课程 业务层处理
 * 
 * @author gym
 */
@Service
public class GymCourseServiceImpl implements IGymCourseService
{
    @Autowired
    private GymCourseMapper gymCourseMapper;

    /**
     * 根据条件分页查询课程列表
     * 
     * @param gymCourse 课程信息
     * @return 课程信息集合信息
     */
    @Override
    public List<GymCourse> selectGymCourseList(GymCourse gymCourse)
    {
        return gymCourseMapper.selectGymCourseList(gymCourse);
    }

    /**
     * 通过课程ID查询课程
     * 
     * @param id 课程ID
     * @return 课程对象信息
     */
    @Override
    public GymCourse selectGymCourseById(Long id)
    {
        return gymCourseMapper.selectGymCourseById(id);
    }

    /**
     * 通过课程名称查询课程
     * 
     * @param name 课程名称
     * @return 课程对象信息
     */
    @Override
    public GymCourse selectGymCourseByName(String name)
    {
        return gymCourseMapper.selectGymCourseByName(name);
    }

    /**
     * 新增课程信息
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    @Override
    public int insertGymCourse(GymCourse gymCourse)
    {
        gymCourse.setCreateTime(DateUtils.getNowDate());
        gymCourse.setIsDeleted(0);
        return gymCourseMapper.insertGymCourse(gymCourse);
    }

    /**
     * 修改课程信息
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    @Override
    public int updateGymCourse(GymCourse gymCourse)
    {
        gymCourse.setUpdateTime(DateUtils.getNowDate());
        return gymCourseMapper.updateGymCourse(gymCourse);
    }

    /**
     * 通过课程ID删除课程
     * 
     * @param id 课程ID
     * @return 结果
     */
    @Override
    public int deleteGymCourseById(Long id)
    {
        return gymCourseMapper.deleteGymCourseById(id);
    }

    /**
     * 批量删除课程信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGymCourseByIds(String ids)
    {
        return gymCourseMapper.deleteGymCourseByIds(Convert.toLongArray(ids));
    }

    /**
     * 校验课程名称是否唯一
     * 
     * @param gymCourse 课程信息
     * @return 结果
     */
    @Override
    public boolean checkCourseNameUnique(GymCourse gymCourse)
    {
        Long courseId = gymCourse.getId() == null ? -1L : gymCourse.getId();
        GymCourse info = gymCourseMapper.checkCourseNameUnique(gymCourse.getName());
        if (info != null && info.getId().longValue() != courseId.longValue())
        {
            return false;
        }
        return true;
    }

    /**
     * 查询所有有效课程
     * 
     * @return 课程信息集合
     */
    @Override
    public List<GymCourse> selectAllValidCourses()
    {
        return gymCourseMapper.selectAllValidCourses();
    }
}
