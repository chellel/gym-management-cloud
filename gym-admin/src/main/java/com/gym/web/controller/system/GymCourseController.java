package com.gym.web.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.gym.common.annotation.Log;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.core.page.TableDataInfo;
import com.gym.common.enums.BusinessType;
import com.gym.system.domain.GymCourse;
import com.gym.system.service.IGymCourseService;

/**
 * 课程管理
 */
@Controller
@RequestMapping("/system/gymcourse")
public class GymCourseController extends BaseController
{
    @Autowired
    private IGymCourseService gymCourseService;

    /**
     * 查询课程列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody GymCourse gymCourse, 
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, 
                             @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize)
    {
        startPage(page, pageSize);
        List<GymCourse> list = gymCourseService.selectGymCourseList(gymCourse);
        return getDataTable(list);
    }

    /**
     * 新增保存课程
     */
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated @RequestBody GymCourse gymCourse)
    {
        if (!gymCourseService.checkCourseNameUnique(gymCourse))
        {
            return error("新增课程'" + gymCourse.getName() + "'失败，课程名称已存在");
        }
        return toAjax(gymCourseService.insertGymCourse(gymCourse));
    }

    /**
     * 获取课程详情
     */
    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResult getCourseDetail(@PathVariable("id") Long id)
    {
        GymCourse gymCourse = gymCourseService.selectGymCourseById(id);
        return success(gymCourse);
    }

    /**
     * 修改保存课程
     */
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated @RequestBody GymCourse gymCourse)
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
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(@RequestBody Map<String, Long> request)
    {
        return toAjax(gymCourseService.deleteGymCourseById(request.get("id")));
    }
    /**
     * 校验课程名称
     */
    @PostMapping("/checkCourseNameUnique")
    @ResponseBody
    public String checkCourseNameUnique(GymCourse gymCourse)
    {
        return gymCourseService.checkCourseNameUnique(gymCourse) ? "0" : "1";
    }

    /**
     * 获取所有有效课程（用于下拉选择）
     */
    @GetMapping("/getAllValidCourses")
    @ResponseBody
    public AjaxResult getAllValidCourses()
    {
        List<GymCourse> courses = gymCourseService.selectAllValidCourses();
        return success(courses);
    }

}