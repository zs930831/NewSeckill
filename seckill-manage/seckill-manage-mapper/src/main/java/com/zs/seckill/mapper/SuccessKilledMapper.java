package com.zs.seckill.mapper;

import com.zs.seckill.pojo.SuccessKilled;
import com.zs.seckill.pojo.SuccessKilledExample;
import com.zs.seckill.pojo.SuccessKilledKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuccessKilledMapper {
    int countByExample(SuccessKilledExample example);

    int deleteByExample(SuccessKilledExample example);

    int deleteByPrimaryKey(SuccessKilledKey key);

    int insert(SuccessKilled record);

    int insertSelective(SuccessKilled record);

    List<SuccessKilled> selectByExample(SuccessKilledExample example);

    SuccessKilled selectByPrimaryKey(SuccessKilledKey key);

    int updateByExampleSelective(@Param("record") SuccessKilled record, @Param("example") SuccessKilledExample example);

    int updateByExample(@Param("record") SuccessKilled record, @Param("example") SuccessKilledExample example);

    int updateByPrimaryKeySelective(SuccessKilled record);

    int updateByPrimaryKey(SuccessKilled record);
}