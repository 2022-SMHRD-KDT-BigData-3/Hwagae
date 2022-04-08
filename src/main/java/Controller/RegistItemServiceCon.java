package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Command.Command;
import Model.CategoryDAO;
import Model.CategoryDTO;
import Model.ItemDAO;
import Model.ItemDTO;

public class RegistItemServiceCon implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int storeId = Integer.parseInt(request.getParameter("storeId"));
			String itemTitle = request.getParameter("itemTitle");
			String itemInfo = request.getParameter("itemInfo");
			String itemCategory = request.getParameter("itemCategory");
			String itemStatus = request.getParameter("itemStatus");
			String exchangeYn = request.getParameter("exchangeYn");
			int price = Integer.parseInt(request.getParameter("price"));
			String includeDeliveryPriceYn = request.getParameter("includeDeliveryPriceYn");
			String relationTag = request.getParameter("relationTag");
			String tradeArea = request.getParameter("tradeArea");
			int stock = Integer.parseInt(request.getParameter("stock"));
			String safetyTradeYn = request.getParameter("safetyTradeYn");
			String imgPath = request.getParameter("imgPath");
			
			ItemDTO dto = new ItemDTO(storeId, itemTitle, itemInfo, itemCategory, itemStatus, exchangeYn, price, includeDeliveryPriceYn, relationTag, tradeArea, stock, safetyTradeYn, imgPath); 
			ItemDAO dao = new ItemDAO();
			int itemId = dao.insertItem(dto);
			
			resultMap.put("rsltCd", 0);
			resultMap.put("itemId", itemId);
			
		}catch(Exception err) {
			err.printStackTrace();
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", "상품등록 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
	}

}
