package com.pressanykeytoac.searchActivitiError;

import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlElementWrapper;
//import javax.xml.bind.annotation.XmlRootElement;
//@XmlRootElement(name = "userTask")
public class userTask extends Father{
	private String id;
	private String name;
	//private int index;
	//private String assignee;
	//还要加上一个表单对象，然后将表单对象的实例放到一个arraylist里面。
  	//这个先空着先把可以用图判断出来的错误解决了。
	
	public userTask() {
   }

    public userTask(String id, String name) {
        this.id = id;
        this.name = name;
        //this.assignee = assignee;
    }
  
    @XmlAttribute(name="id") 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
//    public int getIndex()
//    {
//    	return index;
//    }
//    public void setIndex(int index)
//    {
//    	this.index=index;
//    }
    @Override
    public String toString() {
        return "UserTask [userId=" + id + ", userName=" + name + "]";
    }
	
	
}
