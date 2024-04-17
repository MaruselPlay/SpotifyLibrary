package listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import graphics.GUI;

public class AddLabelOnMouseOverListener extends EventListener implements MouseMotionListener{

  private String mouseOverLabelAdd;
  private String mouseOverLabelBack;
  private JLabel currentLabel;
  private JLabel buttonText;

  public AddLabelOnMouseOverListener(String mouseOverLabelAdd, String mouseOverLabelBack, JLabel buttonText, Component component){
    super(component);

    this.mouseOverLabelAdd = mouseOverLabelAdd;
    this.mouseOverLabelBack = mouseOverLabelBack;
    this.buttonText = buttonText;
    this.currentLabel = new JLabel(this.mouseOverLabelAdd, SwingConstants.CENTER);
    this.currentLabel.setFont(new Font("Arial", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.currentLabel.setOpaque(true);
    this.currentLabel.setForeground(Color.WHITE);
    this.currentLabel.setBackground(Color.BLACK);
    this.currentLabel.setVisible(false);
    GUI.getInstance().getMainPanel().add(this.currentLabel);
  }

  public void addListenerToComponent(){
    this.component.addMouseMotionListener(this);
  }

  public void mouseDragged(MouseEvent event){}

  public void mouseMoved(MouseEvent event){
    this.showLabel(event.getX(), event.getY());
  }

  public void showLabel(int x, int y){
    if(this.currentLabel.isVisible()){
      return;
    }
    if(this.component.getHeight() - y < this.currentLabel.getHeight()){
      y -= this.currentLabel.getHeight();
    }
    if(this.buttonText.getText().equals("Go back")){
      this.currentLabel.setText(this.mouseOverLabelBack);
    }else{
      this.currentLabel.setText(this.mouseOverLabelAdd);
    }
    this.currentLabel.setLocation((int) (GUI.getInstance().getMainPanel().getWidth() - GUI.getInstance().getRightPanel().getWidth() * 0.67), GUI.getInstance().getMainPanel().getHeight() + y - GUI.getInstance().getRightPanel().getHeight());
    this.currentLabel.setSize((int) this.currentLabel.getPreferredSize().getWidth() + 15, (int) this.currentLabel.getPreferredSize().getHeight() + 15);
    this.currentLabel.setVisible(true);
    GUI.getInstance().showUpdated();
  }

  public void hideLabel(){
    if(this.currentLabel.isVisible()){
      this.currentLabel.setVisible(false);
      GUI.getInstance().showUpdated();
    }
  }
}
