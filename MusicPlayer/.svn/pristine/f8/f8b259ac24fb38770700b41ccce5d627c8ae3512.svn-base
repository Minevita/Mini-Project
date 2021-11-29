package Mp3tagParser;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.LogManager;

import org.jaudiotagger.tag.Tag;

import Mp3PathParser.Mp3PathParser;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File; 
import org.jaudiotagger.tag.FieldKey; 
import static MusicPlayerUtil.MusicPlayerCommon.*;

/**mp3파일의 tag데이터를 파싱함.
 * @author 김기락
 * 2021-07-30
 */
public class Mp3tagParser  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//필드
	private String title;
	private String artist;
	private String album;
	private String genre; 		// 여기까지 게터만 구현	

	private int length;			// 게터메서드내에서 분,초 단위로 반환 타입은 String
	private int lengthMinute;	// 이후 분, 초 출력은 차후에 스킵기능에 기준을 정할때 SET을 추가하면 좋을것 같음
	private int lengthSecend;
	private boolean isTrash;	//재생할수 없는 파일이면 true값을 가집니다
	
	public boolean isTrash() {
		return isTrash;
	}
	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getLengthMinute() {
		return lengthMinute;
	}
	public void setLengthMinute(int lengthMinute) {
		this.lengthMinute = lengthMinute;
	}
	public int getLengthSecend() {
		return lengthSecend;
	}
	public void setLengthSecend(int lengthSecend) {
		this.lengthSecend = lengthSecend;
	}
	

//	String 타입 출력을 위한 getter
	
	
	public String getLStr() {
		return length + "";
	}
	public String getLMStr() {
		return lengthMinute + "";
	}
	public String getLSStr() {
		return lengthSecend + "";
	}
	
	
	public Mp3tagParser(){}
	public Mp3tagParser(String path){
		this.parse(path);
	}
	
	/**MP3 파일을 읽어오는 기능
	 * 파일이 일기에 실패한 경우 예외 처리(문장 출력 후 반환)
	 * @author 김기락
	 * 2021-07-30
	 */
	private void parse(String path) {
		File file = new File(path); 
		MP3File mp3;
		Tag tag;
		try {
			mp3 = (MP3File) AudioFileIO.read(file);
		} catch (Exception e) {
			/**재생할수없는 파일일 경우 isTrash에 true값을 저장합니다.
			 * 뮤직리스트 생성시 tag의 isTrash인경우 뮤직객체를 생성하지 않습니다. 
			 * @author 현석
			 * 2021-07-30
			 */
			System.out.println("손상된 파일입니다. 리스트에 추가 할 수 없습니다.");
			this.isTrash = true;
			return;
		} 
		
		
	    /**태그정보 존재 여부 확인 조건문
		*  확인 후 없을 경우 출력될 문장들
		*  @author 김기락
		*  2021-07-30
		*/
		tag = mp3.getTag();

		if (!mp3.hasID3v1Tag()) {								 
			setTitle("Unkown Title"); 		 
			setArtist("Unkown Artist"); 
			setAlbum("Unkown Album");
			setGenre("Unkown Genre");
			this.length = mp3.getAudioHeader().getTrackLength();	
			this.lengthMinute = length / 60;						
			this.lengthSecend = length % 60;						
			return;
		}
		
//		각 변수들에 들어갈 Tag정보	(차후 추가될 tag정보가 있다면 이곳에서 추가)
		
		this.title  = tag.getFirst(FieldKey.TITLE); 				// 노래제목
		this.title  = String.format("%-30s", this.title);
		
		this.artist = tag.getFirst(FieldKey.ARTIST);  			// 아티스트
		this.artist = String.format("%-30s", this.artist);
		
		this.album = tag.getFirst(FieldKey.ALBUM); 				// 앨범
		this.album = String.format("%-30s", this.album);
		
		this.genre = tag.getFirst(FieldKey.GENRE);				// 장르
		this.genre = String.format("%-30s", this.genre);
		
		this.length = mp3.getAudioHeader().getTrackLength();	// 노래길이
		this.lengthMinute = length / 60;						// 분 단위
		this.lengthSecend = length % 60;						// 초 단위
	
	}
	@Override
	public String toString() {
		return "Title  : " + title + 
			 "\nArtist : " + artist + 
			 "\nAlbum  : " + album + 
			 "\nGenre  : " + genre+ 
			 "\nLength : " + lengthMinute + "분 " + lengthSecend + "초";
	}
	
	
}