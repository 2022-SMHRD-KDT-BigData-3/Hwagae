package Controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.Command;
import Model.ItemDAO;
import Model.ItemDTO;
import Model.StoreDAO;
import Model.StoreDTO;
import util.UploadUtil;

public class ShowStoreInfoServiceCon implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		
		String storeId = session.getAttribute("storeId").toString();
		
		StoreDAO storeDao = new StoreDAO();
		ItemDAO itemDao = new ItemDAO();
		UploadUtil iploadUtil = new UploadUtil(request);
		
		StoreDTO storeDto = storeDao.retrieveStoreInfo(request, Integer.parseInt(storeId));
		ArrayList<ItemDTO> itemList = itemDao.retrieveItemList(request, Integer.parseInt(storeId)); 
		
		session.setAttribute("storeDto", storeDto);
		session.setAttribute("itemList", itemList);
		
		return "./productStore.jsp";
	}
	
}
