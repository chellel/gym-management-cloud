package com.gym.system.domain.dto;

/**
 * 课程详细数据DTO
 * 
 * @author gym
 */
public class ClassDetailsDTO
{
    /** 课程名称 */
    private String name;
    
    /** 预约次数 */
    private Long bookings;
    
    /** 签到率 */
    private Double attendanceRate;
    
    /** 满意度 */
    private Double satisfaction;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getBookings()
    {
        return bookings;
    }

    public void setBookings(Long bookings)
    {
        this.bookings = bookings;
    }

    public Double getAttendanceRate()
    {
        return attendanceRate;
    }

    public void setAttendanceRate(Double attendanceRate)
    {
        this.attendanceRate = attendanceRate;
    }

    public Double getSatisfaction()
    {
        return satisfaction;
    }

    public void setSatisfaction(Double satisfaction)
    {
        this.satisfaction = satisfaction;
    }
}

