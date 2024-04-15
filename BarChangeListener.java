import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BarChangeListener extends EventListener implements ChangeListener{

  private TrackProgressBarUpdater thread;

  public BarChangeListener(JSlider component){
    super(component);

    this.thread = new TrackProgressBarUpdater((JSlider) component, GUI.getInstance().getAudioPlayer());
    this.thread.start();
  }

  public void addListenerToComponent(){
    ((JSlider) this.component).addChangeListener(this);
  }

  public void stateChanged(ChangeEvent event){
    if(this.thread.ignore()){
      return;
    }
    this.thread.updateProgressBarPosition(((JSlider) this.component).getValue());
  }
}
