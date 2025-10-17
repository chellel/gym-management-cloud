package com.gym.common.enums;

/**
 * 会籍类型枚举
 * 
 * @author gym
 */
public enum MembershipTypeEnum
{
    MONTH("month", "月度会员"),
    QUARTER("quarter", "季度会员"),
    HALF_YEAR("half_year", "半年会员"),
    YEAR("year", "年度会员"),
    LIFETIME("lifetime", "终身会员");

    private final String code;
    private final String name;

    MembershipTypeEnum(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    /**
     * 根据代码获取枚举
     * 
     * @param code 代码
     * @return 枚举值
     */
    public static MembershipTypeEnum getByCode(String code)
    {
        for (MembershipTypeEnum type : values())
        {
            if (type.getCode().equals(code))
            {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据名称获取枚举
     * 
     * @param name 名称
     * @return 枚举值
     */
    public static MembershipTypeEnum getByName(String name)
    {
        for (MembershipTypeEnum type : values())
        {
            if (type.getName().equals(name))
            {
                return type;
            }
        }
        return null;
    }

    /**
     * 验证代码是否有效
     * 
     * @param code 代码
     * @return 是否有效
     */
    public static boolean isValidCode(String code)
    {
        return getByCode(code) != null;
    }

    /**
     * 验证名称是否有效
     * 
     * @param name 名称
     * @return 是否有效
     */
    public static boolean isValidName(String name)
    {
        return getByName(name) != null;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
