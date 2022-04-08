package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Model.WS_TalkDAO;
import Model.WS_TalkDTO;

public class WS_ChatDB implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		WS_TalkDAO mdao = new WS_TalkDAO();
		ArrayList<WS_TalkDTO> mlist = new ArrayList<WS_TalkDTO>();
		mlist = mdao.talkList();
		
		Gson gson = new Gson();
		
		//webList에 있는 데이터들이 자동으로 json형식으로 변환되어진다.
		//gson 라이브러리를 통해서 json 형태로 만들면 key 값은 클래스의 필드명으로 
		//자동으로 정해진다!
		Object json = gson.toJson(mlist);
		
		
		// 꼭 응답하는 객체가 만들어 지기전 인코딩을 해줘야한다!
		response.setContentType("text/plain; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
}
