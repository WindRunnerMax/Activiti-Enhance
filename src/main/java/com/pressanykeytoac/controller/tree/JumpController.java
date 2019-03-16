package com.pressanykeytoac.controller.tree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tree/jump")
public class JumpController {
	
	@RequestMapping("/group")
	public ModelAndView groupJump() {
		return new ModelAndView("/WEB-INF/views/management/group-tree-list.jsp");
	}
	
	@RequestMapping("/user")
	public ModelAndView userJump() {
		return new ModelAndView("/WEB-INF/views/management/user-tree-list.jsp");
	}
}
