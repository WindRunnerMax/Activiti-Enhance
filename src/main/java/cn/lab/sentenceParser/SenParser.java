package cn.lab.sentenceParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.lab.POM.AllSentenceInfo;
import cn.lab.POM.SentenceAction;
import cn.lab.POM.SentenceActor;
import cn.lab.POM.SingleSentenceInfo;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreeReader;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;


public class SenParser {
	/*stanford parser工具的所有类
	 https://nlp.stanford.edu/nlp/javadoc/javanlp/allclasses-noframe.html*/
	public static LexicalizedParser lp;
	public static LexicalizedParser getLp() {
		return lp;
	}
	public static void setLp(LexicalizedParser lp) {
		SenParser.lp = lp;
	}
	
	public static TreebankLanguagePack tlp;
	public static TreebankLanguagePack getTlp() {
		return tlp;
	}
	public static void setTlp(TreebankLanguagePack tlp) {
		SenParser.tlp = tlp;
	}
	
	public SenParser() {
		String modelPath = "edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz";
		lp = LexicalizedParser.loadModel(modelPath);
		tlp = lp.treebankLanguagePack();
	}
	
	
	public ArrayList<AllSentenceInfo> mainSenParser(String paragrah){
		//文本文件转换成字符串形式
		//String paragrah = text2string(filename);
		//段落分解成整句
		//System.out.println(paragrah);
		List<String> para2sentList = para2sent(paragrah);//根据句号把语段分开。
		//System.out.println("para2sent");
	
		ArrayList<AllSentenceInfo> allSenInfoList = new ArrayList<AllSentenceInfo>();
	    //针对段落中的每一个整句
		for(int i=0; i<para2sentList.size();i++) {	
			String sentSingle = para2sentList.get(i);
			Tree t = lp.parse(sentSingle);
			//获取penn树的解析结果
			String pennTree = getPenn(t,"penn");
			//System.out.println(pennTree);
			//获取依赖关系列表
			List<TypedDependency> dependencyList = getDependency(t);
			//System.out.println(dependencyList);
			Reader reader = new StringReader(pennTree);
			PennTreeReader pennTreeReader = new PennTreeReader(reader);
			Tree pennStrTree;
			try {
				pennStrTree = pennTreeReader.readTree();
			}
			
			catch( IOException e) {
				pennStrTree = null;
				e.printStackTrace();
				}
			AllSentenceInfo asi = new AllSentenceInfo();
			asi.setSentenceID(i);
			asi.setRawSentence(sentSingle);
			asi.setPennTree(pennStrTree);
			asi.setRawDependency(dependencyList);
			ArrayList<SingleSentenceInfo> clauseList = sentDecomposition(asi);
			asi.setAllClause(clauseList);
			allSenInfoList.add(asi);
			
			
		}
		return allSenInfoList;
	}
	
	
	public String text2string(String filename) {//将文本转化成字符串
		File file = new File(filename);
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while((s =br.readLine())!=null) {
				sb.append(System.lineSeparator()+s);
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}
	public List<TypedDependency> getDependency(Tree t) {
		if (t == null)
			return null;

		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(t);
		List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		return tdl;
	}
	
	public  String getPenn(Tree t, String option) {
		TreePrint tp = new TreePrint(option);
		Writer treeString = new StringWriter();
		PrintWriter pw = new PrintWriter(treeString);
		tp.printTree(t, pw);
		return treeString.toString();
	}
	//将段落分成整句
	public List<String> para2sent(String paragrah) {
		paragrah = paragrah.trim();
		
		List<String> para2sentList=Arrays.asList(paragrah.split("\\。"));
		List<String> result=new ArrayList<String>();
		int index=1;
		for(String s:para2sentList)
		{   
			StringBuffer sb = new StringBuffer(s);
			sb.insert(0,""+index+" ");
			index++;
			s=sb.toString();
			result.add(s);
		}
		//System.out.println(result);
		return result;
	}
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////	
	//将整句分解成子句,算法1改为按“，”分解子句
	public ArrayList<SingleSentenceInfo> sentDecomposition(AllSentenceInfo asi) {
		ArrayList<SingleSentenceInfo> singleSenInfoList = new ArrayList<SingleSentenceInfo>();
		String sentence = asi.getRawSentence();
		sentence = sentence.trim();
		List<String> singleSenList = Arrays.asList(sentence.split("\\，"));
		for(int ssl=0;ssl<singleSenList.size();ssl++) {
			
			String singleSentence = singleSenList.get(ssl);
			
			Tree t = lp.parse(singleSentence);
			//获取penn树的解析结果
			String pennTree = getPenn(t,"penn");
			//获取依赖关系列表
			List<TypedDependency> dependencyList = getDependency(t);
			Reader reader = new StringReader(pennTree);
			PennTreeReader pennTreeReader = new PennTreeReader(reader);
			Tree pennStrTree;
			try {
				pennStrTree = pennTreeReader.readTree();
			}
				catch( IOException e) {
				pennStrTree = null;
				e.printStackTrace();
				}
			
			String word_tagStr ="";
			for(int i=0; i<dependencyList.size(); i++)
			{
				TypedDependency td = (TypedDependency)dependencyList.toArray()[i];
				String age = td.dep().toString();
				word_tagStr+=age;
				word_tagStr+=" ";
			}
			SingleSentenceInfo ssi = new SingleSentenceInfo();
			ssi.setword_tagStr(word_tagStr);
			ssi.setSentenceDependency(dependencyList);
			ssi.setSentenceStr(singleSentence);
			ssi.setSentencePennTree(pennStrTree);
			if(pennStrTree!=null) {
				//寻找任务执行组织机构Actor,即句子主语
				ArrayList<SentenceActor> actorList = new ArrayList<SentenceActor>();
				ArrayList<String> rawActorList = determineActors(dependencyList);
				//找最小NP的方法存在两个缺点：一是找到的Actor不完整，需要进一步通过追加amod,compound:nn,appos依赖关系补全；
				//二是找到的Actor粒度太大，包含好几个Actor,需要根据连接词进一步分解。
				//直接将determineActors得到的rawActor传给Actor关系判断函数。
				for(String rawactor:rawActorList) {
					SentenceActor senActor = new SentenceActor();
					String fullActor = findFullActor(rawactor,dependencyList );
					senActor.setRawActor(rawactor);
					senActor.setActor(fullActor);
					actorList.add(senActor);
				}
				ssi.setActorList(actorList);
				
				//寻找任务描述动宾短语的形式,同理actor,找最小的VP
				ArrayList<SentenceAction> actionList = new ArrayList<SentenceAction>();
				ArrayList<String> rawActionList = determineAction(dependencyList);
				for(String rawaction:rawActionList) {
					String actionVP = findMinVP(pennStrTree, rawaction, pennStrTree);
					//String actionVP = findMaxVP(pennStrTree,rawaction, pennStrTree);
					SentenceAction senAction = new SentenceAction();
					senAction.setRawAction(rawaction);
					senAction.setAction(actionVP);
					actionList.add(senAction);
				 }
				ssi.setActionList(actionList);
				}
			singleSenInfoList.add(ssi);	
		}
		return singleSenInfoList;
		
	}
	private ArrayList<String> determineAction(List<TypedDependency> dependencyList) {
		ArrayList<String> actionList = new ArrayList<String>();
		for(TypedDependency tpdp:dependencyList) {
			if(tpdp.reln().toString().equals("root")) {
				actionList.add(tpdp.dep().value());
				for(TypedDependency stpdp:dependencyList) {
					if(stpdp.reln().toString().equals("conj")
							&&stpdp.gov().value().equals(tpdp.dep().value())) {
						actionList.add(stpdp.dep().value());
					}
				}
			}
		}
		//System.out.println(actorSet);
		return actionList;
	}
	//寻找最小VP动词短语
	public String findMinVP(Tree rawPennTree, String targetWord, Tree root) {
		if(rawPennTree.isLeaf()&&rawPennTree.toString().equals(targetWord)) {
			Tree pTree = rawPennTree.parent(root);
			//找到目标词的最小NP
			while(!pTree.label().toString().equals("VP")) {
				if(pTree.equals(root)) {
					break;
				}
				pTree = pTree.parent(root);
			}
			//拼接最小NP中的word
			String NPStr = "";
			List<Tree> pTreeChildTreeList = pTree.getChildrenAsList();
			ArrayList<String> minNPStrList = traversePennTree(pTreeChildTreeList);
			for(String mnsl:minNPStrList) {
				NPStr =NPStr+ mnsl;
			}
			return NPStr;
		}else {
			List<Tree> childList = rawPennTree.getChildrenAsList();
			String resultStr = "";
			if (!childList.isEmpty()) {
				for(Tree ct:childList) {
					resultStr = findMinVP(ct,targetWord, root);
					if(!resultStr.isEmpty()) {
						break;
					}
				}
			}
			return resultStr;
		}
	}
	public String findMaxVP(Tree NowNode,String targetWord, Tree root) {
		if(NowNode.label().toString().equals("VP")) {
			return splicingTree(NowNode);	
		}
		else {
			List<Tree> childList = NowNode.getChildrenAsList();
			for(Tree ct:childList){
				String findMaxVP = findMaxVP(ct,targetWord,root);
				if(findMaxVP!=null)
					return findMaxVP;
			}
		}
		return null;
	}
	public String splicingTree(Tree pTree) {
		//拼接VP中的word
		String NPStr = "";
		List<Tree> pTreeChildTreeList = pTree.getChildrenAsList();
		ArrayList<String> minNPStrList = traversePennTree(pTreeChildTreeList);
		for(String mnsl:minNPStrList) {
			NPStr =NPStr+ mnsl;
		}
		return NPStr;
	}
			
	
	
	
	//判定Actor方法
	public ArrayList<String> determineActors(List<TypedDependency> dependencyList) {
		ArrayList<String> actorList = new ArrayList<String>();
		for(TypedDependency tpdp:dependencyList) {
			if(tpdp.reln().toString().equals("nsubj")|tpdp.reln().toString().equals("nmod:topic")) {
				actorList.add(tpdp.dep().value());
				for(TypedDependency stpdp:dependencyList) {
					if(stpdp.reln().toString().equals("conj")
							&&stpdp.gov().value().equals(tpdp.dep().value())) {
						actorList.add(stpdp.dep().value());
					}
				}    
			}
		}
		//System.out.println(actorSet);
		return actorList;
	}//end determineActors
	/*
	 * findMinNP:寻找要目标单词的最小NP包含，即最小名词短语
	 * findMinNP方法是determineActors函数中使用的方法，寻找主语的最小名词短语
	 *  输入：
	 *   rawPennTree：penn树
	 *   targetWord：要寻找的词
	 *   root:penn树的根
	 *  输出：
	 *   最小名词短语字符串
	 * */
	public String findMinNP(Tree rawPennTree, String targetWord, Tree root) {
		if(rawPennTree.isLeaf()&&rawPennTree.toString().equals(targetWord)) {
			Tree pTree = rawPennTree.parent(root);
			//找到目标词的最小NP
			while(!pTree.isEmpty()) {
				if(pTree.label().toString().equals("NP")) {
					break;
				}
				pTree = pTree.parent(root);
			}
			//拼接最小NP中的word
			String NPStr = "";
			List<Tree> pTreeChildTreeList = pTree.getChildrenAsList();
			ArrayList<String> minNPStrList = traversePennTree(pTreeChildTreeList);
			for(String mnsl:minNPStrList) {
				NPStr =NPStr+ mnsl;
			}
			return NPStr;
		}else {
			List<Tree> childList = rawPennTree.getChildrenAsList();
			String resultStr = "";
			if (!childList.isEmpty()) {
				
				for(Tree ct:childList) {
					resultStr = findMinNP(ct,targetWord, root);
					if(!resultStr.isEmpty()) {
						break;
					}
				}
			}
			return resultStr;
		}
		
	}
	public String findFullActor(String rawActor,List<TypedDependency> dependencyList ) {
		List<String> fullActorList = new ArrayList<String>();
		if(!rawActor.isEmpty()) {
			for(int i=0;i<dependencyList.size();i++) {
				TypedDependency td = dependencyList.get(i);
				if(td.dep().value().equals(rawActor)) {
					fullActorList.add(rawActor);
					for(int j=i-1;j>=0;j--) {
						TypedDependency std = dependencyList.get(j);
						if((std.reln().toString().equals("amod")|std.reln().toString().equals("compound:nn")
						  |std.reln().toString().equals("appos"))) {
							fullActorList.add(0,std.dep().value());	
						}
						else {
							break;
						}
					}
				}
			}
		}
		String fullActor = "";
		for(String actor:fullActorList) {
			fullActor = fullActor+actor;
		}
		return fullActor;
	}
	

	public ArrayList<String> traversePennTree(List<Tree> treeList) {
		ArrayList<String> travResultList = new ArrayList<String>();
		for(Tree listItem:treeList) {
			if(listItem.isLeaf()) {
				travResultList.add(listItem.toString());
			}else {
				travResultList.addAll(traversePennTree(listItem.getChildrenAsList()));
			}
		}
		return travResultList;
		
	}//end of traversePennTree
}
