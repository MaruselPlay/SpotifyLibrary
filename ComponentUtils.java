import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ComponentUtils{

  public static void setSizeRelativeTo(Component componet, Dimension relativeTo, double scale){
    componet.setSize((int) (relativeTo.getWidth() * (scale / 100)), (int) (relativeTo.getHeight() * (scale / 100)));
  }

  public static void setPreferredSizeRelativeTo(Component componet, Dimension relativeTo, double scale){
    componet.setPreferredSize(new Dimension((int) (relativeTo.getWidth() * (scale / 100)), (int) (relativeTo.getHeight() * (scale / 100))));
  }

  public static void setLocationRelativeTo(Component componet, Dimension relativeTo){
    Dimension componentSize = componet.getSize();
    componet.setLocation((int) relativeTo.getWidth() / 2 - (int) componentSize.getWidth() / 2, (int) relativeTo.getHeight() / 2 - (int) componentSize.getHeight() / 2);
  }
  
  public static void setLocationRelativeTo(Component componet, Dimension relativeTo, int offsetX, int offsetY){
    Dimension componentSize = componet.getSize();
    componet.setLocation((int) relativeTo.getWidth() / 2 - (int) componentSize.getWidth() / 2 - offsetX, (int) relativeTo.getHeight() / 2 - (int) componentSize.getHeight() / 2 - offsetY);
  }

  public static JButton makeButton(String name){
    JButton button = new JButton(name);
    button.setForeground(Color.WHITE);
    button.setBackground(Color.BLACK);
    button.setPreferredSize(new Dimension((int) (GUI.getInstance().getWidth() / 8), (GUI.getInstance().getHeight() / 15)));
    button.setFocusPainted(false);
    button.setFocusable(false);
    button.setBorderPainted(false);
    return button;
  }

  public static JButton makeButton(String name, Font font){
    JButton button = ComponentUtils.makeButton(name);
    button.setFont(font);
    return button;
  }

  public static JButton makeButton(String name, Dimension size){
    JButton button = ComponentUtils.makeButton(name);
    button.setPreferredSize(size);
    return button;
  }

  public static JButton makeButton(String name, Font font, Dimension size){
    JButton button = ComponentUtils.makeButton(name, font);
    button.setPreferredSize(size);
    return button;
  }
}
