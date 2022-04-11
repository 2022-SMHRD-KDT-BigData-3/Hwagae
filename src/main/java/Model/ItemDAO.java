package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import util.BusinessException;
import util.UploadUtil;

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
	
	public List<QuestionDTO> retrieveItemQuestion(HttpServletRequest request, int itemId){
		
		List<QuestionDTO> resultList = new ArrayList<QuestionDTO>();
		
		StringBuilder sql = new StringBuilder("SELECT B.STORE_NAME \n");
		sql.append(",B.STORE_ID \n");
		sql.append(",B.PROFILE_IMG \n");
		sql.append(",A.QUESTION \n");
		sql.append(",ROUND((SYSDATE - A.WRITE_DATE) * 24 * 60) AS WRITE_DATE \n");
		sql.append("FROM ITEM_QUESTION A \n");
		sql.append(",STORE B \n");
		sql.append("WHERE A.STORE_ID = B.STORE_ID \n");
		sql.append("AND A.ITEM_ID = ? \n");
		sql.append("ORDER BY A.WRITE_DATE DESC");
		
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, itemId);
			
			// 실행
			rs = psmt.executeQuery();
			
			UploadUtil uploadUtil = new UploadUtil(request);
			String profileImg;
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				profileImg = uploadUtil.getImgFile(rs.getString(3), String.valueOf(rs.getInt(2)), "profile");
				resultList.add(new QuestionDTO(rs.getString(1),  rs.getInt(2), profileImg, rs.getString(4), changeTimeFormat(rs.getInt(5))));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return resultList;
	}
	
	public List<QuestionDTO> insertItemQuestion(HttpServletRequest request, int itemId, int storeId, String itemInfo) {
		
		List<QuestionDTO> resultList = new ArrayList<QuestionDTO>();
		
		StringBuilder sql = new StringBuilder("INSERT INTO ITEM_QUESTION ( \n");
		sql.append("QUESTION_SEQ \n");
		sql.append(",ITEM_ID \n");
		sql.append(",STORE_ID \n");
		sql.append(",QUESTION \n");
		sql.append(",WRITE_DATE \n");
		sql.append(") VALUES ( \n");
		sql.append("ITEM_QUESTION_SEQ.NEXTVAL \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",SYSDATE)");
		
		System.out.println(sql.toString());
		
		try {
			
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1,itemId);
			psmt.setInt(2,storeId);
			psmt.setString(3,itemInfo);
			
			// 실행
			int cnt = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		resultList = retrieveItemQuestion(request, itemId);
		
		return resultList;
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

	public ItemDTO retrieveItem(int pnItemId) {
		ItemDTO dto = null;
		
		StringBuilder sql = new StringBuilder("SELECT A.ITEM_ID AS itemId \n");
		sql.append(",A.STORE_ID AS storeId \n");
		sql.append(",A.ITEM_TITLE AS itemTitle \n");
		sql.append(",A.ITEM_INFO AS itemInfo \n");
		sql.append(",B.SECTION_INFO AS itemCategory \n");
		sql.append(",A.ITEM_STATUS AS itemStatus \n");
		sql.append(",A.EXCHANGE_YN AS exchangeYn \n");
		sql.append(",A.PRICE AS price \n");
		sql.append(",A.INCLUDE_DELIVERY_PRICE_YN AS includeDeliveryPriceYn \n");
		sql.append(",A.TRADE_AREA AS tradeArea \n");
		sql.append(",A.RELATION_TAG AS relationTag \n");
		sql.append(",A.STOCK AS stock \n");
		sql.append(",A.SAFETY_TRADE_YN AS SafetyTradeYn \n");
		sql.append(",A.NUM_LIKE AS numLike \n");
		sql.append(",ROUND((SYSDATE - A.REGISTRATION_DATE) * 24 * 60) AS registrationDate \n");
		sql.append(",C.TRADE_STATUS AS tradeStatus \n");
		sql.append("FROM ITEM A \n");
		sql.append("    ,CATEGORY B \n");
		sql.append("    ,TRADE C \n");
		sql.append("WHERE A.ITEM_CATEGORY = B.CAT_SEQ \n");
		sql.append("AND A.ITEM_ID = C.ITEM_ID \n");
		sql.append("AND A.ITEM_ID = ?");

		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, pnItemId);
			
			// 실행
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				int itemId = rs.getInt(1);
				int storeId = rs.getInt(2);
				String itemTitle = rs.getString(3);
				String itemInfo = rs.getString(4);
				String itemCategory = rs.getString(5);
				String itemStatus = rs.getString(6);
				String exchangeYn = rs.getString(7);
				int price = rs.getInt(8);
				String includeDeliveryPriceYn = rs.getString(9);
				String tradeArea = rs.getString(10);
				String relationTag = rs.getString(11);
				int stock = rs.getInt(12);
				String SafetyTradeYn = rs.getString(13);
				int numLike = rs.getInt(14);
				String registrationDate = changeTimeFormat(rs.getInt(15));
				String tradeStatus = rs.getString(16);
			
				dto = new ItemDTO(itemId, storeId, itemTitle, itemInfo, itemCategory, itemStatus, exchangeYn, price, includeDeliveryPriceYn, relationTag, tradeArea, stock, SafetyTradeYn, numLike, registrationDate, tradeStatus);
				
			};
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return dto;
	}
	
	private String changeTimeFormat(int registrationDate) {
		
		int unit = 0;
		
		//1시간 : 60분
		//하루 : 1440분
		//한 달 : 43200분
		//일 년  : 518400분 
		
		if(registrationDate < 60) {    //1시간 이하값은 분 단위 표시
			
			unit = registrationDate;
			return unit+"분 전";
				
		}else if(registrationDate >= 60 && registrationDate < 1440) { //하루 이하는 시간 단위로 표시
			
			unit = registrationDate/60;
			return unit+"시간 전";
			
		}else if(registrationDate >= 1440 && registrationDate < 43200) { // 한 달 이하는 일 단위로 표시
			
			unit = registrationDate/60/24;
			return unit+"일 전";
			
		}else if(registrationDate >= 43200 && registrationDate < 518400) { // 1년 이전은 달 단위로 표시
			
			unit = registrationDate/60/24/30;
			return unit+"달 전";
			
		}else {
			
			unit = registrationDate/60/24/30/12;
			return unit+"년 전";
			
		}

	}

	public void insertItemLike(int itemId, int buyerId) {
		
		StringBuilder sql = new StringBuilder("INSERT INTO ITEM_LIKE ( \n");
		sql.append("LIKE_SEQ \n");
		sql.append(",ITEM_ID \n");
		sql.append(",STORE_ID \n");
		sql.append(",LIKE_DATE \n");
		sql.append(") VALUES ( \n");
		sql.append("ITEM_LIKE_SEQ.NEXTVAL \n");
		sql.append(",? \n");
		sql.append(",? \n");
		sql.append(",SYSDATE)");
		
		System.out.println(sql.toString());
		
		try {
			
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1,itemId);
			psmt.setInt(2,buyerId);

			// 실행
			int cnt = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
	
	}

	public void deleteItemLike(int itemId, int storeId) {
		
		StringBuilder sql = new StringBuilder("DELETE FROM ITEM_LIKE \n");
		sql.append("WHERE ITEM_ID = ? \n");
		sql.append("AND STORE_ID = ?");
		
		System.out.println(sql.toString());
		
		try {
			
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1,itemId);
			psmt.setInt(2,storeId);

			// 실행
			int cnt = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
	}

	public String retrieveItemLike(int itemId, int buyerId) {
		
		
		String itemLikeYn = "N";

		StringBuilder sql = new StringBuilder("SELECT (CASE WHEN COUNT(*) = 0 THEN 'N' ELSE 'Y' END) AS itemLikeYn \n");
		sql.append("FROM ITEM_LIKE \n");
		sql.append("WHERE ITEM_ID= ? \n");
		sql.append("AND STORE_ID = ?");
		
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, itemId);
			psmt.setInt(2, buyerId);
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(rs.next()) {
				itemLikeYn = rs.getString(1);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return itemLikeYn;
	}

	public ArrayList<ItemDTO> retrieveItemList(HttpServletRequest request){
		return retrieveItemList(request, -1);
	}
	
	
	public ArrayList<ItemDTO> retrieveItemList(HttpServletRequest request, int pnStoreId) {

		ArrayList<ItemDTO> resultList = new ArrayList<ItemDTO>();
		
		StringBuilder sql = new StringBuilder("SELECT A.ITEM_ID AS itemId \n");
		sql.append(",A.STORE_ID AS store_id \n");
		sql.append(",A.IMG_PATH AS imgPath \n");
		sql.append(",B.TRADE_STATUS AS tradeStatus \n");
		sql.append(",A.ITEM_TITLE AS itemTitle \n");
		sql.append(",A.price AS price \n");
		sql.append(",A.SAFETY_TRADE_YN AS safetyTradeYn \n");
		sql.append(",A.NUM_LIKE AS numLike \n");
		sql.append(",(SELECT COUNT(*) FROM ITEM_QUESTION Z WHERE Z.ITEM_ID = A.ITEM_ID) AS numQuestion \n");
		sql.append(",ROUND((SYSDATE - A.REGISTRATION_DATE) * 24 * 60) AS registrationDate \n");
		sql.append(",TO_CHAR(A.REGISTRATION_DATE, 'YY.MM.DD') AS registrationDate2 \n");
		sql.append("FROM ITEM A \n");
		sql.append(",TRADE B \n");
		sql.append("WHERE A.ITEM_ID = B.ITEM_ID \n");
		
		if(pnStoreId > 0) {
			sql.append("AND A.STORE_ID = ? \n");
			sql.append("AND B.TRADE_STATUS <> 'D' \n");
		}else {
			sql.append("AND B.TRADE_STATUS = 'S' \n");
			sql.append("AND ROWNUM <= 40 ");
		}
		
	
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			
			if(pnStoreId > 0) {
				psmt.setInt(1, pnStoreId);
			}
			
			// 실행
			rs = psmt.executeQuery();
			
			UploadUtil uploadUtil = new UploadUtil(request);
			
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				
				int itemId = rs.getInt(1);
				int storeId = rs.getInt(2);
				String imgPath = uploadUtil.getImgFile(rs.getString(3), storeId, itemId) ;
				String tradeStatus = rs.getString(4);
				String itemTitle = rs.getString(5);
				int price = rs.getInt(6);
				String safetyTradeYn = rs.getString(7);
				int numLike = rs.getInt(8);
				int numQuestion = rs.getInt(9);
				String registrationDate = changeTimeFormat(rs.getInt(10));
				String registrationDate2 = rs.getString(11);
								
				resultList.add(new ItemDTO(itemId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate, registrationDate2));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return resultList;
	}

	public ArrayList<QuestionDTO> retrieveStoreQuestion(HttpServletRequest request, int storeId) {
		
		ArrayList<QuestionDTO> resultList = new ArrayList<QuestionDTO>();
		
		StringBuilder sql = new StringBuilder("SELECT C.ITEM_ID AS itemId \n");
		sql.append(",C.STORE_ID AS storeId \n");
		sql.append(",C.ITEM_TITLE AS itemTitle \n");
		sql.append(",A.QUESTION AS quesiton \n");
		sql.append(",C.IMG_PATH AS imgPath \n");
		sql.append(",ROUND((SYSDATE - A.WRITE_DATE) * 24 * 60) AS writeDate \n");
		sql.append("FROM ITEM_QUESTION A \n");
		sql.append(",STORE B \n");
		sql.append(",ITEM C \n");
		sql.append("WHERE A.ITEM_ID = C.ITEM_ID \n");
		sql.append("AND B.STORE_ID = C.STORE_ID \n");
		sql.append("AND B.STORE_ID = ? \n");
		sql.append("ORDER BY A.WRITE_DATE DESC");
		
	
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, storeId);
			
			// 실행
			rs = psmt.executeQuery();
			
			UploadUtil uploadUtil = new UploadUtil(request);
			String profileImg;
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				profileImg = uploadUtil.getImgFile(rs.getString(5), rs.getInt(2), rs.getInt(1));
				resultList.add(new QuestionDTO(rs.getInt(1) ,profileImg, rs.getString(4), changeTimeFormat(rs.getInt(6)), rs.getString(3)));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return resultList;
	}

	public ArrayList<ItemDTO> retrieveLikeList(HttpServletRequest request, int pnStoreId) {
		
		ArrayList<ItemDTO> resultList = new ArrayList<ItemDTO>();
		
		StringBuilder sql = new StringBuilder("SELECT A.ITEM_ID AS itemId \n");
		sql.append(",A.STORE_ID AS storeId \n");
		sql.append(",A.IMG_PATH AS imgPath \n");
		sql.append(",C.TRADE_STATUS AS tradeStatus \n");
		sql.append(",A.ITEM_TITLE AS itemTitle \n");
		sql.append(",A.PRICE AS price \n");
		sql.append(",A.SAFETY_TRADE_YN AS safetyTradeYn \n");
		sql.append(",A.NUM_LIKE AS numLike \n");
		sql.append(",(SELECT COUNT(*) FROM ITEM_QUESTION Z WHERE Z.ITEM_ID = A.ITEM_ID) AS numQuestion \n");
		sql.append(",ROUND((SYSDATE - A.REGISTRATION_DATE) * 24 * 60) AS registrationDate \n");
		sql.append(",TO_CHAR(A.REGISTRATION_DATE, 'YY.MM.DD') AS registrationDate2 \n");
		sql.append("FROM ITEM A \n");
		sql.append("    ,ITEM_LIKE B \n");
		sql.append("    ,TRADE C \n");
		sql.append("WHERE A.ITEM_ID = B.ITEM_ID \n");
		sql.append("  AND A.ITEM_ID = C.ITEM_ID \n");
		sql.append("  AND B.STORE_ID = ? \n");
		sql.append("  AND C.TRADE_STATUS <> 'D' \n");

		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, pnStoreId);
			
			// 실행
			rs = psmt.executeQuery();
			
			UploadUtil uploadUtil = new UploadUtil(request);
			
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				
				int itemId = rs.getInt(1);
				int storeId = rs.getInt(2);
				String imgPath = uploadUtil.getImgFile(rs.getString(3), storeId, itemId) ;
				String tradeStatus = rs.getString(4);
				String itemTitle = rs.getString(5);
				int price = rs.getInt(6);
				String safetyTradeYn = rs.getString(7);
				int numLike = rs.getInt(8);
				int numQuestion = rs.getInt(9);
				String registrationDate = changeTimeFormat(rs.getInt(10));
				String registrationDate2 = rs.getString(11);
								
				resultList.add(new ItemDTO(itemId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate, registrationDate2));
				
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return resultList;
	}

	public void insertTrade() {
		
		ArrayList<Integer> itemList = getNotFoundTradeInfoItem();
	
		for(int i = 0; i < itemList.size() ;i++) {
			insertTrade(itemList.get(i));
		}
	
	}
	
	private ArrayList<Integer> getNotFoundTradeInfoItem(){
		
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		
		StringBuilder sql = new StringBuilder("SELECT ITEM_ID AS itemId \n");
		sql.append("FROM ITEM I \n");
		sql.append("WHERE NOT EXISTS (SELECT 1 \n");
		sql.append("FROM TRADE T \n");
		sql.append("WHERE I.ITEM_ID = T.ITEM_ID)");

		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
	
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				itemList.add(rs.getInt(1));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return itemList;
	}

	public void updateTradeInfo(String impUid, String apprNo, String itemId, String buyerId, int quantity, int totalPrice) {
		
		StringBuilder sql = new StringBuilder("UPDATE TRADE SET \n");
		sql.append("STORE_ID = ? \n");
		sql.append(",QUANTITY = ? \n");
		sql.append(",TOTAL_PRICE = ? \n");
		sql.append(",TRADE_STATUS = 'C' \n");
		sql.append(",CONFIRM_DATE = SYSDATE \n");
		sql.append("WHERE ITEM_ID = ?");
		
		System.out.println(sql.toString());
		
		try {
			
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, buyerId);
			psmt.setInt(2, quantity);
			psmt.setInt(3, totalPrice);
			psmt.setString(4, itemId);

			// 실행
			int cnt = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
	}
	
	public ArrayList<ItemDTO> retrievePageItemList(HttpServletRequest request, int pnStoreId, int page, int no, String status, String keyword){
		
		ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
		
		StringBuilder sql = new StringBuilder(" \n");
		sql.append("SELECT itemId, storeId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate,registrationDate2  \n");
		sql.append("FROM \n");
		sql.append("( \n");
		sql.append("SELECT SEQ, itemId, storeId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate,registrationDate2 \n");
		sql.append("FROM \n");
		sql.append("( \n");
		sql.append("SELECT ROWNUM AS SEQ, itemId, storeId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate,registrationDate2 \n");
		sql.append("FROM \n");
		sql.append("( \n");
		sql.append("SELECT A.ITEM_ID AS itemId \n");
		sql.append(",A.STORE_ID AS storeId \n");
		sql.append(",A.IMG_PATH AS imgPath \n");
		sql.append(",B.TRADE_STATUS AS tradeStatus \n");
		sql.append(",A.ITEM_TITLE AS itemTitle \n");
		sql.append(",A.price AS price \n");
		sql.append(",A.SAFETY_TRADE_YN AS safetyTradeYn \n");
		sql.append(",A.NUM_LIKE AS numLike \n");
		sql.append(",(SELECT COUNT(*) FROM ITEM_QUESTION Z WHERE Z.ITEM_ID = A.ITEM_ID) AS numQuestion \n");
		sql.append(",ROUND((SYSDATE - A.REGISTRATION_DATE) * 24 * 60) AS registrationDate \n");
		sql.append(",TO_CHAR(A.REGISTRATION_DATE, 'YY.MM.DD') AS registrationDate2 \n");
		sql.append(" FROM ITEM A \n");
		sql.append(",TRADE B \n");
		sql.append("WHERE A.ITEM_ID = B.ITEM_ID \n");
		sql.append("AND A.STORE_ID = ? \n");
		if(status.equals("A")) {
			sql.append("AND B.TRADE_STATUS <> 'D' \n");
		}else {
			sql.append("AND B.TRADE_STATUS = ? \n");
		}
		
		if(keyword != null) {
			sql.append("AND A.ITEM_TITLE LIKE ? \n");
		}
	
		sql.append("ORDER BY A.REGISTRATION_DATE DESC \n");
		sql.append(") \n");
		sql.append(") \n");
		sql.append("WHERE SEQ >= ? \n");
		sql.append(") \n");
		sql.append("WHERE ROWNUM <= ? \n");
		             
		System.out.println(sql.toString());

		try {
			db_conn();
			
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, pnStoreId);
			
			if(status.equals("A") == false && keyword != null) {
				System.out.println("1번조건");
				psmt.setString(2, status);
				psmt.setString(3, "%"+keyword+"%");
				psmt.setInt(4,page*(no-1)+1);
				psmt.setInt(5, page);
			}else if(status.equals("A") == false && keyword == null) {
				System.out.println("2번조건");
				psmt.setString(2, status);
				psmt.setInt(3,page*(no-1)+1);
				psmt.setInt(4, page);
			}else if(status.equals("A") == true && keyword != null) {
				System.out.println("3번조건");
				psmt.setString(2, "%"+keyword+"%");
				psmt.setInt(3,page*(no-1)+1);
				psmt.setInt(4, page);
			}else{
				System.out.println("4번조건");
				psmt.setInt(2,page*(no-1)+1);
				psmt.setInt(3, page);
			}
			
			// 실행
			rs = psmt.executeQuery();
			UploadUtil uploadUtil = new UploadUtil(request);
		
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				
				int itemId = rs.getInt(1);
				int storeId = rs.getInt(2);
				String imgPath = uploadUtil.getImgFile(rs.getString(3), storeId, itemId) ;
				String tradeStatus = rs.getString(4);
				String itemTitle = rs.getString(5);
				int price = rs.getInt(6);
				String safetyTradeYn = rs.getString(7);
				int numLike = rs.getInt(8);
				int numQuestion = rs.getInt(9);
				String registrationDate = changeTimeFormat(rs.getInt(10));
				String registrationDate2 = rs.getString(11);
								
				itemList.add(new ItemDTO(itemId, imgPath, tradeStatus, itemTitle, price, safetyTradeYn, numLike, numQuestion, registrationDate, registrationDate2));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return itemList;
	}

	public int retrieveItemCount(int pnStoreId, String status, String keyword) {
		
		int totalCount = 0;
		
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		
		StringBuilder sql = new StringBuilder("select count(*) \n");
		sql.append("FROM ITEM A \n");
		sql.append("    ,TRADE B \n");
		sql.append("WHERE A.ITEM_ID = B.ITEM_ID \n");
		sql.append("AND A.STORE_ID = ? \n");
		
		if(status.equals("A")) {
			sql.append("AND B.TRADE_STATUS <> 'D' \n");
		}else {
			sql.append("AND B.TRADE_STATUS = ? \n");
		}
		
		if(keyword != null) {
			sql.append("AND A.ITEM_TITLE LIKE ? \n");
		}

		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setInt(1, pnStoreId);
			
			if(status.equals("A") == false && keyword != null) {
				psmt.setString(2, status);
				psmt.setString(3, "%"+keyword+"%");
				
			}else if(status.equals("A") == false && keyword == null) {
				psmt.setString(2, status);
			}else if(status.equals("A") == true && keyword != null) {
				psmt.setString(2, "%"+keyword+"%");
			}
			
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return totalCount;
	}
	
	public void updateImg(int itemId, String mainImg) {
		
		StringBuilder sql = new StringBuilder("UPDATE ITEM SET \n");
		sql.append("IMG_PATH = ? \n");
		sql.append(",STORE_ID = 10000002 \n");
		sql.append("WHERE STORE_ID = 10000000 \n");
		sql.append("AND ITEM_ID = ? \n");
	
		System.out.println(sql.toString());
		
		try {
			
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, mainImg);
			psmt.setInt(2, itemId);
			
			// 실행
			int cnt = psmt.executeUpdate();
			
			if(cnt != 0) {
				System.out.println("ITEMID : "+ itemId +" success");
			}else {
				System.out.println("ITEMID : "+ itemId +" fail");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
	}
	
	public void updateMainImg(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ArrayList<Integer> itemList = selectNotFoundImg();
		UploadUtil uploadUtil = new UploadUtil(request);
		
		String mainImg;
		System.out.println("itemCount : "+ itemList.size());
		for(int i = 0; i < itemList.size(); i++) {
			
			mainImg = uploadUtil.getMainImgName(itemList.get(i));
			System.out.println("itemId : "+ itemList.get(i));
			System.out.println("mainImg : "+ mainImg);
			System.out.println();
			
			if(mainImg != null) {
				updateImg(itemList.get(i), mainImg);
			}
			
			
		}
	
	}
	
	private ArrayList<Integer> selectNotFoundImg() {
		
		ArrayList<Integer> itemList = new ArrayList<Integer>();
		
		StringBuilder sql = new StringBuilder("Select ITEM_ID FROM ITEM WHERE STORE_ID = 10000000");

		
		System.out.println(sql.toString());

		try {
			db_conn();
			psmt = conn.prepareStatement(sql.toString());
		
			// 실행
			rs = psmt.executeQuery();
			
			// 결과를 꺼내서 ArrayList로 만들기
			while(rs.next()) {
				itemList.add(rs.getInt(1));
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db_close();
		}
		
		return itemList;
	}
	
	
}
