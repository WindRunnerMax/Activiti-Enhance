package com.pressanykeytoac.controller.turn;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/turn")
@Service
public class taskturnController {
	 
	 @Autowired
	 private TaskService taskService;
	 @Autowired
	 private IdentityService identityService;
	 
	 @ResponseBody
	 @RequestMapping(value = "/handle", method = RequestMethod.GET)
	 public String turnHandle(@RequestParam("taskID") String taskID,@RequestParam("ID") String ID)
	 {
		 try {
			 taskService.setAssignee(taskID, ID);
		} catch (Exception e) {
			// TODO: handle exception
			return "2";
		}
		 return "1";
	 }
	 
	 @ResponseBody
	 @RequestMapping(value = "/nowhandler", produces =  {"application/text;charset=UTF-8"}, method = RequestMethod.GET)
	 public String user(@RequestParam("procID") String procID)
	 {
		 String name="该任务为组任务";
		 try {
			 String nowhander=taskService.createTaskQuery().processInstanceId(procID).singleResult().getAssignee();
				User fullName=identityService.createUserQuery().userId(nowhander).singleResult();
				name="当前办理者: "+fullName.getLastName()+" "+fullName.getFirstName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return name;
	 }
	 
	 @ResponseBody
	 @RequestMapping(value = "/forcehandle",method = RequestMethod.GET)
	 public String forceturn(@RequestParam("procID") String procID,@RequestParam("ID") String ID)
	 {
		 try {
			 String taskID = taskService.createTaskQuery().processInstanceId(procID).singleResult().getId();
			 taskService.setAssignee(taskID, ID);
		} catch (Exception e) {
			// TODO: handle exception
			return "2";
		}
		 return "1";
	 }
}
