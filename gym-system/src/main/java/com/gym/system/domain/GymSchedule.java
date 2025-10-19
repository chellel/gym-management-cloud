package com.gym.system.domain;

import java.util.Date;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.core.domain.BaseEntity;

/**
 * 健身房课程排班对象 gym_schedule
 * 
 * @author gym
 */
public class GymSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排班ID */
    private Long id;

    /** 课程ID */
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    /** 教练ID */
    @NotNull(message = "教练ID不能为空")
    private Long coachId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /** 上课地点 */
    @NotBlank(message = "上课地点不能为空")
    @Size(min = 0, max = 100, message = "上课地点不能超过100个字符")
    private String location;

    /** 最大容量 */
    @NotNull(message = "最大容量不能为空")
    @Min(value = 1, message = "最大容量不能小于1")
    @Max(value = 100, message = "最大容量不能超过100")
    private Integer maxCapacity;

    /** 状态：waiting-待开始，cancelled-已取消，completed-已完成 */
    @NotBlank(message = "状态不能为空")
    @Pattern(regexp = "^(waiting|cancelled|completed)$", message = "状态只能是：waiting(待开始)、cancelled(已取消)、completed(已完成)")
    private String status;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteTime;

    // 关联对象字段（用于显示）
    /** 课程名称 */
    private String courseName;

    /** 教练姓名 */
    private String coachName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getCourseId()
    {
        return courseId;
    }

    public void setCourseId(Long courseId)
    {
        this.courseId = courseId;
    }

    public Long getCoachId()
    {
        return coachId;
    }

    public void setCoachId(Long coachId)
    {
        this.coachId = coachId;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Integer getMaxCapacity()
    {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

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

    public String getCourseName()
    {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getCoachName()
    {
        return coachName;
    }

    public void setCoachName(String coachName)
    {
        this.coachName = coachName;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("courseId", getCourseId())
            .append("coachId", getCoachId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("location", getLocation())
            .append("maxCapacity", getMaxCapacity())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .append("courseName", getCourseName())
            .append("coachName", getCoachName())
            .toString();
    }
}
