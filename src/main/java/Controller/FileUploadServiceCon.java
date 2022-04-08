package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Command.AjaxCommand;
import util.UploadUtil;

@MultipartConfig(
	    fileSizeThreshold = 1024*1024,
	    maxFileSize = 1024*1024*50, //50메가
	    maxRequestSize = 1024*1024*50*12 // 50메가 12개까지
)
public class FileUploadServiceCon implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		List<Part> fileList = (List<Part>) request.getParts();
		String itemId = request.getParameter("itemId");
		String storeId = request.getParameter("storeId");
		
		System.out.println("이미지 갯수 : "+ fileList.size());
		
		
	    UploadUtil uploadUtil = new UploadUtil(request);
	  
	    for(Part part : fileList) {
		    if(part.getName().indexOf("imgPath") < 0) continue; //f로 들어온 Part가 아니면 스킵
		    if(part.getSubmittedFileName().equals("")) continue; //업로드 된 파일 이름이 없으면 스킵
		  
		    uploadUtil.saveFile(part, storeId, itemId);
	    }
		
		  
	}

}
