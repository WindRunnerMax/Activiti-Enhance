package cn.lab.main;

import java.util.ArrayList;
import cn.lab.BPMNmodel2Graph.Graph2Model;
import cn.lab.POM.AllSentenceInfo;
import cn.lab.graphGenerate.ModelGenerate;
import cn.lab.graphpack.Graph;
import cn.lab.sentenceParser.SenParser;
import relationExtraction.relaExtraction;


public class MainMethod {
	private String text;
	
	public MainMethod(String text) {
		super();
		this.text = text;
	}
	public String getText()
	{
		SenParser senPar1 = new SenParser();
		ArrayList<AllSentenceInfo> senParser1 = senPar1.mainSenParser(text);

		relaExtraction relation1 = new relaExtraction(senParser1);
		relation1.markerDetection();
		ModelGenerate mg1 = new ModelGenerate(senParser1,relation1.getPreActor());
		Graph senModel1 = mg1.getSenModel();
		//senModel1.printfGraph();
		Graph2Model gr =new Graph2Model();
		return gr.graph2Model(senModel1);
		
	}
	public static void main(String[] args) {
		
		SenParser senPar1 = new SenParser();
		ArrayList<AllSentenceInfo> senParser1 = senPar1.mainSenParser("./yuanFile/zzcTest.txt");
		relaExtraction relation1 = new relaExtraction(senParser1);
		relation1.markerDetection();
		ModelGenerate mg1 = new ModelGenerate(senParser1,relation1.getPreActor());
		Graph senModel1 = mg1.getSenModel();
		senModel1.printfGraph();
		Graph2Model gr =new Graph2Model();
		gr.graph2Model(senModel1);
	/*	for(AllSentenceInfo asi:senParser1) {
 			System.out.println("******************************************************************");
 			System.out.println("整句ID-->"+asi.getSentenceID());
 			System.out.println("整句字符串-->"+asi.getRawSentence());
   			System.out.println("整句的依赖关系-->"+asi.getRawDependency());
  			System.out.println("整句Penn树-->"+asi.getPennTree());
 			List<SingleSentenceInfo> singleSenList = asi.getAllClause();
 			for(SingleSentenceInfo ssi:singleSenList) {
 				System.out.println("-------------------------------------------------------------");
  				System.out.println("子句字符串-->"+ssi.getSentenceStr());
  				System.out.println("子句分词以及词性标注-->"+ssi.getword_tagStr());
  				System.out.println("子句依赖关系-->"+ssi.getSentenceDependency());
  				System.out.println("子句Penn树-->"+ssi.getSentencePennTree());
  				System.out.println("子句的marker是"+ssi.getSingleMarker());
 				List<SentenceActor> actorList = ssi.getActorList();
 				for(SentenceActor senActor:actorList) {
 					//System.out.println(senActor.getRawActor());
 					System.out.println("子句的actor:"+senActor.getActor()+"actor的Marker:"+senActor.getMarker());
 				}
 				List<SentenceAction> actionList = ssi.getActionList();
 				for(SentenceAction senAction:actionList) {
 					//System.out.println(senActor.getRawActor());
 					System.out.println("子句的action:"+senAction.getAction()+"action的Marker:"+senAction.getMarker());
 				}
 			}
		}//end asi
		*/
  }
}
