package cn.lab.graphGenerate;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Node;

import org.junit.Test;

import cn.lab.POM.AllSentenceInfo;
import cn.lab.POM.SentenceAction;
import cn.lab.POM.SentenceActor;
import cn.lab.POM.SingleSentenceInfo;
import cn.lab.graphpack.Graph;
import cn.lab.graphpack.GraphEdge;
import cn.lab.graphpack.GraphNode;

public class ModelGenerate {

	// 存储句子层次解析的所有信息
	private List<AllSentenceInfo> senParseMain;
	// 由文本生成的流程模型有向图
	private Graph senModel;
	private String preActor;
	// 全局所有的元素的ID
	private int IDIDID;
	private int startCondition;
	private int endCondition;
	private String Condition;
	public ModelGenerate(ArrayList<AllSentenceInfo> spm,String preActor) {
		// 数据的初始化
		this.senParseMain = spm;
		this.senModel = new Graph();
		this.preActor=preActor;
		IDIDID = 0;
		startCondition=0;
		endCondition=0;
		Condition="";
		preProcess();
		//直接执行模型的创建
		buildModel();
	}
	
	//获取模型
	public Graph getSenModel() {
		return senModel;
	}
	
	//对整句信息预处理，根据后一句的marker，对前一句的marker更新
	
	public void preProcess()
	{
		
		for (int spmi = 0; spmi < senParseMain.size(); spmi++) {
			//所有整句的信息
			AllSentenceInfo allSenInfo = senParseMain.get(spmi);
			//整句的所有子句信息
			ArrayList<SingleSentenceInfo> allClause = allSenInfo.getAllClause();//返回句子的信息
			
			for (int aci = 0; aci < allClause.size()-1; aci++) {
				//当前子句
				SingleSentenceInfo singleSenPre = allClause.get(aci);
				SingleSentenceInfo singleSenPost = allClause.get(aci+1);
				//System.out.println(singleSenPre);
				//System.out.println(singleSenPost);
				if(!singleSenPre.getSingleMarker().isEmpty()||singleSenPost.getSingleMarker().isEmpty()||singleSenPost.getSingleMarker().equals("jump"))
					continue;
				//如果前一句是空,后一句不是空才更新.
				//设置当前子句的marker  句子中的关系。
				singleSenPre.setSingleMarker(singleSenPost.getSingleMarker());
				allClause.set(aci, singleSenPre);
			}
			//刷新整句的信息
			allSenInfo.setAllClause(allClause);
			senParseMain.set(spmi, allSenInfo);
		}
		
	}

