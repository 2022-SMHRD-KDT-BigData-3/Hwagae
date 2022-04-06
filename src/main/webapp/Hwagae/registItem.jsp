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
	
</head>
<body>

    <div id="preloader">
        
    </div>

    <div id="wrapper">
    
		<%@ include file="header.jsp"%>
     
   		<div><!--Start Main  -->
   			
   				<section class="section wb">
		            <div class="container">
		      			 <div class="row">
		      			 	<div class="col-md-12 registInfo">
		            			<h2>기본정보<span>*필수항목</span></h2>
		            		</div>
		            		
		            		<div class="col-md-2 registhd">
		            			상품이미지<span class="mandatoryMark">*</span><small id="itemImgCount">(0/12)</small>
		            		</div>
		            		
		            		<div class="col-md-10">
		            			<div class="row">
		            				<div class="col-md-12 imageList">
			            		 		
				            		 		<ui id="multipleContailer">
				            		 			<li class="registImg">
				            		 				<span class="material-icons">photo_camera</span>
				            		 				이미지등록
				            		 				<!-- <form id="fileForm" method="post" enctype="multipart/form-data"> -->
				            		 					<input type="file" id="imgPath" name="imgPath" accept="image/jpg, image/jpeg, image/png" multiple="multiple">
				            		 				<!-- </form> -->
				            		 			</li>
				            		 		</ui>
			            		 		</form>
		            		 		</div>
									<div class="col-md-12 printRoll">
			            		 		<b>* 상품 이미지는 640x640에 최적화 되어 있습니다.</b><br>
			            		 		- 이미지는 상품등록 시 정사각형으로 짤려서 등록됩니다.<br>
			            		 		- 이미지를 클릭 할 경우 원본이미지를 확인할 수 있습니다.<br>
			            		 		- 이미지를 클릭 후 이동하여 등록순서를 변경할 수 있습니다.<br>
			            		 		- 큰 이미지일경우 이미지가 깨지는 경우가 발생할 수 있습니다.<br>
			            		 		- 최대 지원 사이즈인 640 X 640 으로 리사이즈 해서 올려주세요.(개당 이미지 최대 10M)<br>
		            		 		</div>
		            			</div>	           
		            		</div>
		      			 </div>
		      			 
		      			 <hr>
		      			 
		      			 <div class="row RegistItemTitle">
		      			 	<div class="col-md-2 registhd">
		            			제목<span class="mandatoryMark">*</span><small id="itemTitleByte">0/40</small>
		            		</div>
		      			 	
		      			 	<div class="col-md-10 ItemTitleBox">
		            			<input type="text" name="itemTitle" placeholder="상품 제목을 입력해 주세요!">
		            			<span id="titleRoll" style="display:none;"><label class="material-icons">do_not_disturb_off</label>상품명을 2자 이상 입력해주세요.</span>
		            		</div>
		      			 </div>
	
						 <hr>
	
						 <div class="row">
						 	<div class="col-md-2 registhd">
						 		카테고리<span class="mandatoryMark">*</span>
						 	</div>
						 	
						 	<div class="col-md-10">
								<div class="row" style="width: 100%; padding-left: 15px">
									<div class="selectionAllBox">
										<div id="section1" class="col-md-4 selectionBox">
											
										</div>
										<div id="section2" class="col-md-4 selectionBox">
											중분류
										</div>
										<div id="section3" class="col-md-4 selectionBox">
											대분류
										</div>
									</div>
									<div class="col-md-12">
										선택한 카테고리: <label id="setionTag1"></label><label id="setionTag2"></label><label id="setionTag3"></label>
										<input type="hidden" name="itemCategory">
									</div>
						 		</div>
						 	</div>
						 </div>
						
						 <hr>
						
						<div class="row">
							<div class="col-md-2 registhd">
						 		거래지역<span class="mandatoryMark">*</span>
						 	</div>
						 	
						 	<div class="col-md-10">
						 		<div class="row">
		            				<div class="col-md-12 locationOption">
			            		 		<button id="btnGetGpsLocation">내 위치</button>
			            		 		<button id="btnGetLastLocation">최근 지역</button>
			            		 		<button id="btnSearchLocation">주소 검색</button>
			            		 		<button id="btnNotUseLocation">지역설정안함</button>
		            		 		</div>
									<div class="col-md-12 locationPrint">
			            		 		<input type="text" name= "tradeArea" placeholder="선호 거래 지역을 검색해주세요." readonly>
		            		 		</div>
		            			</div>	
						 	</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col-md-2 registhd">
						 		상태<span class="mandatoryMark">*</span>
						 	</div>
						 	<div class="col-md-10">
	  							<input type="radio" name="itemStatus" value="U" checked="checked"/><label>중고상품</label>
	  							<input type="radio" name="itemStatus" id="N"/><label>새상품</label>
						 	</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col-md-2 registhd">
						 		교환<span class="mandatoryMark">*</span>
						 	</div>
						 	<div class="col-md-10">
						 		<input type="radio" name="exchangeYn" value="N" checked="checked"/><label>교환불가</label>
	  							<input type="radio" name="exchangeYn" value="Y"/><label>교환가능</label>
						 	</div>
						</div>
	
						<hr>
	
						<div class="row">
							<div class="col-md-2 registhd">
						 		가격<span class="mandatoryMark">*</span>
						 	</div>
						 	<div class="col-md-10 registPrice">
						 		<input type="number" name= "price" placeholder="숫자를 입력해 주세요"><label>원</label><br>
	  							<input type="checkbox" name="includeDeliveryPriceYn" value="Y"/><label>배송비 포함</label>
						 	</div>
						</div>
	
						<hr>
	
						<div class="row">
							<div class="col-md-2 registhd">
						 		설명<span class="mandatoryMark">*</span>
						 	</div>
						 	<div class="col-md-10 registItemInfo">
						 		<textarea name="itemInfo" placeholder="상품설명을 입력해 주세요(10글자 이상)"></textarea>
						 	</div>
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col-md-2 registhd">
						 		연관태그
						 	</div>
						 	<div class="col-md-10 registItemTag">
						 		<div class="row">
									<div class="col-md-12 ItemTagInput">
			            		 		<input type="text" name="relationTag" placeholder="연관태그를 입력해주세요. (최대 5개)">
		            		 		</div>
		            		 		<div class="col-md-12 printRoll" style="color: black;">
			            		 		- 태그는 띄어쓰기로 구분되며 최대 9자까지 입력할 수 있습니다. <br>
			            		 		- 태그는 검색의 부가정보로 사용 되지만, 검색 결과 노출을 보장하지는 않습니다. <br>
			            		 		- 검색 광고는 태그정보를 기준으로 노출됩니다. <br>
			            		 		- 상품과 직접 관련이 없는 다른 상품명, 브랜드, 스팸성 키워드 등을 입력하면 노출이 중단되거나 상품이 삭제될 수 있습니다.<br>		
		            		 		</div>
		            			</div>	
						 	</div>
						 	
						</div>
						
						<hr>
						
						<div class="row">
							<div class="col-md-2 registhd">
						 		수량
						 	</div>
						 	<div class="col-md-10 registStock">
						 		<input type="number" name="stock" value="1"><label>개</label>
						 	</div>
						</div>
						
						<div class="row">
		      			 	<div class="col-md-12 registInfo">
		            			<h2>빠른 판매<span style="color: black;">내 상품에 안전결제 배지가 표시돼요.</span></h2>
		            		</div>
		            		
		            		<div class="col-md-2 registhd">
		            			옵션
		            		</div>
		            		
		            		<div class="col-md-10 registhd">
		            			<div class="row">
		            				<div class="col-md-12">
			            		 		<input type="checkbox" name="safetyTradeYn" value="Y"/><label>안전결제 환영</label>
		            		 		</div>
									<div class="col-md-12 printSafytyRoll">
			            		 		<p><label class="material-icons">done</label>안전결제 요청을 거절하지 않는 대신 혜택을 받을 수 있어요.</p>
			            		 		<p><label class="material-icons">done</label>내 상품을 먼저 보여주는 전용 필터로 더 빠르게 판매할 수 있어요.</p>
			            		 		<p><label class="material-icons">done</label>번개페이 배지로 더 많은 관심을 받을 수 있어요.</p>		
		            		 		</div>
		            			</div>	
		            		</div>
		            		
						</div>
						
					</div> <!--end Container  -->
				</section>   
				
				<div id="registButtonArea">
					<!-- <input type="submit" value="등록하기"> -->
					<button id="btnRegist">등록하기</button>
				</div>
   			
   								
   		</div><!--end Main  -->
  
    </div>
    <!-- Core JavaScript
    ================================================== -->
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>
    <script src="js/registItem.js"></script>

</body>
</html>