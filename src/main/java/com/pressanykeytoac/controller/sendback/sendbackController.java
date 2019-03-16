package com.pressanykeytoac.controller.sendback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import me.kafeitu.demo.activiti.cmd.*;

@Controller
@RequestMapping("/sendback")
@Service
public class sendbackController {
	
	@Autowired
	private ManagementService managementService;
	
	@Autowired
	private HistoryService historyService;
	
	 @ResponseBody
	 @RequestMapping(value = "/list", method = RequestMethod.GET)
	 public String getHistoryList(@RequestParam("IDNode") String IDNode, @RequestParam("nowNode") String nowNode)
	 {
		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(IDNode).list();
			int listSize=list.size();
			int i=0;
			JSONObject rule=new JSONObject();
			rule.put("code", 0);
			JSONArray array = new JSONArray();
			if (list != null && list.size() > 0) {
				for(HistoricTaskInstance hpi : list) {
		         	++i;
		         	if(i==listSize) break;
		         	JSONObject json = new JSONObject();
		         	json.put("id", hpi.getId());
		         	json.put("key", hpi.getTaskDefinitionKey());
		         	json.put("name", hpi.getName());
		         	array.put(json);
				}
			}
		     rule.put("data", array);
		     rule.put("count", i);
		     return rule.toString();
	 }
	 
	 @ResponseBody
	 @RequestMapping(value = "/handle", method = RequestMethod.GET)
	 public String sendBackHandle(@RequestParam("aimNode") String aimNode, @RequestParam("nowNode") String nowNode,@RequestParam("id") String id)
	 {
//		 System.err.println(nowNode+"   "+aimNode);
//			return "2";
		try {
			List<HistoricTaskInstance> delList = historyService.createHistoricTaskInstanceQuery().processInstanceId(nowNode).list();
			Command<Object> cmd = new JumpActivityCmd(nowNode, aimNode);
			managementService.executeCommand(cmd);
			int delListSize=delList.size();
			int flag=0;
			if(delList!=null && delListSize>0) {
				for(HistoricTaskInstance hpi : delList) {
					if(hpi.getId().equals(id)) flag=1;
					if(flag==1) historyService.deleteHistoricTaskInstance(hpi.getId());
				}
			}
		} catch (Exception e) {
			System.err.println(e);
			return "2";
		}
		return "1";
	 }
}