	//构造模型
	public void buildModel() {
		
		//为了处理第一个整句的第一个子句有多个主语动词的情况，这里在开始的时候添加一个默认的开始节点
		//同时在最后一个句子之后添加一个默认的结束节点，以后可以予以删除
		GraphNode startNode = new GraphNode(++IDIDID, "开始", "","");
		senModel.iHaveANewNode(startNode);
		senModel.setGraphStartNode(startNode.getNodeID());
		
		//表示现在的插入点 ，也就是已经放入模型的最后一个节点
		int mouseCursor = startNode.getNodeID();
		//循环所有的整句，遍历整句中所有的子句
		for (int spmi = 0; spmi < senParseMain.size(); spmi++) {
			
			//一个整句的所有信息
			AllSentenceInfo allSenInfo = senParseMain.get(spmi);
			//整句中所有子句的信息
			ArrayList<SingleSentenceInfo> allClauseInfo = allSenInfo.getAllClause();
			
			//遍历所有的子句
			for (int acii = 0; acii < allClauseInfo.size();) {
				
				//当前子句的所有信息
				SingleSentenceInfo sinSenInfo = allClauseInfo.get(acii);
				ArrayList<SentenceActor> senActor1 = sinSenInfo.getActorList().isEmpty()?searchActor(spmi,acii):sinSenInfo.getActorList();
				//如果执行人列表为空，就寻找执行人，如果不为空那就直接获得
				
				if(sinSenInfo.getSingleMarker().equals("parallel"))
				{
					//并行开始
					GraphNode Pstart = new GraphNode(++IDIDID, "并行", "","");
					senModel.iHaveANewNode(Pstart);
					//并行结束
					GraphNode Pend = new GraphNode(++IDIDID, "并行", "","");
					senModel.iHaveANewNode(Pend);
					
					//下一个子句是否寻找主语
					ArrayList<SentenceActor> senActor2 = allClauseInfo.get(acii+1).getActorList().isEmpty()?searchActor(spmi,acii+1):allClauseInfo.get(acii+1).getActorList();
					
					int tmp1 = processSingleClause(senActor1,sinSenInfo.getActionList(),Pstart.getNodeID(),sinSenInfo.getSentenceStr());//当前子句
					int tmp2 = processSingleClause(senActor2,allClauseInfo.get(acii+1).getActionList(),Pstart.getNodeID(),allClauseInfo.get(acii+1).getSentenceStr());
					//下一子句
					
					//图中的边
					GraphEdge aaoEdges = new GraphEdge(++IDIDID,"",mouseCursor,Pstart.getNodeID());
					GraphEdge aaoEdgee1 = new GraphEdge(++IDIDID,"",tmp1,Pend.getNodeID());
					GraphEdge aaoEdgee2 = new GraphEdge(++IDIDID,"",tmp2,Pend.getNodeID());
					//向模型中插入节点和边
					senModel.iHaveANewEdge(aaoEdges);
					senModel.iHaveANewEdge(aaoEdgee1);
					senModel.iHaveANewEdge(aaoEdgee2);
					//刷新位置
					mouseCursor = Pend.getNodeID();
					acii+=2;
				}
				else if(sinSenInfo.getSingleMarker().equals("select"))
				{
					//sinSenInfo当前子句的所有信息
					GraphNode Sstart = new GraphNode(++IDIDID, "选择", "","");
					senModel.iHaveANewNode(Sstart);
					
					GraphNode Send = new GraphNode(++IDIDID, "选择", "","");
					senModel.iHaveANewNode(Send);
					
					//是否寻找主语
					ArrayList<SentenceActor> senActor2 = allClauseInfo.get(acii+1).getActorList().isEmpty()?searchActor(spmi,acii+1):allClauseInfo.get(acii+1).getActorList();
					
					int tmp1 = processSingleClause(senActor1,sinSenInfo.getActionList(),Sstart.getNodeID(),sinSenInfo.getSentenceStr());
					int tmp2 = processSingleClause(senActor2,allClauseInfo.get(acii+1).getActionList(),Sstart.getNodeID(),allClauseInfo.get(acii+1).getSentenceStr());
					
					
					//图中的边
					GraphEdge aaoEdges = new GraphEdge(++IDIDID,"",mouseCursor,Sstart.getNodeID());//当前结点指向下一选择节点
					GraphEdge aaoEdgee1 = new GraphEdge(++IDIDID,"",tmp1,Send.getNodeID());//当前主语指向选择结点
					GraphEdge aaoEdgee2 = new GraphEdge(++IDIDID,"",tmp2,Send.getNodeID());//下一主语指向选择结点
					//向模型中插入节点和边
				    senModel.iHaveANewEdge(aaoEdges);
					senModel.iHaveANewEdge(aaoEdgee1);
					senModel.iHaveANewEdge(aaoEdgee2);
					//刷新位置
					mouseCursor = Send.getNodeID();
					acii+=2;
				}
				else if(sinSenInfo.getSingleMarker().equals("jump"))
				{
					//跳过开始
					GraphNode Jstart = new GraphNode(++IDIDID, "跳过", "","");
					senModel.iHaveANewNode(Jstart);
					//跳过结束
					GraphNode Jend = new GraphNode(++IDIDID, "跳过", "","");
					senModel.iHaveANewNode(Jend);
					//直接添加一条开始到结束的边，表示跳过
					GraphEdge JE = new GraphEdge(++IDIDID,"",Jstart.getNodeID(),Jend.getNodeID());
					senModel.iHaveANewEdge(JE);
					
					int tmp1 = processSingleClause(senActor1,sinSenInfo.getActionList(),Jstart.getNodeID(),sinSenInfo.getSentenceStr());
					
					GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",mouseCursor,Jstart.getNodeID());
					GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",tmp1,Jend.getNodeID());
					senModel.iHaveANewEdge(aaoEdgeS);
					senModel.iHaveANewEdge(aaoEdgeE);
					//现在最后一个节点是并行网关的最后一个节点
					mouseCursor = Jend.getNodeID();
					acii+=2;
				}
				else if(sinSenInfo.getSingleMarker().equals("return"))
				{
					//跳过开始
					if(sinSenInfo.getActorList().size()!=0)
{
	mouseCursor = processSingleClause(senActor1,sinSenInfo.getActionList(),mouseCursor,sinSenInfo.getSentenceStr());
	acii++;
}
else {
	
	int actorID=0;
	for(GraphNode gn:senModel.getAllGraphNode())
	{
		// System.out.println(gn.getRawSentence().indexOf(preActor));
		 //System.out.println(gn.getRawSentence());
		if(gn.getRawSentence().indexOf(preActor)==0)//改动
		{
			
			actorID=gn.getNodeID();
			break;
		}
	}
	
	GraphNode Jstart = new GraphNode(++IDIDID, "返回", "","");
	senModel.iHaveANewNode(Jstart);	
	GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",mouseCursor,Jstart.getNodeID());
	//GraphEdge aaoEdgeD = new GraphEdge(++IDIDID,"", Jstart.getNodeID(), Jend.getNodeID());
	senModel.iHaveANewEdge(aaoEdgeE);
	GraphNode Jend = new GraphNode(++IDIDID, "返回", "","");
	senModel.iHaveANewNode(Jend);	
	GraphEdge insertEdge =new GraphEdge(++IDIDID,"",Jstart.getNodeID(),Jend.getNodeID());
	senModel.iHaveANewEdge(insertEdge);
	//senModel.iHaveANewEdge(aaoEdgeD);
	for(GraphEdge ge:senModel.getAllGraphEdge())
	{
		//System.out.println(ge.getEndNodeID());
		
		if(actorID==ge.getEndNodeID())
		{
			
			for(GraphNode gn:senModel.getAllGraphNode())
			{
				
				if(ge.getStartNodeID()==gn.getNodeID())
				{
					if(gn.getNodeText().equals("选择")||gn.getNodeText().equals("并行")||gn.getNodeText().equals("返回"))
					{
						
						for(GraphEdge ge2:senModel.getAllGraphEdge())
						{
							if(ge2.getEndNodeID()==gn.getNodeID())
							{
								
								GraphEdge insertEdge1=new GraphEdge(ge2.getEdgeID(),"",ge2.getStartNodeID(),Jend.getNodeID());
								senModel.iHaveANewEdge(insertEdge1);
								GraphEdge insertEdge2=new GraphEdge(++IDIDID,"",Jend.getNodeID(),ge2.getEndNodeID());
								senModel.iHaveANewEdge(insertEdge2);
								senModel.getAllGraphEdge().remove(ge2);
								break;
							}
						}	
					}
					else
				{
						
					GraphEdge insertEdge1=new GraphEdge(ge.getEdgeID(),"",ge.getStartNodeID(),Jend.getNodeID());
					senModel.iHaveANewEdge(insertEdge1);
					GraphEdge insertEdge2=new GraphEdge(++IDIDID,"",Jend.getNodeID(),ge.getEndNodeID());
					senModel.iHaveANewEdge(insertEdge2);
					senModel.getAllGraphEdge().remove(ge);
					break;
				}	
				}
			}
			break;
		}
			
		}
	mouseCursor = Jstart.getNodeID();
	acii++;
	}
	//现在最后一个节点是并行网关的最后一个节点
}//end return 
				else if(sinSenInfo.getSingleMarker().equals("condition"))
				{
					GraphNode node = new GraphNode(++IDIDID, "条件", "","");
					senModel.iHaveANewNode(node);
					if(startCondition==0)
					{
					GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",mouseCursor,node.getNodeID());
					senModel.iHaveANewEdge(aaoEdgeS);
					Condition=sinSenInfo.getSentenceStr().substring(2);
					startCondition=node.getNodeID();
					}
					else {
						GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",mouseCursor,node.getNodeID());
						senModel.iHaveANewEdge(aaoEdgeS);
						Condition=sinSenInfo.getSentenceStr().substring(2);
						endCondition=node.getNodeID();
					}
					//GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",tmp1,Jend.getNodeID());
					mouseCursor = node.getNodeID();
					acii++;
				}
			else
			{
				mouseCursor = processSingleClause(senActor1,sinSenInfo.getActionList(),mouseCursor,sinSenInfo.getSentenceStr());
				acii++;
			}			
			}// end acii	
		}//end spmi
}// end buildModel

