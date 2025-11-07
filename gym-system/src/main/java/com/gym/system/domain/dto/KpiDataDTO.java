package com.gym.system.domain.dto;

import java.math.BigDecimal;

/**
 * KPI数据DTO
 * 
 * @author gym
 */
public class KpiDataDTO
{
    /** 总会员数 */
    private Long totalMembers;
    
    /** 会员增长率 */
    private Double memberGrowth;
    
    /** 课程签到数 */
    private Long totalCheckIns;
    
    /** 签到增长率 */
    private Double checkInGrowth;
    
    /** 活跃率 */
    private Double activityRate;
    
    /** 活跃率增长率 */
    private Double activityGrowth;

    public Long getTotalMembers()
    {
        return totalMembers;
    }

    public void setTotalMembers(Long totalMembers)
    {
        this.totalMembers = totalMembers;
    }

    public Double getMemberGrowth()
    {
        return memberGrowth;
    }

    public void setMemberGrowth(Double memberGrowth)
    {
        this.memberGrowth = memberGrowth;
    }

    public Long getTotalCheckIns()
    {
        return totalCheckIns;
    }

    public void setTotalCheckIns(Long totalCheckIns)
    {
        this.totalCheckIns = totalCheckIns;
    }

    public Double getCheckInGrowth()
    {
        return checkInGrowth;
    }

    public void setCheckInGrowth(Double checkInGrowth)
    {
        this.checkInGrowth = checkInGrowth;
    }

    public Double getActivityRate()
    {
        return activityRate;
    }

    public void setActivityRate(Double activityRate)
    {
        this.activityRate = activityRate;
    }

    public Double getActivityGrowth()
    {
        return activityGrowth;
    }

    public void setActivityGrowth(Double activityGrowth)
    {
        this.activityGrowth = activityGrowth;
    }
}

