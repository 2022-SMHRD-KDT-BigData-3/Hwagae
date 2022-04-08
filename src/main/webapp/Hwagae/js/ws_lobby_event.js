 /**
 * ws_lobby_event.js
 */
	function setWebSocketParam(Store_ID, Item_ID, State){
		let str_ws = null;
		if(Store_ID!=null&&Item_ID!=null){
			str_ws = "ws://172.30.1.5:8081/Hwagae/WebSocketMessage/";
			str_ws += Store_ID + "/";
			str_ws += Item_ID + "/" + State;
		}
		
		return str_ws;
	}
	
	const LOBBY = 0;
	var webSocketLobby = null;
	var webSocket = null;
	var ws_store_id;
	var ws_item_id;
	var ws_roomstate;
	document.addEventListener("DOMContentLoaded", () => { 
		if(webSocket!=null) console.log("websocket is already created.");
		else {
			if(ws_store_id!=null){
				console.log(ws_store_id);
				webSocketLobby = new WebSocket(setWebSocketParam(ws_store_id, '0', LOBBY));
			}else
				console.log('log off.');
		}	
	});
	
	if(webSocketLobby!=null){
		webSocketLobby.onmessage = function(message) {
		console.log(message.data);
		parseJSON(message.data);
		};	
		
		webSocket.onopen = function(message) {
						console.log(message);
		};
		webSocket.onclose = function(message) {
			console.log(message);
		};
		webSocket.onerror = function(message) {
			console.log(message);
		};
	};
	
	

	function disconnect() {
		if(webSocketLobby!=null) webSocketLobby.close();
		console.log('session disconnect')
	}
	
	function parseJSON(jsonText){
		const obj = JSON.parse(jsonText);
		console.log(obj);
		let type = obj.type;
		let div = obj.div;
		let msg = obj.msg;
		
		if(type==="A"){
			if(div==="00"){
				console.log("Alarm message" + msg);
				updateElements(msg);		
			}
		}
	}
		
	function updateElements(msg){
		$('#WaitingRoom').width("150px");           
	}

	function updateAlarm(msg){   
		console.log(msg);
	    $('.msgscrol ').width('150px');
	    $('#notificationMsg').text(msg);
	}
	
	function resetAlarm(){
		$('.msgscrol ').width('0px');
	    $('#notificationMsg').text('');
	}