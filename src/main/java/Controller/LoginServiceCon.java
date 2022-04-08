package Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.Command;
import Model.MemberDAO;
import Model.MemberDTO;

public class LoginServiceCon implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		  System.out.println("[LoginServiceCon]");
		  
		  String moveURL = null;
		  
          String snsid = request.getParameter("snsid");
          String store_id = null;

          MemberDTO dto = new MemberDTO(store_id, snsid);

          MemberDAO dao = new MemberDAO();
          MemberDTO info = dao.naverlogin(dto);

          if (info != null) {
             System.out.println("로그인 성공");
             HttpSession session = request.getSession();
             session.setAttribute("info", info);
             moveURL = "sample.jsp";
          } else {
             System.out.println("로그인 실패");
             moveURL = "JoinDtail.jsp?snsid="+snsid;
          }


		return moveURL;
	}

}
