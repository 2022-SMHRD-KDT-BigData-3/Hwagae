package WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Model.WS_Room;
import Model.WS_Store;
import Model.WS_TalkDAO;
import Model.WS_TalkDTO;

@ServerEndpoint(value = "/WebSocketMessage/{STORE_ID}/{ITEM_ID}/{STATE}")
public class WS_ChatServer implements WS_ServerSetting {
	// 채팅창이 아니더라도(ex 메인페이지) websocket이 연결되어야 알람을 받을수 있기에
	// mapMember에서 login 된 member들을 관리하고, 대기실 및 채팅방은 room instance에서 관리한다.
	private static HashMap<String, WS_Store> mapMember = new HashMap<String, WS_Store>();
	private static WS_Room room = new WS_Room();
	public static final Logger logger = LogManager.getLogger("myTest");
	
	/**
	 * 채팅방에서 새로 고침한 경우 채팅 내역을 다시 불러오기 위한 command
	 * @param :session, 상점id, 메시지 확인 여부
	 * @return : void
	 */
	private void sendHistoryRefresh(Session session, WS_Store mem) {
		String roomName = room.getRoomName(mem.getStore_ID());
		String receiver_store_id = null;
		String sender_store_id = mem.getStore_ID();
		ArrayList<String> members = room.getRoomMember(roomName);
		for(String member:members) {
			receiver_store_id = member;
		}
	
		String jsontext = "";
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "I");
		jsonObject.addProperty("div", HISTORY_REFRESH);
		jsonObject.addProperty("sender_store_id", sender_store_id);
		jsonObject.addProperty("receiver_store_id", receiver_store_id);

		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);

		try {
			session.getBasicRemote().sendText(jsontext);
			logger.debug("history refresh : " + jsontext.toString());
		} catch (IOException e) {
			logger.debug("history refresh : " + e.toString());
		}
	}
	
	/**
	 * 메시지 confirm command 생성
	 * @param :session, 상점id, 메시지 확인 여부
	 * @return : void
	 */
	private void sendMsgConfirm(Session session, String sender_store_ID, String confirm) {
		// server에서 관리하는 알람은 화개톡으로 한정, 회원이 lobby에 있을때만
		String jsontext = "";
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "I");
		jsonObject.addProperty("div", MSG_CONFIRM);
		jsonObject.addProperty("sender_store_id", sender_store_ID);
		jsonObject.addProperty("msg", confirm);

		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);

		try {
			session.getBasicRemote().sendText(jsontext);
			logger.debug("send msg confirm : " + jsontext.toString());
		} catch (IOException e) {
			logger.debug("send msg confirm : " + e.toString());
		}
	}
	
	/**
	 * 알림 텍스트 생성
	 * @param :session, 알림을 받을 상점id, command 구분 값, 알림 메시지
	 * @return : void
	 */
	private void sendAlarm(Session session, String store_ID, String div, String message) {
		// server에서 관리하는 알람은 화개톡으로 한정, 회원이 lobby에 있을때만
		String jsontext = "";
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "A");
		jsonObject.addProperty("div", div);
		jsonObject.addProperty("store_id", store_ID);
		jsonObject.addProperty("msg", message);

		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);

		try {
			session.getBasicRemote().sendText(jsontext);
			logger.debug("send alarm : " + jsontext.toString());
		} catch (IOException e) {
			logger.debug("send alarm : " + e.toString());
		}
	}

	/**
	 * command와 message 분리
	 * @param : command와 대화 메시지가 포함된 클라이언트로 부터 전달받은 메시지
	 * @return : 인덱싱 활용을 위해 파싱된 ArrayList
	 */
	private ArrayList<String> parseMessage(String message) {
		JsonElement element = JsonParser.parseString(message);
		JsonObject obj = element.getAsJsonObject();
		Set<Entry<String, JsonElement>> elements = obj.entrySet();

		ArrayList<String> values = new ArrayList<String>();

		for (Entry<String, JsonElement> entry : elements) 
			values.add(entry.getValue().getAsString());

		return values;
	}

	/**
	 * 페이지 이동, 새로고침 시 websocket session 관리
	 * @param :session : close시킬 session, store_ID : 상점 ID
	 * @return : void
	 */
	public void closeSession(String store_ID) {
		try {
			// client가 로그아웃 하기 전에는 socket close되고 session이 null이 되었더라도
			// mapping은 유지 한다.
			WS_Store mem = mapMember.get(store_ID);
			mem.setSession(null);
			logger.debug("session null : store_id : " + mem.getStore_ID());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}

	// 로그 아웃 처리 시 구현
	private void removeMember(String store_ID) {

	}
	
	/**
	 * 채팅방에서 나간 경우 방 정보 reset
	 * @param :String 상점 id
	 * @return : 방 정보가 없다면 false
	 */
	private boolean removeRoomInfo(String store_ID) {
		boolean ret = false;
		if (room.checkRoomInfo(store_ID)) {
			logger.debug("remove room info : store_id : " + store_ID );
			room.leaveRoom(store_ID);
		}
		return ret;
	}

	/**
	 * websocket connect 이후 path parameter 받은 정보들로 member 변수 설정
	 * @param : store_ID : 상점 ID , item_ID : 상품 ID, session : 현재 websocket session,
	 *         				state : client가 어디 있는지(0: lobby, 1: waiting_room, 2: chatting_room)
	 * @return : void
	 */
	private void addMember(String store_ID, String item_ID, Session session, int state) {
		WS_Store mem = null;

		try {
			// mapMember에 websocket session 정보가 있는지 확인
			mem = mapMember.get(store_ID);

			if (mem == null) {
				logger.debug("a member logged in first, make a instance of member : " + mapMember.size());
				mem = new WS_Store();
				mem.setStore_ID(store_ID);
				mem.setItem_ID(item_ID); // null or 공백: 상품 id가 없는 경우 : 알림 확인, 번개톡 버튼 누른 경우
				mem.setSession(session);
				mem.setState(state);
			} else {
				if (mem.getSession() == null) {
					logger.debug("a member session needs to refresh");
					mem.setSession(session); // session refresh
					room.replaceSession(mem);
				}
			}
		
			if (state == LOBBY) { // 채팅방에서 메인페이지로 이동한 경우
				if (mem.getState() == CHATTING_ROOM) {
					logger.debug("member moved from chatting room to lobby");
					if (!removeRoomInfo(mem.getStore_ID()))
						logger.debug("there is no room to remove. OK! : lobby");
				}
			} else if (state == WAITING_ROOM) { // 채팅방에서 번개톡 버튼을 누른 경우
				if (mem.getState() == CHATTING_ROOM) {
					logger.debug("member moved from chatting room to waiting room");
					if (!removeRoomInfo(mem.getStore_ID()))
						logger.debug("there is no room to remove. OK! : waiting room");
				}
			} else if (state == CHATTING_ROOM) {
				if (mem.getState() == LOBBY) { // 메인에서 연락하기 버튼 누른경우
					logger.debug("member moved from lobby to chatting room");
					if (room.checkRoomInfo(store_ID)) {
						logger.debug("something wrong.");
					} else {
						logger.debug("create a room");
						room.createRoom(mem);
					}
				} else if (mem.getState() == CHATTING_ROOM) { // 채팅방에서 새로 고침 한 경우
					logger.debug("member moved from chatting room to chatting room");
				}
			}

			mem.setState(state);
			mapMember.put(store_ID, mem);
			logger.debug("websocket member added : " + mapMember.size());
		} catch (Exception e) {
			logger.debug(e.toString());
		}
	}

	@OnOpen
	public void OnOpen(@PathParam("STORE_ID") String store_ID, @PathParam("ITEM_ID") String item_ID,
			@PathParam("STATE") int state, Session session) {
		// 페이지 이동, 새로 고침에 의해 매번 session이 변경되기에 mapMember의 관리는 여기서 시작
		logger.debug("check parameters : a client connect");
		store_ID = Integer.toString(Integer.parseInt(store_ID));
		item_ID = Integer.toString(Integer.parseInt(item_ID));
		logger.debug("session id : " + session.getId() + " : Store_ID : " + store_ID + " : Item_ID : " + item_ID
				+ " : State : " + state);
		if(mapMember.get(store_ID)!=null) {
			WS_Store mem = mapMember.get(store_ID);
			// 로그인 후 다시 새로 고침 하는 경우 발생
			logger.debug("this member is already logged in. session changed");
			// session은 변경 되므로 바꿔준다
			mem.setSession(session);
			// 대화방에 있던 상태라면 채팅내역을 다시 불러오기 위한 명령어를 보낸다.
			if(mem.getState()==CHATTING_ROOM)
				sendHistoryRefresh(session, mem);
		}
		else addMember(store_ID, item_ID, session, state);
	}
	
	/**
	 * 메시지를 테이블 형식에 맞게 변경한다.
	 * @param : store_id를 알기위한 WS_Store, msg, 확인 여부
	 * @return : WS_TalkDTO
	 */
	private WS_TalkDTO setWS_DTO(WS_Store sender, String receiver_store_id, String msg, String isConfirmed) {
		WS_TalkDTO wsDTO = new WS_TalkDTO();
		
		wsDTO.setItem_ID(Integer.parseInt(sender.getItem_ID()));
		wsDTO.setSender_store_ID(Integer.parseInt(sender.getStore_ID()));
		wsDTO.setReceiver_store_ID(Integer.parseInt(receiver_store_id));
		wsDTO.setTalk_Info(msg);
		wsDTO.setConfirm_YN(isConfirmed);
		
		return wsDTO;
	}
	
	public void processMessage(ArrayList<String> parseList, Session session) {
		WS_TalkDAO dbControl = new WS_TalkDAO();
		String div = parseList.get(DIV);
		String sender_store_ID = parseList.get(SENDER_STORE_ID);// message sender
		String receiver_store_ID = parseList.get(RECEIVER_STORE_ID);// message receiver
		String item_ID = parseList.get(ITEM_ID); // item of message receiver
		String replyMessage = parseList.get(MSG);

		try {
			WS_Store sender = mapMember.get(sender_store_ID);
			WS_Store receiver = mapMember.get(receiver_store_ID);
			
			if (div.equals(CHATTING)) { // message in the chatting room
				logger.debug("send a message : session id : " + session.getId() + " : msg : " + replyMessage);

				if (receiver != null && receiver.getState() == CHATTING_ROOM) {
					logger.debug("receiver is in the chatting room. let's check if sender and receiver are in the same room");
					dbControl.talkSend(setWS_DTO(sender, receiver_store_ID, replyMessage,"Y")); // save message
					 
					if(room.getRoomName(sender_store_ID).equals(room.getRoomName(receiver_store_ID))) {
						room.broadcastMessage(session, sender_store_ID, replyMessage);// 같은 방 broadcasting message
						sendMsgConfirm(session, sender_store_ID, "Y"); // message confirmed, only receiver is in the room
					}
					else {
						sendUpdateWatingRoom(receiver.getSession(), receiver_store_ID, HWAGAE_TALK, replyMessage);
						sendMsgConfirm(session, sender_store_ID, "N"); // message unconfirmed, only receiver is in the room
					}
					 for(Entry<String, WS_Store> entry:mapMember.entrySet()) {
						 if(entry.getValue()!=null &&
							 !session.getId().equals(entry.getValue().getSession().getId())&&
							 entry.getValue().getState()!=CHATTING_ROOM)
						 sendAlarm(entry.getValue().getSession(), sender_store_ID, HWAGAE_TALK, replyMessage); 
					 }
				} else if (receiver != null) {
					logger.debug("member is in the : " + receiver.getState() + "  (0: lobby, 1: waiting room)");
					if (receiver.getState() == WAITING_ROOM) {
						logger.debug("send update waiting room command");
						dbControl.talkSend(setWS_DTO(sender, receiver_store_ID, replyMessage,"N")); // save message
						//sendMsgConfirm(session, sender_store_ID, "N"); // message unconfirmed
						sendUpdateWatingRoom(receiver.getSession(), receiver_store_ID, HWAGAE_TALK, replyMessage);
					} else {
						logger.debug("send alarm to a member in the lobby.");
						dbControl.talkSend(setWS_DTO(sender, receiver_store_ID, replyMessage,"N")); // save message
						//sendMsgConfirm(session, sender_store_ID, "N"); // message unconfirmed
						sendAlarm(receiver.getSession(), receiver_store_ID, HWAGAE_TALK, replyMessage); 
					}
				}else{ // receiver is logged out.
					dbControl.talkSend(setWS_DTO(sender, receiver_store_ID, replyMessage,"N")); // save message
					//sendMsgConfirm(session, sender_store_ID, "N"); // message unconfirmed
				}
			}
		} catch (IOException e) {
			logger.debug(e.toString());
		}
	}

	/**
	 * 상점이 대기실에 있는 상태라면 대기실 업데이트를 위해 별도 명령어를 전달
	 * @param :session ,상점 ID,  command 구분값, 업데이트 메시지
	 * @return : void
	 */
	private void sendUpdateWatingRoom(Session session, String store_ID, String item_ID, String replyMessage) {
		String jsontext = "";
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "M");
		jsonObject.addProperty("div", WAITING);
		jsonObject.addProperty("store_id", store_ID);
		jsonObject.addProperty("item_id", item_ID);
		jsonObject.addProperty("msg", replyMessage);

		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);

		try {
			session.getBasicRemote().sendText(jsontext);
			logger.debug("send a message to waiting room : " + jsontext.toString());
		} catch (IOException e) {
			logger.debug("send a message to waiting room : " + e.toString());
		}
	}

	public void processInformation(ArrayList<String> parseList, Session session) {
		WS_TalkDAO dbControl = new WS_TalkDAO();
		String sender_store_id = parseList.get(SENDER_STORE_ID); // sender in history of chatting
		String receiver_store_id = parseList.get(RECEIVER_STORE_ID); // receiver in history of chatting
		String item_id = parseList.get(ITEM_ID); // 메시지를 받는 사람(item_id를 통해 store_id(판매자)를 가져 올 수 있다)
		String div = parseList.get(DIV);
		String logged_store_ID = getStore_ID(session);
		WS_Store sender = null;
		WS_Store receiver = null;
		logger.debug("TOTAL_INFO : send_store_id = " + sender_store_id + "receive_store_id =" + receiver_store_id+ " item_id = " + item_id);
		if (div.equals(TOTAL_INFO)) {
			logger.debug("TOTAL_INFO : send_store_id = " + sender_store_id + " item_id = " + item_id);
		} else if (div.equals(LOGOUT)) {
			logger.debug("LOGOUT");
		} else if (div.equals(UPLOAD)) {
			logger.debug("UPLOAD");
		} else if (div.equals(DOWNLOAD)) {
			logger.debug("DOWNLOAD");
		} else if (div.equals(ENTER_ROOM)) { // 대기실에서 해당 채팅 내역 더블클릭해서 방으로 입장한 상황
			logger.debug("enter room logged store id : " + logged_store_ID);
			if(logged_store_ID.equals(sender_store_id)) {
				logger.debug("enter room logged store id same sender store id");
				sender = mapMember.get(sender_store_id);
				receiver = mapMember.get(receiver_store_id);
			}else if(logged_store_ID.equals(receiver_store_id)){
				logger.debug("enter room logged store id same receiver store id");
				sender = mapMember.get(receiver_store_id);
				receiver = mapMember.get(sender_store_id);
			}

			// 아이템 코드와 채팅 내역 테이블에서 receiver가 '나'(로그인한 상점)이고
			// sender가 동일하고 confirmed_yn이 "N" 모든 내역에 대해서 "Y"로 업데이트한다
			dbControl.updateMsgConfirm(sender_store_id, receiver_store_id, item_id); // save confirm message
			
			// 채팅 내역 히스토리, 더블 클릭으로 방 이동
			if(sender.getState()==CHATTING_ROOM) room.leaveRoom(sender_store_id);
			sender.setState(CHATTING_ROOM);
			if (receiver != null) { // 상대방이 접속 중인지 확인, 접속했다면(로그인 해서 websocket을 할당 받은 상태라면)
				// 해당 멤버가 방을 만들었고, 아이템 코드가 같다면 같은 방으로 입장한다.
				if (room.getRoomName(receiver, item_id) != null) {
					logger.debug(
							"enter the exist room, store id :  " + receiver.getStore_ID() + " , item_id : " + item_id);
					room.enterRoom(receiver.getStore_ID(), sender);
				} else {
					logger.debug(
							"there is no room, so make a room :  " + sender.getStore_ID() + " , item_id : " + item_id);
					room.createRoom(sender);
				}
			} else {
				logger.debug(
						"opponent is not logged in :  make a room, and just leave a message");
				room.createRoom(sender);
			}
		}
	}

	@OnMessage
	public void OnMessage(Session session, String message) {
		logger.debug("from client : " + message + " size : " + mapMember.size());
		ArrayList<String> parseList = parseMessage(message);
		String command = parseList.get(TYPE);

		if (command.equals("M")) { // if message
			processMessage(parseList, session);
		} else if (command.equals("I")) { // if information, 주로 client에서 server로 전달하는 정보
			/*
			 * for(String test:parseList) { System.out.println("parsing data : " + test); }
			 */
			processInformation(parseList, session);
		}
	}

	@OnClose
	public void OnClose(Session session, CloseReason closeReason) {
		logger.debug("client is now disconnected. " + closeReason.getCloseCode());
		for (String store_ID : mapMember.keySet()) {
			String mapMember_store_ID = getStore_ID(session);
			if (mapMember_store_ID != null && mapMember_store_ID.equals(store_ID)) {
				closeSession(store_ID);
				logger.debug("member: " + store_ID + " session close.");
			}
		}
	}

	@OnError
	public void OnError(Throwable t) {
		t.printStackTrace();
	}

	/**
	 * onMessage의 session 정보로는 상점 ID를 얻을 수 없어 session 정보를 갖고 있는 mapMember에서 상점 ID를
	 * 찾는다
	 * @param : session : message를 보낸 session
	 * @return : String 상점 ID
	 */
	private String getStore_ID(Session session) {
		String store_ID = null;

		for (Entry<String, WS_Store> entry : mapMember.entrySet()) {
			if (entry.getValue().getSession() != null&& session.getId().equals(entry.getValue().getSession().getId())) {
				store_ID = entry.getKey();
				break;
			}
		}

		return store_ID;
	}

}