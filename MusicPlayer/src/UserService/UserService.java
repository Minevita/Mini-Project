package UserService;


import UserVo.User;
/**
 * 
 * @author 김기락
 *  210805 UserServiceImpl 각 메서드 추가 및 주석 작성 
 *  
 */
//유저 서비스 기능을 정의 합니다.
public interface UserService {
	// 파일 저장
	void saveDb();
	
	// 파일 불러오기
	void loadDb();
	
	// 유저ID로 해당 유저의 List 전체 불러오기
	User userListID(String inputId);
	
	// 유저PW로 해당 유저의 List 전체 불러오기
	User userListPW(String inputPw);
	
	// 게스트 유저 생성
	User guestUser();
	
	// ID입력으로 해당 유저ID 불러오기 
	String userID(String inputId);
	
	// PW입력으로 해당 유저PW 불러오기
	String userPW(String inputPw);
	
	// 회원 가입
	void addUser();
	
	// 로그인
	User login();
	
	void errorMessage();

}
