package com.pressanykeytoac.controller.callback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.interceptor.Command;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import me.kafeitu.demo.activiti.cmd.JumpActivityCmd;
import me.kafeitu.demo.activiti.util.UserUtil;

@Controller
@RequestMapping("/callback")
@Service
public class callbackController {
		@Autowired
		private ManagementService managementService;
		
		@Autowired
		private HistoryService historyService;
		
		 @ResponseBody
		 @RequestMapping(value = "/list", method = RequestMethod.GET)
		 public ModelAndView callBackUp(HttpServletRequest request){
//			 HistoryService historyService = activitiRule.getHistoryService();
			User user = UserUtil.getUserFromSession(request.getSession());
			String identity=user.getId();
			List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().involvedUser(identity).list();
			List<HistoricTaskInstance> callBackList = new ArrayList<HistoricTaskInstance>();;
			if (list != null && list.size() > 0) {
	          for (HistoricProcessInstance hpi: list) {
	        	  	if(hpi.getEndTime()==null)
	        	  	{
	        	  		List<HistoricTaskInstance> hislist = historyService.createHistoricTaskInstanceQuery().processInstanceId(hpi.getId()).list();
	        			int listSize=hislist.size();
	        			HistoricTaskInstance reCallNode=null;
	        			if(listSize>=2) reCallNode=hislist.get(listSize-2);
//		        			System.err.println(reCallNode.getId());
	        			if(reCallNode!=null && reCallNode.getAssignee().equals(identity)) {
	        				callBackList.add(reCallNode);
	        			}
	        	  	}
	           }
	       }
			ModelAndView mav = new ModelAndView("/WEB-INF/views/form/dynamic/callback.jsp");
			mav.addObject("tasks", callBackList);
			return mav;
		 }
		 
		 @ResponseBody
		 @RequestMapping(value = "/handle", method = RequestMethod.GET)
		 public String sendBackHandle(@RequestParam("aimNode") String aimNode, @RequestParam("nowNode") String nowNode,HttpServletRequest request)
		 {
//			 System.err.println(nowNode+"   "+aimNode);
//				return "2";
			User user = UserUtil.getUserFromSession(request.getSession());
			String identity=user.getId();
			try {
				List<HistoricTaskInstance> delList = historyService.createHistoricTaskInstanceQuery().processInstanceId(nowNode).list();
				int delListSize=delList.size();
				if(!delList.get(delListSize-2).getAssignee().equals(identity)) return "3";
				Command<Object> cmd = new JumpActivityCmd(nowNode, aimNode);
				managementService.executeCommand(cmd);
				historyService.deleteHistoricTaskInstance(delList.get(delListSize-2).getId());
				historyService.deleteHistoricTaskInstance(delList.get(delListSize-1).getId());
			} catch (Exception e) {
				System.err.println(e);
				return "2";
			}		
//			System.err.println("========================================================");
//			System.err.println("========================================================");
			return "1";
		 }
}
