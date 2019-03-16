package relationExtraction;

import java.util.ArrayList;
import java.util.List;

import cn.lab.POM.AllSentenceInfo;
import cn.lab.POM.SentenceAction;
import cn.lab.POM.SentenceActor;
import cn.lab.POM.SingleSentenceInfo;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class relaExtraction {
	
		private List<AllSentenceInfo> senParserMain;
		//get方法
		public List<AllSentenceInfo> getSllSenInfo(){
			return senParserMain;
		}
		private  String preActor;
		public  String getPreActor() {
			return preActor;
		}
		//构造方法
		public relaExtraction() {}
		public relaExtraction(List<AllSentenceInfo> senParserMain) {
			this.senParserMain = senParserMain;
			
		}
		public void markerDetection() {
			for(int asil=0;asil<senParserMain.size();asil++) {
				AllSentenceInfo sentence = senParserMain.get(asil);
				//a是整句，s是子句。
				for(int ssil=0;ssil<sentence.getAllClause().size();ssil++) {//获得整句的子句列表
					SingleSentenceInfo singleSentence = sentence.getAllClause().get(ssil);
					List<TypedDependency> singleSenDepList = singleSentence.getSentenceDependency();//依赖关系列表
					//Tree singleSenPennTree = singleSentence.getSentencePennTree();
					//Actor的conj关系的识别，识别结果放入Actor的Marker属性中，paralleral和select两种关系分别表示并发和排他选择关系。
					ArrayList<SentenceActor> singleActorList = singleSentence.getActorList();
				    String actorMarker = determineActorMarker(singleActorList,singleSenDepList);
				    for(SentenceActor singleSenActor:singleActorList) {
			            singleSenActor.setMarker(actorMarker);
				    }//Actor关系	
				     //Action的关系识别，包括paralleral,select和Jump三种关系。
				     //首先识别conj的关系，存入Action的Marker中。
				     //然后识别动宾补关系，存入Action的Marker中。
				     //最后是识别Action的marker依赖关系,存入句子的Marker中，并、与此同时和必要时等。
				    ArrayList<SentenceAction> singleActionList = singleSentence.getActionList();
				    String actionMarker = determineActionMarker(singleActionList,singleSenDepList);
				    for(SentenceAction singleSenAction:singleActionList) {
				        singleSenAction.setMarker(actionMarker);
				    }//Action关系
				    singleSentence.setSingleMarker(determineSingleMarker(singleSenDepList));    
				}
			}
		}
		public String determineSingleMarker(List<TypedDependency> singleSenDepList) {
			String marker = "";
			for(TypedDependency td:singleSenDepList) {
				if(td.reln().toString().equals("advcl:loc")&&td.dep().value().equals("必要")){//状语从句修饰词
					marker = "jump";
					break;
				}
				else if(td.reln().toString().equals("advmod")&&td.dep().value().equals("同时")) {
					marker = "parallel";//副词修饰
				}
				else if(td.reln().toString().equals("advmod")&&td.dep().value().equals("或者")) {
					marker = "select";}//复合名词修饰
					else if(td.reln().toString().equals("nmod:range")&&td.gov().value().equals("返回"))
						{
						 marker="return";
                         preActor=td.dep().value();	
						}
					else if(td.reln().toString().equals("advmod")&&td.dep().value().equals("如果"))
					{
						marker="condition";
					}
				
					//nmod:prep(返回-4, 老师-6)
				
			}
			
			return marker;
			
		}
		
		public String determineActionMarker(ArrayList<SentenceAction> singleActionList,
				List<TypedDependency> singleSenDepList) {
			if(singleActionList.size()<2) {
				return null;
			}
			else {
				String marker = "";
				for(SentenceAction action:singleActionList) {
					//System.out.println(action.getRawAction());
					for(TypedDependency td:singleSenDepList) {
						if(td.reln().toString().equals("cc")
							&&td.dep().value().equals("或")//cc(请假-3, 或-5)
							&&td.gov().value().equals(action.getRawAction())) {
							marker = "select";	
							break;
						}//并列关系，一般取第一个词
						else if(td.reln().toString().equals("cc")
								&&td.gov().value().equals(action.getRawAction())
								&&td.dep().value().equals("同时")) {
							marker = "parallel";
						}
					}	
				}
				return marker;
			}
		}
		
		public String determineActorMarker(ArrayList<SentenceActor> singleActorList,
				List<TypedDependency> singleSenDepList) {//主语列表和依赖关系列表
			if(singleActorList.size()<2) {//只有一个主语，就直接是顺序关系
				return null;
			}
			else {
				String marker = "";
				for(SentenceActor actor:singleActorList) {
					for(TypedDependency td:singleSenDepList) {
						if(td.reln().toString().equals("cc")&&td.dep().value().equals("或")
								&&td.gov().value().equals(actor.getRawActor())) {//cc(副班长-3, 或-2)//这三个值应该分别代指这三个值
							marker = "select";
							break;
						}
						else if((td.reln().toString().equals("cc")
								&&td.dep().value().equals("和")
								&&td.gov().value().equals(actor.getRawActor()))||
								(td.reln().toString().equals("conj")
								&&td.gov().value().equals(actor.getRawActor()))) {
							marker = "parallel";
						}
					}	
					//System.out.println(actor.getRawActor());
				}
				return marker;
			}
		}
		
		
}
