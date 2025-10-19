package com.gym.web.controller.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 管理员登录响应对象
 * 
 * @author gym
 */
public class GymAdminResponse
{
    private Long id;
    private String username;
    private String name;
    private String phone;
    private String role;
    private String status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public GymAdminResponse()
    {
    }

    public GymAdminResponse(Long id, String username, String name, String phone, String role, String status, Date createTime)
    {
        this.id = id;
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createTime = createTime;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
