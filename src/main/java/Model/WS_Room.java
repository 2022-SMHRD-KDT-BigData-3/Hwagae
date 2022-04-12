package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import WebSocket.WS_ServerSetting;

public class WS_Room implements WS_ServerSetting{
	public static final Logger logger = LogManager.getLogger("myTest");
	// 1. rooms에서 내가 속해 있는 방 이름을 가져온다.
	// 2. roomMember에서 위 방 이름을 바탕으로 방에 속한 인원들을 가져온다.
	private static HashMap<String , ArrayList<WS_Store>> roomMember = new HashMap<String, ArrayList<WS_Store>>();
	private static HashMap<String, String> rooms = new HashMap<String, String>(); // <client, client's room title>
	
	public WS_Room() {
		 
	}
	
	// 현재 방에 있는 ws_store의 store_id를 가져온다. 현재구조에서는 최대 1명만 존재
	public ArrayList<String> getRoomMember(String store_id) {
		ArrayList<String>members = new ArrayList<String>();
		ArrayList<WS_Store> stores = roomMember.get(store_id);
		
		if(stores!=null)
			for(WS_Store store:stores) 
				members.add(store.getStore_ID());

		return members;
	}
	
	// 이미 생성된 방에 입장, store_ID = 방장 상점, mem = 방에 추가되어야 할 member
	public int enterRoom(String sender_store_ID, WS_Store mem) {
		logger.debug("enter the room :  number of room members = " + roomMember.size());
		int ret = -1;
		String roomName = rooms.get(sender_store_ID);
		//rooms.put(mem.getStore_ID(), roomName);
		
		if(roomName==null) {
			logger.debug("there is no room. check opponent");
			roomName = rooms.get(mem.getStore_ID());	
		}
		rooms.put(mem.getStore_ID(), roomName);
		
		ArrayList<WS_Store> member = roomMember.get(roomName);
		logger.debug("enter the room :  number of members in the room = " + member.size());
		member.add(mem);
		logger.debug("enter the room :  number of members in the room after adding = " + member.size());
		/*
		 * for(Member element:member) {
		 * if(element.getStore_ID().equals(sender_store_ID)) {
		 * logger.debug("enter room. but the room exists. replace member after removing"
		 * ); element.setSession(mem.getSession()); ret = 1; break; } else {
		 * member.add(mem); } }
		 */
		
		logger.debug("enter the room : member add");
		
		return ret;
	}
	
	// store id로 방 찾기
	public String getRoomName(String store_ID) {
		String roomName = "";
		if(rooms.get(store_ID)!=null) roomName = rooms.get(store_ID); 
		
		return roomName;
	}
	
	// 해당 멤버가 방에 있는가?
	public String getRoomName(WS_Store mem, String item_ID) {
		String roomName = null;
		System.out.println("getRoomName : mem.getItem_ID : " + mem.getItem_ID());
		if(mem.getItem_ID().equals(item_ID)) {
			System.out.println("item id check : mem get id and compare with : " + item_ID);
			roomName = rooms.get(mem.getStore_ID());
		}
			
		System.out.println("getRoomName : " + roomName);
		return roomName;
	}
	
	// 대기실에 있을때 업데이트
	public void updateWatingRoom(WS_Store member, String message) {
		logger.debug("waiting room update");
	}
	
	// 번개톡 버튼 클릭시
	// 알람 및 다른 구매자가 메시지를 보냈을때 채팅 게시판 업데이트 할수 있도록 member state 변경 
	public void joinWaitingRoom(WS_Store member) {
		logger.debug("join the waiting room");
	}
	
	// add a client to the room
	// 번개톡 기준 연락하기를 누를때 대화창 열림
	// 방 생성은 항상 먼저 연락하는 사람(연락하기 버튼을 누른 클라이언트)를 기준으로 한다.
	public void createRoom(WS_Store mem) {
		String roomName = "";
		
		if(rooms.get(mem.getStore_ID())==null) {
			if(roomMember.putIfAbsent(mem.getStore_ID(), new ArrayList<WS_Store>()) == null) {
				roomName = mem.getStore_ID();
				logger.debug("store_id : " + mem.getStore_ID() + " room name : " + roomName +" : " + roomMember.size());
				
				roomMember.get(roomName).add(mem);
				rooms.put(mem.getStore_ID(), roomName);
			}
		}else { // 방 정보가 남아 있는 경우 기존에 있던 방으로 들어간다, ex) 새로고침
			logger.debug(mem.getStore_ID() + ": this room exists in the rooms map. replace the session");
			replaceSession(mem);
		}
	}
	
