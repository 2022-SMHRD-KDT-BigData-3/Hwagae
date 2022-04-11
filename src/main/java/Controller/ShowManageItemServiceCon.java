package Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.Command;
import Model.ItemDAO;
import Model.ItemDTO;
import Model.MemberDTO;
import Model.QuestionDTO;
import Model.StoreDAO;
import Model.StoreDTO;

public class ShowManageItemServiceCon implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		MemberDTO meberDto = (MemberDTO) session.getAttribute("info");
		String storeId = meberDto.getStore_id();
	
		int page =  Integer.parseInt(request.getParameter("page"));
		int no =  Integer.parseInt(request.getParameter("no"));
		String status = request.getParameter("status");
		String keyword = request.getParameter("keyword");
		
		ItemDAO itemDao = new ItemDAO();

		int totalPage = itemDao.retrieveItemCount(Integer.parseInt(storeId), status, keyword);
		totalPage = totalPage/page;
		totalPage++;
		
		ArrayList<ItemDTO> itemList = itemDao.retrievePageItemList(request, Integer.parseInt(storeId), page, no, status, keyword); 
		
		session.setAttribute("totalPage", totalPage);
		session.setAttribute("itemList", itemList);
		
		return "manageItem.jsp";
	}

}
