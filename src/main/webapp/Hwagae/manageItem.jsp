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
	<script>
		let totalPage = ${totalPage};
	</script>
</head>
<body>

    <div id="preloader"></div>
        
    <div id="wrapper">
    
		<%@ include file="header.jsp"%>
     
   		<div><!--Start Main  -->
   			
   				<section class="section wb" style="padding-top: 0px;">
		            <div class="container" style="font-size: 1rem;">
		            
		            	<div class="manageLocationBox">
		      				<a href="registItem.jsp">상품등록</a>
		      				<a href="ShowManageItemServiceCon.do?page=10&no=1&status=A">상품관리</a>
		      			</div>
		      		
		      			<div class="row managerSearchBox">
		      				      		
		      			 	<div class="col-sm-5">
		      			 		<input id="ipbKeyword" type="text" placeholder="상품명을 입력해 주세요.">
		      			 		<button id="btnSerachItem" class="material-icons">search</button>
		      			 	</div>
		      			 	<div class="col-sm-1">
		      			 		<select id="selPage">
		      			 			<option value=10 selected>10개씩</option>
		      			 			<option value=20>20개씩</option>
		      			 			<option value=50>50개씩</option>
		      			 		</select>
		      			 	</div>
		      			 	<div class="col-sm-1">
		      			 		<select id="selStatus">
		      			 			<option value="A" selected>전체</option>
		      			 			<option value="S">판매 중</option>
		      			 			<option value="R">예약 중</option>
		      			 			<option value="C">판매완료</option>
		      			 		</select>
		      			 	</div>
						</div>
						
						<div style="margin-top : 30px;">
						
							<table class="table table-hover manageItemList" style="table-layout:fixed;">
								 <thead>
								   	<tr>
								      	<th style="width: 11rem">사진</th>
								      	<th>판매상태</th>
								      	<th style="width: 300px;">상품명</th>
								      	<th>가격</th>
								      	<th>안전결제환영</th>
								      	<th>찜/댓글</th>
								      	<th>등록일시</th>
								    </tr>
								 </thead>
								 <tbody id="tbody">
								 	<c:forEach items="${itemList}" var="list" varStatus="status">
								   	<tr>
								   	  
								      <td class="align_c">
								      	<a href="ShowItemServiceCon.do?itemId=${list.itemId}&buyerId=${info.store_id}">
								      		<img src="${list.imgPath}">
								      	</a>
								      </td>
								      <td class="align_l">
								      	<select>
								      		<option value="S" ${list.tradeStatus eq 'S' ? 'selected' : ''}>판매중</option>
								      		<option value="R" ${list.tradeStatus eq 'R' ? 'selected' : ''}>예약중</option>
								      		<option value="D" ${list.tradeStatus eq 'D' ? 'selected' : ''}>판매취소</option>
								      		<option value="C" ${list.tradeStatus eq 'C' ? 'selected' : ''}>판매완료</option>
								      	</select>
								      </td>
								      <td class="align_l noneOverflow" style="width: 300px;">${list.itemTitle}</td>
								      <td class="align_c"><fmt:formatNumber>${list.price}</fmt:formatNumber>원</td>
								      <td class="align_c">${list.safetyTradeYn eq 'Y' ? '안전결제가능' : '안전결제불가'}</td>
								      <td class="align_c"><fmt:formatNumber>${list.numLike}</fmt:formatNumber>/<fmt:formatNumber>${list.numQuestion}</fmt:formatNumber></td>
								      <td class="align_c">${list.registrationDate2}</td>
								  	</tr>
								   	</c:forEach>
								 </tbody> 
							</table>
						
						</div>
						
						<div style="display: flex; justify-content: center;">
							<ul class="pagination">
								<li class="page-item"><button id ="btnPrev" class="page-link">&lt;</button></li>
								<%int max = Integer.parseInt(session.getAttribute("totalPage").toString());%>
								<%for(int i=1; i<= max; i++){%>
								<li class="page-item pageBox"><button class="page-link btnPageNo"><%=i%></button></li>
								<%}%>
								<li id="afterBox" class="page-item"><button id= "btnAfter" class="page-link">&gt;</button></li>
							</ul>
						</div>
								
					</div> <!--end Container  -->
				</section>   
				<input type="hidden" name="storeId" value=${info.store_id}>				
   		</div><!--end Main  -->
  
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/manageItem.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/registItem.js"></script>
    
</body>
</html>