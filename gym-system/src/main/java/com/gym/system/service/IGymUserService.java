package com.gym.system.service;

import java.util.List;
import com.gym.system.domain.GymUser;

/**
 * 健身房会员 业务层
 * 
 * @author gym
 */
public interface IGymUserService
{
    /**
     * 根据条件分页查询会员列表
     * 
     * @param gymUser 会员信息
     * @return 会员信息集合信息
     */
    public List<GymUser> selectGymUserList(GymUser gymUser);

    /**
     * 通过用户编号查询会员
     * 
     * @param userId 用户编号
     * @return 会员对象信息
     */
    public GymUser selectGymUserByUserId(String userId);

    /**
     * 通过手机号码查询会员
     * 
     * @param phone 手机号码
     * @return 会员对象信息
     */
    public GymUser selectGymUserByPhone(String phone);

    /**
     * 通过邮箱查询会员
     * 
     * @param email 邮箱
     * @return 会员对象信息
     */
    public GymUser selectGymUserByEmail(String email);

    /**
     * 通过用户名查询会员
     * 
     * @param username 用户名
     * @return 会员对象信息
     */
    public GymUser selectGymUserByUsername(String username);

    /**
     * 通过会员ID查询会员
     * 
     * @param id 会员ID
     * @return 会员对象信息
     */
    public GymUser selectGymUserById(Long id);

    /**
     * 通过会员ID删除会员
     * 
     * @param id 会员ID
     * @return 结果
     */
    public int deleteGymUserById(Long id);

    /**
     * 批量删除会员信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGymUserByIds(String ids);

    /**
     * 保存会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int insertGymUser(GymUser gymUser);

    /**
     * 保存会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int updateGymUser(GymUser gymUser);

    /**
     * 修改会员详细信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int updateGymUserInfo(GymUser gymUser);

    /**
     * 修改会员状态
     * 
     * @param id 会员ID
     * @param status 状态
     * @return 结果
     */
    public boolean updateGymUserStatus(Long id, String status);

    /**
     * 修改会员密码信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int resetGymUserPwd(GymUser gymUser);

    /**
     * 校验用户编号是否唯一
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public boolean checkUserIdUnique(GymUser gymUser);

    /**
     * 校验手机号码是否唯一
     *
     * @param gymUser 会员信息
     * @return 结果
     */
    public boolean checkPhoneUnique(GymUser gymUser);

    /**
     * 校验email是否唯一
     *
     * @param gymUser 会员信息
     * @return 结果
     */
    public boolean checkEmailUnique(GymUser gymUser);

    /**
     * 导入会员数据
     * 
     * @param gymUserList 会员数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importGymUser(List<GymUser> gymUserList, Boolean isUpdateSupport, String operName);
}
