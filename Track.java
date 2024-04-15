import java.io.File;

import javax.swing.ImageIcon;

public class Track{

  private String title;
  private String artist;
  private int duration;
  private int rating;
  private int listens;
  private ImageIcon icon;
  private File file;
  
  public Track(String title, String artist, int duration, int rating, int listens, File file){
    this.title = title;
    this.artist = artist;
    this.duration = duration;
    this.rating = rating;
    this.listens = listens;
    this.file = file;
  }

  public String getTitle(){
    return this.title;
  }

  public String getArtist(){
    return this.artist;
  }

  public int getDuration(){
    return this.duration;
  }

  public int getRating(){
    return this.rating;
  }

  public int getListens(){
    return this.listens;
  }

  public File getFile(){
    return this.file;
  }

  public ImageIcon getIcon(){
    return this.icon;
  }
}
