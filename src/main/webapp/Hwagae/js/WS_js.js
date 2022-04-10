
         var WAITING_ROOM = 1;
         var CHATTING_ROOM = 2;
      var webSocket = null;
/*      var webSocket = new WebSocket("ws://localhost:8081/Hwagae/websocket");*/
      
      if(!store_id)
         console.log('log off. we dont need any websockets.');
      else{
         console.log('websocket lobby setting');
         console.log('item_id ' + item_id);
         if(!room_state) room_state = 0;
         if(!webSocket){
            console.log('websocket is null, make a socket.')
            webSocket = new WebSocket(setWebSocketParam(store_id, item_id, room_state));
         }else{
            console.log('websocket is not null, close and make a new one.');
            WebSocket.close();
            webSocket = new WebSocket(setWebSocketParam(store_id, item_id, room_state));
         }
      } 
      
      if(!webSocket) console.log('websocket is null.');
      else{
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
         try{
            let sendMsg = addCommand("M", "00", msg.val());   
            if(webSocket.readyState == 1){ // open && 공백 문자 제거
                  sendSelfMsg(msg.val());
                  console.log(sendMsg);
                 webSocket.send(sendMsg);
                 msg.val("");   
               }else if(webSocket.readyState == 2 || webSocket.readyState == 3){
                  sendSelfMsg("Server is not ready.");
               }
            }catch (err) {
            console.log(err);
         }
      }
      
      function sendLogout(store_ID){
         let sendMsg = addCommand("I", "01", store_ID);
         if (webSocket.readyState === WebSocket.OPEN) {
             webSocket.send(sendMsg);
           } else {
             // Queue a retry
             setTimeout(() => { sendLogout() }, 1000)
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
            if(!Store_ID&&!Item_ID){
               console.log('abnormal situaltion, store_id & item_id are null.');
            }else{
            if(!Item_ID) Item_ID = '00'; // 그냥 대기실 진입, 채팅 내역을 보고자 함
            str_ws = "ws://172.30.1.5:8081/Hwagae/WebSocketMessage/";
               str_ws += Store_ID + "/";
               str_ws += Item_ID + "/" + State;
         }      
         return str_ws;
         }
      
      function parseM(type, objJson){
          console.log(sender +":"+ item_ID +":"+ receiver + ":" + msg);
          sendRemoteMsg(msg);
      }
      
      function parseA(type, objJson){
        if(div==="00"){
         console.log("Alarm message" + msg);
            updateAlarm(msg);      
        }
      }
      
      function parseI(type, objJson){          
            if(div==="05"){
                console.log("Message confirm");
               if(msg==="Y") {
                  console.log('읽음'); 
                  // 서버에서 1차적으로 방에 있는 경우 읽음으로 저장하고, 로비나 대기실에 있다 방에 들어온 경우
                  // enterRoom command를 받으면 읽음 으로 처리하는 작업이 필요
                  $('.isconfirmed').text("읽음");   
             } else console.log('읽지 않음');
            }else if(div==="06"){
           console.log("History refresh");
           //$(".chatList").trigger("dblclick");
         }
      }
      
      function parseJSON(jsonText){
         try{ 
                  const obj = JSON.parse(jsonText);
                console.log(obj);
              let type = obj.type;
              let div = objJson.div;
              let msg = objJson.msg;
              let sender = objJson.sender_store_id;
              let receiver = objJson.receiver_store_id;
              let item_ID = objJson.item_id;
               if(type==="M"){
                  parseM(type, obj);
               }else if(type==="A"){
                  parseA(type, obj);
               }else if(type==="I"){
                  parseI(type, obj);
               }
            }catch(e){
            console.log(e);
            }
         }
         
         
      function addCommand(type, div, message){
            let jsonText = "";
            if(!seller_store_id){  
               console.log('can not send a msg to opponent.');
           }else{
               jsonText = {'type':type, 'div':div, 'send_store_id':store_id, 'item_id':item_id, 'receive_store_id':seller_store_id, 'msg':message};   
         }
                                   
            return JSON.stringify(jsonText);
        }
      
      function enterRoom(strtype, strdiv, str_store_id, str_item_id, str_receive_store_id, strstate){
         let jsonText = {type:'', div:'', sender_store_id:'', item_id:'', receiver_store_id:'',room_state:''};
         jsonText.type = strtype;
         jsonText.div = strdiv;
         jsonText.sender_store_id = str_store_id;
         jsonText.item_id = str_item_id;
         jsonText.receiver_store_id = str_receive_store_id;
         jsonText.room_state = strstate;
            
         console.log(jsonText);
         jsonText = JSON.stringify(jsonText);
         if (webSocket.readyState === WebSocket.OPEN) {
           webSocket.send(jsonText);
         } else {
          // Queue a retry
            setTimeout(() => { enterRoom(strtype, strdiv, str_store_id, str_item_id, str_receive_store_id, strstate) }, 1000)
         }
      }
      
      if(document.getElementById('textMessage')){
         $("#textMessage").keydown(function(keyNum){ 
               if(keyNum.keyCode == 13){
                  sendMessage(); 
                  return false;
               } 
            });
       }
      //3이 나일때
      if(document.getElementsByClassName('chatList')){
      $(".chatList").on("dblclick",function(){
         let i_s_r_ID = $(this).attr("id").split(",");
         console.log(i_s_r_ID);
         $.ajax({
            url : "WS_ChatDB.ajax",
            //ajax는 데이터를 넘겨 받을때 넘겨받는 데이터 타입을 명시해주지않는다면
            //---> 문자열 타입으로 인식한다
            type : 'GET',
            data : {
            sender : i_s_r_ID[0],
            item : i_s_r_ID[1],
            receiver : i_s_r_ID[2]
         },
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
                  if(store_id==i_s_r_ID[0]){
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
                  else if(store_id==i_s_r_ID[2]){
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
               if(store_id==i_s_r_ID[0]) seller_store_id = i_s_r_ID[2];
               else seller_store_id = i_s_r_ID[1];
               item_id = i_s_r_ID[1];
               enterRoom('I', '04', i_s_r_ID[0], i_s_r_ID[1], i_s_r_ID[2], CHATTING_ROOM);
            },
            error : function(err){
               alert("통신 실패");
            }
         
         });

         $('#chat').scrollTop($('#chat')[0].scrollHeight);
      });
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