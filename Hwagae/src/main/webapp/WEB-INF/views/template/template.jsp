<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE html>
<html lang="UTF-8">
<head>


	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="author" content="">

   	<link href="https://fonts.googleapis.com/css?family=Ubuntu:300,400,400i,500,700" rel="stylesheet" />
	<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,400i,500,700,900" rel="stylesheet" />
   
	 <link rel="apple-touch-icon" href="<c:url value='/resources/images/apple-touch-icon.png'/>"> 
	<link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet"> 
	<link href="<c:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet">
	<link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
	<link href="<c:url value='/resources/css/responsive.css'/>" rel="stylesheet">    
	<link href="<c:url value='/resources/css/colors.css'/>" rel="stylesheet">
 
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<title><tiles:insertAttribute name="title" /></title>
</head>
<body>

    <div id="preloader">
        <img class="preloader" src="<c:url value="/resources/images/loader.gif" />" alt="">
    </div>

    <div id="wrapper">
    
		<tiles:insertAttribute name="header" />
     
   		<div><!--Start Main  -->
   			<section class="section wb">
	            <div class="container">
	                <div class="row">
	                
	                	<tiles:insertAttribute name="body" />
	       			
					</div>  <!--end row  -->
				</div> <!--end Container  -->
			</section>   			
   		</div><!--end Main  -->
  
    	<tiles:insertAttribute name="footer" />
  
    </div>

	 
 	<script src="<c:url value="/resources/js/core/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/js/core/tether.min.js" />"></script>
	<script src="<c:url value="/resources/js/core/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/js/core/custom.js" />"></script>

</body>
</html>