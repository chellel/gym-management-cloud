package com.gym.system.domain;

import java.util.Date;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.common.annotation.Excel;
import com.gym.common.annotation.Excel.ColumnType;
import com.gym.common.annotation.Excel.Type;
import com.gym.common.core.domain.BaseEntity;
import com.gym.common.xss.Xss;

/**
 * 健身房管理员对象 gym_admin
 * 
 * @author gym
 */
public class GymAdmin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 管理员ID */
    @Excel(name = "管理员序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "管理员编号")
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String name;

    /** 手机号码 */
    @Excel(name = "手机号码", cellType = ColumnType.TEXT)
    private String phone;

    /** 密码 */
    private String password;

    /** 角色：super_admin-超级管理员，admin-普通管理员 */
    @Excel(name = "角色", readConverterExp = "super_admin=超级管理员,admin=普通管理员")
    private String role;

    /** 状态：active-正常，inactive-停用 */
    @Excel(name = "状态", readConverterExp = "active=正常,inactive=停用")
    private String status;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public GymAdmin()
    {
    }

    public GymAdmin(Long id)
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

    @JsonProperty("username")
    @Xss(message = "用户名不能包含脚本字符")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 0, max = 100, message = "用户名长度不能超过100个字符")
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @JsonProperty("name")
    @Xss(message = "真实姓名不能包含脚本字符")
    @NotBlank(message = "真实姓名不能为空")
    @Size(min = 0, max = 100, message = "真实姓名长度不能超过100个字符")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @JsonProperty("phone")
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

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @JsonProperty("role")
    @NotBlank(message = "角色不能为空")
    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @JsonProperty("status")
    @NotBlank(message = "状态不能为空")
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @JsonProperty("createTime")
    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("username", getUsername())
            .append("name", getName())
            .append("phone", getPhone())
            .append("role", getRole())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
