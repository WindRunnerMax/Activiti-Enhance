package com.pressanykeytoac.controller.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.kafeitu.demo.activiti.dao.TreeDAO;
import me.kafeitu.demo.activiti.entity.oa.Tree;

@Controller
@RequestMapping("/tree")
public class TreeController {
	@Autowired
	private TreeDAO treeDAO;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object treeList() {
		Map<String,Object> rt = new HashMap<String,Object>();
		List<Tree> ls = treeDAO.findAll();
		rt.put("data", ls);
		rt.put("count", ls.size());
		rt.put("code", 0);
		rt.put("msg", "ok");
		return rt;
	}
	
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String treeSave(@RequestParam("name") String name,
						@RequestParam("pid") Long pid) {
		Tree tree = new Tree();
		tree.setName(name);
		tree.setPid(pid);
		treeDAO.save(tree);
		return "1";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public String treeDelete(@RequestParam("id") Long id) {
		treeDAO.delete(id);
		
		return "1";
	}
}
