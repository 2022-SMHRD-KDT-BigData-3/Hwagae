package Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import Command.Command;
import Model.ItemDAO;
import Model.ItemDTO;
import Model.MemberDTO;

public class ShowSearchItemServiceCon implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String keyword = request.getParameter("q");
		String catSeq = request.getParameter("catSeq");
	
		ItemDAO itemDao = new ItemDAO();
		ArrayList<ItemDTO> itemList = new ArrayList<ItemDTO>();
		
		
		if(keyword != null) {//키워드 검색이용시
			
			if(keyword.substring(0, 1).equals("@")) {
				int storeId = itemDao.retrieveStoreId(keyword);
				if(storeId != -1) {
					itemList = itemDao.retrieveItemList(request, storeId);
				}
			}else {
				itemList = itemDao.searchKeywordItemList(request,keyword);
			}
			
		}else {//카테고리 검색이용시
			String[] categoryInfo = itemDao.retrieveCategoryInfo(catSeq);
			itemList = itemDao.searchCategoryItemList(request,categoryInfo);
			keyword ="";
		}
		
		session.setAttribute("keyword", keyword);
		session.setAttribute("itemList", itemList);
		
		return "searchItem.jsp";
		
	}

}
