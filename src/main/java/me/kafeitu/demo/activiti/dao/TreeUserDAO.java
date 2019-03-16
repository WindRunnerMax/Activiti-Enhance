package me.kafeitu.demo.activiti.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import me.kafeitu.demo.activiti.entity.oa.TreeUser;

@Component
public interface TreeUserDAO extends CrudRepository<TreeUser, Long> {
	TreeUser save(TreeUser treeUser);
	List<TreeUser> findAll();
	void delete(Long id);
}
