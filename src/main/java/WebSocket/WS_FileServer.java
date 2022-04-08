package WebSocket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/WebSocketFile")
public class WS_FileServer {
	static File uploadedFile = null;
    static String fileName = null;
    static FileOutputStream fos = null;
    final static String filePath="d:/download/";

    @OnOpen
    public void OnOpen(Session session, EndpointConfig conf) {
        System.out.println("WebSocket File Server open");
    }

    @OnMessage
    public void processUpload(ByteBuffer msg, boolean last, Session session) {
        System.out.println("Binary Data Recieved");      

        while(msg.hasRemaining()) {         
            try {
                fos.write(msg.get());
            } catch (IOException e) {               
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void message(Session session, String msg) {
        System.out.println("File WebSocket Server Received String message : " + msg);
        if(!msg.equals("end")) {
            fileName=msg.substring(msg.indexOf(':')+1);
            uploadedFile = new File(filePath+fileName);
            try {
                fos = new FileOutputStream(uploadedFile);
            } catch (FileNotFoundException e) {     
                e.printStackTrace();
            }
        }else {
            try {
                fos.flush();
                fos.close();                
            } catch (IOException e) {       
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("File Websocket Session closed: "+ reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable t) {
        t.printStackTrace();
    }
}
