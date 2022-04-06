package Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	// 모든 메소드마다 request, response가 필요
	// 이 과정을 단순화 하기 위해서 interface 생성
	
	// 추상메소드 : 값이 없고, 메소드의 기본형태를 설정
	// 		   : 인터페이스를 상속받으면 강제적으로 구현
	public abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
