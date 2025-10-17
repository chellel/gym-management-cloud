package com.gym.system.domain;

import java.util.Date;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.annotation.Excel;
import com.gym.common.annotation.Excel.ColumnType;
import com.gym.common.annotation.Excel.Type;
import com.gym.common.core.domain.BaseEntity;
import com.gym.common.xss.Xss;

/**
 * 健身房会员对象 gym_user
 * 
 * @author gym
 */
public class GymUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    @Excel(name = "会员序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "会员编号")
    private Long id;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String name;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = ColumnType.TEXT)
    private String phone;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String email;

    /** 密码 */
    private String password;

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

    /** 状态：active-正常，inactive-停用，expired-过期 */
    @Excel(name = "状态", readConverterExp = "active=正常,inactive=停用,expired=过期")
    private String status;

    /** 经验年限（教练专用） */
    @Excel(name = "经验年限")
    private String experience;

    /** 个人简介（教练专用） */
    @Excel(name = "个人简介")
    private String description;

    /** 入职时间（教练专用） */
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;

    public GymUser()
    {
    }

    public GymUser(Long id)
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

    @Xss(message = "用户编号不能包含脚本字符")
    @NotBlank(message = "用户编号不能为空")
    @Size(min = 0, max = 50, message = "用户编号长度不能超过50个字符")
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Xss(message = "用户姓名不能包含脚本字符")
    @NotBlank(message = "用户姓名不能为空")
    @Size(min = 0, max = 100, message = "用户姓名长度不能超过100个字符")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @NotBlank(message = "手机号码不能为空")
    @Size(min = 0, max = 20, message = "手机号码长度不能超过20个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 150, message = "邮箱长度不能超过150个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

    @NotBlank(message = "状态不能为空")
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Size(min = 0, max = 50, message = "经验年限长度不能超过50个字符")
    public String getExperience()
    {
        return experience;
    }

    public void setExperience(String experience)
    {
        this.experience = experience;
    }

    @Size(min = 0, max = 1000, message = "个人简介长度不能超过1000个字符")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getHireDate()
    {
        return hireDate;
    }

    public void setHireDate(Date hireDate)
    {
        this.hireDate = hireDate;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("password", getPassword())
            .append("gender", getGender())
            .append("birthDate", getBirthDate())
            .append("role", getRole())
            .append("status", getStatus())
            .append("experience", getExperience())
            .append("description", getDescription())
            .append("hireDate", getHireDate())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
