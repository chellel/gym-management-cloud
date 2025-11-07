package com.gym.system.mapper;

import java.util.List;
import com.gym.system.domain.GymMembershipType;

/**
 * 会籍套餐类型Mapper接口
 * 
 * @author gym
 */
public interface GymMembershipTypeMapper 
{
    /**
     * 查询会籍套餐类型
     * 
     * @param id 会籍套餐类型主键
     * @return 会籍套餐类型
     */
    public GymMembershipType selectGymMembershipTypeById(Long id);

    /**
     * 查询会籍套餐类型列表
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 会籍套餐类型集合
     */
    public List<GymMembershipType> selectGymMembershipTypeList(GymMembershipType gymMembershipType);

    /**
     * 新增会籍套餐类型
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 结果
     */
    public int insertGymMembershipType(GymMembershipType gymMembershipType);

    /**
     * 修改会籍套餐类型
     * 
     * @param gymMembershipType 会籍套餐类型
     * @return 结果
     */
    public int updateGymMembershipType(GymMembershipType gymMembershipType);

    /**
     * 删除会籍套餐类型
     * 
     * @param id 会籍套餐类型主键
     * @return 结果
     */
    public int deleteGymMembershipTypeById(Long id);

    /**
     * 批量删除会籍套餐类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGymMembershipTypeByIds(Long[] ids);

    /**
     * 根据类型编码查询会籍套餐类型
     * 
     * @param typeCode 类型编码
     * @return 会籍套餐类型
     */
    public GymMembershipType selectGymMembershipTypeByTypeCode(String typeCode);

    /**
     * 查询启用的会籍套餐类型列表
     * 
     * @return 启用的会籍套餐类型集合
     */
    public List<GymMembershipType> selectActiveGymMembershipTypeList();

    /**
     * 检查类型编码是否存在
     * 
     * @param typeCode 类型编码
     * @param id 排除的ID（用于更新时检查）
     * @return 结果
     */
    public int checkTypeCodeUnique(String typeCode, Long id);
}

