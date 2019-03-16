package cn.lab.POM;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class AllSentenceInfo {
	private int sentenceID;//整句ID
	private String rawSentence;//整句字符串
	private List<TypedDependency> rawDependency;//整句依赖关系
	private Tree PennTree;//整句的句法解析树
	private ArrayList<SingleSentenceInfo> AllClauseInfo;//整句的子句列表
	
	//get和set
	public int getSentenceID() {
		return sentenceID;
	}
	public void setSentenceID(int sentenceID) {
		this.sentenceID = sentenceID;
	}
	
	public String getRawSentence() {
		return rawSentence;
	}
	public void setRawSentence(String rawSentence) {
		this.rawSentence = rawSentence;
	}
	
	public List<TypedDependency> getRawDependency(){
		return rawDependency;
	}
	public void setRawDependency(List<TypedDependency> rawDependency) {
		this.rawDependency = rawDependency;
	}
	
	public Tree getPennTree() {
		return PennTree;
	}
	public void setPennTree(Tree PennTree) {
		this.PennTree = PennTree;
	}

	public ArrayList<SingleSentenceInfo> getAllClause(){
		return AllClauseInfo;
	}
	public void setAllClause(ArrayList<SingleSentenceInfo> AllClauseInfo) {
		this.AllClauseInfo = AllClauseInfo;
	}
}
