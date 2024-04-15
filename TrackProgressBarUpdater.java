import javax.swing.JSlider;

public class TrackProgressBarUpdater extends Thread{

  private JSlider trackProgressBar;
  private AudioPlayer audioPlayer;
  private long lastUpdate = 0;
  private int position = -1;
  private boolean ignore = false;

  public TrackProgressBarUpdater(JSlider trackProgressBar, AudioPlayer audioPlayer){
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
        this.ignore = true;
        if(this.position != -1 && System.currentTimeMillis() - this.lastUpdate > 100){
          audioPlayer.setPotision(this.position);
          this.position = -1;
        }else{
          this.trackProgressBar.setValue(audioPlayer.getProgress());
        }
        Thread.sleep(100);
      }catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }

  public void updateProgressBarPosition(int position){
    this.position = position;
    this.lastUpdate = System.currentTimeMillis();
  }
}