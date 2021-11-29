package MusicPlayerUtil;

import java.util.Scanner;

public class MusicPlayerCommon {

	public static final String TITLE = "title";
	public static final String ARTIST = "artist";
	public static final String ALBUM = "album";
	public static final String GENRE = "genre";
	public static final String LENGTH = "length";
	public static final String LENGTH_STR = "length_str"; 
	
	//MusicController의 재생상태
	public static final int INIT = 0;
	public static final int STARTED = 1;
	public static final int SUSPENDED = 2;
	public static final int STOPPED = 3;
	public static final int FINISHED = 4;
	
	//MusciController playSequence 상태
	public static final int NOTHING = 0;
	public static final int PREV = 1;
	public static final int NEXT = 2;
	
	public static Scanner scanner = new Scanner(System.in);
	
	public static int nextInt(String text) {
		while(true) {
			try {
				return Integer.parseInt(nextLine(text));
			}
			catch(Exception e) {
				System.out.println("숫자로 다시 입력을 해주세요.");
			}
		}
	}
	
	public static String nextLine(String text) {
		System.out.print(text);
		return scanner.nextLine();
	}
	
	//문자열을 입력받았을때 지정한 숫자 범위내에서만 int타입으로 반환
	public static int nextIntFromTo(String text, int from, int to) {
		while(true) {
			System.out.print(text);
			String str = scanner.nextLine();
			int num;
			
			try {
				num = Integer.parseInt(str);
			}
			catch(Exception e) {
				System.out.println("숫자로 다시 입력해주세요.");
				continue;
			}
			
			if(from <= num && num <= to) return num;
			else System.out.println("정확한 메뉴번호를 입력 해주세요.>");
		}
	}
	
	//문자열을 입력 받았을때 한글범위 내에서만 반환
	public static String nextLineKorean(String text) {
		while(true) {
			System.out.print(text);
			boolean isKorean = true;
			String str = scanner.nextLine();
			for(int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if('가' <= c && c <= '힣');
				else isKorean = false;
			}
			if(isKorean)return str;
			else System.out.print("한글로 다시 ");
		}
	}
	
	//파라메터의 문자열길이가 limit를 넘어 가면 지정한(max) 길이로 자릅니다.
	public static String getShortString(String str, int limit, int max) {
		if(str.length() > limit) {
			return str.substring(0, max);
		}
		else {
			return str;
		}
	}

	public void manyLine(int num) {
		for(int i = 0; i < num ; i++) {
			System.out.println(" ");
		}
		
	}
	
}
