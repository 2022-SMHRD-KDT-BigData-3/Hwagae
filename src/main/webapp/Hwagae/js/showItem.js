/**
 * 
 */
(function(){
	
	$(document).ready(function() {
		$(".slideImg").hide();
    	$(".slideImg[seq=1]").show();
		$(".slideBtn[seq=1]").addClass("active");
		
		let likeYn = $("#btnItemLike").attr("active");
		
		if(likeYn == "Y"){
			$("#btnItemLike").addClass("btnLikeActive");
		}
		
		if($("input[name=storeId]").val() == $("input[name=buyerId]").val()){
			$(".tradeBtnGroup").hide();
		}
	
	});
	
	$(".slideBtn").click(function(e){
		
		$(".slideBtn").removeClass("active");
		$(this).addClass("active");
		
		seq = $(this).attr("seq");
		
		$(".slideImg").hide();
		$(`.slideImg[seq=${seq}]`).show();
		
	});
	
	
	$("#ipbItemInfo").keyup(function(){
		
		let byte = getTextLength($(this).val());
		
		$(".questionByte").text(byte+"/100");
		

		if(100 < byte){
			$(".questionByte").css("color","red");
		}else{
			$(".questionByte").css("color","black");
		}
		
	});
	
	$("#btnItemLike").click(function(e){
		
		if($("input[name=buyerId]").val() == ''){
			alert("찜등록은 로그인 후 이용하실 수 있습니다.");
			return;
		}
		
		if($("input[name=storeId]").val() == $("input[name=buyerId]").val()){
			alert("자신이 등록한 상품은 찜등록 할 수 없습니다.");
			return;
		}
		
		
		
		let itemLikeYn;
		
		if($(this).hasClass("btnLikeActive")){
			$(this).removeClass("btnLikeActive");
			itemLikeYn= "N";
		}else{
			$(this).addClass("btnLikeActive");
			itemLikeYn= "Y";
		}
		
		changeItemLike(itemLikeYn);
		
	});
	
	
	
	$("#regiQuestionBtn").click(function(e){
		
		if($("input[name=buyerId]").val() == ''){
			alert("상품문의는 로그인 후 이용하실 수 있습니다.");
			return;
		}
		
		param = {
			"itemInfo" : $("#ipbItemInfo").val()
			,"storeId" : $("input[name=storeId]").val()
			,"itemId" : $("input[name=itemId]").val()
		};
	
		$.ajax({
			url : 'insertItemQuestion.ajax'
			,type : 'GET'
			,data : param
			,dataType : 'json'
			,contentType : "application/json;charset=UTF-8"
			,success : function(data) {
				
				$(".comments-list").text(""); //댓글 리스트 초기화
				
				for(let i=0; i < data.length; i++){
					$(".comments-list").append(`
						<div class="media">
	                		<a class="media-left" href="#">
	                    		<img src="images/user.png" alt="" class="rounded-circle">
	                    	</a>
	                    	<div class="media-body">
	                        	<h4 class="media-heading user_name">${data[i].storeName}<small>${data[i].writeDate}</small></h4>
	                       		<p>${data[i].question}</p>
	                        </div>
	                    </div>
					`);
				}
								
  			}, 
			error : function(err) {
  				console.log(err);
			}
  		});

	});
	
	$("#btnBuy").click(async function(e){
		
		let tradeStatus = $("input[name=tradeStatus]").val();
		
		if($("input[name=buyerId]").val() == ''){
			alert("상품구매는 로그인 후 이용하실 수 있습니다.");
			return;
		}

		if(tradeStatus == "C"){
			alert("해당 상품은 거래가 완료된 상태입니다.");
			return;
		}else if(tradeStatus == "D"){
			alert("해당 상품은 판매가 취소된 상태입니다.");
			return;
		}else if(tradeStatus == "R"){
			alert("해당 상품은 다른 사용자가 예약한 상태입니다.");
			return;
		}
		
		let requestParam = {
				pg : 'html5_inicis',
			    pay_method : 'card',
			    merchant_uid: $("input[name=itemId]").val(), // 상점에서 관리하는 주문 번호
			    name : `주문명:${$(".itemTitle").text().substr(0,10)}`,
			    amount : 10,
			    buyer_email : 'iamport@siot.do',
			    buyer_name : '화개장터고객',
			    buyer_tel : '010-1234-5678',
			    buyer_addr : '서울특별시 강남구 삼성동',
			    buyer_postcode : '123-456'
		};
		
		try{
			let rsp = await requestPay(requestParam);
			//let rsp = {};
			await updateTradeInfo(rsp);
			
		}catch(err){
			alert(err);
		}
		
	});
	
	
	
	function changeItemLike(itemLikeYn){
		
		param = {
			"itemLikeYn" : itemLikeYn
			,"storeId" : $("input[name=storeId]").val()
			,"itemId" : $("input[name=itemId]").val()
			,"buyerId" : $("input[name=buyerId]").val()
		};
	
		$.ajax({
			url : 'changeItemLike.ajax'
			,type : 'GET'
			,data : param
			,dataType : 'json'
			,contentType : "application/json;charset=UTF-8"
			,success : function(data) {
				console.log(data);		
  			}, 
			error : function(err) {
  				console.log(err);
			}
  		});
		
	}
	
	function requestPay(requestParam){
		return new Promise(function(resolve, reject){
			
			IMP.init("imp74526635");
			IMP.request_pay(requestParam, function(rsp) {
			
	    		if (rsp.success) {
					resolve(rsp);
					return;
			    } else {
			        reject(rsp.error_msg);
					return;
			    }

			});
				
		});
	} 
	
	function updateTradeInfo(rsp){
		return new Promise(function(resolve, reject){
			
			let param = {
				"impUid" : rsp.imp_uid
				,"apprNo" : rsp.apply_num
				,"itemId" : $("input[name=itemId]").val()
				,"buyerId" : $("input[name=buyerId]").val()
				,"quantity" : $("input[name=stock]").val()
				,"totalPrice" : $(".itemPrice").text().replace("원","")
			};
			/*let param = {
				"impUid" : "123456789"
				,"apprNo" : "123456789"
				,"itemId" : $("input[name=itemId]").val()
				,"buyerId" : $("input[name=buyerId]").val()
				,"quantity" : $("input[name=stock]").val()
				,"totalPrice" : $(".itemPrice").text().replace("원","")
			}*/
			
			

			$.ajax({
				url : 'UpdateTradeInfo.ajax'
				,type : 'GET'
				,data : param
				,dataType : 'json'
				,contentType : "application/json;charset=UTF-8"
				,success : function(data) {
					
					if(data.rsltCd == "0"){
						alert("물품구매가 정상적으로 이루어 졌습니다. \n이용해 주셔서 감사합니다.");
					}else{
						reject(data.errMsg);
					}
					return;	
	  			}, 
				error : function(err) {
	  				reject(err);
					return;
				}
  			});

		});
	}
	
	
}());