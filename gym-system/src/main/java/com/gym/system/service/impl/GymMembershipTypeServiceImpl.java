package com.gym.system.service.impl;

import java.util.List;
import com.gym.common.utils.DateUtils;
import com.gym.common.utils.StringUtils;
import com.gym.common.constant.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.system.mapper.GymMembershipTypeMapper;
import com.gym.system.domain.GymMembershipType;
import com.gym.system.service.IGymMembershipTypeService;

/**
 * 会籍套餐类型Service业务层处理
 * 
 * @author gym
 */
@Service
public class GymMembershipTypeServiceImpl implements IGymMembershipTypeService 
{
    @Autowired
    private GymMembershipTypeMapper gymMembershipTypeMapper;

    /**
     * 查询会籍套餐类型
     * 
     * @param id 会籍套餐类型主键
     * @return 会籍套餐类型
     */
    @Override
    public GymMembershipType selectGymMembershipTypeById(Long id)
    {
        return gymMembershipTypeMapper.selectGymMembershipTypeById(id);
    }

    /**
     * 查询会籍套餐类型列表
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 会籍套餐类型
     */
    @Override
    public List<GymMembershipType> selectGymMembershipTypeList(GymMembershipType gymMembershipType)
    {
        return gymMembershipTypeMapper.selectGymMembershipTypeList(gymMembershipType);
    }

    /**
     * 新增会籍套餐类型
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 结果
     */
    @Override
    public int insertGymMembershipType(GymMembershipType gymMembershipType)
    {
        gymMembershipType.setCreateTime(DateUtils.getNowDate());
        gymMembershipType.setIsDeleted(0);
        return gymMembershipTypeMapper.insertGymMembershipType(gymMembershipType);
    }

    /**
     * 修改会籍套餐类型
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 结果
     */
    @Override
    public int updateGymMembershipType(GymMembershipType gymMembershipType)
    {
        gymMembershipType.setUpdateTime(DateUtils.getNowDate());
        return gymMembershipTypeMapper.updateGymMembershipType(gymMembershipType);
    }

    /**
     * 批量删除会籍套餐类型
     * 
     * @param ids 需要删除的会籍套餐类型主键
     * @return 结果
     */
    @Override
    public int deleteGymMembershipTypeByIds(Long[] ids)
    {
        return gymMembershipTypeMapper.deleteGymMembershipTypeByIds(ids);
    }

    /**
     * 删除会籍套餐类型信息
     * 
     * @param id 会籍套餐类型主键
     * @return 结果
     */
    @Override
    public int deleteGymMembershipTypeById(Long id)
    {
        return gymMembershipTypeMapper.deleteGymMembershipTypeById(id);
    }

    /**
     * 根据类型编码查询会籍套餐类型
     * 
     * @param typeCode 类型编码
     * @return 会籍套餐类型
     */
    @Override
    public GymMembershipType selectGymMembershipTypeByTypeCode(String typeCode)
    {
        return gymMembershipTypeMapper.selectGymMembershipTypeByTypeCode(typeCode);
    }

    /**
     * 查询启用的会籍套餐类型列表
     * 
     * @return 启用的会籍套餐类型集合
     */
    @Override
    public List<GymMembershipType> selectActiveGymMembershipTypeList()
    {
        return gymMembershipTypeMapper.selectActiveGymMembershipTypeList();
    }

    /**
     * 检查类型编码是否唯一
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 结果
     */
    @Override
    public boolean checkTypeCodeUnique(GymMembershipType gymMembershipType)
    {
        Long id = StringUtils.isNull(gymMembershipType.getId()) ? -1L : gymMembershipType.getId();
        GymMembershipType info = gymMembershipTypeMapper.selectGymMembershipTypeByTypeCode(gymMembershipType.getTypeCode());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return false; // 不唯一
        }
        return true; // 唯一
    }
}
