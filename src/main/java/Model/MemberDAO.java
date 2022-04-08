package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO {
	
	// 전역변수 선언
	Connection conn = null; // connection 객체
	PreparedStatement psmt = null; // psmt 객체
	int cnt = 0;
	ResultSet rs = null;
	MemberDTO info = null;
	// DB 연결 메소드
	
	public void db_conn(){
		// 1. ojdbc6.jar -> import 
		// maven에서는 pom.xml에서 라이브러리 넣어서 다운로드
		
		try {
			// 2. Class찾기 -> Java와 DB를 이어주는 통로
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 3. DB에 접속하기 위한 카드키 만들기 (url, id, pw)
			String db_url = "jdbc:oracle:thin:@project-db-stu.ddns.net:1524:xe";
			String db_id = "campus_k_0325_5";
			String db_pw = "smhrd5";
			
			// 4. DB연결 -> 연결 성공시 Connection 객체로 반환
			conn = DriverManager.getConnection(db_url, db_id, db_pw);
			
			if (conn != null) {
				System.out.println("DB 연결 성공");
			} else {
				System.out.println("DB 연결 실패");
			}
		} catch (ClassNotFoundException e) {//Exception e로 한꺼번에 가능
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// DB 종료 메소드
	public void db_close() {
			try {
				if(rs != null) rs.close();
				if(psmt != null) psmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	// 회원가입메소드
	public int naverjoin(MemberDTO dto){
		// db 연결 메소드 호출
		db_conn();
		try {
			// 2. DB에서 뭐할지 결정
			// 회원가입 기능 = 입력받은 데이터들을 web_member Table에 insert하기 "insert into STORE(SNSID, SNSTYPE) values(?,'NAVER')";
			String sql = "insert into STORE(STORE_ID, STORE_NAME, STORE_INFO, SNSID, SNSTYPE, MOBILE, AREA, ZIP_CODE, BASIC_ADDRESS, DTAIL_ADDRESS) values(STORE_SEQ.nextval,?,?,?,'NAVER',?,?,?,?,?)";
			// 3. sql문장을 DB에 전달 ->> 전달 성공 시 PreparedStatement(psmt)객체로 반환
			psmt = conn.prepareStatement(sql);
			
			// 4. 바인드 변수(?) 값 채우기
			psmt.setString(1, dto.getStore_name());
			psmt.setString(2, dto.getStore_info());
			psmt.setString(3, dto.getSnsid());
			psmt.setString(4, dto.getMobile());
			psmt.setString(5, dto.getArea());
			psmt.setString(6, dto.getZip_code());
			psmt.setString(7, dto.getBasic_address());
			psmt.setString(8, dto.getDtail_address());
			
			// 5. sql문 실행
			// insert -> DB에 변화 생김 -> Update
			// cnt는 int 형태로 반환, int의 의미 : 몇개의 행에 변화가 생겼나
			cnt = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db_close();
		}
		return cnt;
	} 
	
	public MemberDTO naverlogin(MemberDTO dto) {
		db_conn();
		try {
			String sql = "select * from STORE where SNSID=? and SNSTYPE='NAVER'";
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, dto.getSnsid());
			
			// select절은 Query -> ResultSet(rs)
			rs = psmt.executeQuery();
			
			// rs.next() : 다음 행에 값이 있는지(t) 없는지(f) boolean타입으로
			if(rs.next()) {
				// 실행문장 실행 = 값이 있다 = 로그인 성공
				String store_id = rs.getString(1);
				String snsid = rs.getString(4);
				System.out.println(snsid);
				System.out.println(store_id);
				// info = 로그인한 사람의 정보를 담고 있는 MemberDTO 형태의 객체
				info = new MemberDTO(store_id, snsid);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db_close();
		} return info;
	}
	
}
