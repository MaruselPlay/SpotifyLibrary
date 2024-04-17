package listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import graphics.GUI;
import spotify.Spotify;
import spotify.Track;

public class ButtonSubmitPressListener extends EventListener implements ActionListener{

  private JTextField title;
  private JTextField artist;
  private JTextField duration;
  private JTextField rating;
  private JTextField listens;
  private ButtonChooseFilePressListener fileChooseListener;
  

  public ButtonSubmitPressListener(
    JButton component,
    JTextField title,
    JTextField artist,
    JTextField duration,
    JTextField rating,
    JTextField listens,
    ButtonChooseFilePressListener fileChooseListener
  ){
    super(component);

    this.title = title;
    this.artist = artist;
    this.duration = duration;
    this.rating = rating;
    this.listens = listens;
    this.fileChooseListener = fileChooseListener;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public boolean checkTitle(){
    if(this.title.getText().length() == 0){
      this.error(this.title, "Can't be empty");
      return false;
    }
    return true;
  }

  public boolean checkArtist(){
    if(this.artist.getText().length() == 0){
      this.error(this.artist, "Can't be empty");
      return false;
    }
    return true;
  }

  public boolean checkDuration(){
    if(this.duration.getText().length() == 0){
      this.error(this.duration, "Can't be empty");
      return false;
    }

    int duration = 0;
    try{
      duration = Integer.parseInt(this.duration.getText());
    }catch(Exception exception){
      this.error(this.duration, "Must be an integer");
      return false;
    }
    if(duration <= 0){
      this.error(this.duration, "Must be greater than 0");
      return false;
    }
    return true;
  }

  public boolean checkRating(){
    if(this.rating.getText().length() == 0){
      this.error(this.rating, "Can't be empty");
      return false;
    }

    int rating = 0;
    try{
      rating = Integer.parseInt(this.rating.getText());
    }catch(Exception exception){
      this.error(this.rating, "Must be an integer");
      return false;
    }
    if(rating < 1 || rating > 5){
      this.error(this.rating, "Must be in range 1-5");
      return false;
    }
    return true;
  }

  public boolean checkListens(){
    if(this.listens.getText().length() == 0){
      this.error(this.listens, "Can't be empty");
      return false;
    }

    int listens = 1;
    try{
      listens = Integer.parseInt(this.listens.getText());
    }catch(Exception exception){
      this.error(this.listens, "Must be an integer");
      return false;
    }
    if(listens < 0){
      this.error(this.listens, "Must be positive");
      return false;
    }
    return true;
  }

  public boolean checkFile(){
    return this.fileChooseListener.isFileSelected();
  }

  public void actionPerformed(ActionEvent event){
    boolean allChecksPassed = true;
    if(!this.checkTitle()){
      allChecksPassed = false;
    }
    if(!this.checkArtist()){
      allChecksPassed = false;
    }
    if(!this.checkDuration()){
      allChecksPassed = false;
    }
    if(!this.checkRating()){
      allChecksPassed = false;
    }
    if(!this.checkListens()){
      allChecksPassed = false;
    }
    if(!this.checkFile()){
      allChecksPassed = false;
      for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
        if(listener instanceof ButtonChooseFilePressListener){
          if(!((ButtonChooseFilePressListener) listener).errorOccured){
            ((ButtonChooseFilePressListener) listener).setLabelText("File not selected!", Color.RED);
          }
        }
      }
    }
    if(allChecksPassed){
      Spotify.getInstance().addTrack(new Track(this.title.getText(), this.artist.getText(), Integer.parseInt(this.duration.getText()), Integer.parseInt(this.rating.getText()), Integer.parseInt(this.listens.getText()), this.fileChooseListener.getFile()));
      this.title.setText(null);
      this.artist.setText(null);
      this.duration.setText(null);
      this.rating.setText(null);
      this.listens.setText(null);

      for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
        if(listener instanceof ButtonChooseFilePressListener){
          ((ButtonChooseFilePressListener) listener).resetFile();
        }else if(listener instanceof ButtonAddTrackPressListener){
          /*
           * NOTE: calling showMainPanel in ButtonAddTrackPressListener makes an error for some reason
           */
          ((ButtonAddTrackPressListener) listener).mainPanel = true;
          ((ButtonAddTrackPressListener) listener).buttonText.setText("Add new");
          System.out.println(1);
        }
      }
      
      GUI.getInstance().hideAddTrackScreen();
      GUI.getInstance().showMainScreen();
      GUI.getInstance().showTrackPanel();
      GUI.getInstance().showUpdated();
    }
  }

  public void error(JTextField field, String error){
    for(EventListener listener : GUI.getInstance().getEventManager().getListeners()){
      if(listener instanceof TextFieldChangeListener && listener.getComponent() == field){
        field.setText(null);
        ((TextFieldChangeListener) listener).getLabel().setForeground(Color.RED);
        ((TextFieldChangeListener) listener).getLabel().setText(error);
        GUI.getInstance().showUpdated();
        break;
      }
    }
  }
}
