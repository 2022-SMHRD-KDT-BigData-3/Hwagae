package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
	
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
					
		public ArrayList<CategoryDTO> retrieveCategory(String psSection1, String psSection2) {
			// 로그인한 사용자에게 온 / 메세지만 가져오기
			StringBuilder sql = new StringBuilder("SELECT CAT_SEQ AS catSeq \n");
			sql.append(",SECTION1 AS section1 \n");
			sql.append(",SECTION2 AS section2 \n");
			sql.append(",SECTION3 AS section3 \n");
			sql.append(",SECTION_INFO AS sectionInfo \n");		
			sql.append("FROM CATEGORY \n");
			sql.append("WHERE 1=1 \n");
			
			if(psSection1 == null && psSection2 == null) {
				sql.append("AND SECTION2= ? \n");
				sql.append("AND SECTION3= ? \n");
			}else if(psSection1 != null && psSection2 == null) {
				sql.append("AND SECTION1= ? \n");
				sql.append("AND SECTION2 != '00' \n");
				sql.append("AND SECTION3= ? \n");
			}else if(psSection1 != null && psSection2 != null) {
				sql.append("AND SECTION1= ? \n");
				sql.append("AND SECTION2= ? \n");
				sql.append("AND SECTION3 != '00' \n");
			}
			
			sql.append("order by CAT_SEQ \n");
			
			ArrayList<CategoryDTO> Categorylist = new ArrayList<CategoryDTO>(); // 데이터를 담을 ArrayList
			System.out.println(sql.toString());
			
			db_conn();
			
			try {
				psmt = conn.prepareStatement(sql.toString());
				
				if(psSection1 == null && psSection2 == null) {
					psmt.setString(1, "00");
					psmt.setString(2, "00");
				}else if(psSection1 != null && psSection2 == null) {
					psmt.setString(1, psSection1);
					psmt.setString(2, "00");
				}else if(psSection1 != null && psSection2 != null) {
					psmt.setString(1, psSection1);
					psmt.setString(2, psSection2);
				}
				
				// 실행
				rs = psmt.executeQuery();
				
				// 결과를 꺼내서 ArrayList로 만들기
				while(rs.next()) {
					int catSeq = rs.getInt(1);	
					String section1 = rs.getString(2);
					String section2 = rs.getString(3);
					String section3 = rs.getString(4);
					String sectionInfo = rs.getString(5);
					
					CategoryDTO dto = new CategoryDTO(catSeq, section1, section2, section3, sectionInfo);
					
					Categorylist.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				db_close();
			}
			
			return Categorylist;	
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

}
