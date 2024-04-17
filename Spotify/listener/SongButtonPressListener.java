package listener;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import graphics.GUI;
import spotify.AudioPlayer;
import spotify.Track;

public class SongButtonPressListener extends EventListener implements ActionListener{

  private Track track;

  public SongButtonPressListener(JButton component, Track track){
    super(component);

    this.track = track;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public void actionPerformed(ActionEvent event){
    GUI.getInstance().getAudioPlayer().play(this.track.getFile().getPath());
    for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
      if(listener instanceof PlayButtonPressListener){
        ((PlayButtonPressListener) listener).playIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/stop.png")));
        ((PlayButtonPressListener) listener).currentStatus = AudioPlayer.STATUS_PLAYING;
      }
    }
  }
}
