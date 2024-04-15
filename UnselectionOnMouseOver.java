import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class UnselectionOnMouseOver extends EventListener implements MouseMotionListener{

  public UnselectionOnMouseOver(Component component){
    super(component);
  }

  public void addListenerToComponent(){
    this.component.addMouseMotionListener(this);
  }

  public void mouseDragged(MouseEvent event){}

  public void mouseMoved(MouseEvent event){
    this.tryHideUnselect();
  }


  public void tryHideUnselect(){
    for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
      if(listener instanceof SelectionOnMouseOver && listener.getComponent() != this.component){
        ((SelectionOnMouseOver) listener).unselect();
      }
    }
  }
}
