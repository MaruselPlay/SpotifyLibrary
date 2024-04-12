import java.util.List;

public class Playlist{

  private List<Track> tracks;
  private String name;
  
  public Playlist(String name/*, List<Track> tracks*/){
    this.name = name;
    //this.tracks = tracks;
  }

  public String getName(){
    return this.name;
  }

  public List<Track> getTracks(){
    return this.tracks;
  }

  public void addTrack(Track track){
    this.tracks.add(track);
  }

  public void removeTrack(Track track){
    this.tracks.remove(track);
  }

  public void removeTrack(int index){
    this.tracks.remove(index);
  }
}
