package com.zs.seckill.exception;

/**
 * @Author: CHOSEN1
 * @Description:秒杀关闭异常
 * @Date:Created in 13:23 2017/11/5
 * @Modified By:
 */
public class SeckillClosedException extends SeckillException{
    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
