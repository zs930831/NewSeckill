package com.zs.seckill.service.impl;

import com.zs.seckill.pojo.Seckill;
import com.zs.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;



/**
 * @Author: CHOSEN1
 * @Description:
 * @Date:Created in 18:03 2017/11/5
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/applicationContext-dao.xml",
        "classpath:spring/applicationContext-trans.xml" })
public class SeckillServiceImplTest {
    private final org.slf4j.Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList);
        logger.info("sclist={ }",seckillList);
    }

    @Test
    public void getSingleSeckill() throws Exception {
        Seckill singleSeckill = seckillService.getSingleSeckill(1000);
        logger.info("ss={ }",singleSeckill);
    }

    @Test
    public void createSeckillAddress() throws Exception {
    }

    @Test
    public void executeSeckill() throws Exception {
    }

}