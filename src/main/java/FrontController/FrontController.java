package FrontController;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.Command;
import Controller.RegistItemServiceCon;


// 확장자 패턴을 활용해서 .do 확장자를 가지고 있으면
// frontcotroller로 모이게 만들어줌!
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[FrontController]");

		// 요청 들어온 주소 확인
		String uri = request.getRequestURI();
		// System.out.println(uri);

		// 프로젝트 이름 확인하기
		String project = request.getContextPath();
		// System.out.println(project);

		// 요청들어온 servlet만 출력하기 -> substring사용
		// substring(start) : start숫자 위치부터 끝까지 문자열 자르기
		// substring(start, end) : start숫자부터 end숫자 전까지 문자열 자르기
		// +1 : '/'까지 잘라내기
		String result = uri.substring(project.length() + 1);
		System.out.println(result);
		
		request.setCharacterEncoding("UTF-8");

		// 업캐스팅
		Command sc = null;
		


		//String moveURL = sc.execute(request, response);
		//response.sendRedirect(moveURL);

	}

}
