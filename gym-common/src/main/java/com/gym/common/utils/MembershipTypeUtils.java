package com.gym.common.utils;

import com.gym.common.enums.MembershipTypeEnum;

/**
 * 会籍类型工具类
 * 
 * @author gym
 */
public class MembershipTypeUtils
{
    /**
     * 根据代码获取中文名称
     * 
     * @param code 代码
     * @return 中文名称
     */
    public static String getChineseName(String code)
    {
        MembershipTypeEnum type = MembershipTypeEnum.getByCode(code);
        return type != null ? type.getName() : code;
    }

    /**
     * 根据中文名称获取代码
     * 
     * @param chineseName 中文名称
     * @return 代码
     */
    public static String getCode(String chineseName)
    {
        MembershipTypeEnum type = MembershipTypeEnum.getByName(chineseName);
        return type != null ? type.getCode() : chineseName;
    }

    /**
     * 验证会籍类型代码是否有效
     * 
     * @param code 代码
     * @return 是否有效
     */
    public static boolean isValidCode(String code)
    {
        return MembershipTypeEnum.isValidCode(code);
    }

    /**
     * 验证会籍类型名称是否有效
     * 
     * @param name 名称
     * @return 是否有效
     */
    public static boolean isValidName(String name)
    {
        return MembershipTypeEnum.isValidName(name);
    }

    /**
     * 获取所有会籍类型的代码数组
     * 
     * @return 代码数组
     */
    public static String[] getAllCodes()
    {
        MembershipTypeEnum[] types = MembershipTypeEnum.values();
        String[] codes = new String[types.length];
        for (int i = 0; i < types.length; i++)
        {
            codes[i] = types[i].getCode();
        }
        return codes;
    }

    /**
     * 获取所有会籍类型的名称数组
     * 
     * @return 名称数组
     */
    public static String[] getAllNames()
    {
        MembershipTypeEnum[] types = MembershipTypeEnum.values();
        String[] names = new String[types.length];
        for (int i = 0; i < types.length; i++)
        {
            names[i] = types[i].getName();
        }
        return names;
    }
}
