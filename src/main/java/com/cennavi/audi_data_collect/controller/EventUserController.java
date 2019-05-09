package com.cennavi.audi_data_collect.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cennavi.audi_data_collect.bean.CVSBean;
import com.cennavi.audi_data_collect.service.EventUserService;
//import com.cennavi.audi_data_collect.service.EventUserService;
import com.cennavi.audi_data_collect.util.CSVUtil;

@RestController
@RequestMapping("/eventUser")
public class EventUserController {
	
	 @Autowired
	    private EventUserService eventUserService;
	 
	 private static SimpleDateFormat inSDF = new SimpleDateFormat("mm/dd/yyyy");
	  private static SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");
	 
	//验证用户信息
	@RequestMapping(value = "/checkUser")
	public boolean checkUser(@RequestBody String JsonDate){
		  JSONObject  json = JSONObject.parseObject(JsonDate);
		return	eventUserService.check(json.getString("name"),json.getString("password"));
	}
	
	 //类型列表
	@RequestMapping(value = "/queryEventType")
	public Object queryEventType(){
		return	eventUserService.queryEventType();
	}
	
	
	//直方图
		@RequestMapping(value = "/queryHistogram")
		public Object queryHistogram(@RequestBody  String queryHistogramDate){
			
			String startTmie="",endTime="",startTimeFrames="",endTimeFrames="", dataLists="";
			  JSONObject  json = JSONObject.parseObject(queryHistogramDate);
			  if(json.getString("isContinuous").equals("true")) {
				   JSONArray str = json.getJSONArray("dataList");
				   Map<Integer, String> map=new HashMap<Integer,String>();
				   int i=0;
				   for (Object obj : str) {
					   map.put(i, obj.toString());
					   i++;
				}
				   if(!map.get(0).equals("") && map.get(0)!=null) {
					   startTmie=map.get(0);
				   }
				   if(!map.get(1).equals("") && map.get(1)!=null) {
					   endTime=map.get(1);
				   }
				  
			  }else {
				  JSONArray dataList = json.getJSONArray("dataList");
				   String data=null;
				   String endData="";
				   for (Object obj : dataList) {
					   data ="'" +obj + "',";
					   endData += data;
				}
				   dataLists= endData.substring(0,endData.length()-1);
			  }
			   
			   JSONArray timeFrames = json.getJSONArray("timeFrame");
			   Map<Integer, String> map2=new HashMap<Integer,String>();
			   int n=0;
			   for (Object obj : timeFrames) {
				   map2.put(n, obj.toString());
				   n++;
				
			}
			   if(!map2.get(0).equals("") && map2.get(0)!=null) {
				   startTimeFrames=map2.get(0);
			   }
			   if(!map2.get(1).equals("") && map2.get(1)!=null) {
				   endTimeFrames=map2.get(1);
			   }
			  String k2=null;
			   String endstr2="";
			  JSONArray str3 = json.getJSONArray("roadSecList");
			  for (Object obj : str3) {
				    k2 =obj + ",";
				   endstr2 += k2;
			}
			  String  roadSecList="";
			  if(endstr2.length()>0) {
				    roadSecList= endstr2.substring(0,endstr2.length()-1);
			  }
			  
			  
//			  json.getString("isContinuous");
			return	eventUserService.queryHistogram(json.getString("city"),json.getString("eventType"),startTmie,endTime,roadSecList,startTimeFrames,endTimeFrames,json.getString("isContinuous"),dataLists);
		
		}
		
	
     //数据统计
	@RequestMapping(value = "/dataStatistics")
	public Object dataStatistics(@RequestBody String dataStatisticsDate){
		
		String startTmie="",endTime="",startTimeFrames="",endTimeFrames="",  dataLists="";
		  JSONObject  json = JSONObject.parseObject(dataStatisticsDate);
		  if(json.getString("isContinuous").equals("true")) {
			   JSONArray str = json.getJSONArray("dataList");
			   Map<Integer, String> map=new HashMap<Integer,String>();
			   int i=0;
			   for (Object obj : str) {
				   map.put(i, obj.toString());
				   i++;
			}
			   if(!map.get(0).equals("") && map.get(0)!=null) {
				   startTmie=map.get(0);
			   }
			   if(!map.get(1).equals("") && map.get(1)!=null) {
				   endTime=map.get(1);
			   }
			  
		  }else {
			  JSONArray dataList = json.getJSONArray("dataList");
			   String data=null;
			   String endData="";
			   for (Object obj : dataList) {
				   data ="'" +obj + "',";
				   endData += data;
			}
			   dataLists= endData.substring(0,endData.length()-1);
		  }
		
		   
		   JSONArray timeFrames = json.getJSONArray("timeFrame");
		   
		   Map<Integer, String> map2=new HashMap<Integer,String>();
		   int n=0;
		   for (Object obj : timeFrames) {
			   map2.put(n, obj.toString());
			   n++;
			
		}
		   if(!map2.get(0).equals("") && map2.get(0)!=null) {
			   startTimeFrames=map2.get(0);
		   }
		   if(!map2.get(1).equals("") && map2.get(1)!=null) {
			   endTimeFrames=map2.get(1);
		   }
		   JSONArray str2 = json.getJSONArray("eventsList");
		   String kk=null;
		   String endstr="";
		   for (Object obj : str2) {
			   kk ="'" +obj + "',";
			   endstr += kk;
		}
		  String  eventsList= endstr.substring(0,endstr.length()-1);
		  String k2=null;
		   String endstr2="";
		  JSONArray str3 = json.getJSONArray("roadSecList");
		  for (Object obj : str3) {
			  k2 =obj + ",";
			   endstr2 += k2;
		}
		  String  roadSecList="";
		  if(endstr2.length()>0) {
			    roadSecList= endstr2.substring(0,endstr2.length()-1);
		  }
		 
		return	eventUserService.dataStatistics(json.getString("city"),eventsList,startTmie,endTime,roadSecList,startTimeFrames,endTimeFrames,json.getString("sort"),json.getString("isContinuous"),dataLists);
				
	}
	
