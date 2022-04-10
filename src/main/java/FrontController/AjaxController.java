package FrontController;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Command.AjaxCommand;
import Controller.ChangeItemLike;
import Controller.FileUploadServiceCon;
import Controller.InsertItemQuestion;
import Controller.ModifyStoreInfoServiceCon;
import Controller.ProfileUploadServiceCon;
import Controller.RegistItemServiceCon;
import Controller.RetrieveCategoryServiceCon;
import Controller.RetrieveLastTradeAreaServiceCon;
import Controller.ShowStoreInfoServiceCon;
import Controller.UpdateTradeInfo;
import Controller.WS_ChatDB;


@WebServlet("*.ajax")

public class AjaxController extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[AjaxController]");

		// 요청 들어온 주소 확인
		String uri = request.getRequestURI();
		System.out.println("uri : "+ uri);

      String[] uriSplit  = uri.split("/");
      String result = uriSplit[uriSplit.length-1];
      
      request.setCharacterEncoding("UTF-8");
   
      // 업캐스팅
      AjaxCommand sc = null;
      
      if (result.equals("RetrieveCategoryServiceCon.ajax")) {   
         sc = new RetrieveCategoryServiceCon();
      }else if(result.equals("RetrieveLastTradeAreaServiceCon.ajax")) {   
         sc = new RetrieveLastTradeAreaServiceCon();
      }else if(result.equals("RegistItemServiceCon.ajax")) {
         sc = new RegistItemServiceCon();
      }else if(result.equals("FileUploadServiceCon.ajax")) {
         sc = new FileUploadServiceCon();
      }else if(result.equals("insertItemQuestion.ajax")) {
         sc = new InsertItemQuestion();
      }else if(result.equals("changeItemLike.ajax")) {
         sc = new ChangeItemLike();
      }else if(result.equals("ModifyStoreInfoServiceCon.ajax")) {
         sc = new ModifyStoreInfoServiceCon();
      }else if(result.equals("ProfileUploadServiceCon.ajax")) {
         sc = new ProfileUploadServiceCon();
      }else if(result.equals("WS_ChatDB.ajax")) {
          sc = new WS_ChatDB();
      }else if(result.equals("UpdateTradeInfo.ajax")) {
    	  sc = new UpdateTradeInfo();
      }
      
      sc.execute(request, response);

   }

}