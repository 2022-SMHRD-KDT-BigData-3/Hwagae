<%@page import="Model.WS_TalkDTO"%>
<%@page import="Model.WS_TalkDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="Model.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	
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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<link rel="stylesheet" href="css/ws_css.css">
</head>
<body>
    <div id="preloader">
        
    </div>

    <div id="wrapper">
    
		<%@ include file="header.jsp"%>
<%
    String redirectItem_Id = (String)request.getParameter("item_id");    
    String redirectState = (String)request.getParameter("roomstate");
    String ws_seller_store_id = "";
	if(redirectItem_Id!=null) {
		System.out.println("recommend item id : " + redirectItem_Id + "room state : " + redirectState);
		ws_item_id = redirectItem_Id;
		ws_room_state = redirectState;
	}

	WS_TalkDAO mdao = new WS_TalkDAO();
	HashMap<String, WS_TalkDTO> mlist = new HashMap<String, WS_TalkDTO>();
	if(ws_info!=null) {
		mlist = mdao.getChatList(ws_info.getStore_id());
		
		if(ws_item_id!=null){
			ws_seller_store_id = Integer.toString(mdao.getStoreByid(Integer.parseInt(ws_item_id)));
			System.out.print("seller store id : " + ws_seller_store_id + ", ws_item_id : " + ws_item_id);
		}
	}
%>
<script>
<%	if(ws_store_id!=null){%>
		store_id = '<%=ws_store_id%>';
<%  }
	if(ws_item_id!=null){%>
		item_id = '<%=ws_item_id%>';
<%	}
	if(ws_room_state!=null){%>
		room_state = '<%=ws_room_state%>';
<%	}
	if(ws_seller_store_id!=null){%>
		seller_store_id = '<%=ws_seller_store_id%>';
<%	}%>

</script>
   		<div><!--Start Main  -->
	<div id="container">
	  <aside>
	    <header>
	      <input type="text" placeholder="search">
	    </header>
	    <ul>
	    <!-- 채팅 목록  -->
	    <%for(Entry<String, WS_TalkDTO> entry:mlist.entrySet()){ %>
	    
	      <li class=chatList	 id="<%=entry.getValue().getSender_store_ID()%>,<%=entry.getValue().getItem_ID()%>,<%=entry.getValue().getReceiver_store_ID()%>">
	      	<h2>
	        	<img src="./ws_img/hwang2.jpg" alt="">
	        	아이디 : <%=entry.getValue().getSender_store_ID()%>
	        	</h2>
	        <div>
	          	<h2>채팅 내용 : <%=entry.getValue().getTalk_Info()%></h2>
	          	<h3>
	            	<span class="status orange"></span>
	            	<%=entry.getValue().getTalk_Date()%>
	          	</h3>
	        </div>
	      </li>
		<%} %>	 
	    </ul>
	  </aside>
	  <main>
	    <header>
	    <!-- 현재채팅방의 상대정보가 나와야함 -->
	      <img src="./ws_img/test01.jpg" alt="">
	      <div>
	        <h2>Chat with V Porter</h2>
	        <h3>already 1902 messages</h3>
	      </div>
	      <img src="./ws_img/star.png" alt="">
	    </header>
	    <ul id="chat">
	    	<li class='me'>
	    		<div class='entete'>
	    			<h3 name='Store_id'></h3>
	    			<h2 class='clock'></h2>
	    			<span class='status blue'></span>
	    		</div>
	    		
	    		<div class='message' name='talk_info'>화개장톡 시작~!
	    		</div>
	    	</li>
	    </ul>	   
	    <footer>	    	
		      	<!-- 송신 메시지를 작성하는 텍스트 박스 -->
				<input id="textMessage"  type="text">				
		      	<img src="./ws_img/picture.png" alt="">
		      	<img src="./ws_img/file.png" alt="">
		      	<!-- 메시지 송신 -->
		      	<input onclick="sendMessage()" value="Send" type="button">
	    </footer>
	  </main>
	</div>
	
   						
   		</div><!--end Main  -->
  
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/registItem.js"></script>
	<script src="js/WS_js.js"></script>
	<script>
	// 채팅 내역까지 불러온 이 후 room state가 2이면 채팅방 진입
	$( document ).ready(function(){
		if(!room_state) console.log('error. there is no infomation about room state.');
		else{
			if(!store_id||!seller_store_id||!item_id){
				// 필수 데이터 값이 없다면 채팅방에 진입할 수 없다.
				console.log('error. need necessary data.');
			}else{
				if(room_state=="2"){ // 채팅방
					enterRoom('I', '04', store_id, item_id, seller_store_id, "2");
				}else
					console.log('waiting room. do nothing.');
			}
		}
	});
	
	</script>
</body>
</html>