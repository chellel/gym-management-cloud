package com.gym.system.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.annotation.Excel;
import com.gym.system.domain.GymUser;

/**
 * 会员管理DTO - 包含用户和会籍信息
 * 
 * @author gym
 */
public class GymMemberDTO extends GymUser
{
    /** 会籍ID */
    private Long membershipId;

    /** 会籍类型 */
    @Excel(name = "会籍类型", readConverterExp = "month=月度会员,quarter=季度会员,half_year=半年会员,year=年度会员,lifetime=终身会员")
    @NotBlank(message = "会籍类型不能为空")
    @Pattern(regexp = "^(month|quarter|half_year|year|lifetime)$", message = "会籍类型只能是：month(月度会员)、quarter(季度会员)、half_year(半年会员)、year(年度会员)、lifetime(终身会员)")
    private String membershipType;

    /** 会籍开始日期 */
    @Excel(name = "会籍开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipStartDate;

    /** 会籍到期日期 */
    @Excel(name = "会籍到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date membershipExpireDate;

    /** 会籍状态 */
    @Excel(name = "会籍状态", readConverterExp = "active=正常,inactive=停用,expired=过期,leave=请假")
    private String membershipStatus;

    /** 剩余天数 */
    @Excel(name = "剩余天数")
    private Integer remainingDays;

    public GymMemberDTO()
    {
    }

    public Long getMembershipId()
    {
        return membershipId;
    }

    public void setMembershipId(Long membershipId)
    {
        this.membershipId = membershipId;
    }

    public String getMembershipType()
    {
        return membershipType;
    }

    public void setMembershipType(String membershipType)
    {
        this.membershipType = membershipType;
    }

    public Date getMembershipStartDate()
    {
        return membershipStartDate;
    }

    public void setMembershipStartDate(Date membershipStartDate)
    {
        this.membershipStartDate = membershipStartDate;
    }

    public Date getMembershipExpireDate()
    {
        return membershipExpireDate;
    }

    public void setMembershipExpireDate(Date membershipExpireDate)
    {
        this.membershipExpireDate = membershipExpireDate;
    }

    public String getMembershipStatus()
    {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus)
    {
        this.membershipStatus = membershipStatus;
    }

    public Integer getRemainingDays()
    {
        return remainingDays;
    }

    public void setRemainingDays(Integer remainingDays)
    {
        this.remainingDays = remainingDays;
    }

    // 为了保持向后兼容，提供一些别名方法
    public String getUserCode()
    {
        return getUserId();
    }

    public void setUserCode(String userCode)
    {
        setUserId(userCode);
    }

    public String getUserName()
    {
        return getName();
    }

    public void setUserName(String userName)
    {
        setName(userName);
    }

    public String getUserStatus()
    {
        return getStatus();
    }

    public void setUserStatus(String userStatus)
    {
        setStatus(userStatus);
    }
}
