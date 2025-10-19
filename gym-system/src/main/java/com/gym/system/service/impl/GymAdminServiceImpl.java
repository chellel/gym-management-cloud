package com.gym.system.service.impl;

import java.util.List;
import com.gym.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.system.mapper.GymAdminMapper;
import com.gym.system.domain.GymAdmin;
import com.gym.system.service.IGymAdminService;

/**
 * 健身房管理员Service业务层处理
 * 
 * @author gym
 * @date 2024-01-15
 */
@Service
public class GymAdminServiceImpl implements IGymAdminService 
{
    @Autowired
    private GymAdminMapper gymAdminMapper;

    /**
     * 查询健身房管理员
     * 
     * @param id 健身房管理员主键
     * @return 健身房管理员
     */
    @Override
    public GymAdmin selectGymAdminById(Long id)
    {
        return gymAdminMapper.selectGymAdminById(id);
    }

    /**
     * 根据用户名查询健身房管理员
     * 
     * @param username 用户名
     * @return 健身房管理员
     */
    @Override
    public GymAdmin selectGymAdminByUsername(String username)
    {
        return gymAdminMapper.selectGymAdminByUsername(username);
    }

    /**
     * 查询健身房管理员列表
     * 
     * @param gymAdmin 健身房管理员
     * @return 健身房管理员
     */
    @Override
    public List<GymAdmin> selectGymAdminList(GymAdmin gymAdmin)
    {
        return gymAdminMapper.selectGymAdminList(gymAdmin);
    }

    /**
     * 新增健身房管理员
     * 
     * @param gymAdmin 健身房管理员
     * @return 结果
     */
    @Override
    public int insertGymAdmin(GymAdmin gymAdmin)
    {
        gymAdmin.setCreateTime(DateUtils.getNowDate());
        return gymAdminMapper.insertGymAdmin(gymAdmin);
    }

    /**
     * 修改健身房管理员
     * 
     * @param gymAdmin 健身房管理员
     * @return 结果
     */
    @Override
    public int updateGymAdmin(GymAdmin gymAdmin)
    {
        gymAdmin.setUpdateTime(DateUtils.getNowDate());
        return gymAdminMapper.updateGymAdmin(gymAdmin);
    }

    /**
     * 批量删除健身房管理员
     * 
     * @param ids 需要删除的健身房管理员主键
     * @return 结果
     */
    @Override
    public int deleteGymAdminByIds(Long[] ids)
    {
        return gymAdminMapper.deleteGymAdminByIds(ids);
    }

    /**
     * 删除健身房管理员信息
     * 
     * @param id 健身房管理员主键
     * @return 结果
     */
    @Override
    public int deleteGymAdminById(Long id)
    {
        return gymAdminMapper.deleteGymAdminById(id);
    }
}
