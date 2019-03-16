<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<script>
		var notLogon = ${empty user};
		if (notLogon) {
			location.href = '${ctx}/login?error=nologon';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include-base-styles.jsp" %>
	<%@ include file="/common/include-jquery-ui-theme.jsp" %>
	<%@ include file="/common/include-custom-styles.jsp" %>
	<title>流程列表</title>
<style type="text/css">
#deploy
{
	background:#fff;
		padding:3px;                				
		border: 1px solid #c0c0c0;
		-moz-border-radius: 10px; 
		-webkit-border-radius: 10px; 
		border-radius:1px;
		font: 14px Tahoma, serif;
}
</style>
	<script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#redeploy').button({
    		icons: {
    			primary: 'ui-icon-refresh'
    		}
    	});
    	$('#deploy').button({
    		icons: {
    			primary: 'ui-icon-document'
    		}
    	}).click(function() {
    		$('#deployFieldset').toggle('normal');
    	});
    });
    </script>
</head>
<body>
	<%@page import="com.pressanykeytoac.searchActivitiError.*"%>
	<%
	if(request.getParameter("id")!=null){ 
		int ID = Integer.parseInt(request.getParameter("id"));
	%>
	<script type="text/javascript">
	var tips="<%=SearchError.searchError(ID)%>";
	if(tips=="")  layermsgsuccess("未发现问题");
	else layermsgwarning(tips);
	</script>
	<% } %>
<fieldset  style='margin-top:10px;margin-bottom:6px;' class="layui-elem-field layui-field-title">
  <legend style="font:14px Tahoma, serif;" >部署管理</legend>
</fieldset>
	<c:if test="${not empty message}">
	<script type="text/javascript">
	layermsgsuccess("${message}");
	</script>
	</c:if>
	<div style="text-align: right;padding: 2px 1em 2px;margin-bottom:15px;" >
		<a style="position:absolute;text-align: right;top:35px;left:0px;" id='deploy' href='#'>部署流程</a>
		<a id='redeploy' href='${ctx }/workflow/redeploy/all' style="display:none">重新部署流程</a>
	</div>
	<br>
	<fieldset id="deployFieldset" style="display: none">
		<legend>部署新流程</legend>
		<div><b>支持文件格式：</b>zip、bar、bpmn、bpmn20.xml</div>
		<form action="${ctx }/workflow/deploy" method="post" enctype="multipart/form-data">
			<input type="file" name="file" />
			<input type="submit" value="Submit" />
		</form>
	</fieldset>
	<table width="100%" class="layui-table" lay-size="sm">
		<thead >
			<tr>
				<th>ProcessDefinitionId</th>
				<th>名称</th>
				<th>Version</th>
				<th>KEY</th>
				<th style='text-align:center;'>XML</th>
				<th style='text-align:center;'>图片</th>
				<th style='text-align:center;'>部署时间</th>
				<th style='text-align:center;'>挂起</th>
				<th style='text-align:center;'>操作</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${page.result }" var="object">
				<c:set var="process" value="${object[0] }" />
				<c:set var="deployment" value="${object[1] }" />
				<tr >
					<td>${process.id }</td>
					<td>${process.name }</td>
					<td>${process.version}</td>
					<td>${process.key }</td>
					<td style='text-align:center;'><a target="_blank" href='${ctx }/workflow/resource/read?processDefinitionId=${process.id}&resourceType=xml'>查看XML</a></td>
					<td style='text-align:center;'><a target="_blank" href='${ctx }/workflow/resource/read?processDefinitionId=${process.id}&resourceType=image'>查看流程</a></td>
					<td>${deployment.deploymentTime }</td>
					<td style='text-align:center;'>${process.suspended} |
						<c:if test="${process.suspended }">
							<a href="processdefinition/update/active/${process.id}">激活</a>
						</c:if>
						<c:if test="${!process.suspended }">
							<a href="processdefinition/update/suspend/${process.id}">挂起</a>
						</c:if>
					</td>
					<td style='text-align:center;'>
                       	<a href='${ctx }/workflow/process/delete?deploymentId=${process.deploymentId}'>删除</a>
                        <a href='${ctx }/workflow/process/convert-to-model/${process.id}'>转换</a>
                        <a href='${ctx }/workflow/process-list?id=${process.deploymentId}'>检查</a>
               </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
</body>
</html>
