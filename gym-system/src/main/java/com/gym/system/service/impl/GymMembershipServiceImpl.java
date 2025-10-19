package com.gym.system.service.impl;

import java.util.List;
import java.util.Date;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gym.common.core.text.Convert;
import com.gym.common.exception.ServiceException;
import com.gym.common.utils.DateUtils;
import com.gym.common.utils.StringUtils;
import com.gym.common.utils.bean.BeanValidators;
import com.gym.system.domain.GymMembership;
import com.gym.system.domain.dto.GymMemberDTO;
import com.gym.system.mapper.GymMembershipMapper;
import com.gym.system.service.IGymMembershipService;

/**
 * 健身房会籍 业务层处理
 * 
 * @author gym
 */
@Service
public class GymMembershipServiceImpl implements IGymMembershipService
{
    private static final Logger log = LoggerFactory.getLogger(GymMembershipServiceImpl.class);

    @Autowired
    private GymMembershipMapper gymMembershipMapper;

    @Autowired
    protected Validator validator;

    /**
     * 根据条件分页查询会籍列表
     * 
     * @param gymMembership 会籍信息
     * @return 会籍信息集合信息
     */
    @Override
    public List<GymMembership> selectGymMembershipList(GymMembership gymMembership)
    {
        return gymMembershipMapper.selectGymMembershipList(gymMembership);
    }

    /**
     * 通过会籍ID查询会籍
     * 
     * @param id 会籍ID
     * @return 会籍对象信息
     */
    @Override
    public GymMembership selectGymMembershipById(Long id)
    {
        return gymMembershipMapper.selectGymMembershipById(id);
    }

    /**
     * 通过用户ID查询会籍列表
     * 
     * @param userId 用户ID
     * @return 会籍信息集合
     */
    @Override
    public List<GymMembership> selectGymMembershipByUserId(Long userId)
    {
        return gymMembershipMapper.selectGymMembershipByUserId(userId);
    }

    /**
     * 通过用户ID查询当前有效会籍
     * 
     * @param userId 用户ID
     * @return 会籍对象信息
     */
    @Override
    public GymMembership selectActiveGymMembershipByUserId(Long userId)
    {
        return gymMembershipMapper.selectActiveGymMembershipByUserId(userId);
    }

    /**
     * 新增会籍信息
     * 
     * @param gymMembership 会籍信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertGymMembership(GymMembership gymMembership)
    {
        // 设置默认状态
        if (StringUtils.isEmpty(gymMembership.getStatus()))
        {
            gymMembership.setStatus("active");
        }
        // 设置默认删除状态
        if (gymMembership.getIsDeleted() == null)
        {
            gymMembership.setIsDeleted(0);
        }
        return gymMembershipMapper.insertGymMembership(gymMembership);
    }

    /**
     * 修改会籍信息
     * 
     * @param gymMembership 会籍信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateGymMembership(GymMembership gymMembership)
    {
        return gymMembershipMapper.updateGymMembership(gymMembership);
    }

    /**
     * 修改会籍状态
     * 
     * @param id 会籍ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public boolean updateGymMembershipStatus(Long id, String status)
    {
        return gymMembershipMapper.updateGymMembershipStatus(id, status) > 0;
    }

    /**
     * 通过会籍ID删除会籍
     * 
     * @param id 会籍ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGymMembershipById(Long id)
    {
        return gymMembershipMapper.deleteGymMembershipById(id);
    }

    /**
     * 批量删除会籍信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGymMembershipByIds(String ids)
    {
        Long[] membershipIds = Convert.toLongArray(ids);
        return gymMembershipMapper.deleteGymMembershipByIds(membershipIds);
    }

    /**
     * 检查用户是否有有效会籍
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public boolean checkUserHasActiveMembership(Long userId)
    {
        return gymMembershipMapper.checkUserHasActiveMembership(userId) > 0;
    }

    /**
     * 查询即将过期的会籍
     * 
     * @param days 天数
     * @return 会籍信息集合
     */
    @Override
    public List<GymMembership> selectExpiringMemberships(int days)
    {
        return gymMembershipMapper.selectExpiringMemberships(days);
    }

    /**
     * 查询已过期的会籍
     * 
     * @return 会籍信息集合
     */
    @Override
    public List<GymMembership> selectExpiredMemberships()
    {
        return gymMembershipMapper.selectExpiredMemberships();
    }

    /**
     * 查询会员列表（联表查询，返回DTO）
     * 
     * @param gymMemberDTO 会员查询条件
     * @return 会员信息集合
     */
    @Override
    public List<GymMemberDTO> selectGymMemberList(GymMemberDTO gymMemberDTO)
    {
        return gymMembershipMapper.selectGymMemberList(gymMemberDTO);
    }

    /**
     * 查询会员统计数据
     * 
     * @return 统计数据Map
     */
    @Override
    public java.util.Map<String, Object> selectMemberStatistics()
    {
        return gymMembershipMapper.selectMemberStatistics();
    }
}
