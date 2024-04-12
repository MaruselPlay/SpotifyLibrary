import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RemoveLabelOnMouseOver extends EventListener implements MouseMotionListener{

  public RemoveLabelOnMouseOver(Component component){
    super(component);
  }

  public void addListenerToComponent(){
    this.component.addMouseMotionListener(this);
  }

  public void mouseDragged(MouseEvent event){}

  public void mouseMoved(MouseEvent event){
    this.tryHideAnyLabels(event.getX(), event.getY());
  }


  public void tryHideAnyLabels(int x, int y){
    for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
      if(listener instanceof AddLabelOnMouseOver){
        ((AddLabelOnMouseOver) listener).hideLabel();
      }
    }
  }
}
