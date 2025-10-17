package com.gym.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.gym.common.annotation.DataScope;
import com.gym.common.constant.UserConstants;
import com.gym.system.domain.GymUser;
import com.gym.common.core.text.Convert;
import com.gym.common.exception.ServiceException;
import com.gym.common.utils.ExceptionUtil;
import com.gym.common.utils.ShiroUtils;
import com.gym.common.utils.StringUtils;
import com.gym.common.utils.bean.BeanValidators;
import com.gym.common.utils.security.Md5Utils;
import com.gym.system.mapper.GymUserMapper;
import com.gym.system.service.IGymUserService;

/**
 * 健身房会员 业务层处理
 * 
 * @author gym
 */
@Service
public class GymUserServiceImpl implements IGymUserService
{
    private static final Logger log = LoggerFactory.getLogger(GymUserServiceImpl.class);

    @Autowired
    private GymUserMapper gymUserMapper;

    @Autowired
    protected Validator validator;

    /**
     * 根据条件分页查询会员列表
     * 
     * @param gymUser 会员信息
     * @return 会员信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<GymUser> selectGymUserList(GymUser gymUser)
    {
        return gymUserMapper.selectGymUserList(gymUser);
    }

    /**
     * 通过用户编号查询会员
     * 
     * @param userId 用户编号
     * @return 会员对象信息
     */
    @Override
    public GymUser selectGymUserByUserId(String userId)
    {
        return gymUserMapper.selectGymUserByUserId(userId);
    }

    /**
     * 通过手机号码查询会员
     * 
     * @param phone 手机号码
     * @return 会员对象信息
     */
    @Override
    public GymUser selectGymUserByPhone(String phone)
    {
        return gymUserMapper.selectGymUserByPhone(phone);
    }

    /**
     * 通过邮箱查询会员
     * 
     * @param email 邮箱
     * @return 会员对象信息
     */
    @Override
    public GymUser selectGymUserByEmail(String email)
    {
        return gymUserMapper.selectGymUserByEmail(email);
    }

    /**
     * 通过会员ID查询会员
     * 
     * @param id 会员ID
     * @return 会员对象信息
     */
    @Override
    public GymUser selectGymUserById(Long id)
    {
        return gymUserMapper.selectGymUserById(id);
    }

    /**
     * 通过会员ID删除会员
     * 
     * @param id 会员ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGymUserById(Long id)
    {
        return gymUserMapper.deleteGymUserById(id);
    }

    /**
     * 批量删除会员信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteGymUserByIds(String ids)
    {
        Long[] userIds = Convert.toLongArray(ids);
        return gymUserMapper.deleteGymUserByIds(userIds);
    }

    /**
     * 保存会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertGymUser(GymUser gymUser)
    {
        return gymUserMapper.insertGymUser(gymUser);
    }

    /**
     * 保存会员信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateGymUser(GymUser gymUser)
    {
        return gymUserMapper.updateGymUser(gymUser);
    }

    /**
     * 修改会员详细信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    public int updateGymUserInfo(GymUser gymUser)
    {
        return gymUserMapper.updateGymUser(gymUser);
    }

    /**
     * 修改会员状态
     * 
     * @param id 会员ID
     * @param status 状态
     * @return 结果
     */
    @Override
    public boolean updateGymUserStatus(Long id, String status)
    {
        return gymUserMapper.updateGymUserStatus(id, status) > 0;
    }

    /**
     * 修改会员密码信息
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    public int resetGymUserPwd(GymUser gymUser)
    {
        return gymUserMapper.resetGymUserPwd(gymUser.getId(), gymUser.getPassword());
    }

    /**
     * 校验用户编号是否唯一
     * 
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    public boolean checkUserIdUnique(GymUser gymUser)
    {
        Long userId = StringUtils.isNull(gymUser.getId()) ? -1L : gymUser.getId();
        GymUser info = gymUserMapper.checkUserIdUnique(gymUser.getUserId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    public boolean checkPhoneUnique(GymUser gymUser)
    {
        Long userId = StringUtils.isNull(gymUser.getId()) ? -1L : gymUser.getId();
        GymUser info = gymUserMapper.checkPhoneUnique(gymUser.getPhone());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param gymUser 会员信息
     * @return 结果
     */
    @Override
    public boolean checkEmailUnique(GymUser gymUser)
    {
        Long userId = StringUtils.isNull(gymUser.getId()) ? -1L : gymUser.getId();
        GymUser info = gymUserMapper.checkEmailUnique(gymUser.getEmail());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 导入会员数据
     * 
     * @param gymUserList 会员数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importGymUser(List<GymUser> gymUserList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(gymUserList) || gymUserList.size() == 0)
        {
            throw new ServiceException("导入会员数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (GymUser gymUser : gymUserList)
        {
            try
            {
                // 验证是否存在这个会员
                GymUser u = gymUserMapper.selectGymUserByUserId(gymUser.getUserId());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, gymUser);
                    gymUser.setCreateBy(operName);
                    this.insertGymUser(gymUser);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、会员 " + gymUser.getUserId() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, gymUser);
                    gymUser.setId(u.getId());
                    gymUser.setUpdateBy(operName);
                    this.updateGymUser(gymUser);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、会员 " + gymUser.getUserId() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、会员 " + gymUser.getUserId() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、会员 " + gymUser.getUserId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
