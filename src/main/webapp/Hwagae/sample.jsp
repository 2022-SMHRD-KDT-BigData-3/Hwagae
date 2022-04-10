<%@page import="Model.ItemDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.ItemDAO"%>
<%@page import="Model.MemberDTO"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="utf-8">

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Site Metas -->
    <title>화개장터</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <!-- Site Icons -->
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    
    <!-- Design fonts -->
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:300,400,400i,500,700" rel="stylesheet"> 
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,400i,500,700,900" rel="stylesheet"> 

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- FontAwesome Icons core CSS -->
    <link href="css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style.css" rel="stylesheet">

    <!-- Responsive styles for this template -->
    <link href="css/responsive.css" rel="stylesheet">

    <!-- Colors for this template -->
    <link href="css/colors.css" rel="stylesheet">
    
    <link href="css/changwookcho.css" rel="stylesheet">
	<script src="js/jquery.min.js"></script>
	
</head>
<body>
	<% 
		// 로그인 정보를 담은 session 불러오기
		// 로그인 성공 시 : info에는 값이 담겨있음.
		// 로그인 실패 시 : info에는 null이 담겨있음.
		MemberDTO info = (MemberDTO)session.getAttribute("info");
		
	%>
	<!-- "<c:url value="/resources/images/loader.gif"/> -->
    <div id="preloader">
        
    </div>

    <div id="wrapper">
    
		<%@ include file="header.jsp"%>
     	<%
     		ItemDAO itemDao = new ItemDAO();
     		ArrayList<ItemDTO> itemList = itemDao.retrieveItemList(request);
     		session.setAttribute("itemList", itemList);
     	%>
     
   		<div><!--Start Main  -->
   			<section class="section wb">
	            <div class="container">
	                <div class="row">
	                	<!-- 여기다가 작업하세요! -->
							<c:forEach items="${itemList}" var="list">
							    		
			    				<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 item" status="${list.tradeStatus}" style="margin-bottom: 20px;">
                                    <div class="blog-box">
                                        <div class="post-media">
                                            <a href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${info.store_id}">
                                                <img src="${list.imgPath}" class="img-fluid">
                                                <div class="hovereffect">
                                                </div><!-- end hover -->
                                            </a>
                                        </div><!-- end media -->
                                        <div class="blog-meta">
                                            <div><a href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${storeDto.storeId}">${list.itemTitle}</a></div>
                                            <div><fmt:formatNumber>${list.price}</fmt:formatNumber>원</div>
                                            <div>${list.registrationDate}</div>
                                        </div><!-- end meta -->
                                    </div><!-- end blog-box -->
	                         	</div>
							    				
							 </c:forEach>
						<!-- 여기다가 작업하세요! -->
					</div>  <!--end row  -->
				</div> <!--end Container  -->
			</section>   			
   		</div><!--end Main  -->
  	
  		<input type="hidden" name="errMsg" value="${errMsg}">
  	
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/jquery.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
   
</body>
</html>