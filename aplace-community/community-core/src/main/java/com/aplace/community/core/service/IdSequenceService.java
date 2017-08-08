package com.aplace.community.core.service;

/**
 * 分布式序列生成器服务
 * @author apeace
 * @version 2017/8/4.
 */
public interface IdSequenceService {

    /**
     * 实现获取时间相关的全局唯一序列号
     *
     * @return 返回全局唯一的id，如果机器调整了时间应重新获取
     */
    long nextIdSequence();
}
