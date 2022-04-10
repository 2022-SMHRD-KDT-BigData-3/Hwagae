<%@page import="java.math.BigInteger"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.security.SecureRandom"%>
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
                        	<%if (ws_info != null) {%>
								<a href="naverLogOut.jsp" target="_self" onclick="openPop()"><img src="images/logout.png" id='logout'>&nbsp;로그아웃&nbsp;</a>
							<%} else {%>
							<%
							String clientId = "iYurGV0OE6snPVlinTga";//애플리케이션 클라이언트 아이디값";
							String redirectURI = URLEncoder.encode("http://localhost:8081/Hwagae/Hwagae/naverCallBack.jsp", "UTF-8");
							SecureRandom random = new SecureRandom();
							String state = new BigInteger(130, random).toString();
							String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
							apiURL += "&client_id=" + clientId;
							apiURL += "&redirect_uri=" + redirectURI;
							apiURL += "&state=" + state;
							session.setAttribute("state", state);
							%>
							<a href="<%=apiURL%>"><img src="images/login.png" id='login'>&nbsp;로그인/회원가입&nbsp;</a>
							<%}%> 
                            <a href="ShowStoreInfoServiceCon.do" class="checkLogin"><img src="images/storesmall.png" id='storesmall'></i>&nbsp;내상점&nbsp;</a></li>
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
                            <a href="sample.jsp"><img src="images/mainlogo.png" alt=""></a>
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
                                <a href="" id='catefont'><img src="images/category.png" id='category'><span>&nbsp;&nbsp;카테고리&nbsp;&nbsp;</span></a>
                            </li>
                            <li class="nav-item">
                                <a href="registItem.jsp" id='sellfont' class="checkLogin"><img src="images/sell.png" id='sell'><span>&nbsp;&nbsp;판매하기&nbsp;&nbsp;</span></a>
                            </li>
                            <li class="nav-item">
                                <a href="ShowStoreInfoServiceCon.do" id='storefont' class="checkLogin"><img src="images/storebig.png" id='storebig'><span>&nbsp;&nbsp;내상점&nbsp;&nbsp;</span></a>
                            </li>
                            <li class="nav-item">
                                <a href="./HwagaeTalk.jsp?roomstate=1" id='talkfont'><img src="images/talk.png" id='talk'><span>&nbsp;&nbsp;화개장톡&nbsp;&nbsp;</span></a>
                            </li>  
                           
                        </ul>
                    </div>
                </nav>
            </div><!-- end container --> 
            <link rel="stylesheet" href="css/header.css">         
        </header><!-- end header -->

	
<script src="js/ws_lobby_event.js"></script>  
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
<script type="text/javascript">
	function openPop() {
	// 네이버 로그아웃(불완전함) var popup = window.open('http://nid.naver.com/nidlogin.logout','popup','width=1px,height=1px'); 
	}
	
	<%
		String loginYn = "Y";
		if(session.getAttribute("info") ==  null){
			loginYn = "N";
		}
	%>
	
	
	
	$(".checkLogin").click(function(e){
		let href = $(this).attr("href");
		let loginYn = '<%=loginYn%>';
		
		if(href == "ShowStoreInfoServiceCon.do" && loginYn == "N"){
			alert("내상점은 로그인 후 이용할 수 있습니다.");
			e.preventDefault();
		}else if(href == "registItem.jsp" && loginYn == "N"){
			alert("상품등록은 로그인 후 이용할 수 있습니다.");
			e.preventDefault();
		}
		
	});

</script>