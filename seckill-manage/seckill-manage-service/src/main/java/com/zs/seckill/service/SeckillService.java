package com.zs.seckill.service;

import com.zs.seckill.dto.Exposer;
import com.zs.seckill.dto.SeckillExecution;
import com.zs.seckill.exception.RepeatKillException;
import com.zs.seckill.exception.SeckillClosedException;
import com.zs.seckill.exception.SeckillException;
import com.zs.seckill.pojo.Seckill;

import java.util.List;

/**
 * 站在使用者的角度设计接口,不要太在乎具体的实现
 */
public interface SeckillService {
    /**
     *@Author:ZhangShuai
     *@Description:获取所有的秒杀列表
     *@Date: 12:59 2017/11/5
     */
    List<Seckill> getSeckillList();
    /**
     *@Author:ZhangShuai
     *@Description:得到一个秒杀记录
     *@Date: 12:59 2017/11/5
     */
    Seckill getSingleSeckill(long seckillId);
    /**
     *@Author:ZhangShuai
     *@Description:秒杀开启时候发布地址，否则显示系统时间和秒杀时间
     *@Date: 13:00 2017/11/5
     *@Modify Info:
     */
    Exposer createSeckillAddress(long seckillId);
    /**
     *@Author:ZhangShuai
     *@Description:执行秒杀
     *@Date: 13:26 2017/11/5
     *@Modify Info:
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
             throws SeckillException,SeckillClosedException,RepeatKillException;
}
