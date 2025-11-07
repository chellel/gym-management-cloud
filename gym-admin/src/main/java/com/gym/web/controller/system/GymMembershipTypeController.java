package com.gym.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gym.common.annotation.Log;
import com.gym.common.core.controller.BaseController;
import com.gym.common.core.domain.AjaxResult;
import com.gym.common.enums.BusinessType;
import com.gym.system.domain.GymMembershipType;
import com.gym.system.service.IGymMembershipTypeService;
import com.gym.common.utils.poi.ExcelUtil;

/**
 * 会籍套餐类型Controller
 * 
 * @author gym
 */
@RestController
@RequestMapping("/system/membershipType")
public class GymMembershipTypeController extends BaseController
{
    @Autowired
    private IGymMembershipTypeService gymMembershipTypeService;

    /**
     * 查询会籍套餐类型列表
     */
    @GetMapping("/list")
    public AjaxResult list(@RequestParam(required = false) String typeCode,
                          @RequestParam(required = false) String typeName,
                          @RequestParam(required = false) String status)
    {
        GymMembershipType gymMembershipType = new GymMembershipType();
        gymMembershipType.setTypeCode(typeCode);
        gymMembershipType.setTypeName(typeName);
        gymMembershipType.setStatus(status);
        
        List<GymMembershipType> list = gymMembershipTypeService.selectGymMembershipTypeList(gymMembershipType);
        return success(list);
    }

    /**
     * 导出会籍套餐类型列表
     */
    @Log(title = "会籍套餐类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GymMembershipType gymMembershipType)
    {
        List<GymMembershipType> list = gymMembershipTypeService.selectGymMembershipTypeList(gymMembershipType);
        ExcelUtil<GymMembershipType> util = new ExcelUtil<GymMembershipType>(GymMembershipType.class);
        util.exportExcel(response, list, "会籍套餐类型数据");
    }

    /**
     * 获取会籍套餐类型详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(gymMembershipTypeService.selectGymMembershipTypeById(id));
    }

    /**
     * 新增会籍套餐类型
     */
    @Log(title = "会籍套餐类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GymMembershipType gymMembershipType)
    {
        if (!gymMembershipTypeService.checkTypeCodeUnique(gymMembershipType))
        {
            return error("新增会籍套餐类型'" + gymMembershipType.getTypeName() + "'失败，类型编码已存在");
        }
        return toAjax(gymMembershipTypeService.insertGymMembershipType(gymMembershipType));
    }

    /**
     * 修改会籍套餐类型
     */
    @Log(title = "会籍套餐类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GymMembershipType gymMembershipType)
    {
        if (!gymMembershipTypeService.checkTypeCodeUnique(gymMembershipType))
        {
            return error("修改会籍套餐类型'" + gymMembershipType.getTypeName() + "'失败，类型编码已存在");
        }
        return toAjax(gymMembershipTypeService.updateGymMembershipType(gymMembershipType));
    }

    /**
     * 删除会籍套餐类型
     */
    @Log(title = "会籍套餐类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gymMembershipTypeService.deleteGymMembershipTypeByIds(ids));
    }

    /**
     * 根据类型编码获取会籍套餐类型详细信息
     */
    @GetMapping(value = "/typeCode/{typeCode}")
    public AjaxResult getInfoByTypeCode(@PathVariable("typeCode") String typeCode)
    {
        return success(gymMembershipTypeService.selectGymMembershipTypeByTypeCode(typeCode));
    }

    /**
     * 获取启用的会籍套餐类型列表
     */
    @GetMapping("/active")
    public AjaxResult getActiveList()
    {
        return success(gymMembershipTypeService.selectActiveGymMembershipTypeList());
    }
}
