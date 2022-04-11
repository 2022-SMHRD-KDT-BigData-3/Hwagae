<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	      			 
					<div class="row" style="border: 1px solid rgb(230, 229, 239)">	 	
						<div class="col-sm-4 imgBox" style="background-image: url(images/store.png); height: 350px;">
							
							<div>
								<img id = "imgProfile" src="${storeDto.profileImg}">
								<input type="file" id="imgPath" name="imgPath" accept="image/jpg, image/jpeg, image/png">
								<a href="ShowManageItemServiceCon.do?page=10&no=1&status=A">내상점 관리</a>
							</div>

						</div>
						

						<div class="col-sm-8" style="padding: 0px 30px;">
						
							<div id="productStoreName">
								${storeDto.storeName}
								<Button id="btnModifyStoreName" style="display:none;">상점명 수정</Button>
							</div>
							
							<div class="viewInfo" style="border-bottom: 1px solid rgb(230, 229, 239); height: 40px;">
								<div class="material-icons" style="color : orange;">store</div>
								<div>${storeDto.accoutCreationDate}</div>
								<div class="material-icons" style="color : skyblue;">accessibility_new</div>
								<div><fmt:formatNumber>${storeDto.numVisitors}</fmt:formatNumber></div>
								<div class="material-icons" style="color : green;">shopping_basket</div>
								<div><fmt:formatNumber>${storeDto.numSales}</fmt:formatNumber></div>
								<div class="material-icons" style="color : #00CED1;">inventory_2</div>
								<div><fmt:formatNumber>${storeDto.numDelivery}</fmt:formatNumber></div>
							</div>
							
							 <div class="registItemInfo">
						 	    <textarea id="storeInfo" class="printStoreInfo" style="height: 160px;" readonly="readonly">${storeDto.storeInfo}</textarea>
						 	    <button id="btnConfirm" style="display: none;">수정</button>
						 	</div>
							
							<button id="btnModifyStoreInfo">소개글 수정</button>
						</div>
			
					 </div>

					 <div style="margin : 0px -15px;">
					 	 <ul class="nav nav-tabs" style="margin-top: 30px;">
							  <li class="nav-item" style="width: 33.33%; text-align: center; font-weight: 600;">
							  		<a class="nav-link active" data-toggle="tab" href="#item">상품</a>
							  </li>
							  <li class="nav-item" style="width: 33.33%; text-align: center; font-weight: 600;">
							    	<a class="nav-link" data-toggle="tab" href="#itemQuestion">상품문의</a>
							  </li>
							  <li class="nav-item" style="width: 33.33%; text-align: center; font-weight: 600;">
							    	<a class="nav-link" data-toggle="tab" href="#itemLike">찜</a>
							  </li>
						 </ul>
					 	 <div class="tab-content">
							  <div class="tab-pane fade show active" id="item">
							    	
							    	<div class="title">
							    	 	<span>상품</span>
							    		<span style="color: red;"></span>
							    	
								    	<select id="selectTradeStatus">
								    		<option value="">전체</option>
								    		<option value="S">판매중</option>
								    		<option value="R">예약중</option>
								    		<option value="R">판매완료</option>
								    	</select>
							    	</div>
							    	
							    	<hr>
							    	
							    	<div class="row">
							    	
							    		<c:forEach items="${itemList}" var="list">
							    		
							    			<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 item" status="${list.tradeStatus}">
		                                       <div class="blog-box">
		                                           <div class="post-media">
		                                               <a href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${info.store_id}">
		                                                   <img src="${list.imgPath}" class="img-fluid">
		                                                   <div class="safetyMark" style="display: ${list.safetyTradeYn eq 'Y' ? 'block' : 'none'}">안전</div>
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
			    		              
							    	</div>
	
							  </div>
							  
							  <div class="tab-pane fade" id="itemQuestion">
									
									<div class="title">
							    	 	<span>상품문의</span>
							    		<span style="color: red;"></span>
							    	</div>
											    	
									<div class="custombox clearfix" style="margin-top: 20px; padding: 1rem 2rem;">
					                     <div class="row">
					                         <div class="col-lg-12">
					                             <div class="comments-list">
					                             	<c:forEach items="${totalQuestionList}" var="list" varStatus="status">
					                                 <div class="media">
					                                     <a class="media-left" href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${info.store_id}">
					                                         <img src="${list.profileImg}" alt="" class="rounded-circle" style="border-radius: 0px;">
					                                     </a>
					                                     <div class="media-body">
					                                         <h4 class="media-heading user_name">${list.itemTitle}<small>${list.writeDate}</small></h4>
					                                         <p>${list.question}</p>
					                                     </div>
					                                 </div>
					                				</c:forEach>
					                             </div>
					                         </div><!-- end col -->
					                     </div><!-- end row -->
					                </div><!-- end custom-box -->
							    		    	
							  </div>
							  
							  <div class="tab-pane fade" id="itemLike">
							    	
							    	<div class="title">
							    	 	<span>찜</span>
							    		<span style="color: red;"></span>
							    	</div>
							    	
							    	<hr>
							    	
							    	<div class="row">
							    	
							    		<c:forEach items="${likeList}" var="list">
							    		
							    			<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12 item" status="${list.tradeStatus}">
		                                       <div class="blog-box">
		                                           <div class="post-media">
		                                               <a href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${info.store_id}">
		                                                   <img src="${list.imgPath}" class="img-fluid">
		                                                   <div class="safetyMark" style="display: ${list.safetyTradeYn eq 'Y' ? 'block' : 'none'}">안전</div>
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
			    		              
							    	</div>
							    	
							  </div>
					 	</div>
					 </div> <!-- 탭출력 -->
				 	
					 
					 
				</div> <!--end Container  -->
			</section>   
			
			<input type="hidden" name="storeId" value="${storeDto.storeId}">
				
   		</div><!--end Main  -->
   		
  		<%@ include file="footer.jsp"%>
  		
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/productStore.js"></script>
    
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

</body>
</html>