package com.pressanykeytoac.searchActivitiError;

import javax.xml.bind.annotation.XmlAttribute;

public class exclusiveGateway extends Father{

	private String id;
	private String name;
	
	public exclusiveGateway() { }
	public exclusiveGateway(String id,String name) { 
		this.id=id;
		this.name=name;
		
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
    public String toString() {
        return "exclusiveGateway [Id=" + id + ", Name=" + name + "]";
    }
}
