package thread;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JSlider;

import graphics.GUI;
import listener.EventListener;
import listener.PlayButtonPressListener;
import spotify.AudioPlayer;

public class TrackProgressBarUpdaterThread extends Thread{

  private JSlider trackProgressBar;
  private AudioPlayer audioPlayer;
  private long lastUpdate = 0;
  private int position = -1;
  private boolean ignore = false;

  public TrackProgressBarUpdaterThread(JSlider trackProgressBar, AudioPlayer audioPlayer){
    this.trackProgressBar = trackProgressBar;
    this.audioPlayer = audioPlayer;
  }

  public boolean ignore(){
    if(this.ignore){
      this.ignore = false;
      return true;
    }
    return false;
  }

  public void run(){
    while(true){
      try{
        if(this.audioPlayer.getStatus() != AudioPlayer.STATUS_PLAYING || this.audioPlayer.getTrack() == null){
          Thread.sleep(200);
          continue;
        }

        if(this.audioPlayer.getTrack().getMicrosecondPosition() + 10 > this.audioPlayer.getTrack().getMicrosecondLength()){
          System.out.println(1);
          this.audioPlayer.restart();
          this.audioPlayer.pause();
          this.trackProgressBar.setValue(0);
          for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
            if(listener instanceof PlayButtonPressListener){
              ((PlayButtonPressListener) listener).playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/play.png")));
              ((PlayButtonPressListener) listener).currentStatus = AudioPlayer.STATUS_PAUSED;
            }
          }
          continue;
        }
        
        this.ignore = true;
        if(this.position != -1 && System.currentTimeMillis() - this.lastUpdate > 10){
          this.audioPlayer.setPotision(this.position);
          this.position = -1;
        }else{
          this.trackProgressBar.setValue(this.audioPlayer.getProgress());
        }
        Thread.sleep(100);
      }catch(InterruptedException exception){
        exception.printStackTrace();
      }
    }
  }

  public void updateProgressBarPosition(int position){
    this.position = position;
    this.lastUpdate = System.currentTimeMillis();
  }
}