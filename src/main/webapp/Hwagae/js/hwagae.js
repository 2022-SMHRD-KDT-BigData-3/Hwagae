/**
 * 
 */
_e = window;

_e.progressBar = {
	
	load : function(){
		
		$.ajax({
			url : "progressBar.html",
			type : "POST",
			dataType:"html",
			async: false,
			success : function(data){
				$("#progressBar").html(data);
				
				//$(".progressBar").css("position", "absolute");
				$(".progressBar").css("height", "40px");
				$(".progressBar").css("width", "40px");
				$(".progressBar").css("top", "50%");
				$(".progressBar").css("left", "50%");
				$(".progressBar").css("padding-right", "0px");
				$(".progressBar").css("text-align", "center");
			
			},fail : function(err){
				console.log("modal.load fail :" + err);
			}
			
		});
	
	},show : function(){
		if($(".progressBar").css('display') == "none"){
			$(".progressBar").modal({backdrop: 'static', keyboard: false}) ;
			if($(".modal-backdrop").length > 1){
				$(".modal-backdrop")[1].className += " none";
				$(".modal-backdrop").css("z-index", 1050) //기본값(1040)+10;	
			}	
		}
	},hide : function(){
		$(".progressBar").modal('hide');
		$(".modal-backdrop").css("z-index", 1040) //기본값(1040)
	}
	
}

_e.modal = {
	
	load : function(divId, targetPath){
		
		$.ajax({
			url : targetPath,
			type : "POST",
			dataType:"html",
			async: false,
			success : function(data){
				$(divId).html(data);
				
				let temp = "."+targetPath.replace(".html","");
				
				$(temp).css("position","fixed");
				//$(temp).css("height", "600px");
				//$(temp).css("width", "400px");
				$(temp).css("top", "30%");
				//$(temp).css("left", "30%");
				$(temp).css("padding-right", "0px");
				$(temp).css("text-align", "center");
				
			},fail : function(err){
				console.log("modal.load fail :" + err);
			}
			
		});
	
	}, show : function(vsTarget){
	
		$("."+vsTarget).modal({backdrop: 'static', keyboard: false}) ;
		
	},close : function(voEvent, voData, vsTarget, voCaller){
	
		$(`.${vsTarget}`).modal("hide");
		$(voCaller).attr("data-returns", voData);
		
		if(typeof(window[vsTarget+"_callback"]) == "function"){
			
			eval(vsTarget+"_callback(voEvent, voCaller)");			
		}

	}
	
};
