package com.pressanykeytoac.controller.naturallanguage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cn.lab.main.MainMethod;

@Controller
@RequestMapping("/natural")
@Service
public class naturallanguagepageController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	 @ResponseBody
	 @RequestMapping(value = "/page")
	 public ModelAndView naturalview()
	 {
		return new ModelAndView("/WEB-INF/views/workflow/natural.html");
	 }
	 
	 @ResponseBody
	 @RequestMapping(value = "/solve", method = RequestMethod.POST)
	 public String solve(@Param("text") String text,@Param("fileName") String fileName) throws UnsupportedEncodingException, XMLStreamException {
		 MainMethod mainMethod = new MainMethod(text);
		 String resourceName = fileName + ".bpmn";
		 String id = repositoryService.createDeployment().addString(resourceName, mainMethod.getText()).deploy().getId();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionResourceName(resourceName).deploymentId(id).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(id,
	                resourceName);
	    XMLInputFactory xif = XMLInputFactory.newInstance();
	    InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
	    XMLStreamReader xtr = xif.createXMLStreamReader(in);
	    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
	    BpmnJsonConverter converter = new BpmnJsonConverter();
	    com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
	    Model modelData = repositoryService.newModel();
	    modelData.setKey(processDefinition.getKey());
	    modelData.setName(processDefinition.getResourceName());
	    modelData.setCategory(processDefinition.getDeploymentId());
	    ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
	    modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
	    modelData.setMetaInfo(modelObjectNode.toString());
	    repositoryService.saveModel(modelData);
	    repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
	    System.err.println("----------已加载-----------");
	    return "1";
//	    ModelAndView mav = new ModelAndView("/WEB-INF/views/workflow/model-list.jsp");
//        List<Model> list = repositoryService.createModelQuery().list();
//        mav.addObject("list", list);
//        System.err.println(mav);
//        return mav;
	 }
}
