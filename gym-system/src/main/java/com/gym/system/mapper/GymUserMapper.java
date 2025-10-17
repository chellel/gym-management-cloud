package com.gym.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.gym.system.domain.GymUser;

/**
 * 健身房会员表 数据层
 * 
 * @author gym
 */
public interface GymUserMapper
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
    public int deleteGymUserByIds(Long[] ids);

    /**
     * 修改会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int updateGymUser(GymUser gymUser);

    /**
     * 修改会员状态
     * 
     * @param id 会员ID
     * @param status 状态
     * @return 结果
     */
    public int updateGymUserStatus(@Param("id") Long id, @Param("status") String status);

    /**
     * 重置会员密码
     * 
     * @param id 会员ID
     * @param password 密码
     * @return 结果
     */
    public int resetGymUserPwd(@Param("id") Long id, @Param("password") String password);

    /**
     * 新增会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    public int insertGymUser(GymUser gymUser);

    /**
     * 校验用户编号是否唯一
     * 
     * @param userId 用户编号
     * @return 结果
     */
    public GymUser checkUserIdUnique(String userId);

    /**
     * 校验手机号码是否唯一
     *
     * @param phone 手机号码
     * @return 结果
     */
    public GymUser checkPhoneUnique(String phone);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public GymUser checkEmailUnique(String email);
}
