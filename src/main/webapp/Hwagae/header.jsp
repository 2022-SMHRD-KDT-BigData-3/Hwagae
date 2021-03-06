<%@page import="java.math.BigInteger"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="Model.WS_TalkDTO"%>
<%@page import="Model.MemberDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	MemberDTO ws_info = (MemberDTO)session.getAttribute("info");
	String ws_store_id = null;
	String ws_item_id = (String)session.getAttribute("item_id");
	String ws_room_state = (String)session.getAttribute("roomstate");
	if(ws_info!=null) {
		System.out.println("header.jsp : logged in.");
		ws_store_id = ws_info.getStore_id();
	}
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
								<a href="naverLogOut.jsp" target="_self" onclick="sendLogoutCommand()" class="font_Gothic"><img src="images/logout.png" id='logout'>&nbsp;????????????&nbsp;</a>
							<%} else {%>
							<%
							String clientId = "iYurGV0OE6snPVlinTga";//?????????????????? ??????????????? ????????????";
							String redirectURI = URLEncoder.encode("http://localhost:8081/Hwagae/Hwagae/naverCallBack.jsp", "UTF-8");
							SecureRandom random = new SecureRandom();
							String state = new BigInteger(130, random).toString();
							String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
							apiURL += "&client_id=" + clientId;
							apiURL += "&redirect_uri=" + redirectURI;
							apiURL += "&state=" + state;
							session.setAttribute("state", state);
							%>
							<a href="<%=apiURL%>"><img src="images/login.png" id='login' class="font_Gothic">&nbsp;?????????/????????????&nbsp;</a>
							<%}%> 
                            <a href="ShowStoreInfoServiceCon.do" class="checkLogin" class="font_Gothic"><img src="images/storesmall.png" id='storesmall'></i>&nbsp;?????????&nbsp;</a></li>
                            <a href="./HwagaeTalk.jsp?roomstate=1"><img src="images/icon.png" id='aa' style="width:30px; height:30px;"></a>
                        </div><!-- end search -->
                    </div><!-- end col -->
                    
                </div><!-- end row -->
            </div><!-- end header-logo -->
        </div><!-- end topbar -->

        <div class="header-section" style="height: 200px;">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="logo">
                            <a href="sample.jsp"><img src="images/mainlogo.png" alt=""></a>
                        </div><!-- end logo -->
                    </div>
                </div><!-- end row -->
         	<div class="messenger">
        		<div class="mesgcircle" style="display: none;">
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
           </div><!-- container -->
        </div><!-- header-section -->

        <header class="header">
            <div class="container" style="padding : 0px 10%;">
            	<div class="row">
            		<div class="col-sm-12 managerSearchBox" style="margin-bottom: 20px;">
            			<div style="height: 100%;">
            				<input id="ipbHeaderKeyword" type="text" placeholder="?????????, ?????????, @????????? ??????" style="font-size: 15px; padding-left: 20px;"/>

		      			 	<button id="btnHeaderSerach" class="material-icons">search</button>
            			</div>
            		</div>
            		
            		<div class="col-sm-3">
            			<button class='navBox' id="btnCategory">
            				<div><img src="images/category.png" id='category'></div>
            				<div>????????????</div>
            			</button>
            		</div>
            		<div class="col-sm-3">
            			<a href="registItem.jsp" id='sellfont' class="checkLogin navBox">
            				<div><img src="images/sell.png" id='sell'></div>
            				<div>????????????</div>
            			</a>
            		</div>
            		<div class="col-sm-3">
            			<a href="ShowStoreInfoServiceCon.do" id='storefont' class="checkLogin navBox">
            				<div><img src="images/storebig.png" id='storebig'></div>
            				<div>?????????</div>
            			</a>
            		</div>
            		<div class="col-sm-3">
            			<a href="./HwagaeTalk.jsp?roomstate=1" id='talkfont' class="checkLogin navBox">
            				<div><img src="images/talk.png" id='talk'></div>
            				<div>????????????</div>
            			</a>
            		</div>
            		
            		<div class="selectionAllBox selectionhdAllBox" style="height: 150px; margin: 10px 0px; display:none;">
						<div id="hdSection1" class="col-md-4 selectionBox">
							
						</div>
						<div id="hdSection2" class="col-md-4 selectionBox">
							?????????
						</div>
						<div id="hdSection3" class="col-md-4 selectionBox">
							?????????
						</div>
					</div>
            		
            	</div>
            </div>
            	   
            
            </div><!-- end container --> 
            <link rel="stylesheet" href="css/header.css">         
        </header><!-- end header -->
        
<script src="js/jquery.min.js"></script>        
<script>
	<%-- document.addEventListener("DOMContentLoaded", () => { 
		<%	
		if(ws_store_id!=null){%>
			store_id = '<%=ws_store_id%>';
		<%  }
		if(ws_item_id!=null){%>
			item_id = '<%=ws_item_id%>';
		<%	}
		if(ws_room_state!=null){%>
			room_state = '<%=ws_room_state%>';
		<%	}%>	
	}); --%>

	
