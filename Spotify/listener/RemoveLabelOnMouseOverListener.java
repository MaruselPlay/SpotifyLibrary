package listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import graphics.GUI;

public class RemoveLabelOnMouseOverListener extends EventListener implements MouseMotionListener{

  public RemoveLabelOnMouseOverListener(Component component){
    super(component);
  }

  public void addListenerToComponent(){
    this.component.addMouseMotionListener(this);
  }

  public void mouseDragged(MouseEvent event){}

  public void mouseMoved(MouseEvent event){
    this.tryHideAnyLabels();
  }


  public void tryHideAnyLabels(){
    for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
      if(listener instanceof AddLabelOnMouseOverListener && listener.getComponent() != this.component){
        ((AddLabelOnMouseOverListener) listener).hideLabel();
      }
    }
  }
}
