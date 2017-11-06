package com.zs.seckill.dto;

import com.zs.seckill.enums.SeckillStateEnum;
import com.zs.seckill.pojo.SuccessKilled;

/**
 * @Author: CHOSEN1
 * @Description:执行秒杀类
 * @Date:Created in 13:12 2017/11/5
 * @Modified By:
 */
public class SeckillExecution {
    private long seckillId;
    //秒杀执行结果
    private int state;
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;
    //秒杀成功
    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }
   //秒杀失败
    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
