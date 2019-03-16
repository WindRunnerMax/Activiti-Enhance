package me.kafeitu.demo.activiti.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import me.kafeitu.demo.activiti.entity.oa.Tree;

@Component
public interface TreeDAO extends CrudRepository<Tree, Long> {
	Tree save(Tree tree);
	List<Tree> findAll();
	void delete(Long id);
}
