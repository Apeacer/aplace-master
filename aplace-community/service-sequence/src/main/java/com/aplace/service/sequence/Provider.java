package com.tcredit.test.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sam on 16/07/2017.
 */
public class Provider
{
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
        context.start();
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
    }
}