</script> 
<script type="text/javascript" src="./js/ws_lobby_event.js"></script>  
<script>
<%
	if(ws_info!=null){ 
		System.out.println("<header.jsp #2> store_id : " + ws_info.getStore_id() + " : item_id : " + ws_item_id + " roomstate : " + ws_room_state);
%>
		store_id = '<%=ws_info.getStore_id()%>';	
		item_id = <%=(String)session.getAttribute("item_id")%>;
		room_state = <%=session.getAttribute("roomstate")%>;
<%	}%>
	item_id = '<%=ws_item_id%>';
	rooms_tate = '<%=ws_room_state%>';

</script>  
<script type="text/javascript">
	function openPop() {
		var popup = window.open('http://nid.naver.com/nidlogin.logout','popup','width=1px,height=1px');
	}
	
	function sendLogoutCommand(){
		sendLogout(ws_store_id);
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
			alert("???????????? ????????? ??? ????????? ??? ????????????.");
			e.preventDefault();
		}else if(href == "registItem.jsp" && loginYn == "N"){
			alert("??????????????? ????????? ??? ????????? ??? ????????????.");
			e.preventDefault();
		}else if(href == "./HwagaeTalk.jsp?roomstate=1" && loginYn == "N"){
			alert("??????????????? ????????? ??? ????????? ??? ????????????.");
			e.preventDefault();
		}
		
	});


	$("#btnHeaderSerach").click(function(e){	
		searchKeywordItem();
	});
	
	$("#ipbHeaderKeyword").keypress(function(e){
		if(e.keyCode = 13){
			searchKeywordItem();
		}
	});
	
	let selectedSection1 = -1;
	let selectedSection2 = -1;
	let selectedSection3 = -1;
	
	$("#btnCategory").click(function(e){
		
		if($(".selectionhdAllBox").css("display") == "none"){
			$(".selectionhdAllBox").show();
		}else{
			$(".selectionhdAllBox").hide();
		}
		
		if($("#hdSection1 > ui").length == 0){
			setCategory(null,null);
		}
	
		
	});
	
	$(document).on("mouseover" , ".btnHdCategory", function() {              
		
		let sectionLevel = $(this).data("level");
		let sectionValue = $(this).data("value");

		if(sectionLevel == "section1" && selectedSection1 != sectionValue){		
			
			//???????????? ui ?????????
			$("#hdSection2").text("");
			$("#hdSection3").text("?????????");
			
			//?????? selectedSection ????????? ?????????
			selectedSection2 = -1;
			selectedSection3 = -1;
			
			//???????????? ?????? ??????
			setCategory(sectionValue,null);
			selectedSection1 = sectionValue;	
			
		}else if(sectionLevel == "section2" && selectedSection2 != sectionValue){		
			
			//???????????? ui ?????????
			$("#hdSection3").text("");

			//?????? selectedSection ????????? ?????????
			selectedSection3 = -1;
			
			setCategory(selectedSection1,sectionValue);
			selectedSection2 = sectionValue;	
		}else{		
			selectedSection3 = sectionValue;
		}
		
	        
     }); 
	
	$(document).on("click" , ".btnHdCategory", function() {
	
		let catSeq = $(this).data("catSeq");
		
		location.href = "ShowSearchItemServiceCon.do?catSeq="+catSeq;
	});
	
	
	function searchKeywordItem(){
		let keyword = $("#ipbHeaderKeyword").val();
		
		if(keyword.length == 0){
			alert("???????????? ????????? ?????????.");
			return;
		}
		location.href = "ShowSearchItemServiceCon.do?q="+keyword;
	}
	
	function setCategory(section1, section2){
		
		section = {
			"section1" : section1
			,"section2" : section2
		};
		
		$.ajax({
			url : 'RetrieveCategoryServiceCon.ajax'
			,type : 'GET'
			,data : section
			,dataType : 'json'
			,contentType : "application/json;charset=UTF-8"
			,success : function(data) {
				
				let ui = $("<ui>");
				let li;
				let button;
				
				for(let i = 0; i < data.length; i++){
				
					li = $("<li>");
					button = $("<button>", { class : "btnHdCategory", text : data[i].sectionInfo});
					button.data("level", getSectionLevel(section1, section2));
					button.data("value", getSectionValue(section1, section2, data[i]));
					button.data("catSeq", data[i].catSeq);
					
					li.append(button);
					ui.append(li);
				
				}
				
				if(section1 == null && section2 ==  null){ //????????? ???????????? ??????
					$("#hdSection1").append(ui);
				}else if(section1 != null && section2 == null){ //????????? ???????????? ??????
					$("#hdSection2").append(ui);
				}else{ //????????? ???????????? ??????
					$("#hdSection3").append(ui);
				}
				
  			}, 
			error : function(err) {
  				console.log(err);
			}
  		});
		
	}
	
	function getSectionValue(section1, section2, data){
		
		let result;
	
		if(section1 == null && section2 ==  null){ //????????? ???????????? ??????
			result = data.section1;
		}else if(section1 != null && section2 == null){ //????????? ???????????? ??????
			result = data.section2;
		}else{
			result = data.section3;
		}
	
		return result;
	}
	
	function getSectionLevel(section1, section2){
	
		let result;
	
		if(section1 == null && section2 ==  null){ //????????? ???????????? ??????
			result = "section1";
		}else if(section1 != null && section2 == null){ //????????? ???????????? ??????
			result = "section2";
		}else{
			result = "section3";
		}
	
		return result;
	}
	
	
	
	
</script>