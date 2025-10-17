package com.gym.system.domain;

import java.util.Date;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.annotation.Excel;
import com.gym.common.annotation.Excel.ColumnType;
import com.gym.common.annotation.Excel.Type;
import com.gym.common.core.domain.BaseEntity;

/**
 * 健身房会籍对象 gym_membership
 * 
 * @author gym
 */
public class GymMembership extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会籍ID */
    @Excel(name = "会籍序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "会籍编号")
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 会籍类型 */
    @Excel(name = "会籍类型", readConverterExp = "month=月度会员,quarter=季度会员,half_year=半年会员,year=年度会员,lifetime=终身会员")
    @NotBlank(message = "会籍类型不能为空")
    @Pattern(regexp = "^(month|quarter|half_year|year|lifetime)$", message = "会籍类型只能是：month(月度会员)、quarter(季度会员)、half_year(半年会员)、year(年度会员)、lifetime(终身会员)")
    private String membershipType;

    /** 会籍开始日期 */
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 会籍到期日期 */
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "active=正常,inactive=停用,expired=过期,leave=请假")
    private String status;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;

    // 关联用户信息（用于显示）
    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userCode;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = ColumnType.TEXT)
    private String phone;

    public GymMembership()
    {
    }

    public GymMembership(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @NotNull(message = "用户ID不能为空")
    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @NotBlank(message = "会籍类型不能为空")
    @Size(min = 0, max = 50, message = "会籍类型长度不能超过50个字符")
    public String getMembershipType()
    {
        return membershipType;
    }

    public void setMembershipType(String membershipType)
    {
        this.membershipType = membershipType;
    }

    @NotNull(message = "开始日期不能为空")
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    @NotNull(message = "到期日期不能为空")
    public Date getExpireDate()
    {
        return expireDate;
    }

    public void setExpireDate(Date expireDate)
    {
        this.expireDate = expireDate;
    }

    @NotBlank(message = "状态不能为空")
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("membershipType", getMembershipType())
            .append("startDate", getStartDate())
            .append("expireDate", getExpireDate())
            .append("status", getStatus())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .append("userName", getUserName())
            .append("userCode", getUserCode())
            .append("phone", getPhone())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
