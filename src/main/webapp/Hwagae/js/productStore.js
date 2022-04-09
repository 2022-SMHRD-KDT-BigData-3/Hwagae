(function(){
	
	$("#imgPath").change(function(e){
		readMultipleImage(e.target);
	});
	

	$("#btnModifyStoreInfo").click(function(){
		
		$("#storeInfo").removeClass("printStoreInfo");
		$("#storeInfo").addClass("editStoreInfo");
		$("#storeInfo").prop("readonly", false);
		
		$("#btnConfirm").show();
		$("#btnModifyStoreInfo").hide();
		
		$("#storeInfo").focus();
		
	});
	
	$("#btnConfirm").click(function(){
		
		let param = {
			"storeId" : $("input[name=storeId]").val()
			,"itemInfo" : $("#storeInfo").val()
		}
		
		$.ajax({
	         url : 'ModifyStoreInfoServiceCon.ajax'
	         ,type : 'GET'
	         ,data : param
	         ,dataType : 'json'
	         ,contentType : "application/json;charset=UTF-8"
	         ,success : function(data) {
	            
	           	if(data.rsltCd == 0){
					$("#storeInfo").removeClass("editStoreInfo");
					$("#storeInfo").addClass("printStoreInfo");
					$("#storeInfo").prop("readonly", true);
					
					$("#btnConfirm").hide();
					$("#btnModifyStoreInfo").show();
				}
	                        
	           
	         },error : function(err) {
	              console.log(err);
	         }
        });
	
	});
	
	$("#selectTradeStatus").change(function(e){
		
		let status = $(this).val();
		
		$(".item").hide();
		
		if(status == ""){ //전체
			$(".item").show();
		}else{
			$(`.item[status=${status}]`).show();
		}
		
		
	});
	
	
	function readMultipleImage(input){
		
		let filesArr = Array.prototype.slice.call(input.files);	

		filesArr.forEach(function(file){
			
			let reader = new FileReader();
			
			reader.onload = function(e){
				$("#imgProfile").attr("src", e.target.result);
				saveProfile(file);
			}
			reader.readAsDataURL(file);
		});
					
	}
	
	function saveProfile(file){
		
		var formData = new FormData();
		formData.append("storeId", $("input[name=storeId]").val());
		formData.append("itemId","profile");
		formData.append("profile", file);
		
		$.ajax({
			 type: "POST"
			,enctype: 'multipart/form-data' // 필수 
			,url: 'ProfileUploadServiceCon.ajax'
			,data: formData // 필수 
			,processData: false // 필수 
			,contentType: false // 필수 cache: false
			, success: function (result) {
				debugger;
			}, error: function (err) {
				debugger;
			}
		 });
		
	}
	
		
}());