package com.zs.seckill.mapper.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.zs.seckill.pojo.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: CHOSEN1
 * @Description:
 * @Date:Created in 18:36 2017/11/6
 * @Modified By:
 */
public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;


    public RedisDao(String ip,int port) {
        jedisPool=new JedisPool(ip,port);
    }
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
    /**
     *@Author:ZhangShuai
     *@Description:相当于反序列化
     *@Date: 19:24 2017/11/6
     *@Modify Info:
     */
    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key="seckill"+seckillId;
                //get->byte[]->反序列化->Object(Seckill)
                byte[] bytes = jedis.get(key.getBytes());
                if ( bytes!=null ){
                    //用protostuff创建一个空对象用来接收反序列化的对象。
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return null;
    }
    /**
     *@Author:ZhangShuai
     *@Description:相当于序列化
     *@Date: 19:24 2017/11/6
     *@Modify Info:
     */
    public String setSeckill(Seckill seckill){
        //set Object(Seckill)->Serilizable->bytes[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key="seckill"+seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String result = jedis.setex(key.getBytes(), 60 * 60, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
           logger.info(e.getMessage(),e);
        }
        return null;
    }
}
