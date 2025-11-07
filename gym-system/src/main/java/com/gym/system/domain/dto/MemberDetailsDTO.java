package com.gym.system.domain.dto;

import java.math.BigDecimal;

/**
 * 会员详细数据DTO
 * 
 * @author gym
 */
public class MemberDetailsDTO
{
    /** 会员类型 */
    private String type;
    
    /** 数量 */
    private Long count;
    
    /** 占比 */
    private Double percentage;
    
    /** 平均收入 */
    private BigDecimal avgRevenue;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Long getCount()
    {
        return count;
    }

    public void setCount(Long count)
    {
        this.count = count;
    }

    public Double getPercentage()
    {
        return percentage;
    }

    public void setPercentage(Double percentage)
    {
        this.percentage = percentage;
    }

    public BigDecimal getAvgRevenue()
    {
        return avgRevenue;
    }

    public void setAvgRevenue(BigDecimal avgRevenue)
    {
        this.avgRevenue = avgRevenue;
    }
}

