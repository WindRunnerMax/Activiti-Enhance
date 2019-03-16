package cn.lab.graphpack;

import java.util.ArrayList;
import java.util.HashSet;

public class Graph {
	
	//构造函数
	public Graph() {
		allGraphNode = new ArrayList<>();//结点
		allGraphEdge = new ArrayList<>();//边
	}
	
	
	//所有的节点
	private ArrayList<GraphNode> allGraphNode;
	//所有的边
	private ArrayList<GraphEdge> allGraphEdge;
	//图的开始节点
	private int graphStartNode;
	
	//------------------------------------------------------------------------------------------
	//自定义函数
	//------------------------------------------------------------------------------------------
	
	//将图的结构打印输出
	public void printfGraph()
	{
		System.out.println("=================================================================================================");
		System.out.println("=================================================================================================");
		
		//循环图中的所有的节点
		for(int nli = 0;nli<allGraphNode.size();++nli)
		{
			GraphNode nodeNli = allGraphNode.get(nli);
			System.out.println(String.format("节点的ID：%d，节点的文本：%s，节点的类型：%s", nodeNli.getNodeID(),nodeNli.getNodeText(),nodeNli.getNodeType()));
		} //end for nli
		for(int age=0;age<allGraphEdge.size();++age)
		{
			GraphEdge edgeNli = allGraphEdge.get(age);
			System.out.println(String.format("边的ID：%d，边的文本：%s，边的开始节点：%d，边的结束节点：%d", edgeNli.getEdgeID(),edgeNli.getEdgeString(),edgeNli.getStartNodeID(),edgeNli.getEndNodeID()));
		}
		
		System.out.println("==================================================================================================");
		System.out.println("==================================================================================================");
	}// end for age

	//向allGraphNode中添加节点
	public void iHaveANewNode(GraphNode node)
	{
		allGraphNode.add(node);
	}
	//向allGraphEdge中添加边
	public void iHaveANewEdge(GraphEdge edge)
	{
		allGraphEdge.add(edge);
	}
	
	
	
	
	//改变节点的属性
	public void changeNode(GraphNode node)
	{
		//改变节点集合中节点的属性
		for(int agn=0;agn<allGraphNode.size();++agn)
		{
			if(allGraphNode.get(agn).getNodeID()==node.getNodeID())
			{
				allGraphNode.set(agn, node);
			}
		}
		//改变边集合中边的属性
		for(int aen=0;aen<allGraphEdge.size();++aen)
		{
			GraphEdge graphEdge = allGraphEdge.get(aen);
			if(graphEdge.getStartNodeID()==node.getNodeID())//开始结点等于当前节点
			{
				graphEdge.setStartNodeID(node.getNodeID());
				allGraphEdge.set(aen, graphEdge);
			}
			if(graphEdge.getEndNodeID()==node.getNodeID())//结束结点等于当前结点
			{
				graphEdge.setEndNodeID(node.getNodeID());
				allGraphEdge.set(aen, graphEdge);
			}
		}
		
	}
	//------------------------------------------------------------------------------------------
	//自定义函数
	//------------------------------------------------------------------------------------------
	
	//set and get
	public ArrayList<GraphNode> getAllGraphNode() {
		return allGraphNode;
	}
	public void setAllGraphNode(ArrayList<GraphNode> allGraphNode) {
		this.allGraphNode = allGraphNode;
	}
	public ArrayList<GraphEdge> getAllGraphEdge() {
		return allGraphEdge;
	}
	public void setAllGraphEdge(ArrayList<GraphEdge> allGraphEdge) {
		this.allGraphEdge = allGraphEdge;
	}
	public int getGraphStartNode() {
		return graphStartNode;
	}
	public void setGraphStartNode(int graphStartNode) {
		this.graphStartNode = graphStartNode;
	}
}
