package com.zs.seckill.exception;

/**
 * @Author: CHOSEN1
 * @Description:所有秒杀相关的业务异常
 * @Date:Created in 13:24 2017/11/5
 * @Modified By:
 */
public class SeckillException extends  RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
