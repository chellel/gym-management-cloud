package com.gym.system.domain.dto;

import java.util.Date;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.annotation.Excel;

/**
 * 会员管理DTO - 包含用户和会籍信息
 * 
 * @author gym
 */
public class GymMemberDTO
{
    /** 用户ID */
    private Long userId;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userCode;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = Excel.ColumnType.TEXT)
    private String phone;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String email;

    /** 性别：0-女，1-男 */
    @Excel(name = "性别", readConverterExp = "0=女,1=男")
    private Integer gender;

    /** 出生日期 */
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    /** 角色：member-会员，coach-教练 */
    @Excel(name = "角色", readConverterExp = "member=会员,coach=教练")
    private String role;

    /** 用户状态：active-正常，inactive-停用，expired-过期，leave-请假 */
    @Excel(name = "用户状态", readConverterExp = "active=正常,inactive=停用,expired=过期,leave=请假")
    private String userStatus;

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

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public GymMemberDTO()
    {
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @NotBlank(message = "用户编号不能为空")
    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    @NotBlank(message = "用户姓名不能为空")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @NotBlank(message = "手机号码不能为空")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getGender()
    {
        return gender;
    }

    public void setGender(Integer gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    @NotBlank(message = "角色不能为空")
    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @NotBlank(message = "用户状态不能为空")
    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
