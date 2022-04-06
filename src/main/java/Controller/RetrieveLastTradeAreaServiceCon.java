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
import util.BusinessException;

public class RetrieveLastTradeAreaServiceCon implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			String storeId = "10000000";
			
			ItemDAO dao = new ItemDAO();
			String lastTradeArea = dao.RetrieveLastTradeArea(storeId);
			
			resultMap.put("rsltCd", 0);
			resultMap.put("lastTradeArea", lastTradeArea);
			
		}catch(BusinessException err) {
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", err.getMessage());
		}catch(Exception err) {
			resultMap.put("rsltCd", "최근 내역 조회 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
		
	}
	
}