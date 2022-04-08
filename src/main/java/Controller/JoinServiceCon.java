package Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Command.Command;
import Model.MemberDAO;
import Model.MemberDTO;

public class JoinServiceCon implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("[JoinServiceCon]");

        String snsid = request.getParameter("snsid");
    	String store_name = request.getParameter("store_name");
    	String store_info = request.getParameter("store_info");
    	String mobile = request.getParameter("mobile");
    	String area = request.getParameter("area");
    	String zip_code = request.getParameter("zip_code");
    	String basic_address = request.getParameter("basic_address");
    	String dtail_address = request.getParameter("dtail_address");
        
        
        
        MemberDTO dto = new MemberDTO(snsid, store_name, store_info, mobile, area, zip_code, basic_address, dtail_address);

        MemberDAO dao = new MemberDAO();
        int cnt = dao.naverjoin(dto);
        
        String moveURL = null;
        
        if (cnt > 0) {
           System.out.println("회원가입 성공");
           moveURL = "./sample.jsp";
        } else {
           System.out.print("회원가입 실패");
           moveURL = "./sample.jsp";
        }
		
		return moveURL;
	}

}