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
import util.UploadUtil;

public class ShowStoreInfoServiceCon implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		
		MemberDTO meberDto = (MemberDTO) session.getAttribute("info");
		String storeId = meberDto.getStore_id();
		
		StoreDAO storeDao = new StoreDAO();
		ItemDAO itemDao = new ItemDAO();
	
		StoreDTO storeDto = storeDao.retrieveStoreInfo(request, Integer.parseInt(storeId));
		ArrayList<ItemDTO> itemList = itemDao.retrieveItemList(request, Integer.parseInt(storeId)); 
		ArrayList<QuestionDTO> totalQuestionList = itemDao.retrieveStoreQuestion(request, Integer.parseInt(storeId));
		ArrayList<ItemDTO> likeList = itemDao.retrieveLikeList(request, Integer.parseInt(storeId));
		
		session.setAttribute("storeDto", storeDto);
		session.setAttribute("itemList", itemList);
		session.setAttribute("totalQuestionList", totalQuestionList);
		session.setAttribute("likeList", likeList);
		
		return "./productStore.jsp";
	}
	
}
