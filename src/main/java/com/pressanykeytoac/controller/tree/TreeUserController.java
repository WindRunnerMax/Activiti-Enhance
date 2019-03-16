package com.pressanykeytoac.controller.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.kafeitu.demo.activiti.dao.TreeUserDAO;
import me.kafeitu.demo.activiti.entity.oa.TreeUser;

@Controller
@RequestMapping("tree/user")
public class TreeUserController {
	@Autowired TreeUserDAO treeUserDAO;
	@Autowired IdentityService identityService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object treeUserList(@RequestParam("comID") Long id) {
		List<TreeUser> ls = treeUserDAO.findAll();
		Map<String,Object> rt = new HashMap<String,Object>();
		rt.put("code", 0);
		rt.put("Msg", "ok");
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		for(TreeUser treeUser : ls) {
			if(treeUser.getTree_id() == id) {
				List<User> users = identityService.createUserQuery().userId(treeUser.getUser_id()).list();
				for(User user : users) {
					Map<String,String> tmp = new HashMap<String,String>();
					tmp.put("id", user.getId());
					tmp.put("name", user.getLastName()+" "+user.getFirstName());
					tmp.put("r_id", treeUser.getId().toString());
					data.add(tmp);
				}
			}
		}
		rt.put("data", data);
		rt.put("count", data.size());
		return rt;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String treeUserSave(@RequestParam("comID") Long tree_id,@RequestParam("userID") String user_id) {
		User user = identityService.createUserQuery().userId(user_id).singleResult();
		if(user == null) return "2";
		TreeUser treeUser = new TreeUser();
		treeUser.setTree_id(tree_id);
		treeUser.setUser_id(user_id);
		treeUserDAO.save(treeUser);
		
		return "1";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public String treeUserDelete(@RequestParam("r_id") Long r_id) {
		treeUserDAO.delete(r_id);
		
		return "1";
	}
}
