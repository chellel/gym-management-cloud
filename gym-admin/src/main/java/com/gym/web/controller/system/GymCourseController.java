package com.gym.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 
 * @author gym
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
//    @RequiresPermissions("system:gymcourse:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GymCourse gymCourse)
    {
        startPage();
        List<GymCourse> list = gymCourseService.selectGymCourseList(gymCourse);
        return getDataTable(list);
    }

    /**
     * 新增保存课程
     */
    @RequiresPermissions("system:gymcourse:add")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated GymCourse gymCourse)
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
    // @RequiresPermissions("system:gymcourse:view")
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
    @RequiresPermissions("system:gymcourse:edit")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated GymCourse gymCourse)
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
    @RequiresPermissions("system:gymcourse:remove")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(gymCourseService.deleteGymCourseByIds(ids));
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

    // ========== REST API 接口 ==========

    /**
     * 根据课程名称获取详细信息
     */
    @GetMapping("/name/{name}")
    @ResponseBody
    public AjaxResult getInfoByName(@PathVariable("name") String name)
    {
        return success(gymCourseService.selectGymCourseByName(name));
    }

    /**
     * REST API - 新增课程
     */
    @PostMapping("/api")
    @ResponseBody
    public AjaxResult addApi(@RequestBody GymCourse gymCourse)
    {
        if (!gymCourseService.checkCourseNameUnique(gymCourse))
        {
            return error("新增课程'" + gymCourse.getName() + "'失败，课程名称已存在");
        }
        return toAjax(gymCourseService.insertGymCourse(gymCourse));
    }

    /**
     * REST API - 修改课程
     */
    @PutMapping("/api")
    @ResponseBody
    public AjaxResult editApi(@RequestBody GymCourse gymCourse)
    {
        if (!gymCourseService.checkCourseNameUnique(gymCourse))
        {
            return error("修改课程'" + gymCourse.getName() + "'失败，课程名称已存在");
        }
        return toAjax(gymCourseService.updateGymCourse(gymCourse));
    }

    /**
     * REST API - 删除课程
     */
    @DeleteMapping("/api/{ids}")
    @ResponseBody
    public AjaxResult removeApi(@PathVariable String ids)
    {
        return toAjax(gymCourseService.deleteGymCourseByIds(ids));
    }

    /**
     * REST API - 获取所有有效课程
     */
    @GetMapping("/api/valid")
    @ResponseBody
    public AjaxResult getAllValidCoursesApi()
    {
        List<GymCourse> courses = gymCourseService.selectAllValidCourses();
        return success(courses);
    }

    /**
     * REST API - 校验课程名称是否唯一
     */
    @PostMapping("/api/checkNameUnique")
    @ResponseBody
    public AjaxResult checkCourseNameUniqueApi(@RequestBody GymCourse gymCourse)
    {
        boolean isUnique = gymCourseService.checkCourseNameUnique(gymCourse);
        return success(isUnique);
    }
}