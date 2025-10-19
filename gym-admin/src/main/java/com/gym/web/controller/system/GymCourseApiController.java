package com.gym.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.system.domain.GymCourse;
import com.gym.system.service.IGymCourseService;

/**
 * 健身房课程 REST API
 * 
 * @author gym
 */
@RestController
@RequestMapping("/api/gymcourse")
public class GymCourseApiController extends BaseController
{
    @Autowired
    private IGymCourseService gymCourseService;

    /**
     * 获取课程列表
     */
    @PostMapping("/list")
    public TableDataInfo list(GymCourse gymCourse)
    {
        startPage();
        List<GymCourse> list = gymCourseService.selectGymCourseList(gymCourse);
        return getDataTable(list);
    }

    /**
     * 根据课程ID获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gymCourseService.selectGymCourseById(id));
    }

    /**
     * 根据课程名称获取详细信息
     */
    @GetMapping(value = "/name/{name}")
    public AjaxResult getInfoByName(@PathVariable("name") String name)
    {
        return success(gymCourseService.selectGymCourseByName(name));
    }

    /**
     * 新增课程
     */
    @PostMapping
    public AjaxResult add(@RequestBody GymCourse gymCourse)
    {
        if (!gymCourseService.checkCourseNameUnique(gymCourse))
        {
            return error("新增课程'" + gymCourse.getName() + "'失败，课程名称已存在");
        }
        return toAjax(gymCourseService.insertGymCourse(gymCourse));
    }

    /**
     * 修改课程
     */
    @PutMapping
    public AjaxResult edit(@RequestBody GymCourse gymCourse)
    {
        if (!gymCourseService.checkCourseNameUnique(gymCourse))
        {
            return error("修改课程'" + gymCourse.getName() + "'失败，课程名称已存在");
        }
        return toAjax(gymCourseService.updateGymCourse(gymCourse));
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        return toAjax(gymCourseService.deleteGymCourseByIds(ids));
    }

    /**
     * 获取所有有效课程
     */
    @GetMapping("/valid")
    public AjaxResult getAllValidCourses()
    {
        List<GymCourse> courses = gymCourseService.selectAllValidCourses();
        return success(courses);
    }

    /**
     * 校验课程名称是否唯一
     */
    @PostMapping("/checkNameUnique")
    public AjaxResult checkCourseNameUnique(@RequestBody GymCourse gymCourse)
    {
        boolean isUnique = gymCourseService.checkCourseNameUnique(gymCourse);
        return success(isUnique);
    }
}
