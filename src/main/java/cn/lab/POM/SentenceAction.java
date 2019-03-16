package cn.lab.POM;

public class SentenceAction {

	private String rawAction;
	private String action;//action字符串
	private String marker;//action的关系流提示词
	//构造函数
	public SentenceAction() {
		rawAction = "";
		action = "";
		marker = "";
		
	}
	//构造函数
	public SentenceAction(String rawAction,String action, String marker) {
		this.rawAction = rawAction;
		this.action = action;
		this.marker = marker;
		
	}
	
	//get set
	public String getRawAction() {
		return rawAction;
	}
	public void setRawAction(String rawAction) {
		this.rawAction = rawAction;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}

}
