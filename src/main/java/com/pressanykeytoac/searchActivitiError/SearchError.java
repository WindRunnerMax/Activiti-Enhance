package com.pressanykeytoac.searchActivitiError;
import java.util.*;
import java.io.StringReader;
//import javax.xml.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
//import com.sun.xml.internal.ws.util.Pool.Unmarshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;


import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
//import org.dom4j.io.OutputFormat;
//import org.dom4j.io.XMLWriter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import org.xml.sax.helpers.XMLReaderFactory;

 
public class SearchError {
	public static Object convertXmlStrToObject(Class<definitions> clazz, String xmlStr) {  
        Object xmlObject = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(clazz);  
            //进行将Xml_转成对象的核心接口  
             Unmarshaller unmarshaller = context.createUnmarshaller();  
            StringReader sr = new StringReader(xmlStr); 
           //SAXParserFactory sax = SAXParserFactory.newInstance();
            //sax.setNamespaceAware(false);//设置忽略命名空间
            xmlObject = unmarshaller.unmarshal(sr);  
        } catch (JAXBException e) {  
            e.printStackTrace();  
        }  
        return xmlObject;  
    }  
    /**
     * 解析带有命名空间的XML
     * 
     * @param cla
     * @param content
     * @return
     * @throws JAXBException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static int flag=0;
    public static Object fromXmlWithNamespace(Class<?> cla, String content)
            throws JAXBException, ParserConfigurationException, SAXException {

        JAXBContext jaxbContext = JAXBContext.newInstance(cla);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader strReader = new StringReader(content);

        XMLReader reader = XMLReaderFactory.createXMLReader();
        //去掉XML的命名空间
        NamespaceFilter inFilter = new NamespaceFilter(null, false);
        inFilter.setParent(reader);

        Source source = new SAXSource(inFilter, new InputSource(strReader));
        Object o = unmarshaller.unmarshal(source);
        
        return o;
    }
	public static String searchError(int id) throws JAXBException, ParserConfigurationException, SAXException {
		ConnectDatabase.init(id);
		String Error_="";
		//获取到了字符串,进行预处理
		//进行遍历
		//找到这个字串的时候就结束遍历</process>
		//找到startEvent 创建一个该类的实例
		//并将其存到map里面，key是id，value是该类的实例
		//其他标签操作同上
		//当读到sequenceFlow的时候便将其sourceRef和 targetRef对应的id在map里面找到;
		//按顺序存到一个图里面，最后进行图的深度优先搜索，找到相应的错误。
		//System.out.println(ConnectDatabase.bpmnXml);
		
		//预处理字符串
		//System.out.println(ConnectDatabase.bpmnXml);
		//int noneStart=ConnectDatabase.bpmnXml.indexOf("<definitions");
		//int indexStart = ConnectDatabase.bpmnXml.indexOf("<process");
		int indexEnd=ConnectDatabase.bpmnXml.indexOf("</process>"); 			
		String stringSearch1 = ConnectDatabase.bpmnXml.substring(0,indexEnd+10); 
		String stringSearch =stringSearch1+"\n</definitions>";
		//String stringSearch2 = ConnectDatabase.bpmnXml.substring(0,noneStart);
	     //String stringSearch = stringSearch2+stringSearch1;
		//System.out.println(stringSearch);	
		//运用JAXB将xml_格式的字符串转化为java类的实例
		
		definitions a=(definitions)fromXmlWithNamespace(definitions.class, stringSearch);
		//System.out.println(a); 
		//System.out.println(a.getStartEvent().size());
		
		
//		for(int i=0;i<a.getUserTask().size();i++)
//		{
//			System.out.println(a.getUserTask().get(i));
//		}
//		for(int i=0;i<a.getSequenceFlow().size();i++)
//		{
//			System.out.println(a.getSequenceFlow().get(i));
//		}
		//将类的实例和其对应的id主键存到map里。
		Map<Integer,Father> mapIndex=new HashMap<Integer,Father>();
		Map<String,Father> mapId=new HashMap<String,Father>();
		int index=0;
		if(a.getProcess().getStartEvent()!=null)
		{
			for(int i=0;i<a.getProcess().getStartEvent().size();i++)
			{
				a.getProcess().getStartEvent().get(i).setIndex(index++);
				//mapIndex.put(a.getStartEvent().get(i).getIndex(), a.getStartEvent().get(i));
				mapId.put(a.getProcess().getStartEvent().get(i).getId(), a.getProcess().getStartEvent().get(i));
			}
		}
	if(a.getProcess().getEndEvent()!=null)
	{
		for(int i=0;i<a.getProcess().getEndEvent().size();i++)
	{
		a.getProcess().getEndEvent().get(i).setIndex(index++);
		//mapIndex.put(a.getEndEvent().get(i).getIndex(), a.getEndEvent().get(i));
		mapId.put(a.getProcess().getEndEvent().get(i).getId(), a.getProcess().getEndEvent().get(i));
	}
		
	}
		if(a.getProcess().getUserTask()!=null)
		{
			for(int i=0;i<a.getProcess().getUserTask().size();i++)
			{
				a.getProcess().getUserTask().get(i).setIndex(index++);
				mapIndex.put(a.getProcess().getUserTask().get(i).getIndex(),a.getProcess().getUserTask().get(i));
				mapId.put(a.getProcess().getUserTask().get(i).getId(),a.getProcess().getUserTask().get(i));
			}
		}
		if(a.getProcess().getSequenceFlow()!=null)
		{
			for(int i=0;i<a.getProcess().getSequenceFlow().size();i++)
			{
				//a.getSequenceFlow().get(i).setIndex(index++);
				//mapIndex.put(a.getSequenceFlow().get(i).getIndex(),a.getSequenceFlow().get(i));
				mapId.put(a.getProcess().getSequenceFlow().get(i).getId(),a.getProcess().getSequenceFlow().get(i));
			}
		}
		if(a.getProcess().getExclusiveGateway()!=null)
		{
			for(int i=0;i<a.getProcess().getExclusiveGateway().size();i++)
		     {
		    	 a.getProcess().getExclusiveGateway().get(i).setIndex(index++);
		    	 mapIndex.put(a.getProcess().getExclusiveGateway().get(i).getIndex(),a.getProcess().getExclusiveGateway().get(i));
		    	 mapId.put(a.getProcess().getExclusiveGateway().get(i).getId(), a.getProcess().getExclusiveGateway().get(i));
		     }
		}
		if(a.getProcess().getsubProcess()!=null)
		{
			for(int i=0;i<a.getProcess().getsubProcess().size();i++)
		     {
		    	 a.getProcess().getsubProcess().get(i).setIndex(index++);
		    	 mapIndex.put(a.getProcess().getsubProcess().get(i).getIndex(),a.getProcess().getsubProcess().get(i));
		    	 mapId.put(a.getProcess().getsubProcess().get(i).getId(), a.getProcess().getsubProcess().get(i));
		     }
		}
		
		
//         for(int i=0;i<index;i++)
//         {
//        	 System.out.println(mapIndex.get(i));
//         }
        //System.out.println(index);
		//将id主键和其对应的实例构造成图
         int find[]=new int[index+1];
         for(int i=0;i<index;i++)
         {
        	 find[i]=i;
         }
      int map[][]=new int[index+1][index+1];
      for(int i=0;i<a.getProcess().getSequenceFlow().size();i++)
      {
    	//System.out.println(mapId.get(a.getSequenceFlow().get(i).getSourceRef())); 
    	int startIndex = mapId.get(a.getProcess().getSequenceFlow().get(i).getSourceRef()).getIndex();
    	int endIndex = mapId.get(a.getProcess().getSequenceFlow().get(i).getTargetRef()).getIndex(); 
    	map[startIndex][endIndex]=1;
    	find[endIndex]=startIndex;
      }
      ////////////////////////
     //////////////////////
      int cnt=0;
  for(int i=0;i<index;i++)
  {
	  if(find[i]==i)
		  cnt++;
  }
  //计算入度和出度
  int inDegree[]=new int[index+1];
  int outDegree[]=new int[index+1];
  for(int i=0;i<index;i++)
 	  for(int j=0;j<index;j++)
   {
	 if(map[i][j]==1)
	 {
 		 inDegree[j]++;
		 outDegree[i]++;
	 }
  }
  if(a.getProcess().getStartEvent()==null||a.getProcess().getEndEvent()==null)
     Error_="至少应该有一个开始事件和一个结束事件"+"<br>";
  
  
  else
      if(cnt!=1)
    	 Error_+="该流程图不是连通图"+"<br>";
      else
      {
    	
  		int endIndex[]=new int[index+1];
  		int size=a.getProcess().getEndEvent().size();
  		for(int i=0;i<size;i++)
  		{
  			endIndex[i]=a.getProcess().getEndEvent().get(i).getIndex();
  		}
  		flag=0;
  		for(int i=0;i<index;i++)
  		{
  			for(int j=0;j<size;j++)
  			{
  				if(outDegree[i]==0&&i!=endIndex[j])
  				{
  					flag=1;
  					break;
  				}
  			}
  			if(flag==1)
  			{
  				Error_+="存在一种特殊的孤立结点，其不在开始结点到结束结点的任意连线上"+"<br>";
  				break;
  			}
  		}
  		   
    	 // int re=dfs(map,index,startIndex,endIndex,size);	  
      }
      //////////不考虑边的方向时，必须是一个联通图////////
   
     for(int i=0;i<index;i++)
   {
    	  /////一个任务只能有一个出度！
   	  if(outDegree[i]>1&&mapIndex.get(i) instanceof userTask)
    	  {
    		 Error_+="存在一任务结点有多个出度 "+"<br>";
    	  }
   	  /////排他网关应该存在一个以上的出度
   	if(outDegree[i]==1&&mapIndex.get(i) instanceof exclusiveGateway)
	  {
		  Error_+="存在一排他网关只有一个出度 "+"<br>";
		 
	  }
   }    
     ////////////////////////////////////////子流程判断//////////////////////////////////////////////
     Map<Integer,Father> mapIndexChild=new HashMap<Integer,Father>();
		Map<String,Father> mapIdChild=new HashMap<String,Father>();
		int indexChild=0;
		if(a.getProcess().getsubProcess()!=null)
		{
			for(int j=0;j<a.getProcess().getsubProcess().size();j++)
			{
				if(a.getProcess().getsubProcess().get(j).getStartEvent()!=null)
				{
					for(int i=0;i<a.getProcess().getStartEvent().size();i++)
					{
						a.getProcess().getsubProcess().get(j).getStartEvent().get(i).setIndex(indexChild++);
						//mapIndex.put(a.getStartEvent().get(i).getIndex(), a.getStartEvent().get(i));
						mapIdChild.put(a.getProcess().getsubProcess().get(j).getStartEvent().get(i).getId(), a.getProcess().getsubProcess().get(j).getStartEvent().get(i));
					}
				}
			if(a.getProcess().getsubProcess().get(j).getEndEvent()!=null)
			{
				for(int i=0;i<a.getProcess().getsubProcess().get(j).getEndEvent().size();i++)
			{
				a.getProcess().getsubProcess().get(i).getEndEvent().get(i).setIndex(indexChild++);
				//mapIndex.put(a.getEndEvent().get(i).getIndex(), a.getEndEvent().get(i));
				mapIdChild.put(a.getProcess().getsubProcess().get(j).getEndEvent().get(i).getId(), a.getProcess().getsubProcess().get(j).getEndEvent().get(i));
			}
				
			}
				if(a.getProcess().getsubProcess().get(j).getUserTask()!=null)
				{
					for(int i=0;i<a.getProcess().getsubProcess().get(j).getUserTask().size();i++)
					{
						a.getProcess().getsubProcess().get(j).getUserTask().get(i).setIndex(indexChild++);
						mapIndexChild.put(a.getProcess().getsubProcess().get(j).getUserTask().get(i).getIndex(),a.getProcess().getsubProcess().get(j).getUserTask().get(i));
						mapIdChild.put(a.getProcess().getsubProcess().get(j).getUserTask().get(i).getId(),a.getProcess().getsubProcess().get(j).getUserTask().get(i));
					}
				}
				if(a.getProcess().getsubProcess().get(j).getSequenceFlow()!=null)
				{
					for(int i=0;i<a.getProcess().getsubProcess().get(j).getSequenceFlow().size();i++)
					{
						//a.getSequenceFlow().get(i).setIndex(index++);
						//mapIndex.put(a.getSequenceFlow().get(i).getIndex(),a.getSequenceFlow().get(i));
						mapIdChild.put(a.getProcess().getsubProcess().get(j).getSequenceFlow().get(i).getId(),a.getProcess().getsubProcess().get(j).getSequenceFlow().get(i));
					}
				}
				if(a.getProcess().getsubProcess().get(j).getExclusiveGateway()!=null)
				{
					for(int i=0;i<a.getProcess().getsubProcess().get(j).getExclusiveGateway().size();i++)
				     {
				    	 a.getProcess().getsubProcess().get(j).getExclusiveGateway().get(i).setIndex(indexChild++);
				    	 mapIndexChild.put(a.getProcess().getsubProcess().get(j).getExclusiveGateway().get(i).getIndex(),a.getProcess().getExclusiveGateway().get(i));
				    	 mapIdChild.put(a.getProcess().getsubProcess().get(j).getExclusiveGateway().get(i).getId(), a.getProcess().getExclusiveGateway().get(i));
				     }
				}
				//////////////////////////	
//		      for(int i=0;i<index;i++)
//		      {
//		     	 System.out.println(mapIndex.get(i));
//		      }
		     //System.out.println(index);
				//将id主键和其对应的实例构造成图
		      int findChild[]=new int[indexChild+1];
		      for(int i=0;i<indexChild;i++)
		      {
		     	 findChild[i]=i;
		      }
		   int mapChild[][]=new int[indexChild+1][indexChild+1];
		   for(int i=0;i<a.getProcess().getsubProcess().get(j).getSequenceFlow().size();i++)
		   {
		 	//System.out.println(mapId.get(a.getSequenceFlow().get(i).getSourceRef())); 
		 	int startIndex = mapIdChild.get(a.getProcess().getsubProcess().get(j).getSequenceFlow().get(i).getSourceRef()).getIndex();
		 	int endIndex = mapIdChild.get(a.getProcess().getsubProcess().get(j).getSequenceFlow().get(i).getTargetRef()).getIndex(); 
		 	mapChild[startIndex][endIndex]=1;
		 	findChild[endIndex]=startIndex;
		   }
		   ///////////////////////////联通图//////////////////////
		   int inDegreeChild[]=new int[indexChild+1];
			  int outDegreeChild[]=new int[indexChild+1];
			  for(int i=0;i<indexChild;i++)
			 	  for(int k=0;k<indexChild;k++)
			   {
				 if(map[i][k]==1)
				 {
			 		 inDegreeChild[k]++;
					 outDegreeChild[i]++;
				 }
			  }
			  
		   if(a.getProcess().getsubProcess().get(j).getEndEvent()==null)
			{
				Error_+="子流程应该至少有一个结束事件"+"<br>";
			}
			
		   else if(a.getProcess().getsubProcess().get(j).getStartEvent()==null)
			{
			   Error_+="子流程应该有且仅有一个开始事件"+"<br>";
			}
			else if(a.getProcess().getsubProcess().get(j).getStartEvent().size()!=1)
			{
				Error_+="子流程应该有且仅有一个开始事件"+"<br>";
			}
			else
			{
				
		  		int endIndex[]=new int[index+1];
		  		int size=a.getProcess().getsubProcess().get(j).getEndEvent().size();
		  		for(int i=0;i<size;i++)
		  		{
		  			endIndex[i]=a.getProcess().getsubProcess().get(j).getEndEvent().get(i).getIndex();
		  		}
		  		flag=0;
		  		for(int i=0;i<index;i++)
		  		{
		  			for(int k=0;k<size;k++)
		  			{
		  				if(outDegreeChild[i]==0&&i!=endIndex[k])
		  				{
		  					flag=1;
		  					break;
		  				}
		  			}
		  			if(flag==1)
		  			{
		  				Error_+="子流程存在一种特殊的孤立结点，其不在开始结点到结束结点的任意连线上"+"<br>";
		  				break;
		  			}
		  		}
			}
		   int cntChild=0;
		for(int i=0;i<indexChild;i++)
		{
			  if(findChild[i]==i)
				  cntChild++;
		}
		   if(cntChild!=1)
			   Error_+="子流程图不是连通图"+"<br>";
		   //////////不考虑边的方向时，必须是一个联通图////////
		  
		  for(int i=0;i<indexChild;i++)
		{
		 	  /////一个任务只能有一个出度！
			  if(outDegreeChild[i]>1&&mapIndexChild.get(i) instanceof userTask)
		 	  {
				  Error_+="子流程中存在一任务结点有多个出度 "+"<br>";
		 	  }
			  /////排他网关应该存在一个以上的出度
			if(outDegreeChild[i]==1&&mapIndexChild.get(i) instanceof exclusiveGateway)
			  {
				Error_+="子流程中存在一排他网关只有一个出度 "+"<br>";
			  }
		} 
			}
		}
		//////////////////////////////////////	
		return Error_;
	}	
}


