import java.awt.Component;

public abstract class EventListener{

  protected Component component;

  public EventListener(Component component){
    this.component = component;
    this.addListenerToComponent();
  }

  public Component getComponent(){
    return this.component;
  }

  public abstract void addListenerToComponent();
}
