import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ButtonChooseFilePressListener extends EventListener implements ActionListener{

  private File file;
  private JTextField title;
  private JTextField artist;
  private JTextField duration;
  private JTextField rating;
  private JTextField listens;
  private boolean isFileSelected = false;

  public boolean errorOccured = false;
  public JLabel errorLabel;

  public ButtonChooseFilePressListener(
    JButton component,
    JTextField title,
    JTextField artist,
    JTextField duration,
    JTextField rating,
    JTextField listens,
    JLabel errorLabel
  ){
    super(component);

    this.title = title;
    this.artist = artist;
    this.duration = duration;
    this.rating = rating;
    this.listens = listens;
    this.errorLabel = errorLabel;
  }

  public void addListenerToComponent(){
    ((JButton) this.component).addActionListener(this);
  }

  public boolean isFileSelected(){
    return this.isFileSelected;
  }

  public File getFile(){
    return this.file;
  }

  public void actionPerformed(ActionEvent event){
    JFileChooser fileSelector = new JFileChooser(FileSystemView.getFileSystemView());
    fileSelector.setDialogTitle("Select Mp3");
    fileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileSelector.setFileFilter(new FileNameExtensionFilter(".wav","wav"));
    if(fileSelector.showOpenDialog(this.component) == JFileChooser.APPROVE_OPTION){
      File file = fileSelector.getSelectedFile();
      this.trySelectFile(file);
      if(this.isFileSelected){
        this.setLabelText(file.getName() + " selected", new Color(120, 120, 120));
      }
    }
  }

  public void trySelectFile(File file){
    try{
      AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
      if(format.getType().getExtension() != "wav"){
        this.setLabelText("Unsupported file format", Color.RED);
        return;
      }

      try{
        if(Files.copy(Paths.get(file.getPath()), Paths.get(System.getProperty("user.dir") + "\\" + file.getName())) == null){
          this.setLabelText("Failed in loading the file", Color.RED);
          return;
        }
      }catch(FileAlreadyExistsException exception){

      }

      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      this.duration.setText(((int) (clip.getMicrosecondLength() / 1000 / 1000)) + "");

      this.file = new File(System.getProperty("user.dir") + "\\" + file.getName());
      this.isFileSelected = true;
    }catch(Exception exception){
      this.setLabelText("Unsupported file format", Color.RED);
      exception.printStackTrace();
    }
  }

  public void setLabelText(String error, Color color){
    this.errorLabel.setForeground(color);
    this.errorLabel.setText(error);
    GUI.getInstance().showUpdated();
  }
}
