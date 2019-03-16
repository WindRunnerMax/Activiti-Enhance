<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>任务列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/jquery.validate.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/messages_cn.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
    <script type="text/javascript">
        var processType = '${empty processType ? param.processType : processType}';
    </script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/form/dynamic/handler.js" type="text/javascript"></script>
	<!-- <script src="${ctx }/js/module/form/dynamic/dynamic-form-handler.js" type="text/javascript"></script> -->
	<style type="text/css">
	.ui-state-hover{background:#fff;}
	.inputclass{
		width: 100%;
		border: none;
	}
	.layui-table tbody tr:hover{
	background-color:transparent;
	}
	</style>
	<script type="text/javascript">
		function callBack(name,key,id)
		{
				$.ajax({											
					url:"/activiti/pressanykeytoac/callback/handle?aimNode="+key+"&nowNode="+id,
					success:function(done)
					{
						if(done == "1") 
						{ 
								layui.use('layer', function(){
								  var layer = layui.layer;
								  layer.msg(name+": 撤回成功", {icon: 1,time:1000},function(){window.location.reload();});
								});
						}
						else if(done == "3"){
							layui.use('layer', function(){
								  var layer = layui.layer;
								  layer.msg(name+": 撤回失败,接收方已办理", {icon: 5});
								});
						}
						else
						{
								layui.use('layer', function(){
								  var layer = layui.layer;
								  layer.msg(name+": 撤回失败", {icon: 5});
								});
						}
					},
					error:function()
					{
							layui.use('layer', function(){
								  var layer = layui.layer;
								  layer.msg(name+": 网络故障", {icon: 5});
								});
						
					}
				});
		}
	</script>
	
</head>

<body>
<fieldset  style='margin-top:10px;margin-bottom:10px;' class="layui-elem-field layui-field-title">
  <legend style="font:14px Tahoma, serif;" >撤回任务</legend>
</fieldset>
  
	<c:if test="${not empty message}">
	<script type="text/javascript">
	layermsgsuccess("${message}");
	</script>
	</c:if>
	<table  class="layui-table" lay-size="sm">
		<tr style='background:#f3f3f3;'>
			<th>任务ID</th>
			<th>任务Key</th>
			<th>任务名称</th>
			<th>流程定义ID</th>
			<th>流程实例ID</th>
			<th>优先级</th>
			<th>任务创建日期</th>
			<th>任务逾期日</th>
			<th>任务描述</th>
			<th>任务所属人</th>
			<th style='text-align:center;'>操作</th>
		</tr>

		<c:forEach items="${tasks }" var="task">
		<tr>
			<td>${task.id }</td>
			<td>${task.taskDefinitionKey }</td>
			<td>${task.name }</td>
			<td>${task.processDefinitionId }</td>
			<td>${task.processInstanceId }</td>
			<td>${task.priority }</td>
			<td>${task.createTime }</td>
			<td>${task.dueDate }</td>
			<td>${task.description }</td>
			<td>${task.owner }</td>
			<td style='text-align:center;'>
				<c:if test="${not empty task.assignee }">
					<%-- 此处用tkey记录当前节点的名称 --%>
					&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" onclick="callBack('${task.name }','${task.taskDefinitionKey }','${task.processInstanceId }')">撤回</a>&nbsp;&nbsp;&nbsp;
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>

	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>

</body>
</html>
