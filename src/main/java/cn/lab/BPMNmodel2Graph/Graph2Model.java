package cn.lab.BPMNmodel2Graph;


import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.dom4j.Document;  
import org.dom4j.DocumentHelper;  
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.lab.graphpack.Graph;
import cn.lab.graphpack.GraphEdge;
import cn.lab.graphpack.GraphNode; 

public class Graph2Model {

	public String graph2Model(Graph senModel1){	
	//DocumentHelper提供了创建Document对象的方法  
	Document document = DocumentHelper.createDocument();  
	//添加节点信息  
	Element rootElement = document.addElement("definitions","http://www.omg.org/spec/BPMN/20100524/MODEL")
			.addNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance")
			.addNamespace("xsd","http://www.w3.org/2001/XMLSchema")
			.addNamespace("activiti","http://activiti.org/bpmn")
			.addNamespace("bpmndi","http://www.omg.org/spec/BPMN/20100524/DI")
			.addNamespace("omgdc", "http://www.omg.org/spec/DD/20100524/DC" )
			.addNamespace("omgdi", "http://www.omg.org/spec/DD/20100524/DI")
			.addAttribute("typeLanguage", "http://www.w3.org/2001/XMLSchema")
			.addAttribute("expressionLanguage", "http://www.w3.org/1999/XPath")
			.addAttribute("targetNamespace", "http://www.activiti.org/processdef");
	 Element process=rootElement.addElement("process")
			 .addAttribute("isExecutable", "true")
			 .addAttribute("id", "process")
			 .addAttribute("name", "wfbnb");
	 for(GraphNode gn: senModel1.getAllGraphNode()){
	   	//System.out.println(gn.getNodeText());
	   	if(gn.getNodeText().equals("开始"))
	   	{
	   		Element startEvent=process.addElement("startEvent")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
	   		        startEvent.addAttribute("name",gn.getNodeText());
	   	}	
	   	else if(gn.getNodeText().equals("流程/结束"))
	   	{
	   		Element endEvent=process.addElement("endEvent")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
	   		      
	   		     endEvent.addAttribute("name","结束");
	   		
		}else if(gn.getNodeText().equals("选择"))
		{
			Element exclusiveGateway=process.addElement("exclusiveGateway")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
		}
		else if(gn.getNodeText().equals("并行"))
		{
			Element parallelGateway=process.addElement("parallelGateway")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
		}
		else if(gn.getNodeText().equals("返回"))
		{
			Element parallelGateway=process.addElement("exclusiveGateway")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
		}
		else if(gn.getNodeText().equals("条件"))
		{
			Element exclusiveGateway=process.addElement("exclusiveGateway")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
		}
	   	else {
	   		Element userTask=process.addElement("userTask")
	   				.addAttribute("id", "SID-"+gn.getNodeID());
	   		        userTask.addAttribute("name",gn.getNodeText());
	   		        
		}
	   }
	   for(GraphEdge ge: senModel1.getAllGraphEdge())
	   {
		   Element sequenceFlow=process.addElement("sequenceFlow")
				   .addAttribute("sourceRef","SID-"+ge.getStartNodeID())
				   .addAttribute("targetRef","SID-"+ge.getEndNodeID())
	   				.addAttribute("id", "SID-"+ge.getEdgeID())
	   				.addAttribute("name", ""+ge.getEdgeString());
	   }
		Element BPMNDiagram=rootElement.addElement("bpmndi:BPMNDiagram")
				   .addAttribute("id", "BPMNDiagram_process");
	    Element BPMNPlane=BPMNDiagram.addElement("bpmndi:BPMNPlane")
	    		.addAttribute("bpmnElement","process")
	    		.addAttribute("id","BPMNPlane_process");
	//这里可以继续添加子节点，也可以指定内容  
	//开始结点大小是30 30，排他和并发都是40 40，其他结点都是60 80 
	 //初始位置是30 150
	//初始位置是30 ，每个增量是120.
	    int x=30;
	    int y=105;
	    int flagEnd = 0;
	    int flagCondition=0;
	    int flag=0;
	    int flag1=2;
	    int flagReturn=1;
	    for(GraphNode gn: senModel1.getAllGraphNode()) {
	       	//System.out.println(gr.getNodeText());            	
	   	if(gn.getNodeText().equals("开始"))
	       	{
	       		Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", "115.0");
	       		     x+=120;
	       	}	
	       	else if(gn.getNodeText().equals("流程/结束"))
	       	{
	       		Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", "115.0");
	       		        flagEnd=gn.getNodeID();
	       		   
			}else if(gn.getNodeText().equals("选择"))
			{
				Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", "115.0");
	       		        flag++;
	       		        if(flag==1)
	       		     x+=260;
	       		     
	       		
				
			}
			else if(gn.getNodeText().equals("并行"))
			{
				Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", "115.0");
	       		        flag++;
	       		        if(flag==1)
	       		     x+=260;
			}
			else if(gn.getNodeText().equals("条件"))
			{
				Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", "115.0");
	       		        flagCondition++;
	       		       
			}
			else if(gn.getNodeText().equals("返回"))
			{
				Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
				if(flagReturn==1)
				{ shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "30.0")
	       		        .addAttribute("width", "30.0")
	       		        .addAttribute("x", ""+(x-90))
	       		        .addAttribute("y", "230.0");
				flagReturn++;
				}
				else if(flagReturn==2)
				{
					shape.addElement("omgdc:Bounds")
	   		        .addAttribute("height", "30.0")
	   		        .addAttribute("width", "30.0")
	   		        .addAttribute("x", ""+(x-90))
	   		        .addAttribute("y", "340.0");
					flagReturn=1;
					x+=120;   
				}
			  
			}
	       	else {
	       		if(flag==2)
	       		{
	       			if(flag1==2)
	       			{
	       				x-=150;
	       				y+=50;
	       				flag1--;
	       			}
	       			else if(flag1==1)
	       			{
	       				y-=100;
	       				flag1--;
	       			}
	       		
	       		}
	       		if(flagCondition==1)
	       		{ 
	   		        x+=150;
	       			y-=50;
	       		}   		
	       	else if(flagCondition==2)
	       	  {
	       		  x-=140;
	       	      y+=50;
	       	  }
	       		Element shape=BPMNPlane.addElement("bpmndi:BPMNShape")
	       				.addAttribute("bpmnElement", "SID-"+gn.getNodeID())
	       				.addAttribute("id", "BPMNShape_"+gn.getNodeID());
	       		        shape.addElement("omgdc:Bounds")
	       		        .addAttribute("height", "60.0")
	       		        .addAttribute("width", "80.0")
	       		        .addAttribute("x", ""+x)
	       		        .addAttribute("y", ""+y);
	       		 if(flag==0)
	       		 {
	       			 x+=120;
	       			 y=105;
	       		 }
	       		 else if(flag==2&&flag1==0)
	   			 {
					 flag=0;
					 flag1=2;
					 x+=240;
					 y=105;
				}    
	       		 if(flagCondition==1)
	       		 {
	       			 y=105;
	       			 x+=20;
	       		 }
	       		 else
	       		 if(flagCondition==2)
	       		 {
	       			 x+=140;
	       			  y=105;
	       			 flagCondition=0;
	       			 
	       		 }
			}
	      	
	       }
	    
	    int sum=100;
	       for(GraphEdge ge: senModel1.getAllGraphEdge())
	       {

	           		Element edge=BPMNPlane.addElement("bpmndi:BPMNEdge")
	            			   .addAttribute("bpmnElement", "SID-"+ge.getEdgeID())
	            			   .addAttribute("id", "BPMNEdge_"+ge.getEdgeID());
	            	   edge.addElement("omgdi:waypoint")
	            	    .addAttribute("x",""+sum)
	            	    .addAttribute("y","105");
	            	   edge.addElement("omgdi:waypoint")
	            	    .addAttribute("x",""+(sum+100))
	            	    .addAttribute("y","105");
	            	   sum+=180;
	   			
	       }
	   String xmlText = document.asXML();  
	   return xmlText;    
		       
	}       
	
}

		
		

