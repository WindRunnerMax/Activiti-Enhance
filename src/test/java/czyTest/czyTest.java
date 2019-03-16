package czyTest;

import org.junit.Rule;
import org.junit.Test;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.TreebankLanguagePack;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.json.*;

public class czyTest {
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	// 创建流程引擎
	// ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void test() {
		
	HistoryService historyService = activitiRule.getHistoryService();
	TaskService taskService = activitiRule.getTaskService();
	IdentityService identityService = activitiRule.getIdentityService();
	
	
		//封装json
//		JSONObject rule=new JSONObject();
//		rule.put("msg", 0);
//		rule.put("count", 3);
//		JSONArray array = new JSONArray();
//		for(int i=0;i<3;++i)
//		{
//			JSONObject json = new JSONObject();
//			json.put("id",  Integer.toString(i));
//			array.put(json);
//		}
//		rule.put("data",array);
//		System.err.println(rule.toString());
		
		
		
		//获取历史任务
//		HistoryService historyService = activitiRule.getHistoryService();
//		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId("2752").list();
//		int i=0;
//		JSONObject rule=new JSONObject();
//		rule.put("msg", 0);
//		JSONArray array = new JSONArray();
//        if (list != null && list.size() > 0) {
//            for (HistoricTaskInstance hpi : list) {
//            	++i;
//            	JSONObject json = new JSONObject();
//            	json.put("id", hpi.getTaskDefinitionKey());
//            	json.put("name", hpi.getName());
//            	array.put(json);
//            }
//        }
//        rule.put("data", array);
//        rule.put("count", i);
//        System.err.println(rule.toString());
		
		
		
		//建模测试
//		LexicalizedParser lp;
//		TreebankLanguagePack tlp;
//		String modelPath = "edu/stanford/nlp/models/lexparser/xinhuaFactoredSegmenting.ser.gz";
//		lp = LexicalizedParser.loadModel(modelPath);
//		tlp = lp.treebankLanguagePack();
//		System.err.println("success");
		
		
		
		//撤回测试
//		HistoryService historyService = activitiRule.getHistoryService();
//		List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().involvedUser("admin").list();
//		List<HistoricTaskInstance> callBackList = new ArrayList<HistoricTaskInstance>();
//		if (list != null && list.size() > 0) {
//          for (HistoricProcessInstance hpi: list) {
//        	  	if(hpi.getEndTime()==null)
//        	  	{
//        	  		List<HistoricTaskInstance> hislist = historyService.createHistoricTaskInstanceQuery().processInstanceId(hpi.getId()).list();
//        			int listSize=hislist.size();
//        			HistoricTaskInstance reCallNode=null;
//        			if(listSize>=2) reCallNode=hislist.get(listSize-2);
////        			System.err.println(reCallNode.getId());
//        			if(reCallNode.getAssignee().equals("admin")) {
//        				callBackList.add(reCallNode);
//        			}
//        	  	}
//           }
//       }
//		List<HistoricTaskInstance> delList = historyService.createHistoricTaskInstanceQuery().processInstanceId("37501").list();
//		int delListSize=delList.size();
//		System.err.println(delList.get(delListSize-2).getAssignee());
//		System.err.println(callBackList.size());
//		System.err.println(123);
		
		
		
		//回退删除测试

//		List<HistoricTaskInstance> delList = historyService.createHistoricTaskInstanceQuery().processInstanceId("17513").list();
//		int delListSize=delList.size();
//		int flag=0;
//		if(delList!=null && delListSize>0) {
//			for(HistoricTaskInstance hpi : delList) {
//				if(hpi.getId().equals("25002")) flag=1;
//				if(flag==1) historyService.deleteHistoricTaskInstance(hpi.getId());
//			}
//		}
		
	
	
	
		//任务转办测试
//		System.err.println(taskService.createTaskQuery().processInstanceId("42603").singleResult().getId());
//		String nowhander=taskService.createTaskQuery().processInstanceId("42618").singleResult().getAssignee();
//		User fullName=identityService.createUserQuery().userId(nowhander).singleResult();
//		String name=fullName.getLastName()+" "+fullName.getFirstName();
//		System.err.println(name);
//		taskService.setAssignee("42571", "admin");
//		System.err.println(taskService.createTaskQuery().processInstanceId(procID).singleResult().getId());
	
		
	
	
		//任务委派测试
//		String taskOwner = taskService.createTaskQuery().taskId("42598").singleResult().getOwner();
//		System.err.println(taskOwner);
//		taskService.delegateTask("42612", "top");
//		taskService.resolveTask("42612",map);
		
		
	}
}
