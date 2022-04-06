package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.BusinessException;

public class ItemDAO {

	// 전역변수 선언
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	
	// DB연결메소드
	public void db_conn() {
		try {
			// 1. ojdbc6.jar -> import
			// maven에서는 pom.xml에서 라이브러리 넣어서 다운로드
			
			// 2. Class찾기 -> Java와 DB를 이어주는 통로
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 3. DB에 접속하기 위한 카드키만들기 (url, id, pw)
			String db_url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
			String db_id = "campus_k_0325_5";
			String db_pw = "smhrd5";
			
			// 4. DB연결-> 연결 성공 시 Connection 객체로 반환
			conn = DriverManager.getConnection(db_url, db_id, db_pw);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// DB종료메소드
	public void db_close() {
			try {
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public String RetrieveLastTradeArea(String storeId) throws BusinessException{
		
		String lastTradeArea = "";
		
		StringBuilder sql = new StringBuilder("SELECT TRADE_AREA AS tradeArea \n");
		sql.append(",MAX(REGISTRATION_DATE) AS registrationDate \n");
		sql.append("FROM ITEM \n");
		sql.append("WHERE STORE_ID = ? \n");
		sql.append("GROUP BY TRADE_AREA");
		
		System.out.println(sql.toString());
		
		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, storeId);
		
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(rs.next()) {
				lastTradeArea = rs.getString(1);
			}else {
				throw new BusinessException("최근 지역 정보가 없습니다. 신규로 거래지역을 입력해 주세요.");
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
	
		return lastTradeArea;	
	}
	
	private int getItemId() {
		
		int itemId = 0;
		StringBuilder sql = new StringBuilder("SELECT ITEM_SEQ.NEXTVAL FROM DUAL");
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(rs.next()) {
				itemId = rs.getInt(1);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return itemId;
	}
	

	public int insertItem(ItemDTO dto) {
		
		int itemId = getItemId();
		
		StringBuilder sql = new StringBuilder("INSERT INTO ITEM ( \n");
		sql.append("ITEM_ID \n");
		sql.append(",STORE_ID \n");
		sql.append(",ITEM_TITLE \n");
		sql.append(",ITEM_INFO \n");
		sql.append(",ITEM_CATEGORY \n");
		sql.append(",ITEM_STATUS \n");
		sql.append(",EXCHANGE_YN \n");
		sql.append(",PRICE \n");
		sql.append(",DELIVERY_PRICE \n");
		sql.append(",INCLUDE_DELIVERY_PRICE_YN \n");
		sql.append(",TRADE_AREA \n");
		sql.append(",RELATION_TAG \n");
		sql.append(",STOCK \n");
		sql.append(",SAFETY_TRADE_YN \n");
		sql.append(",IMG_PATH \n");
		sql.append(",NUM_LIKE \n");
		sql.append(",REGISTRATION_DATE \n");
		sql.append(") VALUES( \n");
		sql.append("? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",SYSDATE )");
		
		System.out.println(sql.toString());
		
		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			
			psmt.setInt(1, itemId);
			psmt.setInt(2, dto.getStoreId());
			psmt.setString(3, dto.getItemTitle());
			psmt.setString(4, dto.getItemInfo());
			psmt.setString(5, dto.getItemCategory());
			psmt.setString(6, dto.getItemStatus());
			psmt.setString(7, dto.getExchangeYn());
			psmt.setInt(8, dto.getPrice());
			psmt.setInt(9, 0); //택배비는 필요없는 듯하다.
			psmt.setString(10, dto.getIncludeDeliveryPriceYn());
			psmt.setString(11, dto.getTradeArea());
			psmt.setString(12, dto.getRelationTag());
			psmt.setInt(13, dto.getStock());
			psmt.setString(14, dto.getSafetyTradeYn());
			psmt.setString(15, dto.getImgPath());
			psmt.setInt(16, 0);
			
			// 실행
			int cnt = psmt.executeUpdate();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(cnt > 0) {
				System.out.println("itemInsert Success");
			}else {
				System.out.println("itemInsert fail");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		insertTrade(itemId);
		return itemId;
	}
	
	private void insertTrade(int itemId) {
		
		StringBuilder sql = new StringBuilder("INSERT INTO TRADE( \n"); 
		sql.append("TRADE_ID \n");
		sql.append(",ITEM_ID \n");
		sql.append(",STORE_ID \n");
		sql.append(",TRADE_STATUS \n");
		sql.append(",QUANTITY \n");
		sql.append(",TOTAL_PRICE \n");
		sql.append(",CONFIRM_DATE \n");
		sql.append(") VALUES( \n");
		sql.append("TRADE_SEQ.NEXTVAL\n");
		sql.append(",? \n");
		sql.append(",NULL \n");
		sql.append(",'S' \n");
		sql.append(",0 \n");
		sql.append(",0 \n");
		sql.append(",NULL)");
		
		try {
			db_conn();
			
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, itemId);
			
			// 실행
			int cnt = psmt.executeUpdate();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(cnt > 0) {
				System.out.println("insertTrade Success");
			}else {
				System.out.println("insertTrade fail");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
	}
	
	
}
