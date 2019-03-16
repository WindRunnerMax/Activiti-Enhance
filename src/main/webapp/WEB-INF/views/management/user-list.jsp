<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/global.jsp"%>
    <%@ include file="/common/meta.jsp" %>
    <link rel="stylesheet" href="${ctx }/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/bootstrap-responsive.min.css" type="text/css"/>
    <link rel="stylesheet" href="${ctx }/css/style.css" type="text/css"/>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/bootstrap.min.js"></script>
    <title>用户列表--management</title>
    <script type="text/javascript">
        $(function() {
            $('.edit-user').click(function() {
                var $tr = $('#' + $(this).data('id'));
                $('#userId').val($tr.find('.prop-id').text());
                $('#firstName').val($tr.find('.prop-firstName').text());
                $('#lastName').val($tr.find('.prop-lastName').text());
                $('#email').val($tr.find('.prop-email').text());
            });

            // 组提示
            $('.groups').tooltip();

            // 点击按钮设置用户的组
            $('.set-group').click(function() {
                $('#setGroupsModal input[name=userId]').val($(this).data('userid'));
                $('#setGroupsModal input[name=group]').attr('checked', false);
                var groupIds = $(this).data('groupids').split(',');
                $(groupIds).each(function() {
                    $('#setGroupsModal input[name=group][value=' + this + ']').attr('checked', true);
                });
                $('#setGroupsModal').modal();
            });
        });
        function sub()
        {
            if($('#userId').val()=="" || $('#lastName').val()==""  || $('#email').val()=="" || $('#password').val()=="")
            {
                alert("请填写完整信息");
                return false;
            }
            else return true;
        }
    </script>
</head>
<body>
<ul class="nav nav-pills">
    <li class="active"><a href="${ctx}/management/identity/user/list"><i class="icon-user"></i>用户管理</a></li>
    <li><a href="${ctx}/management/identity/group/list"><i class="icon-list"></i>组管理</a></li>
	<li><a href="${ctx}/pressanykeytoac/tree/jump/user"><i class="layui-icon" style="color: #000;">&#xe62e;</i>用户树管理</a></li>
    <li><a href="${ctx}/pressanykeytoac/tree/jump/group"><i class="layui-icon" style="color: #000;">&#xe613;</i>组角色管理</a></li>
</ul>
<hr>
<c:if test="${not empty message}">
    <div id="message" class="alert alert-success">${message}</div>
    <!-- 自动隐藏提示信息 -->
    <script type="text/javascript">
        setTimeout(function() {
            $('#message').hide('slow');
        }, 5000);
    </script>
</c:if>
<c:if test="${not empty errorMsg}">
    <div id="messageError" class="alert alert-error">${errorMsg}</div>
    <!-- 自动隐藏提示信息 -->
    <script type="text/javascript">
        setTimeout(function() {
            $('#messageError').hide('slow');
        }, 5000);
    </script>
</c:if>
<div class="row">
    <div class="span6">
        <fieldset>
            <legend><small>用户列表</small></legend>
            <table width="100%" class="table table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th>用户ID</th>
                    <th>姓名</th>
                    <th>Email</th>
                    <th>所属组</th>
                    <th width="90" style="text-align: center;">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.result }" var="user">
                    <tr id="${user.id}">
                        <td class="prop-id">${user.id}</td>
                        <td>
                                ${user.firstName} ${user.lastName}
                            <span class="prop-firstName" style="display: none">${user.firstName}</span>
                            <span class="prop-lastName" style="display: none">${user.lastName}</span>
                        </td>
                        <td class="prop-email">${user.email}</td>
                        <c:set var="groupNames" value="${''}" />
                        <c:set var="groupIds" value="${''}" />
                        <c:forEach items="${groupOfUserMap[user.id]}" var="group" varStatus="row">
                            <c:set var="groupNames" value="${groupNames}${group.name}" />
                            <c:set var="groupIds" value="${groupIds}${group.id}" />
                            <c:if test="${row.index + 1 < fn:length(groupOfUserMap[user.id])}">
                                <c:set var="groupNames" value="${groupNames}${','}" />
                                <c:set var="groupIds" value="${groupIds}${','}" />
                            </c:if>
                        </c:forEach>
                        <c:if test="${empty groupNames}">
                            <c:set var="groupNames" value="${'还未设置所属组，请单击设置'}" />
                        </c:if>
                        <td title="${groupNames}" class="groups">
                            <a href="#" class="set-group" data-groupids="${groupIds}" data-userid="${user.id}" data-toggle="modal">共${fn:length(groupOfUserMap[user.id])}个组</a><br>
                        </td>
                        <td>
                            <a style="float: left;" class="layui-btn layui-btn-normal layui-btn-sm edit-user" data-id="${user.id}"><i class="layui-icon">&#xe642;</i></a>
			    <a style="float: right;" class="layui-btn layui-btn-danger layui-btn-sm" href="${ctx}/management/identity/user/delete/${user.id}"><i class="layui-icon">&#xe640;</i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
        </fieldset>
    </div>

    <!-- 新增、编辑用户的Model -->
    <div class="span6">
        <form action="${ctx }/management/identity/user/save" class="form-horizontal" method="post">
            <fieldset>
                <legend><small>新增/编辑用户</small></legend>
                <div id="messageBox" class="alert alert-error input-large controls" style="display:none">输入有误，请先更正。</div>
                <div class="control-group">
                    <label for="userId" class="control-label">用户ID:</label>
                    <div class="controls">
                        <input type="text" id="userId" name="userId" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="lastName" class="control-label">姓:</label>
                    <div class="controls">
                        <input type="text" id="lastName" name="lastName" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="firstName" class="control-label">名:</label>
                    <div class="controls">
                        <input type="text" id="firstName" name="firstName" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="email" class="control-label">Email:</label>
                    <div class="controls">
                        <input type="text" id="email" name="email" class="required" />
                    </div>
                </div>
                <div class="control-group">
                    <label for="password" class="control-label">密码:</label>
                    <div class="controls">
                        <input type="password" id="password" name="password" class="required" />
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" onclick="return sub()" class="layui-btn layui-btn-normal layui-btn-sm"><i class="layui-icon">&#xe605;</i>保存</button>
                    <button type="reset" class="layui-btn layui-btn-primary layui-btn-sm"><i class="layui-icon">&#xe9aa;</i>重置</button>
                </div>
            </fieldset>
        </form>
    </div>

    <div id="setGroupsModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="setGroupsModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="setGroupsModalLabel">设置用户的所属组</h3>
        </div>
        <div class="modal-body">
            <form action="${ctx}/management/identity/group/set" method="POST">
                <input type="hidden" name="userId" />
                <div class="row">
                    <div class="span3">
                        <c:forEach items="${allGroup}" var="group">
                            <label class="checkbox"><input type="checkbox" name="group" value="${group.id}" />${group.name}</label>
                        </c:forEach>
                    </div>
                    <div class="span2">
                        <button type="submit" class="layui-btn layui-btn-normal layui-btn-sm">确定添加</button>
                        <button class="layui-btn layui-btn-primary layui-btn-sm" data-dismiss="modal" aria-hidden="true">关闭</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>