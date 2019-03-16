<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>Activiti</title>
	<script>
		var logon = ${not empty user};
		if (logon) {
			location.href = '${ctx}/main/index';
		}
	</script>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <style type="text/css">
        .login-center {
            width: 600px;
            margin-left:auto;
            margin-right:auto;
        }
        #loginContainer {
            margin-top: 3em;
        }
        .login-input {
            padding: 4px 6px;
            font-size: 14px;
            vertical-align: middle;
        }
		.bin
		{
			margin-top: 5px;
			border-bottom: 1px solid #c0c0c0;
			width:292px;
			margin-left: 50px;
			height: 44px;
		}
    </style>

  <script src="/activiti/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="/activiti/js/common/plugins/jui/jquery-ui-1.9.2.min.js" type="text/javascript"></script>
    <script src="/activiti/js/common/layui/layui.js" type="text/javascript"></script> 
	 <link rel="stylesheet" href="/activiti/js/common/layui/css/layui.css">
	 <link rel="stylesheet" href="/activiti/js/common/login/index.css">
    <script src="/activiti/js/common/login/verify.js" type="text/javascript"></script>
	<link rel="stylesheet" href="/activiti/js/common/login/verify.css">
    <script type="text/javascript">
	function msg(message) {
		layui.use('layer', function(){
	  var layer = layui.layer;
	  layer.msg(''+message+'', {
		  area: '200px',
	  offset: 'v',
	  anim: 6
	});
	//layer.msg('TEST', {icon: 2});
	});
	}
	 function validate()
	 {
	 	var logid=$("#loginid").val();
	 	
	 	if(flag==0) 
	 	{
	 		msg("请进行验证");
	 		return false;
	 	}
	 	else if (logid=="") {
	 		msg("用户名不能为空");
	 		return false;
	 	}
	 	else return true;
	 }
	</script>
<body>


    <div id="loginContainer" class="login-center">
    <c:if test="${not empty param.error}">
            <script type="text/javascript">
            msg("用户名或密码错误");
            </script>
        </c:if>
        <c:if test="${not empty param.timeout}">
            <script type="text/javascript">
            msg("未登录");
            </script>
        </c:if>
		<form id="form" class="layui-form layui-form-pane">
			<div id="main">
			<div style="font-size:3em;margin-top:3px;margin-bottom:9px;">Activiti</div>

			<!--<div class="layui-form-item" style="background:#fff;">
   			 <label class="layui-form-label" style="margin-top:6px;margin-left:50px;width:70px;">账号: </label>
   		 <div class="layui-input-inline">
     			 <input style="width:223px;" type="text" name="username" id="loginid" class="layui-input">
    		 </div>
			</div>
			<div class="layui-form-item" style="margin-top:-8px;">
    			<label class="layui-form-label" style="margin-top:6px;margin-left:50px;width:70px;">密码:  </label>
    		<div class="layui-input-inline">
     		 <input style="width:223px;" type="password" name="password" id="passid" class="layui-input">
   		 </div>
		</div>-->
		<div class="bin" style="">
		<div style="height:8px;"></div>
		<label style='display:inline; font: 14px Arial;'>帐号：&nbsp;</label><input style="font: 15px Arial;border: none;width:223px;display:inline;height:20px;" type="text" name="username" id="loginid" >
		</div>
		<div class="bin" style="">
		<div style="height:12px;"></div>
		<label style='display:inline; font: 14px Arial;'>密码：&nbsp;</label><input style="font: 15px Arial;border: none;width:223px;display:inline;" type="password" name="password" id="passid" >
		</div>
		
		<div id='limit'><div style=' font: 12px Microsoft YaHei;' id="mpanel" ></div></div>
			<button id="login"  type="submit" onclick='return validate()' style="margin-left:50px;margin-top:10px;margin-bottom:-10px;">登录</button>
			</div>
		</form>
	
<script type="text/javascript">
layui.use('form', function(){
  var form = layui.form; 
  form.render();
});   
</script>
<script type="text/javascript">
$('#mpanel').slideVerify({
		type : 1,		
		vOffset : 5,	
		barSize : {
			width : '98%',
			height : '35px',
		},
		ready : function() {
		},
		success : function() {
			$("#form").attr('action','/activiti/user/logon');
			$("#form").attr('method','get');
			flag=1;
		},		
	});
</script>
</div></body></html>
</html>
