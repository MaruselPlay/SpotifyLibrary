import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class PlayButtonPressListener extends EventListener implements ActionListener{

  private int currentStatus = AudioPlayer.STATUS_STOPPED;
  private JLabel playIcon;

  public PlayButtonPressListener(JButton component, JLabel playIcon){
    super(component);

    this.playIcon = playIcon;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public void actionPerformed(ActionEvent event){
    if(this.currentStatus == AudioPlayer.STATUS_STOPPED){
      this.playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("stop.png"))));
      GUI.getInstance().getAudioPlayer().play("C:\\Server\\SpotifyLibrary\\song.wav");
      this.currentStatus = AudioPlayer.STATUS_PLAYING;
    }else if(this.currentStatus == AudioPlayer.STATUS_PAUSED){
      this.playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("stop.png"))));
      GUI.getInstance().getAudioPlayer().resume();
      this.currentStatus = AudioPlayer.STATUS_PLAYING;
    }else if(this.currentStatus == AudioPlayer.STATUS_PLAYING){
      this.playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("play.png"))));
      GUI.getInstance().getAudioPlayer().pause();
      this.currentStatus = AudioPlayer.STATUS_PAUSED;
    }
  }
}
