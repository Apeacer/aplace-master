package com.aplace.service.sequence;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sam on 16/07/2017.
 */
public class StartUp {

    private final static Logger LOGGER = Logger.getLogger(StartUp.class);
    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        context.start();
        LOGGER.info("dubbo provider started...");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    context.stop();
                    context.close();
                    LOGGER.info("dubbo provider stopped!");
                } catch (Throwable t) {
                    LOGGER.error(t.getMessage(), t);
                }
                synchronized (StartUp.class) {
                    running = false;
                    StartUp.class.notify();
                }
            }
        });
        synchronized (StartUp.class) {
            while (running) {
                try {
                    StartUp.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
//    public static void main(String[] args) throws Exception {
//        ClassPathXmlApplicationContext context =
//                new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
//        context.start();
//        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
//    }
}
