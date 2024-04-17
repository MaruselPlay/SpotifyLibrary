package listener;

import java.util.ArrayList;
import java.util.List;

public class EventManager{
  
  private List<EventListener> listeners = new ArrayList<EventListener>();

  public void addListener(EventListener listener){
    this.listeners.add(listener);
  }

  public List<EventListener> getListeners(){
    return this.listeners;
  }
}
