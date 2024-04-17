package spotify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import graphics.GUI;

public class Spotify{

  private final static String storageFile = System.getProperty("user.dir") + "\\trackStorage.txt";
  private static Spotify instance;

  private GUI gui = new GUI(this);
  private ArrayList<Track> tracks = new ArrayList<Track>();
  
  /**
   * The main function creates an instance of the Spotify class and starts it.
   */
  public static void main(String[] args){
    Spotify instance = new Spotify();
    Spotify.instance = instance;
    instance.start();
  }

  /**
   * The function returns an instance of the Spotify class.
   * 
   * @return An instance of the Spotify class is being returned.
   */
  public static Spotify getInstance(){
    return Spotify.instance;
  }

  /**
   * The `start` function reads tracks from a file and initializes the GUI.
   */
  public void start(){
    this.readTracksFromFile();
    this.gui.init();
  }

  /**
   * The `addTrack` method adds a track to a list and updates the track list in the GUI.
   * 
   * @param track The `addTrack` method takes a `Track` object as a parameter. This `Track` object
   * represents a track that you want to add to a list of tracks.
   */
  public void addTrack(Track track){
    this.tracks.add(track);
    GUI.getInstance().trackList = this.tracks;
  }

  /**
   * The getTracks function returns an ArrayList of Track objects.
   * 
   * @return An ArrayList of Track objects is being returned.
   */
  public ArrayList<Track> getTracks(){
    return this.tracks;
  }

  /**
   * The function `saveTracksToFile` writes the tracks stored in a list to a file in Java.
   */
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

  /**
   * The function reads tracks from a file and adds them to a collection.
   */
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
