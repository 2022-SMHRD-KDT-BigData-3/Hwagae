package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Model.ItemDAO;

public class UpdateTradeInfo implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			String impUid = request.getParameter("impUid").toString();
			String apprNo = request.getParameter("apprNo").toString();
			String itemId = request.getParameter("itemId").toString();
			String buyerId = request.getParameter("buyerId").toString();
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
						
			ItemDAO dao = new ItemDAO();
			dao.updateTradeInfo(impUid,apprNo,itemId,buyerId,quantity,totalPrice);
			
			resultMap.put("rsltCd", 0);
			
		}catch(Exception err) {
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", "상품구매 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
		
	}	
	
}
