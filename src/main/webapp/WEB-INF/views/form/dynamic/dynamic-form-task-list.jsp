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
	<script type="text/html" id="barDemo">
  		<a class="layui-btn layui-btn-xs" lay-event="sendback">退回</a>
	</script>	
	<script type="text/html" id="userbarDemo">
		<a class="layui-btn layui-btn-xs" lay-event="taskTurn">转办</a>
	</script>
	<script type="text/javascript">
	function sendBack(id)
	{
		layui.use('layer', function(){
  			var layer = layui.layer;
 			 layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			   offset:'t',
	  			area: '410px',
			  shadeClose: true,
			  content: "<div style='padding:10px;max-height:"+height+"px;overflow-y:auto;'><div id='choice'  lay-filter='choiceBar'></div></div>"
			});
		})
	  layui.use('table', function(){
			  var table = layui.table; 
			  table.render({
				//skin: 'line',
				elem: '#choice',
				url:'/activiti/pressanykeytoac/sendback/list?IDNode='+id+'&nowNode='+id
				,cols: [[
				  {field:'key', width:'120', title: 'ID',sort: true}
				  ,{field:'name', width:'130', title: '用户名称',sort: true}
				  ,{title: '操作',align:'center' , toolbar: '#barDemo',style:'text-align: center;'}
				]],
			  });
			  table.on('tool(choiceBar)', function(obj){
				    var data = obj.data,layEvent = obj.event;
					if(layEvent === 'sendback'){
						$.ajax({											
							url:"/activiti/pressanykeytoac/sendback/handle?aimNode="+data.key+"&nowNode="+id+"&id="+data.id,
							success:function(done)
							{
								if(done == "1") 
								{ 
										layui.use('layer', function(){
										  var layer = layui.layer;
										  layer.msg(data.name+": 回退成功", {icon: 1,time:1000},function(){window.location.reload();});
										});
								}
								else
								{
										layui.use('layer', function(){
										  var layer = layui.layer;
										  layer.msg(data.name+": 回退失败", {icon: 5});
										});
								}
							},
							error:function()
							{
									layui.use('layer', function(){
										  var layer = layui.layer;
										  layer.msg("网络故障", {icon: 5});
										});
								
							}
						});
				      };
				    });
				  });
	}

function taskTurn(id){
	layui.use('layer', function(){
			var layer = layui.layer;
			 layer.open({
		  type: 1,
		  title: false,
		  closeBtn: 0,
		   offset:'t',
  			area: '410px',
		  shadeClose: true,
		  content: "<div style='padding:10px;max-height:"+height+"px;overflow-y:auto;'><div id='userchoice'  lay-filter='userchoiceBar'></div></div>"
		});
	})
  layui.use('table', function(){
		  var table = layui.table; 
		  table.render({
			//skin: 'line',
			elem: '#userchoice',
			url : '/activiti/pressanykeytoac/users/load'
			,cols: [[
			  {field:'id', width:'120', title: '任务KEY',sort: true}
			  ,{field:'name', width:'130', title: '任务名称',sort: true}
			  ,{title: '操作',align:'center' , toolbar: '#userbarDemo',style:'text-align: center;'}
			]],
		  });
		  table.on('tool(userchoiceBar)', function(obj){
			    var data = obj.data,layEvent = obj.event;
				if(layEvent === 'taskTurn'){
					$.ajax({											
						url:"/activiti/pressanykeytoac/turn/handle?taskID="+id+"&ID="+data.id,
						success:function(done)
						{
							if(done == "1") 
							{ 
									layui.use('layer', function(){
									  var layer = layui.layer;
									  layer.msg(data.name+": 转办成功", {icon: 1,time:1000},function(){window.location.reload();});
									});
							}
							else
							{
									layui.use('layer', function(){
									  var layer = layui.layer;
									  layer.msg(data.name+": 转办失败", {icon: 5});
									});
							}
						},
						error:function()
						{
								layui.use('layer', function(){
									  var layer = layui.layer;
									  layer.msg("网络故障", {icon: 5});
									});
							
						}
					});
			      };
			    });
			  });
	}
	</script>
	
</head>

<body>
<fieldset  style='margin-top:10px;margin-bottom:10px;' class="layui-elem-field layui-field-title">
  <legend style="font:14px Tahoma, serif;" >任务列表</legend>
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
				<c:if test="${empty task.assignee }">
					<a class="claim" href="${ctx }/form/dynamic/task/claim/${task.id}?processType=${param.processType}">签收</a>
				</c:if>
				<c:if test="${not empty task.assignee }">
					<%-- 此处用tkey记录当前节点的名称 --%>
					<a style="cursor: pointer;" class="handle" tkey='${task.taskDefinitionKey }' tname='${task.name }' tid='${task.id }'>办理</a>
					<a style="cursor: pointer;" onclick="taskTurn('${task.id }')">转办</a>
					<a style="cursor: pointer;" onclick="sendBack('${task.processInstanceId }')">退回</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>

	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>

</body>
</html>
