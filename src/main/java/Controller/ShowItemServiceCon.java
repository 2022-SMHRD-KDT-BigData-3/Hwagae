package Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.Command;
import Model.ItemDAO;
import Model.ItemDTO;
import util.UploadUtil;

public class ShowItemServiceCon implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			HttpSession session = request.getSession();
			System.out.println("itemId : "+ request.getParameter("itemId"));
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int buyerId = 0;
			int storeId = 0;
			
			if(request.getParameter("buyerId") != null && request.getParameter("buyerId").equals("") == false) {
				buyerId = Integer.parseInt(request.getParameter("buyerId"));
			}
			
			ItemDAO dao = new ItemDAO();
			ItemDTO itemDto = dao.retrieveItem(itemId);
			UploadUtil uploadUtil = new UploadUtil(request);
			
			System.out.println("itemId : "+itemId);
			System.out.println("storeId : "+itemDto.getStoreId());
			
			storeId = itemDto.getStoreId();
			
			session.setAttribute("itemDto", itemDto);
			session.setAttribute("imgPath", uploadUtil.getImgFiles(storeId, itemId));
			session.setAttribute("questionList", dao.retrieveItemQuestion(request, itemId));
			session.setAttribute("itemLikeYn", dao.retrieveItemLike(itemId, buyerId));
			
		}catch(Exception err) {
			err.printStackTrace();
		}
		
		return "./showItem.jsp";
	}

}
