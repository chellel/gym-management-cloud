package com.gym.system.mapper;

import java.util.List;
import com.gym.system.domain.GymAdmin;

/**
 * 健身房管理员Mapper接口
 * 
 * @author gym
 * @date 2024-01-15
 */
public interface GymAdminMapper 
{
    /**
     * 查询健身房管理员
     * 
     * @param id 健身房管理员主键
     * @return 健身房管理员
     */
    public GymAdmin selectGymAdminById(Long id);

    /**
     * 根据用户名查询健身房管理员
     * 
     * @param username 用户名
     * @return 健身房管理员
     */
    public GymAdmin selectGymAdminByUsername(String username);

    /**
     * 查询健身房管理员列表
     * 
     * @param gymAdmin 健身房管理员
     * @return 健身房管理员集合
     */
    public List<GymAdmin> selectGymAdminList(GymAdmin gymAdmin);

    /**
     * 新增健身房管理员
     * 
     * @param gymAdmin 健身房管理员
     * @return 结果
     */
    public int insertGymAdmin(GymAdmin gymAdmin);

    /**
     * 修改健身房管理员
     * 
     * @param gymAdmin 健身房管理员
     * @return 结果
     */
    public int updateGymAdmin(GymAdmin gymAdmin);

    /**
     * 删除健身房管理员
     * 
     * @param id 健身房管理员主键
     * @return 结果
     */
    public int deleteGymAdminById(Long id);

    /**
     * 批量删除健身房管理员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGymAdminByIds(Long[] ids);
}