	//折线图
		@RequestMapping(value = "/brokenLine")
		public Object brokenLine(@RequestBody String brokenLineData){
			
			String startTmie="",endTime="",startTimeFrames="",endTimeFrames="",  dataLists="";
			
			  JSONObject  json = JSONObject.parseObject(brokenLineData);
			 
			  if(json.getString("isContinuous").equals("true")) {
				   JSONArray str = json.getJSONArray("dataList");
				   Map<Integer, String> map=new HashMap<Integer,String>();
				   int i=0;
				   for (Object obj : str) {
					   map.put(i, obj.toString());
					   i++;
				}
				   if(!map.get(0).equals("") && map.get(0)!=null) {
					   startTmie=map.get(0);
				   }
				   if(!map.get(1).equals("") && map.get(1)!=null) {
					   endTime=map.get(1);
				   }
				  
			  }else {
				  JSONArray dataList = json.getJSONArray("dataList");
				   String data=null;
				   String endData="";
				   for (Object obj : dataList) {
					   data ="'" +obj + "',";
					   endData += data;
				}
				   dataLists= endData.substring(0,endData.length()-1);
			  }
			   
			   JSONArray timeFrames = json.getJSONArray("timeFrame");
			   Map<Integer, String> map2=new HashMap<Integer,String>();
			   int n=0;
			   for (Object obj : timeFrames) {
				   map2.put(n, obj.toString());
				   n++;
				
			}
			   if(!map2.get(0).equals("") && map2.get(0)!=null) {
				   startTimeFrames=map2.get(0);
			   }
			   if(!map2.get(1).equals("") && map2.get(1)!=null) {
				   endTimeFrames=map2.get(1);
			   }
			  String k2=null;
			   String endstr2="";
			  JSONArray str3 = json.getJSONArray("roadSecList");
			  for (Object obj : str3) {
				  k2 =obj + ",";
				   endstr2 += k2;
			}
			  String  roadSecList="";
			  if(endstr2.length()>0) {
				    roadSecList= endstr2.substring(0,endstr2.length()-1);
			  }
			   
			return	eventUserService.brokenLine(json.getString("city"),json.getString("eventType"),startTmie,endTime,roadSecList,startTimeFrames,endTimeFrames,json.getString("isContinuous"),dataLists);
		}
		
