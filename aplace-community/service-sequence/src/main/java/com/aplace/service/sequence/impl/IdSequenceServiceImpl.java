package com.aplace.service.sequence.impl;

import com.aplace.community.core.service.IdSequenceService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * 实现获取时间相关的全局唯一序列号服务
 *
 * @author apeace
 * @version 2017/8/4.
 */
@Service
public class IdSequenceServiceImpl implements IdSequenceService {

    /**
     * 配置属性
     */
    @Resource(name = "settings")
    Properties settings;

    /** 开始时间截 (2015-01-01) */
    private final long beginTimestamp = 1420041600000L;

    /**
     * 机器码位数
     */
    private long machineBit = Long.parseLong(settings.getProperty("machineBit"));
    /**
     * 机器码最大值
     */
    private long machineMax = -1L ^ (-1L << machineBit);

    /**
     * 毫秒内自增位位数
     */
    private long sequenceBit = Long.parseLong(settings.getProperty("sequenceBit"));
    /**
     * 毫秒内自增位最大值
     */
    private long sequenceMax = -1 ^ (-1 << machineBit);

    /**
     * 机器码左移位数
     */
    private long sequenceShift = 0;
    /**
     * 机器码左移位数
     */
    private long machineShift = sequenceShift + sequenceBit;
    /**
     * 时间戳左移位数
     */
    private long timestampShift = machineShift + machineBit;

    /**
     * 机器码
     */
    private long machineCode;
    /**
     * 上次生产id的时间戳
     */
    private long lastTimestamp = -1L;
    /**
     * 毫秒自增位，初始为0
     */
    private long sequence = 0L;

    /**
     * 构造方法，初始化机器码
     */
    public IdSequenceServiceImpl() {
        // 得到机器码
        machineCode = Long.parseLong(settings.getProperty("machineCode"));
        // 机器码在范围外
        if (machineCode < 0L || machineCode > machineMax) {
            throw new IllegalArgumentException("machineCode < 0 or machineCode out of bound");
        }

    }

    /**
     * 实现获取时间相关的全局唯一序列号
     *
     * @return 返回全局唯一的id，如果机器调整了时间应重新获取
     */
    public synchronized long nextIdSequence() {
        long timestamp = getCurrentMillis();
        // 机器调整时间了
        if (timestamp < lastTimestamp) {
            return -1L;
        }
        //如果是同一时间生成的，则进行毫秒内序列递增
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMax;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = getNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 赋值上一个时间戳
        lastTimestamp = timestamp;

        return (timestamp - beginTimestamp) << timestampShift
                | machineCode << machineShift
                | sequence << sequenceShift;
    }

    /**
     * 获取当前毫秒
     *
     * @return 得到服务器当前毫秒
     */
    private long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long getNextMillis(long lastTimestamp) {
        long timestamp = getCurrentMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentMillis();
        }
        return timestamp;
    }


    public static void main (String [] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        context.start();

        IdSequenceServiceImpl idSequenceService = (IdSequenceServiceImpl) context.getBean("idSequenceServiceImpl");

        long idSequence = idSequenceService.nextIdSequence();

        System.out.println(idSequence);
    }
}



















