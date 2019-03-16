package cn.lab.POM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

//子句信息
public class SingleSentenceInfo {

	private String sentenceStr;//子句字符串
	private String word_tagStr;//子句的分词和词性标注
	private List<TypedDependency> sentenceDependency;//子句依赖关系列表
	private Tree sentencePennTree;//子句的句法结构树
	private ArrayList<SentenceActor> actorList;//子句actor列表
	//private ArrayList<String> actorList;//子句actor列表
	private ArrayList<SentenceAction> actionList;//子句action列表
	private String singleMarker;
	
	//构造函数
	public SingleSentenceInfo() {
		sentenceStr = "";
		sentenceDependency = null;
		sentencePennTree = null;
		actorList = null;
		actionList = null;
		singleMarker = "";
	}

	//get set函数
	
	public String getSentenceStr() {
		return sentenceStr;
	}
	public void setSentenceStr(String sentenceStr) {
		this.sentenceStr = sentenceStr;
	}
	   
	public String getword_tagStr() {
		return word_tagStr;
	}
	public void setword_tagStr(String word_tagStr) {
		this.word_tagStr = word_tagStr;
	}
	
	public List<TypedDependency> getSentenceDependency(){
		return sentenceDependency;
	}
	public void setSentenceDependency(List<TypedDependency> sentenceDependency) {
		this.sentenceDependency = sentenceDependency;
	}
	
	public Tree getSentencePennTree() {
		return sentencePennTree;
	}
	public void setSentencePennTree(Tree sentencePennTree) {
		this.sentencePennTree = sentencePennTree;
	}
	
	public ArrayList<SentenceActor> getActorList(){
		return actorList;
	}
	public void setActorList(ArrayList<SentenceActor> actorList) {
		this.actorList = actorList;
	}
	
	public ArrayList<SentenceAction> getActionList(){
		return actionList;
	}
	public void setActionList(ArrayList<SentenceAction> actionList) {
		this.actionList = actionList;
	}
	
	public String getSingleMarker(){
		return singleMarker;
	}
	public void setSingleMarker(String singleMarker) {
		this.singleMarker = singleMarker;
	}

}