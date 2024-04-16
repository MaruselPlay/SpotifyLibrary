import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Spotify{

  private final static String storageFile = "trackStorage.txt";
  private static Spotify instance;

  private GUI gui = new GUI(this);
  private ArrayList<Track> tracks = new ArrayList<Track>();
  
  public static void main(String[] args){
    Spotify instance = new Spotify();
    Spotify.instance = instance;
    instance.start();
  }

  public static Spotify getInstance(){
    return Spotify.instance;
  }

  public void start(){
    this.readTracksFromFile();
    this.gui.init();
  }

  public void addTrack(Track track){
    this.tracks.add(track);
    GUI.getInstance().trackList = this.tracks;
  }

  public ArrayList<Track> getTracks(){
    return this.tracks;
  }

  public void saveTracksToFile(){
    try{
      FileWriter fileWriter = new FileWriter(Spotify.storageFile);
      for(int i = 0; i < this.tracks.size(); i++){
        fileWriter.write(this.tracks.get(i).toString() + "\n");
      }
      fileWriter.close();
    }catch(Exception exception){
      System.out.println("An error happend while saving to file: ");
      exception.printStackTrace();
    }
  }

  public void readTracksFromFile(){
    try{
      BufferedReader fileReader = new BufferedReader(new FileReader(Spotify.storageFile));
      String line;
      while((line = fileReader.readLine()) != null){
        this.addTrack(Track.fromString(line));
      }
      fileReader.close();
    }catch(Exception exception){}
  }
}
