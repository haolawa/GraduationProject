package com.example.graduationproject.utils;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(16[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    private static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![a-zA-Z\\W]+$)(?![0-9\\W]+$)[0-9A-Za-z]{6,12}$";


    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
}
