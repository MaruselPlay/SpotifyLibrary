import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonAddTrackPressListener extends EventListener implements ActionListener{

  private boolean mainPanel = true;
  private JLabel buttonText;

  public ButtonAddTrackPressListener(JButton component, JLabel buttonText){
    super(component);
    this.buttonText = buttonText;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public void actionPerformed(ActionEvent event){
    if(this.mainPanel){
      this.mainPanel = false;
      this.buttonText.setText("Go back");
      GUI.getInstance().hideMainScreen();
      GUI.getInstance().showAddTrackScreen();
      GUI.getInstance().hideTrackPanel();
      GUI.getInstance().showUpdated();
    }else{
      this.mainPanel = true;
      this.buttonText.setText("Add new");
      GUI.getInstance().hideAddTrackScreen();
      GUI.getInstance().showMainScreen();
      GUI.getInstance().showTrackPanel();
      GUI.getInstance().showUpdated();
    }
  }
}
