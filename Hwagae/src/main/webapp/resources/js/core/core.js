_e = window;

_e.load = {
		
	header : function(){
		if($("#header").length !== 0){
			$.ajax({
				url : "header.html",
				type : "POST",
				dataType:"html",
				async: false,
				success : function(data){
			
					$("#header").html(data);
											
				},fail : function(err){
					console.log("header load fail :" + err);
				}
				
			});
		}
		
	}
	
	,footer : function(){
		if($("#footer").length !== 0){
			$.ajax({
				url : "footer.html",
				type : "POST",
				dataType:"html",
				async: false,
				success : function(data){			
					$("#footer").html(data);
											
				},fail : function(err){
					console.log("footer load fail :" + err);
				}
				
			});
		}
		
	}
	
};