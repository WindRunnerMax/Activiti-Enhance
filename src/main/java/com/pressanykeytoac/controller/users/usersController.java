package com.pressanykeytoac.controller.users;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
@Service
public class usersController {
	
	@Autowired
	private IdentityService identityService;
	
	@RequestMapping(value = "/load")
	@ResponseBody
	public String getUsers() {
		List<User> users = identityService.createUserQuery().list();
		JSONObject rule=new JSONObject();
		rule.put("code", 0);
		JSONArray array = new JSONArray();
		for(User user : users) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getLastName()+" "+user.getFirstName());
			array.put(json);
		}
		rule.put("data", array);
		rule.put("count", identityService.createUserQuery().count());
		return rule.toString();
	}
}
