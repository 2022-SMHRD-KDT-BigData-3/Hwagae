 /**
 * ws_lobby_event.js
 */
    var LOBBY = 0;
    var webSocket = null;
   var seller_store_id = null;
   var store_id = null;
   var item_id = null;
   var room_state = null;
      
   function setWebSocketParam(Store_ID, Item_ID, State){
      let str_ws = null;
      if(Store_ID!=null&&Item_ID!=null){
         str_ws = "ws://172.30.1.5:8081/Hwagae/WebSocketMessage/";
         str_ws += Store_ID + "/";
         str_ws += Item_ID + "/" + State;
      }
      
      return str_ws;
   }
   
   document.addEventListener("DOMContentLoaded", () => { 
      if(!webSocket){
         if(!store_id){
            console.log('header.jsp log off');
         }else{
            console.log('websocket in header, item_id: 00 setting');
            webSocket = new WebSocket(setWebSocketParam(store_id, '00', LOBBY));
         }
      } 
      else {
         console.log("websocket is already created.");
      }
   });
   
   if(webSocket!=null){
      webSocket.onmessage = function(message) {
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
      if(webSocket!=null) webSocketLobby.close();
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
      

   function updateAlarm(msg){   
      console.log(msg);
       $('.msgscrol ').width('150px');
       $('#notificationMsg').text(msg);
   }
   
   function resetAlarm(){
      $('.msgscrol ').width('0px');
       $('#notificationMsg').text('');
   }