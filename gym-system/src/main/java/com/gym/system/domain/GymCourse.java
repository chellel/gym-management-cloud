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
import com.gym.common.xss.Xss;

/**
 * 课程信息对象 gym_course
 * 
 * @author gym
 */
public class GymCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    @Excel(name = "课程序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "课程编号")
    private Long id;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String name;

    /** 课程描述 */
    @Excel(name = "课程描述")
    private String description;

    /** 课程时长（分钟） */
    @Excel(name = "课程时长", cellType = ColumnType.NUMERIC)
    private Integer durationMinutes;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;

    public GymCourse()
    {
    }

    public GymCourse(Long id)
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

    @Xss(message = "课程名称不能包含脚本字符")
    @NotBlank(message = "课程名称不能为空")
    @Size(min = 0, max = 100, message = "课程名称长度不能超过100个字符")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Size(min = 0, max = 1000, message = "课程描述长度不能超过1000个字符")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @NotNull(message = "课程时长不能为空")
    @Min(value = 1, message = "课程时长不能小于1分钟")
    @Max(value = 480, message = "课程时长不能超过480分钟")
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
            .append("name", getName())
            .append("description", getDescription())
            .append("durationMinutes", getDurationMinutes())
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
