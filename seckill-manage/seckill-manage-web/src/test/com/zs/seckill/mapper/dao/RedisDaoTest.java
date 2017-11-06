package com.zs.seckill.mapper.dao;

import com.zs.seckill.mapper.SeckillMapper;
import com.zs.seckill.pojo.Seckill;
import com.zs.seckill.pojo.SeckillExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: CHOSEN1
 * @Description:
 * @Date:Created in 19:43 2017/11/6
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-dao.xml"})
public class RedisDaoTest {
    private long seckillId=1001;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillMapper seckillMapper;
    @Test
    public void testSeckill() throws Exception {
        //get and put
        Seckill seckill=redisDao.getSeckill(seckillId);
        if ( seckill==null ){
            //没取到从数据库里面那
            Seckill seckill1 = seckillMapper.selectByPrimaryKey(seckillId);
            if ( seckill1!=null ){
                //放入redis
                String s = redisDao.setSeckill(seckill1);
                System.out.println(s);
                Seckill seckill2 = redisDao.getSeckill(seckillId);
                System.out.println(seckill2);

            }
        }else {
            System.out.println(seckill);
        }
    }
}