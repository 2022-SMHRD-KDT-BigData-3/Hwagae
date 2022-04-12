package Model;

import java.io.IOException;

import javax.websocket.Session;

public class WS_Store {
	private Session session = null;
	private Session fileSession = null;
	private String Store_ID;
	private String Item_ID;
	private String Store_Name;
	
	public String getStore_Name() {
		return Store_Name;
	}

	public void setStore_Name(String store_Name) {
		Store_Name = store_Name;
	}
	
	private int state;
	
	public WS_Store() {
		this.state = 0; 
	}
	
	public void remove() throws IOException {
		if(session!=null) session.close();
		if(fileSession!=null) fileSession.close();
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Session getFileSession() {
		return fileSession;
	}
	public void setFileSession(Session fileSession) {
		this.fileSession = fileSession;
	}
	public String getStore_ID() {
		return Store_ID;
	}
	public void setStore_ID(String store_ID) {
		Store_ID = store_ID;
	}
	public String getItem_ID() {
		return Item_ID;
	}
	public void setItem_ID(String item_ID) {
		Item_ID = item_ID;
	}
	
}
