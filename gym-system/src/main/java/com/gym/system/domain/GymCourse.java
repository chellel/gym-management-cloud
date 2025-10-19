package com.gym.system.domain;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.core.domain.BaseEntity;

/**
 * 健身房课程对象 gym_course
 * 
 * @author gym
 */
public class GymCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    private Long id;

    /** 课程名称 */
    @NotBlank(message = "课程名称不能为空")
    @Size(min = 0, max = 100, message = "课程名称不能超过100个字符")
    private String name;

    /** 课程描述 */
    @Size(min = 0, max = 500, message = "课程描述不能超过500个字符")
    private String description;

    /** 课程时长（分钟） */
    @NotNull(message = "课程时长不能为空")
    @Min(value = 1, message = "课程时长不能小于1分钟")
    @Max(value = 480, message = "课程时长不能超过480分钟")
    private Integer durationMinutes;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date deleteTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getDurationMinutes()
    {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes)
    {
        this.durationMinutes = durationMinutes;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public java.util.Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(java.util.Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .append("durationMinutes", getDurationMinutes())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .toString();
    }
}