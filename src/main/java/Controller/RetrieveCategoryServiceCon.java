package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Command.Command;
import Model.CategoryDAO;
import Model.CategoryDTO;

public class RetrieveCategoryServiceCon implements AjaxCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		try {
		
			String section1 = request.getParameter("section1") == null || request.getParameter("section1").equals("") ? null : request.getParameter("section1");
			String section2 = request.getParameter("section2") == null || request.getParameter("section2").equals("") ? null : request.getParameter("section2");
			
			System.out.println("section1 : "+section1);
			System.out.println("section1 : "+section2);
			
			CategoryDAO dao = new CategoryDAO();
			ArrayList<CategoryDTO> categoryList = dao.retrieveCategory(section1, section2);
			
			out.print(gson.toJson(categoryList));
			
		}catch(Exception err) {
			err.printStackTrace();
		}
		
	}
	
}