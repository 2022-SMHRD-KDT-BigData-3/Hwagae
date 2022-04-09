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
								
  			}, 
			error : function(err) {
  				console.log(err);
			}
  		});
		
	}
	
		
}());