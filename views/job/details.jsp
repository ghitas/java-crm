<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	<div class="row bg-title">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
			<h4 class="page-title">Chi tiết công việc</h4>
		</div>
	</div>
	<!-- /.row -->
	<!-- .row -->
	<div class="row">
		<div class="white-box">
			<form class="form-horizontal form-material">
				<div class="form-group">
					<label class="col-md-12">${job.name }</label>
				</div>
				<div class="form-group">
					<label for="example-email" class="col-md-12">${job.description }</label>
				</div>
				<div class="form-group">
					<label class="col-md-12">${job.startDate }</label>
				</div>
				<div class="form-group">
					<label class="col-md-12">${job.endDate }</label>
				</div>
			</form>
		</div>
	</div>
</div>