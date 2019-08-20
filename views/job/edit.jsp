<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<div class="row bg-title">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<h4 class="page-title">Cập nhật dự án</h4>
		</div>
	</div>
	<!-- /.row -->
	<!-- .row -->
	<div class="row">
		<div class="col-md-2 col-12"></div>
		<div class="col-md-8 col-xs-12">
			<div class="white-box">
				<p style="color: red;">${message }</p>
				<form class="form-horizontal form-material" action="<c:url value="/job/edit?id=${job.id }"/>" method="post">
					<div class="form-group">
						<label class="col-md-12">Name</label>
						<div class="col-md-12">
							<input type="text"class="form-control form-control-line" name="name" value="${job.name }">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-12">Start date</label>
						<div class="col-md-12">
							<input type="date" class="form-control form-control-line" name="startDate" value="${job.startDate }">
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-12">Phone No</label>
						<div class="col-md-12">
							<input type="date" class="form-control form-control-line" name="endDate" value="${job.endDate }">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<button type="submit" class="btn btn-success">Edit job</button>
							<a href="<c:url value="/job"/>" class="btn btn-primary">Quay lại</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="col-md-2 col-12"></div>
	</div>
	<!-- /.row -->
</div>