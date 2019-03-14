/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by guanshinfo-lizhunan on 2017/7/24.
 * 正则表达式工具类
 */

public class FormatCheckUtils {

    /**
     * 用户名验证规则
     * 只能是数字或者是英文字母
     *
     * @param string
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isUsername(String string) throws PatternSyntaxException {
        String regExp = "([a-zA-Z0-9]{6,32})";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 验证邮件地址是否合法
     *
     * @param s
     * @return
     */
    public static boolean isEmail(String s) {
        Pattern pattern = Pattern
           .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
         Matcher mc = pattern.matcher(s);
        return mc.matches();
    }

}
