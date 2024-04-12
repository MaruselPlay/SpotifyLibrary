import java.util.List;
import java.util.ArrayList;

public class Spotify{

  private GUI gui = new GUI(this);
  private List<Playlist> playlists = new ArrayList<Playlist>();
  private List<Track> tracks = new ArrayList<Track>();
  
  public static void main(String[] args){
    Spotify instance = new Spotify();
    instance.start();
  }

  public void start(){
    Playlist playlist;
    for(int i = 0; i < 100; i++){
      playlist = new Playlist("Playlist " + i);
      this.playlists.add(playlist);
    }
    this.gui.init();
  }

  public List<Playlist> getPlaylists(){
    return this.playlists;
  }

  public void addTrack(Track track){
    this.tracks.add(track);
  }

  public List<Track> getTracks(){
    return this.tracks;
  }
}
