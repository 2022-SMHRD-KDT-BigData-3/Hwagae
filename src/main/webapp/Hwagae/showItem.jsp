<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="Model.ItemDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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
    
    <link href="css/footer.css" rel="stylesheet">
    <link href="css/changwookcho.css" rel="stylesheet">
	
	<script src="js/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
</head>
<body>

    <div id="preloader">
        <img class="preloader" src="<c:url value="/resources/images/loader.gif" />" alt="">
    </div>

    <div id="wrapper">
    
		<%@ include file="header.jsp"%>
     
   		<div><!--Start Main  -->
   			<section class="section wb">
	            <div class="container" style="font-size: 1rem;">
	            
	      			 <div class="row itemTotalInfo">	 	
						<div class="col-sm-5 imgBox">
							<div class="slider">
						
							    <ul id="imgholder" class="imgs">
							    	<c:forEach items="${imgPath}" var="list" varStatus="status">
							    		<li><img src="${list}" class= "slideImg" seq="${status.count}"></li>
							    	</c:forEach>
							    </ul>
							    <div class="bullets">
							    	<c:forEach items="${imgPath}" var="list" varStatus="status">
							    		<label seq="${status.count}" class="slideBtn">&nbsp;</label>
							    	</c:forEach>

							    </div>
							</div>
						</div>
						

						<div class="col-sm-7">
							<div class="itemTitle">${itemDto.itemTitle}</div>
							<div class="itemPrice"><fmt:formatNumber>${itemDto.price}</fmt:formatNumber>원</div>
							
							<input type="hidden" name="stock" value="${itemDto.stock}">
							<input type="hidden" name="tradeStatus" value="${itemDto.tradeStatus}">
							<input type="hidden" name="safetyTradeYn" value="${itemDto.safetyTradeYn}">
							<hr>
							
							<div class="viewInfo">
								<div class="material-icons">favorite</div>
								<div><fmt:formatNumber>${itemDto.numLike}</fmt:formatNumber></div>
								<div class="material-icons">schedule</div>
								<div>${itemDto.registrationDate}</div>
							</div>
							
							<div class="tradeInfo">
								<div>
									<div>상품상태</div><div>${itemDto.itemStatus eq 'U' ? '중고' : '신품'}</div>
								</div>
								<div>
									<div>교환여부</div><div>${itemDto.exchangeYn eq 'Y' ? '교환가능' : '교환불가능'}</div>
								</div>
								<div>
									<div>배송비</div><div>${itemDto.includeDeliveryPriceYn eq 'Y' ? '배송비 포함' : '배송비 별도'}</div>
								</div>
								<div>
									<div>거래지역</div><div>${itemDto.tradeArea}</div>
								</div>	
							</div>	
	
							<div class="tradeBtnGroup">
								<div class="row">
									<div class="col-sm-4">
										<button id="btnItemLike" active="${itemLikeYn}"><label class="material-icons">favorite</label>찜</button>
									</div>
									<div class="col-sm-4">
										<button id="btnTalk" class="checkLogin">연락하기</button>
									</div>
									<div class="col-sm-4">
										<button id="btnBuy">바로구매</button>	
									</div>
								</div>		
							</div>
							
						</div>
			
					 </div><!--itemTotalInfo -->
					 
					 <div class="row">
					 					 
						<div class="col-sm-12">
						
							<ul class="nav nav-tabs">
							  <li class="nav-item" style="width: 50%; text-align: center; font-weight: 600;">
							    <a class="nav-link active" href="#qwe">상품정보</a>
							  </li>
							  <li class="nav-item" style="width: 50%; text-align: center; font-weight: 600;">
							    <a class="nav-link" href="#asd">상품문의</a>
							  </li>
							</ul>
			
							
							<div class="title" ID="qwe">상품정보</div>
							<hr>
							
							<%
								ItemDTO itemDto = (ItemDTO) session.getAttribute("itemDto");
								String vendorUrlInfo = itemDto.getVendorUrlInfo();
								String vendorUrl = itemDto.getVendorUrl();
							%>
							
							
							
							<%if(vendorUrlInfo != null){%>
								
								<div class="media">
	                                    
	                                     <img src="./images/hwagaeLogo.png" alt="" class="rounded-circle">
	                                    
	                                     <div id="modalInfo"class="media-body" style="font-weight: bold;line-height: 70px;font-size: 1.2rem;">
	                                      	화개장터에서 이 상품에 대해서 안내해 드립니다!
	                                     </div>
	                                     <%if(vendorUrl != null){%>
										 <div class="vendorUrlBox" style="height: 80px; line-height: 60px;">
											<a class="btn btn-warning" href="<%=vendorUrl%>">구매처로 이동</a>
										 </div>
										 <%}%>
	                            </div>
								
								<div class="modalTableBox">
	                             
	                             	<table class="table table-bordered table-striped" style="table-layout:fixed;">
	                             		 <colgroup style="width: 40%;"></colgroup>
	                             		 <colgroup style="width: 60%;"></colgroup>
										 <tbody>
										 
										 	<%
										 	ObjectMapper mapper = new ObjectMapper();
											LinkedHashMap<String, String> vendorUrlInfoMap = mapper.readValue(vendorUrlInfo, LinkedHashMap.class);
										 	%>
										 
										   <%for ( String key : vendorUrlInfoMap.keySet() ) {%>
										   		<tr>
										   			<td><%=key%></td>
										   			<td><%=vendorUrlInfoMap.get(key)%></td>
										   		</tr>
											<%}%>
										   
										 </tbody> 	
									</table>
	                             
	                             </div>
								
								<hr>
							<%}%>
							
							
						    <div id="itmeInfoBox">
						    	${itemDto.itemInfo}
						    </div>
						    
						    <div id = "subInfo" class="row">
						        <div class="col-sm-4">
								    <div><span class="material-icons">place</span>거래지역</div>
									<div>${itemDto.tradeArea}</div>
						        </div>
						        <div class="col-sm-4">
								    <div><span class="material-icons">article</span>카테고리</div>
									<div>${itemDto.itemCategory}</div>
						        </div>
						        <div class="col-sm-4">
								    <div><span class="material-icons">bookmark</span>상품태그</div>
									<div>${itemDto.relationTag}</div>
						        </div>
							</div>
							      
							<div class="title" ID="asd">상품문의</div>
						    
						    <div class="registItemInfo">
						 	    <textarea id="ipbItemInfo" name="itemInfo" placeholder="상품문의 입력"></textarea>
						 	</div>
						 		
						 	<div style="position: relative;">
						 		<div class="questionByte">0 / 100</div>
						 		<button id="regiQuestionBtn"><span class="material-icons">edit</span>등록</button>
						 	</div>
						   
						</div>
						
					 </div>
					 
					 <div class="custombox clearfix" style="margin-top: 20px; padding: 1rem 2rem;">
	                     <div class="row">
	                         <div class="col-lg-12">
	                             <div class="comments-list">
	                             	<c:forEach items="${questionList}" var="list" varStatus="status">
	                                 <div class="media">
	                                     <a class="media-left" href="#">
	                                         <img src="${list.profileImg}" alt="" class="rounded-circle">
	                                     </a>
	                                     <div class="media-body">
	                                         <h4 class="media-heading user_name">${list.storeName}<small>${list.writeDate}</small></h4>
	                                         <p>${list.question}</p>
	                                     </div>
	                                 </div>
	                				</c:forEach>
	                             </div>
	                         </div><!-- end col -->
	                     </div><!-- end row -->
	                     
	                </div><!-- end custom-box -->
					 
				
				</div> <!--end Container  -->
			</section>   
			
			<input type="hidden" name="itemId" value="${itemDto.itemId}">
			<input type="hidden" name="storeId" value="${itemDto.storeId}">
			<input type="hidden" name="buyerId" value="${info.store_id}">	
			 
   		</div><!--end Main  -->
   		
  		<%@ include file="footer.jsp"%>
  		
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/showItem.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/WS_js.js"></script>
    <script>

    item_id = $('input[name=itemId]').val();
    seller_store_id = $('input[name=buyerId]').val();
	<%
	String loginYn2 = "Y";
	if(session.getAttribute("info") ==  null){
		loginYn2 = "N";
	}
	%>

	$(".checkLogin").click(function(e){
	let id = $(this).attr("id");
	let loginYn = '<%=loginYn2%>';
	
	if(id == "btnTalk" && loginYn == "N"){
		alert("연락하기는 로그인 후 이용할 수 있습니다.");
		e.preventDefault();
	}else if(id == "btnTalk" && loginYn == "Y"){
		console.log('showitem : item_id' + $('itemID').text());
		if(store_id==seller_store_id)
			location.href="./HwagaeTalk.jsp?item_id=" + item_id + "&roomstate=1";
		else
			location.href="./HwagaeTalk.jsp?item_id=" + item_id + "&roomstate=2";
	}
	
});
    </script>
</body>
</html>