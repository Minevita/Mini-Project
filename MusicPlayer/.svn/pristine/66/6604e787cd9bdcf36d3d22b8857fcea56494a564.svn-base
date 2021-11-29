package MusicPlayerController;

import static MusicPlayerUtil.MusicPlayerCommon.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;

import javazoom.jl.decoder.*;
import javazoom.jl.player.*;

public class MusicPlayerController implements Runnable{

	/**
	 * 디코딩을 위해 javazoom이 제공하는 클래스를 오버라이딩
	 * @author 문현석
	 * 2021-07-31
	 */
	static class Sample{
		private short[] buffer;
		private int length;
		
		public Sample(short[] buf, int s) {
			buffer = buf.clone();
			length = s;
		}
		
		public short[] GetBuffer() {
			return buffer;
		}
		
		public int GetLength() {
			return length;
		}
	}
	
	/**
	 * 디코딩 관련 멤버입니다
	 */
	public static final int BUFFER_SIZE = 20000;
	private Decoder decoder;
//	private AudioDevice out;
	private JavaSoundAudioDevice out2 = new JavaSoundAudioDevice();
	private ArrayList<Sample> playes;
	private int length;
	private int samplingIndex; 						// 샘플링데이터를 읽어 재생할때 현재 샘플링인덱스가 몇인지 기록(일지정지후 다시 재생할때 사용됩니다.)
	private boolean isDecodeSkip 	= false;		// 외부에서 디코딩시간 도중 다른곡으로 전환할때 디코딩을 스킵하게 하는 flag
	private boolean isDecoding 		= false;		// 현재 디코딩 중인지 true false값을 반환 
	private float saveVolumeVar		= 0.f;
	
	private int state 		= INIT;
	private boolean isSkip 	= false;				// 곡을 스킵했을때 빠져나갈 flag
	
	private Thread thread;							
	private boolean isRun = true;					// 쓰레드를 종료시키기위한 flag
	
	private int messageState							= INIT;
	private static final int AUDIO_DEVICE_NOT_FOUND 	= 1;	//재생장치를 찾을수없을때
	
	{
		/**
		 * 생성자 블록에서 초기화과정이 필요한 메서드를 호출합니다.
		 * @author 문현석
		 * 2021-08-10
		 */
		if(out2.getSource() == null)
			try {
				out2.createSource();
				saveVolumeVar = (0.86f * -30) + 6.f;
				out2.setLineGain((int)saveVolumeVar);
			} catch (JavaLayerException e) {}
	}
	
	/**
	 * 음악파일을 읽어올수있는 상태인지 확인합니다.
	 * @return boolean
	 */
	public boolean isInvalid() {
		return (decoder == null || out2 == null || playes == null || !out2.isOpen());
	}
	
