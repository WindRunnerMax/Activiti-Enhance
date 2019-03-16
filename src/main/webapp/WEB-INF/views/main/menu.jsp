<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--<script src="${ctx }/js/common/layui/layui.js" type="text/javascript"></script> 
<link rel="stylesheet"  href="${ctx }/js/common/layui/css/layui.css" />-->
<style type="text/css">
	a:hover{background: #fff;}
</style>
<ul id="css3menu" style='margin-left:-328px;background:#fff;border:         0px solid #fff;width:50px;margin-top:53px;'>
	<li class="topfirst"><a style='width:173px;background:#fff;padding:10px;border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;cursor: pointer;' rel="main/welcome"><i class="layui-icon layui-icon-home" style="position: relative;font-size: 14px;"></i>  首页</a></li>
	<!--<li>
		<a rel="#" style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius:1px;text-align: center;'>请假（普通表单）</a>
		<ul>
			<li><a rel="oa/leave/apply">请假申请(普通)</a></li>
			<li><a rel="oa/leave/list/task">请假办理(普通)</a></li>
			<li><a rel="oa/leave/list/running">运行中流程(普通)</a></li>
			<li><a rel="oa/leave/list/finished">已结束流程(普通)</a></li>
		</ul>
	</li>-->
	<!--<li>
		<a rel="#" style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;'>动态表单</a>
		<ul>
			<li><a rel="form/dynamic/process-list">流程列表</a></li>
			<li><a rel="form/dynamic/task/list">任务列表</a></li>
			<li><a rel="form/dynamic/process-instance/running/list">运行中流程表</a></li>
			<li><a rel="form/dynamic/process-instance/finished/list">已结束流程</a></li>
		</ul>
	</li>
	<li>
		<a rel="#" style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;'>外置表单</a>
		<ul>
			<li><a rel="form/formkey/process-list">流程列表(外置)</a></li>
			<li><a rel="form/formkey/task/list">任务列表(外置)</a></li>
			<li><a rel="form/formkey/process-instance/running/list">运行中流程表(外置)</a></li>
			<li><a rel="form/formkey/process-instance/finished/list">已结束流程(外置)</a></li>
		</ul>
	</li>-->
	
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel="form/dynamic/process-list?processType=all"><i class="layui-icon layui-icon-spread-left" style="position: relative;font-size: 14px;"></i>  流程列表</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel="form/dynamic/task/list?processType=all"><i class="layui-icon layui-icon-form" style="position: relative;font-size: 15px;"></i>  任务列表</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel="pressanykeytoac/callback/list"><i class="layui-icon layui-icon-prev-circle" style="position: relative;font-size: 15px;"></i>  撤回任务</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel="pressanykeytoac/natural/page"><i class="layui-icon layui-icon-util" style="position: relative;font-size: 15px;"></i>  快速建模</a></li>
     <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel='workflow/processinstance/running'><i class="layui-icon layui-icon-console" style="position: relative;font-size: 14px;"></i>  运行中流程</a></li>
     <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel="form/dynamic/process-instance/finished/list?processType=all"><i class="layui-icon layui-icon-log" style="position: relative;font-size: 14px;"></i>  已结束流程</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel='workflow/process-list'><i class="layui-icon layui-icon-app" style="position: relative;font-size: 14px;"></i>  已部署流程</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' rel='workflow/model/list'><i class="layui-icon layui-icon-layouts" style="position: relative;font-size: 14px;"></i>  模型工作区</a></li>
	  <c:if test="${user.id eq 'admin'}">
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' href="#" rel='management/database'><i class="layui-icon layui-icon-engine" style="position: relative;font-size: 14px;"></i>  数据库</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' href="#" rel='management/job/list'><i class="layui-icon layui-icon-tabs" style="position: relative;font-size: 13px;"></i>  作业管理</a></li>
	  <li><a style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' href="#" rel='management/identity/user/list'><i class="layui-icon layui-icon-user" style="position: relative;font-size: 14px;"></i>  用户与组</a></li>
	 </c:if>
	 <!--<a href="${ctx }/workflow/processinstance/running" >123</a>-->
  <!-- test <li>
        <a rel='#' style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' title="不区分表单类型，可以显示设计器设计后部署的流程">综合流程</a>
        <ul>
            <li><a rel="form/dynamic/process-list?processType=all">流程列表</a></li>
            <li><a rel="form/dynamic/task/list?processType=all">任务列表</a></li>
            <li><a rel="form/dynamic/process-instance/running/list?processType=all">运行中流程表</a></li>
            <li><a rel="form/dynamic/process-instance/finished/list?processType=all">已结束流程</a></li>
        </ul>
    </li>
	<li>
		<a rel="#" style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' >管理模块</a>
		<ul>
			<li>
				<a rel='#'>流程管理</a>
				<ul>
					<li><a rel='workflow/process-list'>流程定义及部署管理</a></li>
					<li><a rel='workflow/processinstance/running'>运行中流程</a></li>
					<li><a rel='workflow/model/list'>模型工作区</a></li>
				</ul>
			</li>
			<!--<li><a href="#" rel='management/engine'>引擎属性</a></li>
			<li><a href="#" rel='management/database'>引擎数据库</a></li>
			<li><a href="#" rel='management/job/list'>作业管理</a></li>
			<li><a href="#" rel='management/identity/user/list'>用户与组</a></li>
		</ul>
	</li>-->
	<!--<li>
		<a rel="#" style='width:173px;margin-top:5px;background:#fff;padding:10px;          border: 1px solid #c0c0c0;-moz-border-radius: 10px; -webkit-border-radius: 10px; border-radius:1px;text-align: center;' >Rest示例</a>
		<ul>
			<li><a href="${ctx}/rest/management/properties" target="_blank">引擎属性</a></li>
			<li><a href="${ctx}/rest/runtime/tasks" target="_blank">我的任务</a></li>
			<li><a href="${ctx}/rest/runtime/executions" target="_blank">我参与的流程</a></li>
			<li><a href="${ctx}/rest/management/tables" target="_blank">数据库表</a></li>
			<li><a href="${ctx}/rest/identity/users" target="_blank">用户</a></li>
			<li><a href="${ctx}/rest/identity/groups" target="_blank">组</a></li>
		</ul>
	</li>-->
</ul>

