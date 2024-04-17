package listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import graphics.GUI;

public class UnselectionOnMouseOverListener extends EventListener implements MouseMotionListener{

  public UnselectionOnMouseOverListener(Component component){
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
      if(listener instanceof SelectionOnMouseOverListener && listener.getComponent() != this.component){
        ((SelectionOnMouseOverListener) listener).unselect();
      }
    }
  }
}