		//csv
		@RequestMapping(value = "/exportCsv")
		public Object exportCsv(@RequestBody String exportData,HttpServletResponse response) throws ParseException {

			String startTmie="",endTime="",startTimeFrames="",endTimeFrames="",  dataLists="";
			
			  JSONObject  json = JSONObject.parseObject(exportData);
			 
			  if(json.getString("isContinuous").equals("true")) {
				   JSONArray str = json.getJSONArray("dataList");
				   Map<Integer, String> map=new HashMap<Integer,String>();
				   int i=0;
				   for (Object obj : str) {
					   map.put(i, obj.toString());
					   i++;
				}
				   if(!map.get(0).equals("") && map.get(0)!=null) {
					   startTmie=map.get(0);
				   }
				   if(!map.get(1).equals("") && map.get(1)!=null) {
					   endTime=map.get(1);
				   }
				  
			  }else {
				  JSONArray dataList = json.getJSONArray("dataList");
				   String data=null;
				   String endData="";
				   for (Object obj : dataList) {
					   data ="'" +obj + "',";
					   endData += data;
				}
				   dataLists= endData.substring(0,endData.length()-1);
			  }
			   
			   JSONArray timeFrames = json.getJSONArray("timeFrame");
			   Map<Integer, String> map2=new HashMap<Integer,String>();
			   int n=0;
			   for (Object obj : timeFrames) {
				   map2.put(n, obj.toString());
				   n++;
				
			}
			   if(!map2.get(0).equals("") && map2.get(0)!=null) {
				   startTimeFrames=map2.get(0);
			   }
			   if(!map2.get(1).equals("") && map2.get(1)!=null) {
				   endTimeFrames=map2.get(1);
			   }
			   JSONArray str2 = json.getJSONArray("eventsList");
			   String kk=null;
			   String endstr="";
			   for (Object obj : str2) {
				   kk ="'" +obj + "',";
				   endstr += kk;
			}
			  String  eventsList= endstr.substring(0,endstr.length()-1);
			  String k2=null;
			   String endstr2="";
			  JSONArray str3 = json.getJSONArray("roadSecList");
			  for (Object obj : str3) {
				  k2 =obj + ",";
				   endstr2 += k2;
			}
			  String  roadSecList="";
			  if(endstr2.length()>0) {
				    roadSecList= endstr2.substring(0,endstr2.length()-1);
			  }
			  List<CVSBean> list=	eventUserService.exportCsv(json.getString("city"),eventsList,startTmie,endTime,roadSecList,startTimeFrames,endTimeFrames,json.getString("isContinuous"),dataLists);
	       

		        HashMap map = new LinkedHashMap();
		        map.put("1", "event_id");
		        map.put("2", "type_name");
		        map.put("3", "road_name");
		        map.put("4", "date");
		        map.put("5", "time");
//			 List<CVSBean> list=	eventUserService.exportTestCsv();
			
		        String fileds[] = new String[] { "event_id", "type_name","road_name","date","time" };
		        try {
					CSVUtil.exportFile(response, map, list, fileds);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//直接调用

            
            
	        return null;
	    }
		@RequestMapping(value = "/exportTestCsv")
		public Object exportTestCsv(HttpServletResponse response) throws ParseException {
			HashMap map = new LinkedHashMap();
	        map.put("1", "event_id");
	        map.put("2", "type_name");
	        map.put("3", "road_name");
	        map.put("4", "date");
	        map.put("5", "time");
		 List<CVSBean> list=	eventUserService.exportTestCsv();
		
	        String fileds[] = new String[] { "event_id", "type_name","road_name","date","time" };
	        try {
				CSVUtil.exportFile(response, map, list, fileds);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//直接调用
			
			return null;
		}
}