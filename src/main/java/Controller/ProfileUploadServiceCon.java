package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Command.AjaxCommand;
import Model.StoreDAO;
import util.UploadUtil;

public class ProfileUploadServiceCon implements AjaxCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Part profile = request.getPart("profile");
		String itemId = request.getParameter("itemId");
		String storeId = request.getParameter("storeId");
		
	    UploadUtil uploadUtil = new UploadUtil(request);
	  
		uploadUtil.saveProfile(profile, storeId, itemId);
	    
		StoreDAO dao = new StoreDAO();
		dao.updateProfileImg(profile.getSubmittedFileName(), Integer.parseInt(storeId));
		
	
	}

}
