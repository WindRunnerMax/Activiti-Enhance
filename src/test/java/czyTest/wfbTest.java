package czyTest;

import static org.junit.Assert.*;

import java.util.List;

import org.activiti.bpmn.model.FormProperty;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;

public class wfbTest {

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void test() {
		FormService formService = activitiRule.getFormService();
		String processDefinitionId = null;
		formService.getStartFormData(processDefinitionId);
	}
}
