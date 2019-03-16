package com.pressanykeytoac.controller.tree;

import java.util.HashSet;
import java.util.Set;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/tree/group")
public class GroupUserController {
	
	@Autowired
	private IdentityService identityService;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String groupUserAdd(@RequestParam("users") String users,
								@RequestParam("groupID") String groupId,
								@RequestParam("groupName") String groupName) {
		String[] us = users.split(",");
		Set<String> set = new HashSet<String>();
		for(String tmp : us) {
			set.add(tmp);
		}
		Group group = identityService.newGroup(groupId);
		group.setName(groupName);
		group.setType("assignment");
		identityService.saveGroup(group);
		for(String userId : set) {
			identityService.createMembership(userId, group.getId());
		}
		
		return "1";
	}
}
