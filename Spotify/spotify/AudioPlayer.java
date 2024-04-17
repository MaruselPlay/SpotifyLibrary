package spotify;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer{
  
  public static final int STATUS_PLAYING = 1;
  public static final int STATUS_PAUSED = 2;
  public static final int STATUS_STOPPED = 3;

	private Long currentFrame;
	private Clip currentTrack;
	private int currentStatus = AudioPlayer.STATUS_STOPPED;
	private String curentFile;

  public int getStatus(){
    return this.currentStatus;
  }

  public int getProgress(){
    if(this.currentStatus == AudioPlayer.STATUS_STOPPED || this.currentTrack == null){
      return 0;
    }
    return (int) (this.currentTrack.getMicrosecondPosition() / (this.currentTrack.getMicrosecondLength() + 0.001) * 1000);
  }

  public Clip getTrack(){
    return this.currentTrack;
  }

	public void play(String filePath){
    this.curentFile = filePath;
    if(this.currentStatus == AudioPlayer.STATUS_PLAYING){
      this.stop();
    }
    this.currentStatus = AudioPlayer.STATUS_PLAYING;
    this.resetAudioStream();
    this.currentTrack.start();
	}

	public void pause(){
		if(this.currentStatus != AudioPlayer.STATUS_PLAYING){
			return;
		}
		this.currentFrame = this.currentTrack.getMicrosecondPosition();
		this.currentTrack.stop();
		this.currentStatus = AudioPlayer.STATUS_PAUSED;
	}

	public void resume(){
		if(this.currentStatus != AudioPlayer.STATUS_PAUSED){
			return;
		}
		this.resetAudioStream();
		this.currentTrack.setMicrosecondPosition(this.currentFrame);
		this.currentTrack.start();
    this.currentStatus = AudioPlayer.STATUS_PLAYING;
	}

	public void restart(){
		this.currentTrack.stop();
		this.currentTrack.close();
		this.resetAudioStream();
		this.currentFrame = 0L;
		this.currentTrack.setMicrosecondPosition(0);
		this.currentTrack.start();
	}
	
	public void stop(){
    if(this.currentStatus == AudioPlayer.STATUS_STOPPED){
			return;
		}
		this.currentFrame = 0L;
		this.currentTrack.stop();
		this.currentTrack.close();
    this.currentStatus = AudioPlayer.STATUS_STOPPED;
	}
	
	public void setPotision(float position){
    if(position > 1000 || position < 0 || this.currentStatus == AudioPlayer.STATUS_STOPPED || this.currentTrack == null){
      return;
    }

    this.currentTrack.stop();
    this.currentTrack.close();
    this.resetAudioStream();

    /**
     * Stangely if there is no delay, the whole program freezes
     */
    try{
      Thread.sleep(50);
    }catch(Exception exception){}

    this.currentFrame = (long) (position / 1000 * this.currentTrack.getMicrosecondLength());
    this.currentTrack.setMicrosecondPosition((long) (position / 1000 * this.currentTrack.getMicrosecondLength()));
    this.currentTrack.start();
	}
	
	public void resetAudioStream(){
    try{
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(this.curentFile).getAbsoluteFile());

      this.currentTrack = AudioSystem.getClip();
      this.currentTrack.open(audioInputStream);
    }catch(Exception exception){
			System.out.println("Error occured while playing the sound");
			exception.printStackTrace();
		}
	}
} 
