<%@page import="Model.WS_TalkDTO"%>
<%@page import="Model.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberDTO ws_info = (MemberDTO)session.getAttribute("info");
	String ws_item_id = (String)session.getAttribute("item_id");
	String ws_roomstate = (String)session.getAttribute("roomstate");
	
	if(ws_info!=null)
		System.out.println("<header.jsp> store_id : " + ws_info.getStore_id() + " : item_id : " + ws_item_id + " roomstate : " + ws_roomstate);
%>    
<div class="collapse top-search" id="collapseExample">
            <div class="card card-block">
                <div class="newsletter-widget text-center">
                    <form class="form-inline">
                        <input type="text" class="form-control" placeholder="What you are looking for?">
                        <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
                    </form>
                </div><!-- end newsletter -->
            </div>
        </div><!-- end top-search -->

        <div class="topbar-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-4 col-md-6 col-sm-6 hidden-xs-down">
                        <div class="topsocial">
                           
                           
                        </div><!-- end social -->
                    </div><!-- end col -->

                    <div class="col-lg-4 hidden-md-down">
                        <div class="topmenu text-center">
                            <ul class="list-inline">
                               
                            </ul><!-- end ul -->
                        </div><!-- end topmenu -->
                    </div><!-- end col -->

                    <div class="col-lg-4 col-md-6 col-sm-6 col-xs-12">
                        <div class="topsearch text-right">
                            <li class="list-inline-item"><a href="blog-category-01.html"><i class="fa fa-sign-in" aria-hidden="true"></i>로그인/회원가입</a></li>
                                <li class="list-inline-item"><a href="page-contact.html"><i class="fa fa-bell" aria-hidden="true"></i> 내상점</a></li>
                        </div><!-- end search -->
                    </div><!-- end col -->
                    
                </div><!-- end row -->
            </div><!-- end header-logo -->
        </div><!-- end topbar -->

        <div class="header-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="logo">
                            <a href="index.html"><img src="images/mainlogo.png" alt=""></a>
                        </div><!-- end logo -->
                    </div>
                </div><!-- end row -->
                <div class="messenger">
        <div class="mesgcircle">
            <div id="notificationScroll" class="msgscrol">
                <span id="notificationMsg"></span>
            </div>
            <div class="mesgload">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    </div>
            </div><!-- end header-logo -->
        </div><!-- end header -->

        <header class="header">
            <div class="container">
               <nav class="navbar navbar-inverse navbar-toggleable-md">
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#cloapediamenu" aria-controls="cloapediamenu" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-md-center" id="cloapediamenu">
                        <ul class="navbar-nav">
                           
                               
                           <li class="nav-item">
                                <a class="nav-link color-pink-hover" href="blog-category-01.html">카테고리</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link color-red-hover" href="blog-category-02.html">판매하기</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link color-aqua-hover" href="blog-category-03.html">내상점</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link color-green-hover" href="blog-category-04.html">번개톡</a>
                            </li>  
                           
                        </ul>
                    </div>
                </nav>
            </div><!-- end container --> 
            <link rel="stylesheet" href="css/header.css">         
        </header><!-- end header -->

	
<script src="js/ws_lobby_event.js">
</script>  
<script>
<%
	if(ws_info!=null){ 
		System.out.println("<header.jsp #2> store_id : " + ws_info.getStore_id() + " : item_id : " + ws_item_id + " roomstate : " + ws_roomstate);
%>
		console.log("test");
		ws_store_id = '<%=ws_info.getStore_id()%>';	

<%	}%>
	ws_item_id = '<%=ws_item_id%>';
	ws_roomstate = '<%=ws_roomstate%>';
	console.log(ws_store_id +" : "+ ws_item_id + " : " + ws_roomstate);
</script>  