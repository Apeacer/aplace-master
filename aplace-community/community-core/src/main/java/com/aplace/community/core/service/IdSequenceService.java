package com.aplace.community.core.service;

/**
 * 分布式序列生成器服务
 * @author apeace
 * @version 2017/8/4.
 */
public interface IdSequenceService {

    /**
     * 获得一个时间相关的序列号
     *
     * @param machineId 序列号首，区别哪台机器的序列号
     * @return 结果
     */
    String nextIdSequence(String machineId);
}
