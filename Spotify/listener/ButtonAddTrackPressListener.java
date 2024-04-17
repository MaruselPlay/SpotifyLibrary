package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import graphics.GUI;

public class ButtonAddTrackPressListener extends EventListener implements ActionListener{

  public boolean mainPanel = true;
  public JLabel buttonText;

  public ButtonAddTrackPressListener(JButton component, JLabel buttonText){
    super(component);
    this.buttonText = buttonText;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public void actionPerformed(ActionEvent event){
    if(this.mainPanel){
      this.showNewTrackAddPanel();
    }else{
      this.showMainPanel();
    }
  }

  public void showMainPanel(){
    this.mainPanel = true;
    this.buttonText.setText("Add new");
    GUI.getInstance().hideAddTrackScreen();
    GUI.getInstance().showMainScreen();
    GUI.getInstance().showTrackPanel();
    GUI.getInstance().showUpdated();
  }

  public void showNewTrackAddPanel(){
    this.mainPanel = false;
    this.buttonText.setText("Go back");
    GUI.getInstance().hideMainScreen();
    GUI.getInstance().showAddTrackScreen();
    GUI.getInstance().hideTrackPanel();
    GUI.getInstance().showUpdated();
  }
}
