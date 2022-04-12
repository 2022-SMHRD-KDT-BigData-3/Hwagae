(function(){
	
	var _e = this;
	
	$('.modalItemInfo').on("show.bs.modal", function(e){
		_e.useUrlInfoYn = "N";
		addItemInfoRow();
	});
	
	$('.modalItemInfo').on("hidden.bs.modal", function(e){
		$("tbody").text("");	
	});
	
	
	function addItemInfoRow(){
		
		var keys = Object.keys(_e.urlItemInfo);
		
	    for (var i=0; i<keys.length; i++) {
	    	var key = keys[i];
	    	let lag = `<tr>
						<td>${key}</td>
						<td>${_e.urlItemInfo[key]}</td>
					   </tr>`
			$("tbody").append(lag);
	    }
		
	}
	
	$("#btnModalConfirm").click(function(e){
		_e.useUrlInfoYn = "Y";
		$("#btnModalClose").trigger("click");
	});
	
	
}());