	public void replaceSession(WS_Store replaceMem) {
		ArrayList<WS_Store> memberInRoom = roomMember.get(replaceMem.getStore_ID()); 
		try {
			if(memberInRoom!=null&&!memberInRoom.isEmpty()) {
				for(WS_Store mem:memberInRoom) {
					logger.debug("find the room where i was in" );
					if(mem.getStore_ID().equals(replaceMem.getStore_ID())) {
						logger.debug("find out the room and replace session" );
						//mem.getSession().close(); // room class에서는 세션 관리 하지 않는다.
						//mem.setFileSession(replaceMem.getFileSession());
						mem.setItem_ID(replaceMem.getItem_ID());
						//mem.setSession(replaceMem.getSession());
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// 방에서 나갈때 처리
	public void leaveRoom(String store_ID) {
		String roomName = rooms.get(store_ID);
		String leaveMember = null;
		logger.debug("leaveRoom roomName(store_id): " + roomName);
		
		if(roomName!=null) {
			ArrayList<WS_Store> members = roomMember.get(roomName);
			for(WS_Store mem:members) {
				if(mem.getStore_ID().equals(store_ID)) {
					logger.debug("a member in the room has been removed : " + store_ID);
					
					if(members.size()==0) {
						rooms.remove(store_ID);
						rooms.remove(roomName);
						roomMember.get(roomName).clear();
						roomMember.remove(roomName);
						break;
					}else {
						logger.debug("save leave member : " + mem.getStore_ID());
						leaveMember = mem.getStore_ID();
					}
					members.remove(mem);
					break;
				}
			}
			try {
				for(WS_Store remainedMem:members) {
					if(remainedMem.getSession()!=null)
						remainedMem.getSession().getBasicRemote().
						sendText(convertJsonLeaveMsg(leaveMember, remainedMem.getStore_Name(), remainedMem.getItem_ID(),"OUT"));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			rooms.remove(store_ID);
		}
	}
	
	// 단순히 방이 있었는지 확인, 페이지 새로고침 등은 방 정보를 갱신하지 않는다.
	public boolean checkRoomInfo(String store_ID) {
		boolean ret = false;
		if(rooms.get(store_ID)!=null) ret = true;
		
		return ret;
	}
	
	private String convertJsonLeaveMsg(String store_ID, String store_Name, String item_ID, String message) {
		String jsontext = "";
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "I");
		jsonObject.addProperty("div", LEAVE_ROOM);
		jsonObject.addProperty("sender_store_id", store_ID);
		jsonObject.addProperty("store_name", store_Name);
		
		if(item_ID!=null)
			jsonObject.addProperty("item_id", item_ID);
		else
			jsonObject.addProperty("item_id", "00");
		jsonObject.addProperty("msg",message);
		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);
		
		logger.debug("jsontext : " + jsontext); 

		return jsontext;
	}
	
	private String convertJson(String store_ID, String store_Name, String item_ID, String message) {
		String jsontext = "";
		
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("type", "M");
		jsonObject.addProperty("div", CHATTING);
		jsonObject.addProperty("sender_store_id", store_ID);
		jsonObject.addProperty("store_name", store_Name);
		jsonObject.addProperty("item_id", item_ID);
		jsonObject.addProperty("msg",message);
	
		// JsonObject를 Json 문자열로 변환
		jsontext = gson.toJson(jsonObject);
		
		logger.debug("jsontext : " + jsontext); 

		return jsontext;
	}
	
	public void broadcastMessage(Session session, String store_ID, String message) throws IOException {
		logger.debug("store_id " + store_ID + " the number of rooms : " + roomMember.size());
		String roomName = rooms.get(store_ID);
		ArrayList<WS_Store> member = roomMember.get(roomName);
		
		if(member!=null&&!member.isEmpty()) {
			logger.debug("store_id " + store_ID + " the number of members in the room : " + member.size());
			for(WS_Store mem:member) {
				if(mem.getSession()!=null&&!mem.getSession().getId().equals(session.getId())) {
					logger.debug("broadcast a message: " + message + " to " + mem.getStore_ID());
					message = convertJson( mem.getStore_ID(), mem.getStore_Name(),  mem.getItem_ID(), message); // mem.getStore_ID = 메시지를 받는 사람
					mem.getSession().getBasicRemote().sendText(message);
				}
			}
		} 
	}
	
}
