package com.pressanykeytoac.searchActivitiError;
import javax.xml.bind.annotation.XmlAttribute;
public class startEvent extends Father{
	    private String id;
	    private String initiator;
	   // private int index;  
	    
	    public startEvent() {}
	    public startEvent(String id,String initiator)
	    {
	    	this.id=id;
	    	this.initiator=initiator;
	    	
	    }
	    @XmlAttribute(name="id") 
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }
	    @XmlAttribute(name="initiator")
	    public String getInitiator()
	    {
	    	return initiator;
	    }
	    public void setInitiator(String initiator)
	    {
	    	this.initiator=initiator;
	    }
//	    public int getIndex()
//	    {
//	    	return index;
//	    }
//	    public void setIndex(int index)
//	    {
//	    	this.index=index;
//	    }
	    
	    @Override
	    public String toString() {
	        return "startEvent [id=" + id + ", initiator=" + initiator + "]";
	    }
}
