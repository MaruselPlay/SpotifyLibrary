import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class SelectionOnMouseOver extends EventListener implements MouseMotionListener{

  private Color originalColour;
  private boolean selected = false;

  public SelectionOnMouseOver(Component component){
    super(component);
    this.originalColour = component.getBackground();
  }

  public void addListenerToComponent(){
    this.component.addMouseMotionListener(this);
  }

  public void mouseDragged(MouseEvent event){}

  public void mouseMoved(MouseEvent event){
    this.select();
  }

  public void select(){
    if(!this.selected){
      this.component.setBackground(new Color(80, 80, 80));
      GUI.getInstance().showUpdated();
      this.selected = true;
    }
  }

  public void unselect(){
    if(this.selected){
      this.component.setBackground(this.originalColour);
      GUI.getInstance().showUpdated();
      this.selected = false;
    }
  }
}
