package com.zs.seckill.controller;

import com.zs.seckill.dto.Exposer;
import com.zs.seckill.dto.SeckillExecution;
import com.zs.seckill.enums.SeckillStateEnum;
import com.zs.seckill.exception.RepeatKillException;
import com.zs.seckill.exception.SeckillClosedException;
import com.zs.seckill.exception.SeckillException;
import com.zs.seckill.pojo.Seckill;
import com.zs.seckill.service.SeckillService;
import com.zs.seckill.utils.ExceptionUtil;
import com.zs.seckill.utils.SeckillResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: CHOSEN1
 * @Description:
 * @Date:Created in 20:41 2017/11/5
 * @Modified By:
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET )
    private String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",
            method = RequestMethod.GET)
    public String detail(@PathVariable(value = "seckillId")Long seckillId,Model model){
        if ( seckillId==null ){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getSingleSeckill(seckillId);
        if ( seckill==null ){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
                    method = RequestMethod.POST,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable(value = "seckillId")Long seckillId){
        SeckillResult<Exposer> result=null;
        try {
            Exposer exposer = seckillService.createSeckillAddress(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            e.getStackTrace();
            result=new SeckillResult<Exposer>(false,ExceptionUtil.getStackTrace(e));
        }
        return result;

    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable(value = "seckillId")Long seckillId,
                                                   @PathVariable(value = "md5")String md5 ,
                                                     @CookieValue(value = "killPhone")Long userPhone){

        if ( userPhone==null ){
            return new SeckillResult<SeckillExecution>(false,"请登录在秒杀");
        }
        SeckillExecution seckillExcution=null;
        SeckillResult<SeckillExecution> result=null;
        try{
            seckillExcution = seckillService.executeSeckill(seckillId,userPhone,md5);
            result= new SeckillResult<SeckillExecution>(true,seckillExcution);
        }catch (RepeatKillException e1) {
            seckillExcution= new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result= new SeckillResult<SeckillExecution>(true,seckillExcution);
        }catch (SeckillClosedException e2) {
            seckillExcution= new SeckillExecution(seckillId, SeckillStateEnum.END);
            result= new SeckillResult<SeckillExecution>(true,seckillExcution);
        }catch (SeckillException e3){
            seckillExcution= new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result= new SeckillResult<SeckillExecution>(true,seckillExcution);
        }catch (Exception e){
            e.getStackTrace();
            result= new SeckillResult<SeckillExecution>(true,e.getMessage());
        }
        return result;
}

    @RequestMapping(value ="/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> getTime(){
        Date date = new Date();
        return new SeckillResult<Long>(true,date.getTime());
    }
}