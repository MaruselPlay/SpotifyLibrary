import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI{

  private JFrame frame = new JFrame("SpotifyLibrary");
  private JPanel mainPanel = new JPanel(null);
  private JLabel label = new JLabel();
  
  public static void main(String[] args){
    GUI instance = new GUI();
    instance.start();
  }

  public void setLocationRelativeTo(Component componet, Dimension relativeTo){
    Dimension componentSize = componet.getSize();
    componet.setLocation((int) relativeTo.getWidth() / 2 - (int) componentSize.getWidth() / 2, (int) relativeTo.getHeight() / 2 - (int) componentSize.getHeight() / 2);
  }
  
  public void setLocationRelativeTo(Component componet, Dimension relativeTo, int offsetX, int offsetY){
    Dimension componentSize = componet.getSize();
    componet.setLocation((int) relativeTo.getWidth() / 2 - (int) componentSize.getWidth() / 2 - offsetX, (int) relativeTo.getHeight() / 2 - (int) componentSize.getHeight() / 2 - offsetY);
  }

  public void start(){
    this.init();
    this.frame.setVisible(true);
  }

  public void init(){
    this.frame.setSize(600, 400);
    this.setLocationRelativeTo(this.frame, Toolkit.getDefaultToolkit().getScreenSize());
    //this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.frame.setLayout(new GridLayout(1,1));
    this.mainPanel.setBackground(Color.black);
    
    UIDefaults uiDefaults = UIManager.getDefaults();
    //uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.green));
    uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.green));
    //this.frame.setDefaultLookAndFeelDecorated(true);

    this.frame.setContentPane(this.mainPanel);
  }
}