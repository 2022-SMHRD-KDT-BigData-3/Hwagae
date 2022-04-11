/**
 * 
 */
(function(){
	
	let no =1;
	let selPage = 10;
	let status = "A";
	let keyword = "";
	
	
	$(document).on("click" , ".btnPageNo", function(e) {  
		no = $(this).text();	
		retrieveManageItem();
	});
	

	$("#btnPrev").click(function(e){
		
		if(no == 1){
			return;
		}
		no--;

		retrieveManageItem();
	})
	
	$("#btnAfter").click(function(e){
		
		if(no == totalPage){
			return;
		}
		no++;
		
		retrieveManageItem();
	});
	
	
	$("#btnSerachItem").click(function(e){
		no =1;
		selPage = $("#selPage").val();
		status = $("#selStatus").val();
		keyword = $("#ipbKeyword").val();
		
		retrieveManageItem();		
	});
	
	
	function retrieveManageItem(){
		return new Promise(function(resolve, reject){
			
			let param = {
				"storeId" : $("input[name=storeId]").val()
				,"page" : selPage
				,"keyword" : keyword
				,"status" : status
				,"no" : no
			}
			
			
			$.ajax({
				url : 'RetrieveManageItem.ajax'
				,type : 'GET'
				,data : param
				,dataType : 'json'
				,contentType : "application/json;charset=UTF-8"
				,success : function(data) {
					
					$("#tbody").text(""); //물품관리 리스트 초기화
					$(".pageBox").remove();
					let tag;
					for(let i = 0 ; i < data.itemList.length; i++){
						
						tag= `<tr>  	  
								      <td class="align_c">
								      	<a href="ShowItemServiceCon.do?itemId="${data.itemList[i].itemId}"&buyerId="${data.itemList[i].store_id}">
								      		<img src="${data.itemList[i].imgPath}">
								      	</a>
								      </td>
								      <td class="align_l">
								      	<select>
								      		<option value="S" ${data.itemList[i].tradeStatus == 'S' ? 'selected' : ''}>판매중</option>
								      		<option value="R" ${data.itemList[i].tradeStatus == 'R' ? 'selected' : ''}>예약중</option>
								      		<option value="D" ${data.itemList[i].tradeStatus == 'D' ? 'selected' : ''}>판매취소</option>
								      		<option value="C" ${data.itemList[i].tradeStatus == 'C' ? 'selected' : ''}>판매완료</option>
								      	</select>
								      </td>
								      <td class="align_l noneOverflow" style="width: 300px;">${data.itemList[i].itemTitle}</td>
								      <td class="align_c"><fmt:formatNumber>${data.itemList[i].price}</fmt:formatNumber>원</td>
								      <td class="align_c">${data.itemList[i].safetyTradeYn == 'Y' ? '안전결제가능' : '안전결제불가'}</td>
								      <td class="align_c">${data.itemList[i].numLike}/${data.itemList[i].numQuestion}</td>
								      <td class="align_c">${data.itemList[i].registrationDate2}</td>
								</tr>`;
						
						$("#tbody").append(tag);
					}
					
					for(let i = 1; i <= data.totalPage; i++){
						$("#afterBox").before(`<li class="page-item pageBox"><button class="page-link btnPageNo">${i}</button></li>`)
					}
					
					
						
	  			}, 
				error : function(err) {
	  				console.log(err);
				}
  			});
			
			
		});
	}

	
}());