package UserVo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MusicVo.*;
/**
 * @author 김기락
 * Serialiable 상속하여 직렬화가능 클래스 만듬
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String pw;
	private Map<String, List<Music>> playList = new HashMap<String, List<Music>>();
	private List<Music> favoritList = new ArrayList<Music>();
	
	
	public List<Music> getFavoritList() {
		return favoritList;
	}
	public void setFavoritList(List<Music> favoritList) {
		this.favoritList = favoritList;
	}
	public User(){}
	public User(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}
	public User(String id, String pw, Map<String, List<Music>> playList) {
		this.id = id;
		this.pw = pw;
		this.playList = new HashMap<String, List<Music>>(playList);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public Map<String, List<Music>> getPlayList() {
		return playList;
	}
	public void setPlayList(Map<String, List<Music>> playList) {
		this.playList = playList;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", playList=" + playList + ", favoritList=" + favoritList + "]";
	}
}
