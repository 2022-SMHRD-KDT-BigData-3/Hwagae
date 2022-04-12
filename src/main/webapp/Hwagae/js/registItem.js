(function(){
	
	_e = window;
	_e.progressBar.load();
	_e.modal.load("#divModalItemInfo", "modalItemInfo.html"); //상품정보검색 팝업 셋팅
	_e.urlItemInfo= {};
	_e.useUrlInfoYn = "N";
	
	let fileList = new Array();
	
	$(document).ready(function() {
    	setCategory(null,null);
	});
	
	
	/**
     * 상품이미지 업로드. 
     **/
	$("#imgPath").change(function(e){
		readMultipleImage(e.target);
	});
	
	/**
     * 카테고리 선택값에 따라 하위 카테고리의 목록을 변경한다. 
     **/
	let selectedSection1 = -1;
	let selectedSection2 = -1;
	let selectedSection3 = -1;
	
	$(document).on("click" , ".btnCategory", function() {              
		
		let sectionLevel = $(this).data("level");
		let sectionValue = $(this).data("value");

		if(sectionLevel == "section1" && selectedSection1 != sectionValue){		
			
			//카테고리 ui 초기화
			$("#section2").text("");
			$("#section3").text("소분류");
			
			//카테고리 경로 초기화
			$("#setionTag1").text($(this).text());
			$("#setionTag2").text("");
			$("#setionTag3").text("");
			
			//하위 selectedSection 변수값 초기화
			selectedSection2 = -1;
			selectedSection3 = -1;
			
			//카테고리 정보 로드
			setCategory(sectionValue,null);
			selectedSection1 = sectionValue;	
			
		}else if(sectionLevel == "section2" && selectedSection2 != sectionValue){		
			
			//카테고리 ui 초기화
			$("#section3").text("");
			
			//카테고리 경로 초기화
			$("#setionTag2").text(" > "+$(this).text());
			$("#setionTag3").text("");
			
			//하위 selectedSection 변수값 초기화
			selectedSection3 = -1;
			
			setCategory(selectedSection1,sectionValue);
			selectedSection2 = sectionValue;	
		}else{
			//카테고리 경로 초기화
			$("#setionTag3").text(" > "+$(this).text());
			selectedSection3 = sectionValue;
		}
		$("input[name=itemCategory]").val($(this).data("catSeq"));
		        
     });   

	/*
	 * 사용자가 입력하는 판매제목이 DB에서 설정된 크기를 초과하지 않도록 체크한다.
	 */
	$("input[name=itemTitle]").keyup(function(){
		let byte = getTextLength($(this).val());
		let length = $(this).val().length;
		
		$("#itemTitleByte").text(byte+"/40");
		
		if(2 >= length){
			$("#titleRoll").show();	
		}else{
			$("#titleRoll").hide();
		}
		
		if(40 < byte){
			$("#itemTitleByte").css("color","red");
		}else{
			$("#itemTitleByte").css("color","black");
		}
		
	});
	
	/**
     * geolocation을 통해 위치정보 획득 후 카카오API를 통해 주소정보를 획득한다. 
     **/
	$("#btnGetGpsLocation").click(async function(){
  		
		try{
			let position = await getPosition();
			let tradeArea = await getLocation(position);
			
			$("input[name=tradeArea]").val(tradeArea);
		}catch(err){
			alert(err);
		}
	
	});
		
	/**
     * 사용자가 등록한 ITEM 정보 중 마지막으로 등록한 ITEM의 TRADE_AREA정보를 획득한다. 
     **/
	$("#btnGetLastLocation").click(function(){	
		retrieveLastTradeArea();
	});
	
	/**
     * 카카오주소API 통해 주소검색 팝업창을 호출하고 선택값을 획득한다. 
     **/
	$("#btnSearchLocation").click(function(){
		new daum.Postcode({
		        oncomplete: function(data) {
		          $("input[name=tradeArea]").val(data.roadname);
		        }
		    }).open();
	});
	
	/**
     * 거래지역을 설정하지 않음으로 처리한다. 
     **/
	$("#btnNotUseLocation").click(function(){
		$("input[name=tradeArea]").val("");
		$("input[name=tradeArea]").prop("placeholder", "지역설정안함")
	});
	
	
	$("#btnRegist").click(async function(){

		try{
			let itemId = await registItem();
			await fileUpload(itemId);	
		}catch(err){
			alert(err);
		}
		
	});
	
	$("input[name=vendorUrl]").blur(async function(e){
		
		try{
			_e.progressBar.show();
			
			let data = await crawlingItemInfo($(this).val());
			_e.urlItemInfo =data;
			
			_e.modal.show("modalItemInfo");
			
		}catch(err){
			alert(err);
		}finally{
			_e.progressBar.hide();
		}
		
	});
	
	$("#btnTest").click(function(e){
		_e.modal.show("modalItemInfo");
	});
	
	
	
	function getTextLength(str) {
       var len = 0;
       for (var i = 0; i < str.length; i++) {
           if (escape(str.charAt(i)).length == 6) {
               len++;
          }
           len++;
      }
       return len;
	}
	
	function getPosition(){
		return new Promise(function(resolve, reject){
					
			if (navigator.geolocation) { // GPS를 지원하면
			
		    	navigator.geolocation.getCurrentPosition(function(position) {
				
					let result = {};
					result.latitude = position.coords.latitude;
					result.longitude = position.coords.longitude;
					
					resolve(result);
					
			    }, function(error) {
			      	reject('GPS정보를 정상적으로 조회하지 못했습니다. 주소 검색을 이용해 주세요.');		 
			    }, {
			      	enableHighAccuracy: false,
			      	maximumAge: 0,
			      	timeout: Infinity
			    });
		  	} else {
		    	reject('GPS를 지원하지 않습니다. 주소 검색을 이용해 주세요.');
		 	}
		});
		
	}
	
	function getLocation(position){
		return new Promise(function(resolve, reject){
			
			let apiKey = "89d1c7fb0e52d31f75afe14b77cbeff7";
			
			$.ajax({
				url : 'https://dapi.kakao.com/v2/local/geo/coord2address.json?x=' + position.longitude +'&y=' + position.latitude,
    			type : 'GET',
    			headers : {
      				'Authorization' : `KakaoAK ${apiKey}`
    			},
    			success : function(data) {
					
					if(data.documents.length == 0){
						reject("현재 위치에 대한 주소정보를 획득하지 못했습니다. 주소 검색을 이용해 주세요.");
						return;
					}
					
      				resolve(data.documents[0].road_address.road_name);
      			}, 
    			error : function(err) {
      				reject("현재 위치에 대한 주소정보를 획득하지 못했습니다. 주소 검색을 이용해 주세요.");
    			}
  			});
									
		});
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
				
				let ui = $("<ui>");;
				let li;
				let button;
				
				for(let i = 0; i < data.length; i++){
				
					li = $("<li>");
					//button = $("<button>", { class : "btnCategory", text : data[i].sectionInfo, level : getSectionLevel(section1, section2), value : getSectionValue(section1, section2, data[i])});
					button = $("<button>", { class : "btnCategory", text : data[i].sectionInfo});
					button.data("level", getSectionLevel(section1, section2));
					button.data("value", getSectionValue(section1, section2, data[i]));
					button.data("catSeq", data[i].catSeq);
					
					li.append(button);
					ui.append(li);
				
				}
				
				if(section1 == null && section2 ==  null){ //대분류 카테고리 생성
					$("#section1").append(ui);
				}else if(section1 != null && section2 == null){ //중분류 카테고리 생성
					$("#section2").append(ui);
				}else{ //소분류 카테고리 생성
					$("#section3").append(ui);
				}
				
  			}, 
			error : function(err) {
  				console.log(err);
			}
  		});
		
	}
	
	function getSectionValue(section1, section2, data){
	
		let result;
	
		if(section1 == null && section2 ==  null){ //대분류 카테고리 생성
			result = data.section1;
		}else if(section1 != null && section2 == null){ //중분류 카테고리 생성
			result = data.section2;
		}else{
			result = data.section3;
		}
	
		return result;
	}
	
	function getSectionLevel(section1, section2){
	
		let result;
	
		if(section1 == null && section2 ==  null){ //대분류 카테고리 생성
			result = "section1";
		}else if(section1 != null && section2 == null){ //중분류 카테고리 생성
			result = "section2";
		}else{
			result = "section3";
		}
	
		return result;
	}

	
	function readMultipleImage(input){
		
		let filesArr = Array.prototype.slice.call(input.files);	
		let li;
		let img;
		
		filesArr.forEach(function(file){
			
			fileList.push(file);
			let reader = new FileReader();
			
			reader.onload = function(e){
				li = $("<li>", { class : "registImg"});
				img = $("<img>", {src : e.target.result});
				li.append(img);
				$("#multipleContailer").append(li);
			}

			reader.readAsDataURL(file);
		});
		$("#itemImgCount").text(`(${fileList.length}/12)`)
	}
	
	function retrieveLastTradeArea(){
		
		$.ajax({
			url : 'RetrieveLastTradeAreaServiceCon.ajax'
			,type : 'GET'
			,data : section
			,dataType : 'json'
			,contentType : "application/json;charset=UTF-8"
			,success : function(data) {
				$("input[name=tradeArea]").val(data.lastTradeArea);
  			}, 
			error : function(err) {
  				console.log(err);
			}
		});
		
	}
	

	function registItem(){
		return new Promise(function(resolve, reject){
			
			let requestParam = {
				"storeId" : $("input[name=storeId]").val()
				,"itemTitle" : $("input[name=itemTitle]").val()
				,"itemInfo" : $("textarea[name=itemInfo]").val()
				,"itemCategory" : $("input[name=itemCategory]").val()
				,"itemStatus" : $("input[name=itemStatus]:checked").val()         
				,"exchangeYn" : $("input[name=exchangeYn]:checked").val() 
				,"price" : $("input[name=price]").val()
				,"includeDeliveryPriceYn" : $("input[name=includeDeliveryPriceYn]").is(":checked") ? "Y" : "N"
				,"relationTag" : $("input[name=relationTag]").val()
				,"tradeArea" : $("input[name=tradeArea]").val()
				,"stock" : $("input[name=stock]").val()
				,"safetyTradeYn" : $("input[name=safetyTradeYn]").is(":checked") ? "Y" : "N"
				,"imgPath" : fileList[0].name	
				,"vendorUrl" : $("input[name=vendorUrl]").val()
				,"vendorUrlInfo" : _e.useUrlInfoYn == "Y" ? JSON.stringify(_e.urlItemInfo) : ""
			}
		
			console.log(requestParam);
		
			$.ajax({
				url : 'RegistItemServiceCon.ajax'
				,type : 'GET'
				,data : requestParam
				,dataType : 'json'
				,contentType : "application/json;charset=UTF-8"
				,success : function(data) {
					if(data.rsltCd == 0){
						resolve(data.itemId);
					}else{
						reject(data.errMsg);
					}
					return;
	  			}, 
				error : function(err) {
	  				console.log(err);
				}
			});
			
		});
	}
	
	function fileUpload(itemId){
		
		var formData = new FormData();
		formData.append("itemId", itemId);
		formData.append("storeId", $("input[name=storeId]").val());
		
		for(let i =0; i < fileList.length; i++){
			formData.append("imgPath"+0, fileList[i]);
		}
			
		$.ajax({
			 type: "POST"
			, enctype: 'multipart/form-data' // 필수 
			,url: 'FileUploadServiceCon.ajax'
			, data: formData // 필수 
			,processData: false // 필수 
			,contentType: false // 필수 cache: false
			, success: function (result) {
				location.href = "sample.jsp";
			}, error: function (err) {
				debugger;
			}
		 });
	
	}
	
	function crawlingItemInfo(url){
		return new Promise(function(resolve,reject){
			
			$.ajax({
				url : 'CrawlingItemInfoServiceCon.ajax'
				,type : 'GET'
				,data : {"url" : url}
				,dataType : 'json'
				,contentType : "application/json;charset=UTF-8"
				,success : function(data) {
					
					if(data.rsltCd == 0){
						resolve(data.itemInfo);
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