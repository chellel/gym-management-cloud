package com.gym.util;

import org.apache.shiro.lang.codec.Base64;
import com.gym.common.utils.security.CipherUtils;

/**
 * 生成固定的 RememberMe Cookie 密钥
 * 运行此类的 main 方法，将生成的密钥复制到 application.yml 的 shiro.cookie.cipherKey 配置中
 */
public class GenerateCipherKey
{
    public static void main(String[] args)
    {
        String cipherKey = Base64.encodeToString(CipherUtils.generateNewKey(128, "AES").getEncoded());
        System.out.println("生成的 cipherKey:");
        System.out.println(cipherKey);
        System.out.println("\n请将此密钥复制到 application.yml 的 shiro.cookie.cipherKey 配置中");
    }
}

