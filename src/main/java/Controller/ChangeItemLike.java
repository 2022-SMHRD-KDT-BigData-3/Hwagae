package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Model.ItemDAO;
import Model.QuestionDTO;

public class ChangeItemLike implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int storeId = Integer.parseInt(request.getParameter("storeId"));
			String itemLikeYn = request.getParameter("itemLikeYn");
			System.out.println("itemLikeYn : "+itemLikeYn);
			ItemDAO dao = new ItemDAO();
			
			if(itemLikeYn.equals("Y")) {
				dao.insertItemLike(itemId, storeId);
			}else {
				dao.deleteItemLike(itemId, storeId);
			}
			
			resultMap.put("rsltCd", 0);
		
		}catch(Exception err) {
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", "아이템 찜처리 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
		
	}

}
