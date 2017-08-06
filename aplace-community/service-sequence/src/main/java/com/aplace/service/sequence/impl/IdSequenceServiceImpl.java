package com.aplace.service.sequence.impl;

import com.aplace.community.core.service.IdSequenceService;

/**
 * 实现获取时间相关的全局唯一序列号服务
 * @author apeace
 * @version 2017/8/4.
 */
public class IdSequenceServiceImpl implements IdSequenceService{

    /**
     * 实现获取时间相关的全局唯一序列号
     *
     * @param machineId 序列号首，区别哪台机器的序列号
     * @return
     */
    public String nextIdSequence(String machineId) {
        return machineId+" test;";
    }
}
