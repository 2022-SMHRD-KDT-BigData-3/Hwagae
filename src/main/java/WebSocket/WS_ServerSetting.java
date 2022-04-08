package WebSocket;

public interface WS_ServerSetting {
	// room state
	public static final int LOBBY = 0;
	public static final int WAITING_ROOM = 1;
	public static final int CHATTING_ROOM = 2;
	
	// command order
	public static final int TYPE = 0;
	public static final int DIV = 1;
	public static final int SENDER_STORE_ID = 2;						
	public static final int ITEM_ID = 3;
	public static final int RECEIVER_STORE_ID = 4;
	public static final int MSG = 5;
	
	// div 
	public static final String CHATTING = "00";
	public static final String WAITING = "01";
	public static final String HWAGAE_TALK = "00";
	public static final String LIKE_ITEM = "01";						
	public static final String TOTAL_INFO = "00";
	public static final String LOGOUT = "01";
	public static final String UPLOAD = "02";
	public static final String DOWNLOAD = "03";
	public static final String ENTER_ROOM = "04";
	public static final String MSG_CONFIRM = "05";
	public static final String HISTORY_REFRESH = "06";
}
