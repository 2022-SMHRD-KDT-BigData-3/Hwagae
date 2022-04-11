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
import Model.ItemDAO;
import Model.ItemDTO;
import util.BusinessException;

public class RetrieveManageItem implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			String storeId = request.getParameter("storeId");
			int page =  Integer.parseInt(request.getParameter("page"));
			int no =  Integer.parseInt(request.getParameter("no"));
			String status = request.getParameter("status");
			String keyword = request.getParameter("keyword");
			
			ItemDAO itemDao = new ItemDAO();
			
			int totalPage = itemDao.retrieveItemCount(Integer.parseInt(storeId), status, keyword);
			totalPage = totalPage/page;
			totalPage++;
			
			ArrayList<ItemDTO> itemList = itemDao.retrievePageItemList(request, Integer.parseInt(storeId), page, no, status, keyword); 
			
			resultMap.put("rsltCd", 0);
			resultMap.put("totalPage", totalPage);
			resultMap.put("itemList", itemList);
			
		}catch(Exception err) {
			resultMap.put("rsltCd", "관리물품조회 중 알 수 없는 오류가 발생하였습니다.");
			err.printStackTrace();
		}
		
		out.print(gson.toJson(resultMap));
		
	}

}
