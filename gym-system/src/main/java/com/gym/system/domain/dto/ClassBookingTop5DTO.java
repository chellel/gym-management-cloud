package com.gym.system.domain.dto;

/**
 * 课程预约Top5数据DTO
 * 
 * @author gym
 */
public class ClassBookingTop5DTO
{
    /** 课程名称 */
    private String name;
    
    /** 预约次数 */
    private Long bookings;

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
}

