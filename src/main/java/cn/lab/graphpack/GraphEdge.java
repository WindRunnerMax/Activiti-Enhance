package cn.lab.graphpack;

public class GraphEdge {

	//边的ID
	private int edgeID;
	//边的文本
	private String edgeString;
	//边的开始节点
	private int startNodeID;
	//边的结束节点
	private int endNodeID;
	
	
	public GraphEdge(int edgeID,String edgeString,int startNodeID,int endNodeID) {
		
		this.edgeID = edgeID;
		this.edgeString = edgeString;
		this.startNodeID = startNodeID;
		this.endNodeID = endNodeID;
	}
	
	
	//set and get
	public int getEdgeID() {
		return edgeID;
	}
	public void setEdgeID(int edgeID) {
		this.edgeID = edgeID;
	}
	public String getEdgeString() {
		return edgeString;
	}
	public void setEdgeString(String edgeString) {
		this.edgeString = edgeString;
	}
	public int getStartNodeID() {
		return startNodeID;
	}
	public void setStartNodeID(int startNodeID) {
		this.startNodeID = startNodeID;
	}
	public int getEndNodeID() {
		return endNodeID;
	}
	public void setEndNodeID(int endNodeID) {
		this.endNodeID = endNodeID;
	}
	
	
	
	
	
	
	
	
}
