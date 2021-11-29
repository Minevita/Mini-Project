package MusicPlayerController;

import java.util.List;

import MusicVo.Music;

/**
 * 뮤직플레이어 컨트롤러 서비스 인터페이스는
 * 꼭 구현해야할 퍼블릭 메서드를 정의합니다
 * @author 문현석
 * 2021-08-02
 *
 */
public interface MusicPlayerControllerService {
	/**
	 * 파라메터로 받은 뮤직리스트를 저장합니다.
	 * @author 문현석
	 * 2021-08-02
	 * @param musicList
	 */
	void setPlayList(List<Music> playList);
	
	/**
	 * 컨트롤러의 플레이 리스트에 접근할수 있습니다.
	 * @return
	 */
	List<Music> getPlayList();
	
	/**
	 * 로그아웃시 쓰레드를 종료하는데 외부에서 호출됩니다.
	 * @author 문현석
	 * 2021-08-10
	 */
	void closeThread();
	
	/**
	 * 재생목록의 리스트를 컨트롤 합니다(재생, 일시정지, 다음곡 , 이전곡등)
	 * @author 문현석
	 * 2021-08-02
	 */
	void musicController();
	
	void addNowPlayList(Music music);
	
	void instantPlay(Music music);
	
	void listPlay(List<Music> musicList);
	
	boolean isGuest();
	
	void setGuest(boolean isGuest);
	
	void message();
	
	void setVolumeValue(int vol);
}
