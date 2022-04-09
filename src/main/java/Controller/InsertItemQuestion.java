package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Model.CategoryDAO;
import Model.CategoryDTO;
import Model.ItemDAO;
import Model.ItemDTO;
import Model.QuestionDTO;

public class InsertItemQuestion implements AjaxCommand{
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		try {
		
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int storeId = Integer.parseInt(request.getParameter("storeId"));
			String itemInfo = request.getParameter("itemInfo");

			ItemDAO dao = new ItemDAO();
			List<QuestionDTO> questionList = dao.insertItemQuestion(request,itemId, storeId, itemInfo);
			
			out.print(gson.toJson(questionList));
			
		}catch(Exception err) {
			err.printStackTrace();
		}
		
	}

}
