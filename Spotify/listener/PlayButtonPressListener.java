package listener;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import spotify.AudioPlayer;
import graphics.GUI;

public class PlayButtonPressListener extends EventListener implements ActionListener{

  public int currentStatus = AudioPlayer.STATUS_STOPPED;
  public  JLabel playIcon;

  public PlayButtonPressListener(JButton component, JLabel playIcon){
    super(component);

    this.playIcon = playIcon;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public void actionPerformed(ActionEvent event){
    if(this.currentStatus == AudioPlayer.STATUS_PAUSED){
      this.playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\resources\\stop.png")));
      GUI.getInstance().getAudioPlayer().resume();
      this.currentStatus = AudioPlayer.STATUS_PLAYING;
    }else if(this.currentStatus == AudioPlayer.STATUS_PLAYING){
      this.playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\resources\\play.png")));
      GUI.getInstance().getAudioPlayer().pause();
      this.currentStatus = AudioPlayer.STATUS_PAUSED;
    }
  }
}
