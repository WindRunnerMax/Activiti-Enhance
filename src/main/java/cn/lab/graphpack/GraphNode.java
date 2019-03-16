package cn.lab.graphpack;

public class GraphNode {
	
	//节点的ID
	private int nodeID;
	//节点的文本
	private String nodeText;
	private String rawSentence;
	
	//节点的类型
	//开始事件 startevent 活动activity 结束事件endevent  X类型的网关 xor  +类型的网关and
	private String nodeType;
	public GraphNode() {
		nodeID = -1;
		nodeText = "";
		nodeType = "";
		rawSentence="";
		
	}
	public GraphNode(int nodeID,String nodeText,String nodeType,String rawSentence) {
		this.nodeID = nodeID;
		this.nodeText = nodeText;
		this.nodeType = nodeType;
		this.rawSentence=rawSentence;
	}
	
	
	//set and get
	public String getRawSentence() {
		return rawSentence;
	}
	public void setRawSentence(String rawSentence) {
		this.rawSentence = rawSentence;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public String getNodeText() {
		return nodeText;
	}
	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
}
