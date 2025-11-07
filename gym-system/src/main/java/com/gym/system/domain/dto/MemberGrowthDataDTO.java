package com.gym.system.domain.dto;

/**
 * 会员增长趋势数据DTO
 * 
 * @author gym
 */
public class MemberGrowthDataDTO
{
    /** 日期 */
    private String date;
    
    /** 新增会员数 */
    private Long newMembers;
    
    /** 总会员数 */
    private Long totalMembers;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Long getNewMembers()
    {
        return newMembers;
    }

    public void setNewMembers(Long newMembers)
    {
        this.newMembers = newMembers;
    }

    public Long getTotalMembers()
    {
        return totalMembers;
    }

    public void setTotalMembers(Long totalMembers)
    {
        this.totalMembers = totalMembers;
    }
}

