package cn.lab.POM;

//actor
public class SentenceActor {
	private String rawActor;//原actor字符串
	private String actor;//actor字符串
	private String marker;//actor的关系标志词：或、和、“、”
	
	//构造函数
	public SentenceActor() {
		rawActor = "";
		actor = "";
		marker = "";
	}
	
	//构造函数
    public SentenceActor(String actor, String marker) {
    	this.actor = actor;
    	this.marker = marker;
    }
    
    //get set
    public String getActor() {
    	return actor;
    }
    public void setActor(String actor) {
    	this.actor = actor;
    }
    
    public String getRawActor() {
    	return rawActor;
    }
    public void setRawActor(String rawActor) {
    	this.rawActor = rawActor;
    }
    
    public String getMarker() {
    	return marker;
    }
    public void setMarker(String marker) {
    	this.marker = marker;
    }
}


