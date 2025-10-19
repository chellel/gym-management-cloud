package com.gym.system.service;

import java.util.List;
import com.gym.system.domain.GymMembership;

/**
 * 健身房会籍 业务层
 * 
 * @author gym
 */
public interface IGymMembershipService
{
    /**
     * 根据条件分页查询会籍列表
     * 
     * @param gymMembership 会籍信息
     * @return 会籍信息集合信息
     */
    public List<GymMembership> selectGymMembershipList(GymMembership gymMembership);

    /**
     * 通过会籍ID查询会籍
     * 
     * @param id 会籍ID
     * @return 会籍对象信息
     */
    public GymMembership selectGymMembershipById(Long id);

    /**
     * 通过用户ID查询会籍列表
     * 
     * @param userId 用户ID
     * @return 会籍信息集合
     */
    public List<GymMembership> selectGymMembershipByUserId(Long userId);

    /**
     * 通过用户ID查询当前有效会籍
     * 
     * @param userId 用户ID
     * @return 会籍对象信息
     */
    public GymMembership selectActiveGymMembershipByUserId(Long userId);

    /**
     * 新增会籍信息
     * 
     * @param gymMembership 会籍信息
     * @return 结果
     */
    public int insertGymMembership(GymMembership gymMembership);

    /**
     * 修改会籍信息
     * 
     * @param gymMembership 会籍信息
     * @return 结果
     */
    public int updateGymMembership(GymMembership gymMembership);

    /**
     * 修改会籍状态
     * 
     * @param id 会籍ID
     * @param status 状态
     * @return 结果
     */
    public boolean updateGymMembershipStatus(Long id, String status);

    /**
     * 通过会籍ID删除会籍
     * 
     * @param id 会籍ID
     * @return 结果
     */
    public int deleteGymMembershipById(Long id);

    /**
     * 批量删除会籍信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGymMembershipByIds(String ids);

    /**
     * 检查用户是否有有效会籍
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public boolean checkUserHasActiveMembership(Long userId);

    /**
     * 查询即将过期的会籍
     * 
     * @param days 天数
     * @return 会籍信息集合
     */
    public List<GymMembership> selectExpiringMemberships(int days);

    /**
     * 查询已过期的会籍
     * 
     * @return 会籍信息集合
     */
    public List<GymMembership> selectExpiredMemberships();

    /**
     * 查询会员列表（联表查询，返回DTO）
     * 
     * @param gymMemberDTO 会员查询条件
     * @return 会员信息集合
     */
    public List<com.gym.system.domain.dto.GymMemberDTO> selectGymMemberList(com.gym.system.domain.dto.GymMemberDTO gymMemberDTO);

    /**
     * 查询会员统计数据
     * 
     * @return 统计数据Map
     */
    public java.util.Map<String, Object> selectMemberStatistics();

}
