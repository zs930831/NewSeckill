package com.zs.seckill.service.impl;

import com.zs.seckill.dto.Exposer;
import com.zs.seckill.dto.SeckillExecution;
import com.zs.seckill.enums.SeckillStateEnum;
import com.zs.seckill.exception.RepeatKillException;
import com.zs.seckill.exception.SeckillClosedException;
import com.zs.seckill.exception.SeckillException;
import com.zs.seckill.mapper.SeckillMapper;
import com.zs.seckill.mapper.SuccessKilledMapper;
import com.zs.seckill.pojo.Seckill;
import com.zs.seckill.pojo.SeckillExample;
import com.zs.seckill.pojo.SuccessKilled;
import com.zs.seckill.pojo.SuccessKilledExample;
import com.zs.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService{
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SuccessKilledMapper successKilledMapper;
    private static final String SALT="qW3124!@@$#%";
    @Override
    public List<Seckill> getSeckillList() {
        SeckillExample seckillExample = new SeckillExample();
        List<Seckill> list = seckillMapper.selectByExample(seckillExample);
        if ( list!=null&&list.size()>0 ){
            return list;
        }
        return null;
    }

    @Override
    public Seckill getSingleSeckill(long seckillId) {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        if ( seckill!=null ){
            return seckill;
        }
        return null;
    }

    @Override
    public Exposer createSeckillAddress(long seckillId) {
        Seckill seckill = seckillMapper.selectByPrimaryKey(seckillId);
        if ( seckill==null ){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime=new Date();
        if ( nowTime.getTime()<startTime.getTime()
                || nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),
                    startTime.getTime(),endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    private String getMd5(long seckillId){
        String s = seckillId + "/" + SALT;
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(s.getBytes());
        return md5DigestAsHex;
    }
    @Override
    @Transactional
    /**
     *@Author:ZhangShuai
     *@Description:基于注解的事务。保证事务方法的执行时间尽量短，
     * 不要穿插其他的网络操作。
     *@Date: 16:58 2017/11/5
     *@Modify Info:
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillClosedException, RepeatKillException {

        if ( null == md5 || !md5.equals(getMd5(seckillId))){
            throw new SeckillException("data has been rewrite!");
        }
        //执行秒杀逻辑：减少库存+记录购买行为
        Date killTime = new Date();
        try {
            //自己写的mapper
             int number = seckillMapper.updateNumber(seckillId, killTime);
            if ( number<=0 ){
                //库存小于0，秒杀结束
                //看是否该明细被重复插入，即用户是否重复秒杀
                throw new RepeatKillException("Seckill is repeating!");
            }else {
                SuccessKilled sc=new SuccessKilled();
                sc.setState((byte)0);
                sc.setSeckillId(seckillId);
                sc.setCreateTime(killTime);
                sc.setUserPhone(userPhone);
                int i = successKilledMapper.insert(sc);
                System.out.println("i:"+i);
                if ( i<=0){
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new SeckillClosedException("Seckill is over!");
                }else {
                    //秒杀成功
                    SuccessKilledExample example = new SuccessKilledExample();
                    SuccessKilledExample.Criteria criteria = example.createCriteria();
                    criteria.andSeckillIdEqualTo(seckillId);
                    List<SuccessKilled> list = successKilledMapper.selectByExample(example);
                    if ( list!=null&&list.size()>0 ){
                        SuccessKilled successKilled = list.get(0);
                        return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                    }else {
                        throw new RepeatKillException("Seckill is repeating!");
                    }

                }
            }
        }catch (RepeatKillException e1) {
            throw e1;
        }catch (SeckillClosedException e2) {
            throw e2;
        }catch (SeckillException e3){
            throw e3;
        }
        catch (Exception e) {
            throw new SeckillException("seckill error"+e.getStackTrace());
        }
       // return null;
    }
}
