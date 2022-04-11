package FrontController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import Command.Command;
import Controller.RegistItemServiceCon;
import Controller.RetrieveCategoryServiceCon;
import Controller.ShowItemServiceCon;
import Controller.ShowManageItemServiceCon;
import Controller.ShowSearchItemServiceCon;
import Controller.ShowStoreInfoServiceCon;
import Controller.JoinServiceCon;
import Controller.LoginServiceCon;
import Model.ItemDAO;
import Model.MemberDAO;
import Model.MemberDTO;
import util.UploadUtil;


// 확장자 패턴을 활용해서 .do 확장자를 가지고 있으면
// frontcotroller로 모이게 만들어줌!
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[FrontController]");

		// 요청 들어온 주소 확인
		String uri = request.getRequestURI();
		System.out.println("uri : "+ uri);

		String[] uriSplit  = uri.split("/");
		String result = uriSplit[uriSplit.length-1];
		
		request.setCharacterEncoding("UTF-8");

		// 업캐스팅
		Command sc = null;
		
		if (result.equals("ShowItemServiceCon.do")) {	 //등록상품보기
			sc = new ShowItemServiceCon();
		} else if(result.equals("ShowStoreInfoServiceCon.do")) {  //내상점관리
			sc = new ShowStoreInfoServiceCon();
		} else if (result.equals("LoginServiceCon.do")) { //로그인
    	 	 sc = new LoginServiceCon();
       	} else if (result.equals("JoinServiceCon.do")) { //회원가입
    	  	 sc = new JoinServiceCon();
       	} else if (result.equals("ShowManageItemServiceCon.do")) { //상품관리
       		 sc = new ShowManageItemServiceCon();
       	} else if(result.equals("ShowSearchItemServiceCon.do")) { //상품검색
       		 sc = new ShowSearchItemServiceCon();
       	} else if(result.equals("UdateMainImg.do")) {	//이미지파일폴더의 첫번째 이미지명을 상품대표 이미지로 수정(test데이터생성용)
       		
       		ItemDAO dao = new ItemDAO();
       		dao.updateMainImg(request);
       	
       		return;
       	}
		
		
		String moveURL = sc.execute(request, response);
		response.sendRedirect(moveURL);

	}

}
