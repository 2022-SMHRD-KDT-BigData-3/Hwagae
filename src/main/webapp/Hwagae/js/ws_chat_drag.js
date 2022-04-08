/**
 * chat_drag.js
 */
	var chatWindow = document.querySelector('#start_msg');

    /* 박스 안에 Drag 들어왔을 때 */
    chatWindow.addEventListener('dragenter', function(e) {
        console.log('dragenter');
    });
    
    /* 박스 안에 Drag를 하고 있을 때 */
    chatWindow.addEventListener('dragover', function(e) {
        e.preventDefault();
        console.log('dragover');
    });
    
    /* 박스 밖으로 Drag가 나갈 때 */
    chatWindow.addEventListener('dragleave', function(e) {
        console.log('dragleave');
    });
    
    /* 박스 안에서 Drag를 Drop했을 때 */
    chatWindow.addEventListener('drop', function(e) {
        e.preventDefault();

        console.log('drop');
		var file = e.dataTransfer.files;          
        console.log(file[0]);
		connectFileServer(file);
    });

	var FileWebSocket = null;
    function connectFileServer(file){
    	var url = "ws://172.30.1.18:8085/WebSocketTest/WebSocketFile";    
		//var url = "ws://localhost:8085/WebSocketTest/WebSocketFile";
		FileWebSocket = new WebSocket(url);
        FileWebSocket.binaryType="arraybuffer";

		FileWebSocket.onopen = function() {
            console.log("File Websocket Server Connected.");
			sendFile(file);
			
        };

        FileWebSocket.onmessage = function(evt) {
            console.log(evt.msg);
        };

        FileWebSocket.onclose = function() {
            console.log("File Websocket Server Connection is closed...");
        };
        FileWebSocket.onerror = function(e) {
            console.log(e.msg);
        }
	};    
        
	function sendFile(file){
    	//var file = document.getElementById('file').files[0];
		FileWebSocket.send('filename:'+file[0].name);
		console.log("WebSocket SendFile : " + file[0].name);
		var reader = new FileReader();
		var rawData = new ArrayBuffer();
	
		reader.loadend = function() {}
		reader.onload = function(e) {
			rawData = e.target.result;
			FileWebSocket.send(rawData);
			console.log("The file has been transferred.")
			FileWebSocket.send('end');
		}

		reader.readAsArrayBuffer(file[0]);
    };