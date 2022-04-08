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
import Model.StoreDAO;
import util.BusinessException;

public class ModifyStoreInfoServiceCon implements AjaxCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			int storeId= Integer.parseInt(request.getParameter("storeId"));
			String storeInfo = request.getParameter("storeInfo");
			
			
			StoreDAO dao = new StoreDAO();
			dao.modifyStoreInfo(storeId, storeInfo);
			
			resultMap.put("rsltCd", 0);
			
			
		}catch(Exception err) {
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", "최근 내역 조회 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
	}

	
	
	
}
