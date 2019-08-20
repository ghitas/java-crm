<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Page Content -->
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Chi tiết công việc </h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                <ol class="breadcrumb">
                    <li><a href="#">Dashboard</a></li>
                    <li class="active">Blank Page</li>
                </ol>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- BEGIN THỐNG KÊ -->
        <div class="row">
            <!--col -->
            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                <div class="white-box">
                    <div class="col-in row">
                        <div class="col-md-6 col-sm-6 col-xs-6"> <i data-icon="E"
                                class="linea-icon linea-basic"></i>
                            <h5 class="text-muted vb">CHƯA BẮT ĐẦU</h5>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <h3 class="counter text-right m-t-15 text-danger">${open }%</h3>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="progress">
                                <div class="progress-bar progress-bar-danger" role="progressbar"
                                    aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${open}%">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col -->
            <!--col -->
            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                <div class="white-box">
                    <div class="col-in row">
                        <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                data-icon="&#xe01b;"></i>
                            <h5 class="text-muted vb">ĐANG THỰC HIỆN</h5>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <h3 class="counter text-right m-t-15 text-megna">${process }%</h3>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="progress">
                                <div class="progress-bar progress-bar-megna" role="progressbar"
                                    aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${process }%">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col -->
            <!--col -->
            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                <div class="white-box">
                    <div class="col-in row">
                        <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                data-icon="&#xe00b;"></i>
                            <h5 class="text-muted vb">HOÀN THÀNH</h5>
                        </div>
                        <div class="col-md-6 col-sm-6 col-xs-6">
                            <h3 class="counter text-right m-t-15 text-primary">${finish }%</h3>
                        </div>
                        <div class="col-md-12 col-sm-12 col-xs-12">
                            <div class="progress">
                                <div class="progress-bar progress-bar-primary" role="progressbar"
                                    aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: ${finish }%">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- END THỐNG KÊ -->

        <!-- BEGIN DANH SÁCH CÔNG VIỆC -->
        <c:forEach items="${jobs }" var="job">
	        <div class="row">
	            <div class="col-xs-12">
	                <a href="#" class="group-title">
	                    <img width="30" src="plugins/images/users/pawandeep.jpg" class="img-circle" />
	                    <span>${job.userName }</span>
	                </a>
	            </div>
	            <div class="col-md-4">
	                <div class="white-box">
	                    <h3 class="box-title">Chưa thực hiện</h3>
	                    <div class="message-center">
	                        <c:forEach items="${openTasks }" var="task">
		                        <a href="#">
		                            <div class="mail-contnet">
		                                <h5>${task.userName }</h5> <span class="mail-desc">${task.name }</span> <span
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
	                        <c:forEach items="${processTasks }" var="task">
		                        <a href="#">
		                            <div class="mail-contnet">
		                                <h5>${task.userName }</h5> <span class="mail-desc">${task.name }</span> <span
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
	                    	<c:forEach items="${finishTasks }" var="task">
		                        <a href="#">
		                            <div class="mail-contnet">
		                                <h5>${task.userName }</h5> <span class="mail-desc">${task.name }</span> <span
		                                    class="time">${task.startDate }</span>
		                            </div>
		                        </a>
	                    	</c:forEach>
	                    </div>
	                </div>
	            </div>
	        </div>
        </c:forEach>
        <!-- END DANH SÁCH CÔNG VIỆC -->
    </div>
    <!-- /.container-fluid -->
    <footer class="footer text-center"> 2018 &copy; myclass.com </footer>
</div>
<!-- /#page-wrapper -->