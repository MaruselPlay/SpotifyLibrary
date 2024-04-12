import java.awt.Component;

import javax.swing.JPanel;

public class CustomComponent{

  private Component component;
  private JPanel panel;
  
  public CustomComponent(Component component, JPanel panel){
    this.component = component;
    this.panel = panel;
  }

  public Component getActualComponent(){
    return this.component;
  }

  public Component getComponent(){
    return this.panel;
  }
}
