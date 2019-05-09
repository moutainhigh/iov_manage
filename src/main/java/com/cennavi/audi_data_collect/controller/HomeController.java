package com.cennavi.audi_data_collect.controller;


import com.cennavi.audi_data_collect.bean.ParamsBean;
import com.cennavi.audi_data_collect.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cennavi on 2019/4/29.
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @ResponseBody
    @RequestMapping("/getRoad")
    public List<Map<String,Object>> getRoad(){
        Map<String,Object> state = new HashMap<>();
        try {
            List<Map<String,Object>> list = homeService.getRoad();

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回全部路段的id
     */
    @RequestMapping(value = "/getSegmentId/{x}/{y}/{z}", produces = "application/x-protobuf")
    public String getSegmentId(@PathVariable("x") Integer x,@PathVariable("y") Integer y,@PathVariable("z") Integer z, HttpServletResponse response){
        try{
            byte[] result = homeService.getSegmentId(x,y,z);
            if(result != null && result.length > 0){
               response.getOutputStream().write(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 行人事件热力图
     */
    @RequestMapping(value = "/getVRUEvent/{x}/{y}/{z}/{sDate}/{eDate}/{dates}/{sTime}/{eTime}/{orderType}/{road}", produces = "application/x-protobuf")
    public String getVRUEvent(@PathVariable("x") int x , @PathVariable("y") int y , @PathVariable("z") int z ,
                           @PathVariable("sDate") String sDate,@PathVariable("eDate") String eDate,@PathVariable("dates") String dates,@PathVariable("sTime") String sTime,
                           @PathVariable("eTime") String eTime,@PathVariable("orderType") int orderType,@PathVariable("road") String road, HttpServletResponse response){
        try{
            byte[] result = homeService.getVRUEvent(x,y,z,sDate,eDate,dates,sTime,eTime,orderType,road);
            if(result != null && result.length > 0){
                response.getOutputStream().write(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 选择某一个路段查看信息
     */
    @ResponseBody
    @RequestMapping("/getPointInfo")
//    public List<Map<String,Object>> getPointInfo(Integer id,String[] dataList,String[] timeFrame,String isContinuous){
    public List<Map<String,Object>> getPointInfo(@RequestBody ParamsBean paramsBean){
        Map<String,Object> state = new HashMap<>();
        //Map<String,Object> info = new HashMap<>();
        try {
            List<Map<String,Object>> list = homeService.getPointInfo(paramsBean);

            state.put("state",200);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        state.put("state",500);
        return null;
    }

}