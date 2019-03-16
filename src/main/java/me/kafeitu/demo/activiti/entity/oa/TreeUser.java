package me.kafeitu.demo.activiti.entity.oa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tree_user")
public class TreeUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String user_id;
	
	@Column
	private Long tree_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Long getTree_id() {
		return tree_id;
	}

	public void setTree_id(Long tree_id) {
		this.tree_id = tree_id;
	}

	@Override
	public String toString() {
		return "TreeUser [id=" + id + ", user_id=" + user_id + ", tree_id=" + tree_id + "]";
	}

	

	
}
