package com.gym.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gym.common.annotation.Excel;
import com.gym.common.core.domain.BaseEntity;

/**
 * 课程预约对象 gym_booking
 * 
 * @author gym
 * @date 2025-10-19
 */
public class GymBooking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约ID，主键 */
    private Long id;

    /** 排班ID，外键 */
    @Excel(name = "排班ID")
    private Long scheduleId;

    /** 会员ID，外键 */
    @Excel(name = "会员ID")
    private Long memberId;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date bookingTime;

    /** 签到时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "签到时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkinTime;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deleteTime;

    /** 排班信息（关联查询） */
    private GymSchedule schedule;

    /** 会员信息（关联查询） */
    private GymUser member;

    /** 课程名称（关联查询） */
    private String courseName;

    /** 教练名称（关联查询） */
    private String coachName;

    /** 排班开始时间（关联查询） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleStartTime;

    /** 排班结束时间（关联查询） */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleEndTime;

    /** 排班地点（关联查询） */
    private String scheduleLocation;

    /** 排班状态（关联查询） */
    private String scheduleStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setScheduleId(Long scheduleId) 
    {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() 
    {
        return scheduleId;
    }

    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }

    public void setBookingTime(Date bookingTime) 
    {
        this.bookingTime = bookingTime;
    }

    public Date getBookingTime() 
    {
        return bookingTime;
    }

    public void setCheckinTime(Date checkinTime) 
    {
        this.checkinTime = checkinTime;
    }

    public Date getCheckinTime() 
    {
        return checkinTime;
    }

    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    public void setDeleteTime(Date deleteTime) 
    {
        this.deleteTime = deleteTime;
    }

    public Date getDeleteTime() 
    {
        return deleteTime;
    }

    public void setSchedule(GymSchedule schedule) 
    {
        this.schedule = schedule;
    }

    public GymSchedule getSchedule() 
    {
        return schedule;
    }

    public void setMember(GymUser member) 
    {
        this.member = member;
    }

    public GymUser getMember() 
    {
        return member;
    }

    public void setCourseName(String courseName) 
    {
        this.courseName = courseName;
    }

    public String getCourseName() 
    {
        return courseName;
    }

    public void setCoachName(String coachName) 
    {
        this.coachName = coachName;
    }

    public String getCoachName() 
    {
        return coachName;
    }

    public void setScheduleStartTime(Date scheduleStartTime) 
    {
        this.scheduleStartTime = scheduleStartTime;
    }

    public Date getScheduleStartTime() 
    {
        return scheduleStartTime;
    }

    public void setScheduleEndTime(Date scheduleEndTime) 
    {
        this.scheduleEndTime = scheduleEndTime;
    }

    public Date getScheduleEndTime() 
    {
        return scheduleEndTime;
    }

    public void setScheduleLocation(String scheduleLocation) 
    {
        this.scheduleLocation = scheduleLocation;
    }

    public String getScheduleLocation() 
    {
        return scheduleLocation;
    }

    public void setScheduleStatus(String scheduleStatus) 
    {
        this.scheduleStatus = scheduleStatus;
    }

    public String getScheduleStatus() 
    {
        return scheduleStatus;
    }

    @Override
    public String toString() {
        return "GymBooking{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", memberId=" + memberId +
                ", bookingTime=" + bookingTime +
                ", checkinTime=" + checkinTime +
                ", isDeleted=" + isDeleted +
                ", deleteTime=" + deleteTime +
                ", courseName='" + courseName + '\'' +
                ", coachName='" + coachName + '\'' +
                ", scheduleStartTime=" + scheduleStartTime +
                ", scheduleEndTime=" + scheduleEndTime +
                ", scheduleLocation='" + scheduleLocation + '\'' +
                ", scheduleStatus='" + scheduleStatus + '\'' +
                '}';
    }
}
