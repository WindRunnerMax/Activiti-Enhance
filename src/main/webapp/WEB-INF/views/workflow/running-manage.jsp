<%@page import="me.kafeitu.demo.activiti.util.ProcessDefinitionCache,org.activiti.engine.RepositoryService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>管理运行中流程</title>
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
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script type="text/html" id="userbarDemo">
		<a class="layui-btn layui-btn-xs" lay-event="taskTurn">转办</a>
	</script>
	<script type="text/javascript">
	var height=$(window).height()*0.55;
	$(function() {
		// 跟踪
	    $('.trace').click(graphTrace);
	});
	function nowOwner(procID)
	{
		$.ajax({												//ajax
			type:"get",
			url:"/activiti/pressanykeytoac/turn/nowhandler?procID="+procID
			,dataType:"text",							//返回类型
			success:function(data)
			{
				if(data==null) alert("Error");
				else{
					layui.use('layer', function(){
						var layer = layui.layer;
							layer.tips(data, '#'+procID,{tips: 1,tipsMore: true});
						})
				}
			},
			error:function()
			{
				alert("Error");
			}
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
				  {field:'id', width:'120', title: 'ID',sort: true}
				  ,{field:'name', width:'130', title: '用户名称',sort: true}
				  ,{title: '操作',align:'center' , toolbar: '#userbarDemo',style:'text-align: center;'}
				]],
			  });
			  table.on('tool(userchoiceBar)', function(obj){
				    var data = obj.data,layEvent = obj.event;
					if(layEvent === 'taskTurn'){
						$.ajax({											
							url:"/activiti/pressanykeytoac/turn/forcehandle?procID="+id+"&ID="+data.id,
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
  <legend style="font:14px Tahoma, serif;" >运行中流程</legend>
</fieldset>
	<%
	RepositoryService repositoryService = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()).getBean(org.activiti.engine.RepositoryService.class);
	ProcessDefinitionCache.setRepositoryService(repositoryService);
	%>
	<c:if test="${not empty message}">
	<script type="text/javascript">
	layermsgsuccess("${message}");
	</script>
	</c:if>
	<table   class="layui-table" lay-size="sm" >
		<tr style='background:#f3f3f3;'>
			<th>执行IDssss</th>
			<th>流程实例ID</th>
			<th>流程定义ID</th>
			<th style='text-align:center;'>当前节点</th>
			<th style='text-align:center;'>是否挂起</th>
			<th style='text-align:center;'>操作</th>
		</tr>

		<c:forEach items="${page.result }" var="p">
		<c:set var="pdid" value="${p.processDefinitionId }" />
		<c:set var="activityId" value="${p.activityId }" />
		<tr>
			<td>${p.id }</td>
			<td>${p.processInstanceId }</td>
			<td>${p.processDefinitionId }</td>
			<td style='text-align:center;'>
			<a class="trace" style="cursor: pointer;" pid="${p.id }" pdid="${p.processDefinitionId}" title="点击查看流程图"><%=ProcessDefinitionCache.getActivityName(pageContext.getAttribute("pdid").toString(), ObjectUtils.toString(pageContext.getAttribute("activityId"))) %></a>
			<c:if test="${user.id eq 'admin'}">
			<a style="cursor: pointer;display:block;display: inline;" id="${p.processInstanceId }" title="点击查看当前办理者" onclick="nowOwner('${p.processInstanceId }')">办理者</a>
			</c:if>
			</td>
			<td style='text-align:center;'>${p.suspended }</td>
			<td style='text-align:center;'>
				<c:if test="${p.suspended }">
					<a href="update/active/${p.processInstanceId}">激活</a>
				</c:if>
				<c:if test="${!p.suspended }">
					<a href="update/suspend/${p.processInstanceId}">挂起</a>
				</c:if>
				<c:if test="${user.id eq 'admin'}">
					<a style="cursor: pointer;" onclick="taskTurn('${p.processInstanceId }')">流转</a>
				</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>

</body>
</html>
