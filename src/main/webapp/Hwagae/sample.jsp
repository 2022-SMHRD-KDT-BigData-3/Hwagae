<%@page import="Model.MemberDTO"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="utf-8">

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Site Metas -->
    <title>Cloapedia - Stylish Magazine Blog Template</title>
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
     
   		<div><!--Start Main  -->
   			<section class="section wb">
	            <div class="container">
	                <div class="row">
	                	<!-- 여기다가 작업하세요! -->
	                	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
						<script type="text/javascript">
							function openPop() {
								var popup = window.open('http://nid.naver.com/nidlogin.logout','popup','width=1px,height=1px');
							}
						</script>
						
						<%if (info != null) {%>
						<h1><%=info.getSnsid()%>님 환영합니다.</h1>
						<%} else {%>
						<h1>로그인을 해주세요</h1>
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
						<a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a> 
						<%}%>  
	                	
						<%if (info != null) {%>
						<a href="naverLogOut.jsp" target="_self" onclick="openPop()">로그아웃</a>
						<%} %>
						
						<!-- 여기다가 작업하세요! -->
					</div>  <!--end row  -->
				</div> <!--end Container  -->
			</section>   			
   		</div><!--end Main  -->
  
    	<%@ include file="footer.jsp"%>
  
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/jquery.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

</body>
</html>