
		
   		const WAITING_ROOM = 1;
   		const CHATTING_ROOM = 2;
		var mem_data = new Array();
   		mem_data[0]='1';
		mem_data[1]='1';
		mem_data[2]='1';
		mem_data[3]='1';
/*		var webSocket = new WebSocket("ws://localhost:8081/Hwagae/websocket");*/
		if(webSocketLobby!=null) console.log("header websocket test");
      	else {
			var webSocket = null;
			if(ws_store_id!=null)
				webSocket = new WebSocket(setWebSocketParam(ws_store_id, '1', WAITING_ROOM));
			else{
				console.log("not log in. so webosket is null.")	
				webSocket = null;
			}
		}
		/* 나중에 민재씨 세션만들고나서
		if(roomstate==="1"){
      		console.log(item_id + "waiting room");     		
   		}
   		else if(roomstate==="2"){
      		console.log(item_id + "chatting room");
      		var webSocket = new WebSocket(setWebSocketParam(mem_data[0], mem_data[1], CHATTING_ROOM));
   		}*/
		
		if(webSocket!=null){
			webSocket.onopen = function(message) {
						
			};
			webSocket.onclose = function(message) {
				
			};
			webSocket.onerror = function(message) {
				
			};
			webSocket.onmessage = function(message) {										
				parseJSON(message.data);
			};
		}
				//내가보낸 메시지 sendMessage
		function sendMessage() {
			let msg = $('#textMessage');
			console.log(msg.val());
			let sendMsg = addCommand("M", "00", msg.val());	
			if(webSocket.readyState == 1){ // open && 공백 문자 제거
         		sendSelfMsg(msg.val());
		        webSocket.send(sendMsg);
		        msg.val("");   
      		}else if(webSocket.readyState == 2 || webSocket.readyState == 3){
         		sendSelfMsg("Server is not ready.");
      		}
	
		}
		
		function sendSelfMsg(msg){
			let time1 = Time();
			let liTag = "<li class='me'>"+
	        			"<div class='entete'>"+
	        			"<h3 class='clock'>"+time1+"</h3>"+
				        "<h2 name='Store_id'>V</h2>"+
				        "<h2 class='isconfirmed'>"+'읽지 않음'+"</h2>" +
				        "<span class='status blue'></span>"+
				        "</div>"+
				        "<div class='triangle'></div>"+
				        "<div class='message' name='talk_info'>"+
				          msg+
				        "</div>"+
				     	"</li>";
	      	$('#chat').append(liTag);
	      	$('#chat').scrollTop($('#chat')[0].scrollHeight);
		}
		//상대방이라고 가정한 에코메시지
		function sendRemoteMsg(msg){
			let time1 = Time();
			let liTag2 = "<li class='you'>"+
						"<div class='entete'>"+
						"<h3>"+time1+"</h3>"+
				        "<h2>V</h2>"+
				        "<span class='status green'></span>"+
				        "</div>"+
				        "<div class='triangle'></div>"+
				        "<div class='message'>"+
				          msg+
				        "</div>"+
				     	"</li>";			
			$('#chat').append(liTag2);
			$('#chat').scrollTop($('#chat')[0].scrollHeight);
		}
		//시간함수
		function Time(){
			let today = new Date();
			let hours = today.getHours(); // 시
			let minutes = today.getMinutes();  // 분
			let t = hours+"시"+minutes+"분"
			return t;
		}
		
		function disconnect() {
			webSocket.close();
		}
		
		//채팅목록 더블클릭시 주소로 이동
		function openURL(){
			window.open("https://www.naver.com/")
		};
		
		function setWebSocketParam(Store_ID, Item_ID, State){
    	  	let str_ws = null;
      		if(Store_ID!=null&&Item_ID!=null){
         	str_ws = "ws://172.30.1.5:8081/Hwagae/WebSocketMessage/";
         	str_ws += Store_ID + "/";
         	str_ws += Item_ID + "/" + State;
      		}      
      	return str_ws;
   		}

		function parseJSON(jsonText){
         try{ 
               	const obj = JSON.parse(jsonText);
             	console.log(obj);
             	let type = obj.type;
             	let div = obj.div;
            	let msg = obj.msg;
            
               if(type==="M"){
                  let sender = obj.sender_store_id;
                  let receiver = obj.receiver_store_id;
                  let item_ID = obj.item_id;
                  console.log(sender +":"+ item_ID +":"+ receiver + ":" + msg);
                  sendRemoteMsg(msg);
               }else if(type==="A"){
                  if(div==="00"){
                     console.log("Alarm message" + msg);
                     updateElements(msg);      
                  }
               }else if(type==="I"){
               if(div==="01"){
                  console.log('logout');
               }else if(div==="05"){
                  console.log("Message confirm");
                  if(msg==="Y") {
					console.log('읽음'); 
					// 서버에서 1차적으로 방에 있는 경우 읽음으로 저장하고, 로비나 대기실에 있다 방에 들어온 경우
					// enterRoom command를 받으면 읽음 으로 처리하는 작업이 필요
					$('.isconfirmed').text("읽음");	
				  }
                  else console.log('읽지 않음');
               }else if(div==="06"){
				  console.log("History refresh");
				  $(".chatList").trigger("dblclick");
			   }
            }
            }catch(e){
            console.log(e);
         }
         }
	
		function addCommand(type, div, message){
            let jsonText = "";
            
            if(type==="M"){
               jsonText = {'type':type, 'div':div, 'send_store_id':ws_store_id, 'item_id':mem_data[1], 'receive_store_id':mem_data[2], 'msg':message};   
            }else if(type==="I"){
               jsonText = {'type':type, 'div':div,
               'store_id':ws_store_id, 'item_id':mem_data[1],
               'store_name':mem_data[2], 'item_name':mem_data[3]};
            }                        
            return JSON.stringify(jsonText);
      }
		
		function enterRoom(strtype, strdiv, str_store_id, str_item_id, str_receive_store_id, strstate){
			   jsonText = {type:'', div:'', sender_store_id:'', item_id:'', receiver_store_id:'',room_state:''};
			   jsonText.type = strtype;
			   jsonText.div = strdiv;
			   jsonText.sender_store_id = str_store_id;
			   jsonText.item_id = str_item_id;
			   jsonText.receiver_store_id = str_receive_store_id;
			   jsonText.room_state = strstate;
			   
			   console.log(jsonText);
			   jsonText = JSON.stringify(jsonText);
			   webSocket.send(jsonText);		   
		}
		
		$("#textMessage").keydown(function(keyNum){ 
         	if(keyNum.keyCode == 13){
            	sendMessage(); 
            	return false;
         	} 
      	});
		//3이 나일때
		$(".chatList").on("dblclick",function(){
         let i_s_r_ID = $(this).attr("id").split(",");
         console.log(i_s_r_ID);
         $.ajax({
            url : "WS_ChatDB.ajax",
            //ajax는 데이터를 넘겨 받을때 넘겨받는 데이터 타입을 명시해주지않는다면
            //---> 문자열 타입으로 인식한다
            type : 'GET',
            dataType : "json", 
            contentType : "application/json;charset=UTF-8",
            //넘겨 받는 데이터는 무조건! json 형식으로 인식할꺼에요!
            //넘겨 받는 데이터는 json 타입이어야만 합니다!
            
            success : function(result){               
               
               $('#chat').html("");
               for(let i =0;i<result.length;i++){               
                  let item_ID = result[i].item_ID;
                        let sender_store_ID = result[i].sender_store_ID;
                        let receiver_store_ID = result[i].receiver_store_ID;
                        let talk_Info = result[i].talk_Info;
                  let talk_Date = result[i].talk_Date;
                  // 본인 채팅풍선
                  if(sender_store_ID==1){
                           $('#chat').append("<li class='me'>"+
                                   "<div class='entete'>"+
                                   "<h3 name='Store_id'>"+sender_store_ID+"</h3>"+
                                   "<h2 class='clock'>"+", "+talk_Date+"</h2>"+
                                   "<span class='status blue'></span>"+
                                   "</div>"+
                                   "<div class='triangle'></div>"+
                                   "<div class='message' name='talk_info'>"+
                                     talk_Info+
                                   "</div>"+
                                   "</li>");
                  }
                  // 상대 채팅 풍선
                  else if(sender_store_ID==i_s_r_ID[2]){
                     $('#chat').append("<li class='you'>"+
                                 "<div class='entete'>"+
                                 "<h3>"+sender_store_ID+"</h3>"+
                                   "<h2>"+", "+talk_Date+"</h2>"+
                                   "<span class='status green'></span>"+
                                   "</div>"+
                                   "<div class='triangle'></div>"+
                                   "<div class='message'>"+
                                     talk_Info+
                                   "</div>"+
                                   "</li>")
                  }
                    }
               enterRoom('I', '04', i_s_r_ID[0], i_s_r_ID[1], i_s_r_ID[2], CHATTING_ROOM);
            },
            error : function(err){
               alert("통신 실패");
            }
         
         });

         $('#chat').scrollTop($('#chat')[0].scrollHeight);
      });
		/* 대기실 업데이트 구현 필요*/
		/* 메시지 읽음 읽지않음 구현 필요*/