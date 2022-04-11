package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import util.UploadUtil;

public class StoreDAO {

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
		
		public void modifyStoreInfo(int storeId, String storeInfo) {
			
			StringBuilder sql = new StringBuilder("UPDATE STORE SET \n");
			sql.append("STORE_INFO = ? \n");
			sql.append("WHERE STORE_ID = ?");
			
			System.out.println(sql.toString());
			
			try {
				
				db_conn();
				psmt = conn.prepareStatement(sql.toString());
				psmt.setInt(1,storeId);
				psmt.setString(2,storeInfo);

				// 실행
				int cnt = psmt.executeUpdate();
							
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				db_close();
			}
			
			
		}

		public StoreDTO retrieveStoreInfo(HttpServletRequest request, int pnStoreId) {
			
			StoreDTO dto = null;
			
			StringBuilder sql = new StringBuilder("SELECT STORE_ID AS storeId \n");
			sql.append(",STORE_NAME AS storeName \n");
			sql.append(",STORE_INFO AS storeInfo \n");
			sql.append(",NUM_VISITORS AS numVisitors \n");
			sql.append(",NUM_SALES AS numSales \n");
			sql.append(",NUM_DELIVERY AS numDelivery \n");
			sql.append(",ROUND((SYSDATE - ACCOUNT_CREATION_DATE) * 24 * 60) AS accountCreationDate \n");
			sql.append(",NVL(PROFILE_IMG, '') AS profileImg \n");
			sql.append("FROM STORE \n");
			sql.append("WHERE STORE_ID = ?");
			

			System.out.println(sql.toString());

			UploadUtil uploadUtil = new UploadUtil(request);
			
			try {
				db_conn();
				psmt = conn.prepareStatement(sql.toString());
				psmt.setInt(1, pnStoreId);
				
				rs = psmt.executeQuery();
				
				// 결과를 꺼내서 ArrayList로 만들기
				if(rs.next()) {
				
					int storeId = rs.getInt(1);
					String storeName = rs.getString(2);
					String storeInfo = rs.getString(3);
					int numVisitors = rs.getInt(4);
					int numSales = rs.getInt(5);
					int numDelivery = rs.getInt(6);
					String accoutCreationDate = changeTimeFormat(rs.getInt(7));
					String profileImg = uploadUtil.getImgFile(rs.getString(8), String.valueOf(storeId), "profile")  ;

					dto = new StoreDTO(storeId, storeName, storeInfo, numVisitors, numSales, numDelivery, accoutCreationDate, profileImg);
				}
						
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

		public void updateProfileImg(String submittedFileName, int storeId) {
			StringBuilder sql = new StringBuilder("UPDATE STORE SET \n");
			sql.append("PROFILE_IMG = ? \n");
			sql.append("WHERE STORE_ID = ?");
			
			System.out.println(sql.toString());
			
			try {
				
				db_conn();
				psmt = conn.prepareStatement(sql.toString());
				psmt.setString(1,submittedFileName);
				psmt.setInt(2,storeId);

				// 실행
				int cnt = psmt.executeUpdate();
							
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				db_close();
			}
		}
		
}
