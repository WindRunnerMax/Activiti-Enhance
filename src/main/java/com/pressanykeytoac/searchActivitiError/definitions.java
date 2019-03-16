package com.pressanykeytoac.searchActivitiError;

//import java.util.List;

//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="definitions")
public class definitions {

	    private process process;
	    
	    //public definitions(){ }
	   
	   //@XmlElementWrapper(name = "process") 
	    @XmlElement(name="process") 
	    public process getProcess() {
	        return process;
	    }

	    public void setProcess(process process) {
	        this.process = process;
	    }
	    
	    @Override
	    public String toString() {
	    return "Process:"+process; 
	    }
}