	/**
	 * 스트림을 통해 샘플링하여 버퍼에 집어 넣습니다.
	 * @param path
	 * @return 버퍼에 생성가능하고 디코딩 가능한 파일일 경우 true값을 반환합니다.
	 */
	protected boolean GetPlayes(String path) {
		if(isInvalid()) return false;
		
		try {
			Header header;
			SampleBuffer pb;
			FileInputStream in = new FileInputStream(path);
			Bitstream bitstream = new Bitstream(in);
			if((header = bitstream.readFrame()) == null) return false;
			
			while(length < BUFFER_SIZE && header != null) {
				isDecoding = true;
				if(decoder == null)System.out.println("디코더 널임");
				pb = (SampleBuffer)decoder.decodeFrame(header, bitstream);
				playes.add(new Sample(pb.getBuffer(), pb.getBufferLength()));
				length++;
				bitstream.closeFrame();
				header = bitstream.readFrame();
				if(isDecodeSkip) { 
				// 외부에서 디코딩시간 도중 다른곡으로 전환할때 디코딩을 스킵하게 하는 flag
					close();
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 재생할 파일의 경로를 파라메터로 받아 디코딩 준비를 합니다.
	 * @author 문현석
	 * 2021-08-02
	 * @param path
	 * @return 디코딩준비에 성공하면 true값을 반환합니다.
	 */
	public synchronized boolean open(String path) {
		try {
			decoder = new Decoder();
//			out = FactoryRegistry.systemRegistry().createAudioDevice();
			if(out2 == null) out2 = new JavaSoundAudioDevice();
			if(out2.getSource() == null) out2.createSource();
			out2.setLineGain(saveVolumeVar);
			
			playes = new ArrayList<Sample>(BUFFER_SIZE);
			length = 0;
			out2.open(decoder);
			GetPlayes(path);
			isDecoding = false;
		}
		catch(Exception e) {
			decoder = null;
			out2 = null;
			playes = null;
			return false;
		}
		return true;
	}
	
	// 재생컨트롤러를 사용하지 않을때 쓰레드 종료
	// 사운드디바이스 종료
	public void closeThread() {
		isRun = false;
	}
	
	/**
	 * 쓰레드를 새로 할당받고 활성화 시킵니다.
	 * @author 문현석
	 * 2021-08-02
	 */
	public void start() {
		synchronized(this) {
			if(this.thread == null) {
				this.thread = new Thread(this);
			}
			this.thread.start();
		}
	}
	
	/**play메서드만 재생시키는 쓰레드입니다.
	 * isRun 값이 false가 됐을때 실행을 종료합니다.
	 * @author 문현석
	 * 2021-08-05
	 */
	@Override
	public void run() {
		while(thread.isAlive()) {
			if(!isRun) break;
			play();
		}
		close();
		this.state = INIT;
		this.isRun = true;
		this.thread = null;
	}
	
	/**
	 * 디코딩한 데이터를 다시 재생시키는 메서드입니다.
	 * 재생이 끝나면 close()메서드를 호출합니다.
	 * @author 문현석
	 * 2021-08-02
	 */
	public synchronized void play() {
		//재생장치(오디오 디바이스에 문제발생)오류 일때 재생안하고.
		//재생 쓰레드를 종료 시킴
		if(state == STARTED && isInvalid()) { 
//			messageState = AUDIO_DEVICE_NOT_FOUND;
//			state = INIT;
//			closeThread();
			return;
		}
		try {
			//현재 재생중이던 샘플링 인덱스는 값이 초기화 되지 않고 다시재생때 쓰입니다.
			while(samplingIndex < length){ 
				
				// 쓰레드를 멈출때 or 곡을 스킵할때 빠져나갑니다.
				if(!isRun || isSkip) return; 
				
				//재생중 일시정지상태거나 파일로드 초기상태일땐 재생하지 않음.
				if(state == SUSPENDED || state == INIT) {
					return; 
				}
				//정지 상태일땐 샘플링인덱스를  0으로 만들고 재생하지 않음.
				else if(state == STOPPED) {
					samplingIndex = 0;
					return;
				}
				//외부에서 종료 스테이터스 값을 주었을때 재생을 끝냅니다.
				else if(state == FINISHED) break;
				
				// 디코딩한 데이터를 사운드 디바이스로 읽어 재생합니다.
				out2.writeImpl(playes.get(samplingIndex).GetBuffer(), 0, playes.get(samplingIndex).GetLength());
				samplingIndex++;
			}
		}
		catch(JavaLayerException e) {}
		state = FINISHED;
		close();
	}
	
	/**
	 * 플레이중인 음악 재생시간을 밀리초 단위로 반환합니다.
	 * @author 문현석
	 * 2021-08-10
	 * @return
	 */
	public int getPosition() {
		if(out2 != null) {
			return out2.getPosition();			
		}
		else {
			return 0;
		}
	}
	
	/**
	 * 오디오 디바이스가 할당되어있고 mp3파일의 디코딩 상태가 완료 되었을때 호출하면
	 * 오디오 디바이스를 종료시키고 디코딩관련 멤버를 초기화 시킵니다
	 * @author 문현석
	 * 2021-08-02
	 */
	public synchronized void close() {
		
		if(out2 != null) {
			if(out2.getSource() != null) {
				saveVolumeVar = out2.getVolume();
				out2.closeImpl();
			}
			out2.close();
			out2 = null;
		}
		
		isSkip			= false;
		isDecodeSkip	= false;
		isDecoding		= false;
		samplingIndex 	= 0;
		length 			= 0;
		playes 			= null;
		decoder 		= null;
	}
	
	/**
	 * 파라메터로 받은 값으로 볼륨 수치를 초기화 합니다.
	 * @param volume
	 */
	public void initVolume(float volume) {
		out2.setLineGain(volume);
	}
	
	/**
	 * 파라메터로 받을 값으로 볼륨을 조절합니다.
	 * 범위 한계치를 넘어가지 않게 조건문을 걸어줍니다.
	 * @author 문현석
	 * 2021-08-10
	 * @param gain
	 * @return
	 */
	public void setVolume(float volume) {
		if(out2 != null && out2.getSource() != null) {
			float cleanValue = (out2.getVolume() + volume);
			if(cleanValue < -80.f) cleanValue = 80.f;
			else if(cleanValue > 6.f) cleanValue = 6.f;
			out2.setVolume(cleanValue);
		}
	}
	
	/**
	 * 볼륨의 현재 값(-80.f ~ 6.f 사이)를 백분율계산 한 값으로 반환
	 * @author 문현석
	 * @return volume.f;
	 */
	public int getVolume() {
		if(out2 != null && out2.getSource() != null) { 
			return (int)((out2.getVolume() + 80.f) / 86.f * 100);
		}
		else {
			return -1;
		}
	}
	
	public void message() {
		switch (messageState) {
		case AUDIO_DEVICE_NOT_FOUND:
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@오디오 재생 장치를 찾을수 없습니다.@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			messageState = INIT;
			break;
		default:
			break;
		}
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public boolean isSkip() {
		return isSkip;
	}

	public void setSkip(boolean isSkip) {
		this.isSkip = isSkip;
	}
	
	public boolean isDecoding() {
		return isDecoding;
	}

	public void setDecoding(boolean isDecoding) {
		this.isDecoding = isDecoding;
	}
	
	public boolean isDecodeSkip() {
		return isDecodeSkip;
	}

	public void setDecodeSkip(boolean isDecodeSkip) {
		this.isDecodeSkip = isDecodeSkip;
	}
}
