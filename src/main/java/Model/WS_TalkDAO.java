package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class WS_TalkDAO {
	// 전역변수 선언
			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			
			int cnt = 0;
			WS_TalkDTO info = null;
			
			// DB연결메소드
			public void db_localConn() {
				try {				
					// 1. Class찾기 -> Java와 DB를 이어주는 통로
					Class.forName("oracle.jdbc.driver.OracleDriver");					
					// 2. DB에 접속하기 위한 카드키만들기 (url, id, pw)
					String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
					String db_id = "hr";
					String db_pw = "hr";					
					// 3. DB연결-> 연결 성공 시 Connection 객체로 반환
					conn = DriverManager.getConnection(db_url, db_id, db_pw);
					
					if(conn != null) {
						System.out.println("DB연결성공");
					}else {
						System.out.println("DB연결실패");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
			public void db_conn() {
				try {				
					// 1. Class찾기 -> Java와 DB를 이어주는 통로
					Class.forName("oracle.jdbc.driver.OracleDriver");					
					// 2. DB에 접속하기 위한 카드키만들기 (url, id, pw)
					String db_url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
					String db_id = "campus_k_0325_5";
					String db_pw = "smhrd5";					
					// 3. DB연결-> 연결 성공 시 Connection 객체로 반환
					conn = DriverManager.getConnection(db_url, db_id, db_pw);
					
					if(conn != null) {
						System.out.println("DB연결성공");
					}else {
						System.out.println("DB연결실패");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
			// DB종료메소드
			public void db_close() {
				try {
					if(psmt != null) psmt.close();
					if(conn != null) conn.close();
					if (rs != null)  rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			// get store_id by item_id
			public int getStoreByid(int item_id) {
				String sql = "select store_id from item where item_id=?";		
				int store_id = 0;
				db_conn();				
				
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, item_id);
					rs = psmt.executeQuery();		
					
					if(rs.next()) store_id = rs.getInt(1);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					db_close();
				}
				
				return store_id;
			}
			
			//메시지 DB에 입력
			public int talkSend(WS_TalkDTO dto) {			
				// sql문 작성
				String sql = "insert into HWAGAE_TALK values(talk_num_seq.nextval, ?, ?, ?, ?, ?, sysdate)";		
				// 1. connection 생성
				db_conn();				
				try {
					// 2. psmt 객체 생성
					psmt = conn.prepareStatement(sql);			
					// 3. 바인드변수(?) 채우기		
					psmt.setInt(1, dto.getItem_ID());
					psmt.setInt(2, dto.getSender_store_ID());
					psmt.setInt(3, dto.getReceiver_store_ID());
					psmt.setString(4, dto.getTalk_Info());	
					psmt.setString(5, dto.getConfirm_YN());
					// 4. 실행
					cnt = psmt.executeUpdate();					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					db_close();
				}
				return cnt;
			}
			
			public String getStoreName(String store_id) {
				String storeName = null;
				String sql = "select STORE_NAME from store where store_id=?";
				db_conn();
				
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, Integer.parseInt(store_id));
					
					rs = psmt.executeQuery();					
					// 결과를 꺼내서 ArrayList 로 만들기
					if(rs.next()) {
						storeName = rs.getString(1);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					db_close();
				}
				
				return storeName;
			}
			
			public ArrayList<WS_TalkDTO> talkList(String sender, String item, String receiver) {
				// 로그인한 사용자에게 온 메세지만 가져오기
				System.out.println("[talkList 접속완료");
				// sql 문장은 talkDB.sql에도 있습니다
				String sql = "select *from hwagae_talk where item_id=? and ((sender_store_id=? and receiver_store_id=?) or (sender_store_id=? and receiver_store_id=?)) order by talk_date asc";
				// 데이터를 담을 ArrayList
				ArrayList<WS_TalkDTO> mlist = new ArrayList<WS_TalkDTO>();
				db_conn();				
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, Integer.parseInt(item));
					psmt.setInt(2,  Integer.parseInt(sender));
					psmt.setInt(3,  Integer.parseInt(receiver));
					psmt.setInt(4,  Integer.parseInt(receiver));
					psmt.setInt(5,  Integer.parseInt(sender));
					// 실행
					rs = psmt.executeQuery();					
					// 결과를 꺼내서 ArrayList 로 만들기
					while(rs.next()) {						
						int talk_Seq = rs.getInt(1);
						int item_ID = rs.getInt(2);
						int sender_store_ID = rs.getInt(3);
						int receiver_store_ID = rs.getInt(4);
						String talk_Info = rs.getString(5);
						String confirm_YN = rs.getString(6);
						String talk_Date= rs.getString(7);					
						
						WS_TalkDTO dto = new WS_TalkDTO(talk_Seq,item_ID,sender_store_ID, receiver_store_ID, talk_Info,confirm_YN, talk_Date);						
						mlist.add(dto);
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					db_close();
				}
				return mlist;
			}			
			
			// 채팅목록 구현을 위한 아이디 채팅내용 시간 가져오기
			public ArrayList<WS_TalkDTO> talkList() {
				// 로그인한 사용자에게 온 메세지만 가져오기
				System.out.println("[talkList 접속완료");
				// sql 문장은 talkDB.sql에도 있습니다
				//String sql = "SELECT * FROM (SELECT talk_Seq,item_ID, sender_store_ID,receiver_store_ID, TALK_INFO, confirm_YN, TALK_DATE,RANK() OVER (PARTITION BY item_ID ORDER BY TO_CHAR(TALK_DATE, 'YYYY-MM-DD HH24:MI:SS') DESC) AS RNK FROM HWAGAE_TALK)";				
				String sql = "select *from hwagae_talk where item_id=? and ((sender_store_id=? and receiver_store_id=?) or (sender_store_id=? and receiver_store_id=?))";
				// 데이터를 담을 ArrayList
				ArrayList<WS_TalkDTO> mlist = new ArrayList<WS_TalkDTO>();
				db_conn();				
				try {
					psmt = conn.prepareStatement(sql);
//					psmt.setString(1,info.getEmail());					
					// 실행
					rs = psmt.executeQuery();					
					// 결과를 꺼내서 ArrayList 로 만들기
					while(rs.next()) {						
						int talk_Seq = rs.getInt(1);
						int item_ID = rs.getInt(2);
						int sender_store_ID = rs.getInt(3);
						int receiver_store_ID = rs.getInt(4);
						String talk_Info = rs.getString(5);
						String confirm_YN = rs.getString(6);
						String talk_Date= rs.getString(7);					
						WS_TalkDTO dto = new WS_TalkDTO(talk_Seq,item_ID,sender_store_ID, receiver_store_ID, talk_Info,confirm_YN, talk_Date);						
						mlist.add(dto);
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					db_close();
				}
				return mlist;
			}
			
			// update message confirmation.
			public void updateMsgConfirm(String sender_store_id, String receiver_store_id, String item_id) {
				// 채팅 내역으로 저장된 항목 중 상대방에 보낸 메시지의 확인 항목을 업데이트 해야함
				// ws_js.js에서 enterRoom시 보내는 sender_store_id는 로그인 한 store이므로 
				// update문에서는 receiver_store_id=로그인 한 store가 되어야함.
				String sql = "update hwagae_talk set confirm_yn='Y' where sender_store_id=? and receiver_store_id=? and item_id=? and confirm_yn='N'";		
				db_conn();				
				try {
					psmt = conn.prepareStatement(sql);					
					psmt.setInt(1, Integer.parseInt(receiver_store_id));  // enterRoom에서 설정하는 값에 따라 달라진다.
					psmt.setInt(2, Integer.parseInt(sender_store_id));
					psmt.setInt(3, Integer.parseInt(item_id));

					cnt = psmt.executeUpdate();					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					db_close();
				}
			}
			
			private boolean checkID(HashMap<String, String> list, String item_id, String sender_store_id,
			      String receiver_store_id, String[] temp) {
			      boolean ret = false;

			      if (list.get(item_id + "!" + sender_store_id) != null) {
			         temp[0] = item_id + "!" + sender_store_id;
			         //System.out.println("check id .key : " + item_id + "!" + sender_store_id + " value : "
			         //      + list.get(item_id + "!" + sender_store_id));
			         String[] send_receive = list.get(item_id + "!" + sender_store_id).split(":");
			         if (send_receive == null)
			            ret = false;
			         else if ((sender_store_id.equals(send_receive[0]) || sender_store_id.equals(send_receive[1]))
			               && (receiver_store_id.equals(send_receive[0]) || receiver_store_id.equals(send_receive[1])))
			            ret = true;
			      } else if (list.get(item_id + "!" + receiver_store_id) != null) {
			         temp[0] = item_id + "!" + receiver_store_id;
			         String[] send_receive = list.get(item_id + "!" + receiver_store_id).split(":");
			         if (send_receive == null)
			            ret = false;
			         else if ((sender_store_id.equals(send_receive[0]) || sender_store_id.equals(send_receive[1]))
			               && (receiver_store_id.equals(send_receive[0]) || receiver_store_id.equals(send_receive[1])))
			            ret = true;
			      }

			      return ret;
			   }

			   private void makeID(HashMap<String, String> list, HashMap<String, WS_TalkDTO> data, WS_TalkDTO mem) {
			      String key = mem.getItem_ID() + "!" + mem.getSender_store_ID();
			      String value = mem.getSender_store_ID() + ":" + mem.getReceiver_store_ID() + ":" + mem.getTalk_Info();
			      //System.out.println("make chatting history .key : " + key + " value : " + value);
			      list.put(key, value);
			      data.put(key, mem);
			   }

			   public HashMap<String, WS_TalkDTO> getChatList(String logged_store_id) {
			      HashMap<String, String> itemList = new HashMap<String, String>();
			      HashMap<String, WS_TalkDTO> dataList = new HashMap<String, WS_TalkDTO>();

			      //System.out.println("logged store : " + logged_store_id);
			      String temp = null;
			      String[] temp2 = new String[2];
			      String sql = "SELECT * FROM  (SELECT talk_seq, item_id, sender_store_id, receiver_store_id, TALK_INFO, confirm_yn, TALK_DATE,"
			            + "RANK() OVER (PARTITION BY item_id ORDER BY TO_CHAR(TALK_DATE, 'YYYY-MM-DD HH24:MI:SS') asc) AS RNK FROM hwagae_talk)"
			            + "WHERE sender_store_id = ? or receiver_store_id=?";
			      db_conn();
			      try {
			         psmt = conn.prepareStatement(sql);
			         psmt.setString(1, logged_store_id);
			         psmt.setString(2, logged_store_id);

			         rs = psmt.executeQuery();

			         while (rs.next()) {
			            WS_TalkDTO chatLastHistoryByItem = new WS_TalkDTO();
			            chatLastHistoryByItem.setTalk_Seq(rs.getInt(1));// talk_seq
			            chatLastHistoryByItem.setItem_ID(rs.getInt(2)); // item_id
			            chatLastHistoryByItem.setSender_store_ID(rs.getInt(3)); // sender_store_id
			            chatLastHistoryByItem.setReceiver_store_ID(rs.getInt(4)); // receiver_store_id
			            chatLastHistoryByItem.setTalk_Info(rs.getString(5)); // talk_info
			            chatLastHistoryByItem.setConfirm_YN(rs.getString(6)); // isconfirmed
			            chatLastHistoryByItem.setTalk_Date(rs.getString(7)); // date

			            if (temp != null) {
			               //System.out.println("item_id : " + chatLastHistoryByItem.getItem_ID() + ", temp: " + temp);

			               if (checkID(itemList, Integer.toString(chatLastHistoryByItem.getItem_ID()),
			                      Integer.toString(chatLastHistoryByItem.getSender_store_ID()),  Integer.toString(chatLastHistoryByItem.getReceiver_store_ID()),
			                     temp2)) { // 상품코드가 있다면
			                  //System.out.println("check send,receiver id : temp2 : " + temp2[0]);
			                  itemList.put(temp2[0], chatLastHistoryByItem.getSender_store_ID() + ":"
			                        + chatLastHistoryByItem.getReceiver_store_ID());
			                  dataList.put(temp2[0], chatLastHistoryByItem);
			               } else 
			                  makeID(itemList, dataList, chatLastHistoryByItem);
			            } else { // 비교를 위한 초기값 설정
			               temp = logged_store_id;
			               makeID(itemList, dataList, chatLastHistoryByItem);
			            }
			         }
			      } catch (Exception e) {
			         e.printStackTrace();
			      } finally {
			         db_close();
			      }
			      
			      for(Entry<String, WS_TalkDTO> data:dataList.entrySet())
			    	  System.out.println("key : " + data.getKey() + ", item_id : " + data.getValue().getItem_ID());
			      return dataList;
			   }
}
