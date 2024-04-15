import java.util.List;
import java.util.ArrayList;

public class Spotify{

  private static Spotify instance;

  private GUI gui = new GUI(this);
  private List<Track> tracks = new ArrayList<Track>();
  
  public static void main(String[] args){
    Spotify instance = new Spotify();
    Spotify.instance = instance;
    instance.start();
  }

  public static Spotify getInstance(){
    return Spotify.instance;
  }

  public void start(){
    this.gui.init();
  }

  public void addTrack(Track track){
    this.tracks.add(track);
  }

  public List<Track> getTracks(){
    return this.tracks;
  }
}
