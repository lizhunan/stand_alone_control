/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control;

import android.os.Handler;
import android.os.Message;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.util.AESUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = AESUtils.aesEncrypt("DDD", AESUtils.VIPARA);
        String s1 = AESUtils.aesDecrypt(s, AESUtils.VIPARA);
        System.out.println(s);
        System.out.println(s1);
        List a = new ArrayList();
        a.add("d");
    }
}