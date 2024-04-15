import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


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
  }
}
