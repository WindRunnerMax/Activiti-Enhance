package com.pressanykeytoac.searchActivitiError;

import javax.xml.bind.annotation.XmlAttribute;

public class sequenceFlow extends Father{
	
	private String id;
	private String sourceRef;
	private String targetRef;
	//private int index;
	
	public sequenceFlow() { }
	public sequenceFlow(String id,String sourceRef,String targetRef) { 
		this.id=id;
		this.sourceRef=sourceRef;
		this.targetRef=targetRef;	
	}		
	@XmlAttribute(name="id") 
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @XmlAttribute(name="sourceRef") 
    public String getSourceRef()
    {
    	return sourceRef;
    }
    public void setSourceRef(String sourceRef)
    {
    	this.sourceRef=sourceRef;
    }
    @XmlAttribute(name="targetRef")
    public String getTargetRef()
    {
    	return targetRef;
    }
    public void setTargetRef(String targetRef)
    {
    	this.targetRef=targetRef;
    }
//    public int getIndex()
//    {
//    	return index;
//    }
//    public void setIndex(int index)
//    {
//    	this.index=index;
//    }
//    
    @Override
    public String toString()
    {
    	return "sequenceFlow [Id=" + id + ", sourceRef=" + sourceRef + ", targetRef=" + targetRef +"]";
    }
}
