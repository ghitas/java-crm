<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	<div class="row bg-title">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<h4 class="page-title">Chi tiết thành viên</h4>
		</div>
	</div>
	<!-- /.row -->
	<!-- .row -->
	<div class="row">
		<div class="col-md-4 col-xs-12">
			<div class="white-box">
				<div class="user-bg">
					<img width="100%" alt="user" src="<c:url value="/assets/plugins/images/large/img1.jpg" />">
					<div class="overlay-box">
						<div class="user-content">
							<a href="javascript:void(0)"><img
								src="<c:url value="/assets/plugins/images/users/genu.jpg"/>" class="thumb-lg img-circle"
								alt="img"></a>
							<h4 class="text-white">${user.fullname }</h4>
							<h5 class="text-white">${user.email }</h5>
						</div>
					</div>
				</div>
				<div class="user-btm-box">
					<div class="col-md-4 col-sm-4 text-center">
						<p class="text-purple">
							<i class="ti-facebook"></i>
						</p>
						<h4>${open }%</h4>
						<h6>Chưa thực hiện</h6>
					</div>
					<div class="col-md-4 col-sm-4 text-center">
						<p class="text-blue">
							<i class="ti-twitter"></i>
						</p>
						<h4>${process }%</h4>
						<h6>Đang thực hiện</h6>
					</div>
					<div class="col-md-4 col-sm-4 text-center">
						<p class="text-danger">
							<i class="ti-dribbble"></i>
						</p>
						<h4>${finish }%</h4>
						<h6>Hoàn thành</h6>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-8 col-xs-12">
			<div class="white-box">
				<form class="form-horizontal form-material">
					<div class="form-group">
						<label class="col-md-12">Full Name</label>
						<input type="text" class="form-control form-control-line" value="${user.fullname }"/>
					</div>
					<div class="form-group">
						<label for="example-email" class="col-md-12">Email</label>
						<input type="text" class="form-control form-control-line" value="${user.email }"/>
					</div>
					<div class="form-group">
						<label class="col-sm-12">Role</label>
						<input type="text" class="form-control form-control-line" value="${user.roleName }"/>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br />
	<!-- /.row -->
	<!-- BEGIN DANH SÁCH CÔNG VIỆC -->
	<h4>DANH SÁCH CÔNG VIỆC</h4>
	<div class="row">
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Chưa thực hiện</h3>
				<div class="message-center">
					<c:forEach items="${openTasks }" var="task" varStatus="loop">
						<a href="<c:url value="/job"/>">
							<div class="mail-contnet">
								<h5>${task.jobName }</h5>
								<span class="mail-desc">${task.name}</span> <span
									class="time">${task.startDate }</span>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Đang thực hiện</h3>
				<div class="message-center">
					<c:forEach items="${processTasks }" var="task" varStatus="loop">
						<a href="<c:url value="/job"/>">
							<div class="mail-contnet">
								<h5>${task.jobName }</h5>
								<span class="mail-desc">${task.name}</span> <span
									class="time">${task.startDate }</span>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="white-box">
				<h3 class="box-title">Đã hoàn thành</h3>
				<div class="message-center">
					<c:forEach items="${finishTasks }" var="task" varStatus="loop">
						<a href="<c:url value="/job"/>">
							<div class="mail-contnet">
								<h5>${task.jobName }</h5>
								<span class="mail-desc">${task.name}</span> <span
									class="time">${task.endDate }</span>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<!-- END DANH SÁCH CÔNG VIỆC -->
</div>