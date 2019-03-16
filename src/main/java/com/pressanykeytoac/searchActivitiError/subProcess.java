package com.pressanykeytoac.searchActivitiError;

import java.util.List; 
import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


public class subProcess extends Father {
	
	 private String id;
	    private String name;
	    private List<startEvent> startEvent;
	    private List<endEvent> endEvent;
	    private List<userTask> userTask;
	    private List<sequenceFlow> sequenceFlow;
	    private List<exclusiveGateway> exclusiveGateway;
	    
	    public subProcess(){ }
	    public subProcess(String id,String name)
	    {
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
	    @XmlAttribute(name="isExecutable")
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    @XmlElement(name="startEvent")
	    public List<startEvent> getStartEvent()
	    {
	    	return startEvent;
	    }
	    public void setStartEvent(List<startEvent> startEvent)
	    {
	    	this.startEvent=startEvent;
	    }
	    @XmlElement(name="endEvent")
	    public List<endEvent> getEndEvent()
	    {
	    	return endEvent;
	    }
	    public void setEndEvent(List<endEvent> endEvent)
	    {
	    	this.endEvent=endEvent;
	    }
	    @XmlElement(name = "userTask")
	    public List<userTask> getUserTask() {
	    	         return userTask;
	    	     }
	   public void setUserTask(List<userTask> userTask) {
	    	         this.userTask = userTask;
	         }
	    @XmlElement(name = "sequenceFlow")
	   public List<sequenceFlow> getSequenceFlow() {
	   	         return sequenceFlow;
	   	     }
	  public void setSequenceFlow(List<sequenceFlow> sequenceFlow) {
	   	         this.sequenceFlow = sequenceFlow;
	        } 
	    @XmlElement(name = "exclusiveGateway")
	  public List<exclusiveGateway> getExclusiveGateway() {
	  	         return exclusiveGateway;
	  	     }
	  public void setExclusiveGateway(List<exclusiveGateway> exclusiveGateway) {
	  	         this.exclusiveGateway = exclusiveGateway;
	       } 
	   
	    @Override
	    public String toString() {
	        return "subProcess [Id=" + id + ", name=" + name + ",\n startEvent " + startEvent+",\n endEvent"+endEvent+","
	        		+ ",\n exclusiveGateway"+exclusiveGateway+",\n sequenceFlow "+sequenceFlow+"]";
	    }

}