	/*
	 * processSingleClause:根据一个子句的信息，将子句放入模型中
	 * 参数：子句的主语链表、子句动宾链表、子句的标识，现在光标所处的位置
	 * 函数返回：光标位置
	 * 
	 * */
	public int processSingleClause(ArrayList<SentenceActor> actorList,ArrayList<SentenceAction> actionList,int mouseCursor,String sinSenInfo)
	{//mouseCursor当前的插入点id
		int currentPos = mouseCursor;	//对应不同类型的前一个选择结点。
//		null parallel jump select
		//主语和动宾的拼接结果
		ArrayList<String> forkActActResult = forkActorAndAction(actorList,actionList);
		if(actorList.size()==1)//只有一个主语的时候
		{
			for(String aaoStr:forkActActResult)
			{
				if(startCondition==0&&endCondition==0)
				{
					GraphNode aaoNode = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					//图中的边
					GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",currentPos,aaoNode.getNodeID());//
					//向模型中插入节点和边
					senModel.iHaveANewNode(aaoNode);
					senModel.iHaveANewEdge(aaoEdgeS);
					currentPos = aaoNode.getNodeID();
					
				}
				else if(startCondition!=0&&endCondition==0)
				{
					GraphNode Node = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					senModel.iHaveANewNode(Node);
					GraphEdge Edge= new GraphEdge(++IDIDID,Condition,startCondition,Node.getNodeID());
					senModel.iHaveANewEdge(Edge);
					currentPos = Node.getNodeID();
				}
				else if(endCondition!=0)
				{
					GraphNode Node = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					senModel.iHaveANewNode(Node);
					GraphEdge Edge1= new GraphEdge(++IDIDID,Condition,startCondition,Node.getNodeID());
					GraphEdge Edge2= new GraphEdge(++IDIDID,"",Node.getNodeID(),endCondition);
					senModel.iHaveANewEdge(Edge1);
					senModel.iHaveANewEdge(Edge2);
					currentPos = endCondition;
					startCondition=0;
					endCondition=0;
					Condition="";
				}
			}
		}
		else 
			if(!actorList.isEmpty()&&actorList.get(0).getMarker().equals("parallel"))
		{
			//并行开始
			GraphNode Pstart = new GraphNode(++IDIDID, "并行", "","");
			senModel.iHaveANewNode(Pstart);
			//并行结束
			GraphNode Pend = new GraphNode(++IDIDID, "并行", "","");
			senModel.iHaveANewNode(Pend);
			for(String aaoStr:forkActActResult)
			{
				//图的节点
				GraphNode aaoNode = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
				//图中的边
				GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",Pstart.getNodeID(),aaoNode.getNodeID());
				GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",aaoNode.getNodeID(),Pend.getNodeID());
				//向模型中插入节点和边
				senModel.iHaveANewNode(aaoNode);
				senModel.iHaveANewEdge(aaoEdgeS);
				senModel.iHaveANewEdge(aaoEdgeE);
			}
			//图中的边
			GraphEdge aaoEdges = new GraphEdge(++IDIDID,"",mouseCursor,Pstart.getNodeID());
			senModel.iHaveANewEdge(aaoEdges);
			//刷新位置
			currentPos = Pend.getNodeID();
		}
		else if(!actorList.isEmpty()&&actorList.get(0).getMarker().equals("jump"))
		{
			//跳过开始
			GraphNode Jstart = new GraphNode(++IDIDID, "跳转", "","");
			senModel.iHaveANewNode(Jstart);
			//跳过结束
			GraphNode Jend = new GraphNode(++IDIDID, "跳转", "","");
			senModel.iHaveANewNode(Jend);
			for(String aaoStr:forkActActResult)
			{
				//图的节点
				GraphNode aaoNode = new GraphNode(++IDIDID, aaoStr, "","");
				//图中的边
				GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",Jstart.getNodeID(),aaoNode.getNodeID());
				GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",aaoNode.getNodeID(),Jend.getNodeID());
				//向模型中插入节点和边
				senModel.iHaveANewNode(aaoNode);
				senModel.iHaveANewEdge(aaoEdgeS);
				senModel.iHaveANewEdge(aaoEdgeE);
			}
			//图中的边
			GraphEdge aaoEdges = new GraphEdge(++IDIDID,"",mouseCursor,Jstart.getNodeID());
			GraphEdge jumpEdges = new GraphEdge(++IDIDID,"",Jstart.getNodeID(),Jend.getNodeID());
			senModel.iHaveANewEdge(aaoEdges);
			senModel.iHaveANewEdge(jumpEdges);
			//刷新位置
			currentPos = Jend.getNodeID();
		}
		else if(!actorList.isEmpty()&&actorList.get(0).getMarker().equals("select"))
		{
			//选择开始
			GraphNode Sstart = new GraphNode(++IDIDID, "选择", "","");
			senModel.iHaveANewNode(Sstart);
			//选择结束
			GraphNode Send = new GraphNode(++IDIDID, "选择", "","");
			senModel.iHaveANewNode(Send);//排他网关应该是不需要这个节点的。
			for(String aaoStr:forkActActResult)
			{
				//图的节点
				GraphNode aaoNode = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
				//图中的边
				GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",Sstart.getNodeID(),aaoNode.getNodeID());
				GraphEdge aaoEdgeE = new GraphEdge(++IDIDID,"",aaoNode.getNodeID(),Send.getNodeID());
				//向模型中插入节点和边
				senModel.iHaveANewNode(aaoNode);
				senModel.iHaveANewEdge(aaoEdgeS);
				senModel.iHaveANewEdge(aaoEdgeE);
			}
			//图中的边
			GraphEdge aaoEdges = new GraphEdge(++IDIDID,"",mouseCursor,Sstart.getNodeID());
			senModel.iHaveANewEdge(aaoEdges);
			//刷新位置
			currentPos = Send.getNodeID();
		}
		else
		{
			for(String aaoStr:forkActActResult)
			{
				if(startCondition==0&&endCondition==0)
				{
					GraphNode aaoNode = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					//图中的边
					GraphEdge aaoEdgeS = new GraphEdge(++IDIDID,"",currentPos,aaoNode.getNodeID());//
					//向模型中插入节点和边
					senModel.iHaveANewNode(aaoNode);
					senModel.iHaveANewEdge(aaoEdgeS);
					currentPos = aaoNode.getNodeID();
					
				}
				else if(startCondition!=0&&endCondition==0)
				{
					GraphNode Node = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					senModel.iHaveANewNode(Node);
					GraphEdge Edge= new GraphEdge(++IDIDID,Condition,startCondition,Node.getNodeID());
					senModel.iHaveANewEdge(Edge);
					currentPos = Node.getNodeID();
				}
				else if(endCondition!=0)
				{
					GraphNode Node = new GraphNode(++IDIDID, aaoStr, "",sinSenInfo);
					senModel.iHaveANewNode(Node);
					GraphEdge Edge1= new GraphEdge(++IDIDID,Condition,startCondition,Node.getNodeID());
					GraphEdge Edge2= new GraphEdge(++IDIDID,"",Node.getNodeID(),endCondition);
					senModel.iHaveANewEdge(Edge1);
					senModel.iHaveANewEdge(Edge2);
					currentPos = endCondition;
					startCondition=0;
					endCondition=0;
					Condition="";
				}
			}
		}
		return currentPos;	
	}//end processSingleClause
	/*
	 * searchActor:如果子句的主语为空，需要到前寻找主语的方法
	 * 参数：senID整句ID，clauseID子句ID
	 * 函数返回：寻找到的主语的链表
	 * 
	 * */
	public ArrayList<SentenceActor> searchActor(int senID,int clauseID)
	{
		//System.out.println("没有主语的句子：整句："+senID+"子句"+clauseID);
		//通过主句的id和子句的id来查找actor
		ArrayList<SentenceActor> tmpActor = new ArrayList<>();
		
		//首先吧当前整句循环完毕
		for(;clauseID>-1;--clauseID)
		{
			if(senParseMain.get(senID).getAllClause().get(clauseID).getActorList().size()==1)
			{
				//System.out.println("在第一个里面找到"+senParseMain.get(senID).getAllClause().get(clauseID).getActorList().get(0).getActor());
				
				return senParseMain.get(senID).getAllClause().get(clauseID).getActorList();
			}
		}
		//接上面，当前整句没有，到前面的整句中寻找主语
		for(--senID;senID>0;--senID)
		{
			clauseID = senParseMain.get(senID).getAllClause().size()-1;
			for(;clauseID>-1;--clauseID)
			{
				if(senParseMain.get(senID).getAllClause().get(clauseID).getActorList().size()==1)
				{
					//System.out.println("在第2个里面找到"+senParseMain.get(senID).getAllClause().get(clauseID).getActorList().get(0).getActor());
					return senParseMain.get(senID).getAllClause().get(clauseID).getActorList();
				}
			}
		}
		//System.out.println("没有找到");
		//接上面，如果都没有找到，那么返回的就是空
		return tmpActor;
	}//end searchActor
	public ArrayList<String> forkActorAndAction(ArrayList<SentenceActor> actorList,ArrayList<SentenceAction> actionList)
	{
		ArrayList<String> forkResult = new ArrayList<>();
		
		if(actorList.isEmpty())
		{
			for(SentenceAction sa:actionList)
			{
				//forkResult.add("/");
				forkResult.add(sa.getAction());
			}
		}
		else
		{
			for(SentenceActor sac:actorList)
			{
				for(SentenceAction sa:actionList)
				{
					forkResult.add(sac.getActor()+"/"+sa.getAction());
				}
			}
		}
		return forkResult;
	}
}
