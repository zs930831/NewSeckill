package com.zs.seckill.exception;

/**
 * @Author: CHOSEN1
 * @Description:重复秒杀异常
 * @Date:Created in 13:20 2017/11/5
 * @Modified By:
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
