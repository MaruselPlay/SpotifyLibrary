package listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import graphics.GUI;
import spotify.Spotify;
import spotify.Track;

public class ScrollPanelListener extends EventListener implements ActionListener, DocumentListener{

  private JButton sortByTitle = new JButton("Title sort");
  private JButton sortByRating = new JButton("Rating sort");
  private JTextField searchBox = new JTextField(40);
  private boolean ascendingTitle = false;
  private boolean ascendingRating = false;

  public ScrollPanelListener(JPanel mainScrollPanel){
    super(mainScrollPanel);

    this.sortByTitle.setForeground(new Color(180, 180, 180));
    this.sortByTitle.setBackground(new Color(50, 50, 50));
    this.sortByTitle.setFocusPainted(false);
    this.sortByTitle.setFocusable(false);
    this.sortByTitle.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.sortByTitle.setBorderPainted(false);
    this.sortByTitle.setLocation(950, 60);
    this.sortByTitle.setSize(this.sortByTitle.getPreferredSize());
    GUI.getInstance().getEventManager().addListener(new SelectionOnMouseOverListener(this.sortByTitle));
    GUI.getInstance().addDefaultListeners(this.sortByTitle);
    mainScrollPanel.add(this.sortByTitle);

    this.sortByRating.setForeground(new Color(180, 180, 180));
    this.sortByRating.setBackground(new Color(50, 50, 50));
    this.sortByRating.setFocusPainted(false);
    this.sortByRating.setFocusable(false);
    this.sortByRating.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.sortByRating.setLocation(1100, 60);
    this.sortByRating.setBorderPainted(false);
    this.sortByRating.setSize(this.sortByRating.getPreferredSize());
    GUI.getInstance().getEventManager().addListener(new SelectionOnMouseOverListener(this.sortByRating));
    GUI.getInstance().addDefaultListeners(this.sortByRating);
    mainScrollPanel.add(this.sortByRating);

    this.searchBox.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.searchBox.setSize(this.searchBox.getPreferredSize());
    this.searchBox.setLocation(10, 63);
    this.searchBox.setForeground(new Color(200, 200, 200));
    this.searchBox.setOpaque(false);
    GUI.getInstance().getEventManager().addListener(new TextFieldChangeListener(this.searchBox, "Search", 3, 2));
    mainScrollPanel.add(this.searchBox);

    mainScrollPanel.setBackground(Color.BLACK);
    this.sortByTitle.addActionListener(this);
    this.sortByRating.addActionListener(this);
    this.searchBox.getDocument().addDocumentListener(this);
  }

  public void addListenerToComponent(){}

  public void changedUpdate(DocumentEvent event){
    this.performSearch();
  }

  public void removeUpdate(DocumentEvent event){
    this.performSearch();
  }

  public void insertUpdate(DocumentEvent event){
    this.performSearch();
  }

  public void performSearch(){
    ArrayList<Track> trackList = new ArrayList<Track>(Spotify.getInstance().getTracks());
    Track[] tracks = new Track[trackList.size()];
    String[] letters = this.searchBox.getText().split("");
    for(int i = 0; i < trackList.size(); i++){
      boolean letterExists = true;
      for(int j = 0; j < letters.length; j++){
        if(letters[j].equals("") && letters[j].equals(" ")){
          continue;
        }
        if(!trackList.get(i).getTitle().toLowerCase().contains(letters[j].toLowerCase()) && !trackList.get(i).getArtist().toLowerCase().contains(letters[j].toLowerCase())){
          letterExists = false;
        }
      }
      if(letterExists){
        tracks[i] = trackList.get(i);
      }
    }

    GUI.getInstance().trackList = new ArrayList<Track>(Arrays.asList(tracks).stream().filter(element -> element != null).collect(Collectors.toList()));
    GUI.getInstance().showMainScreen();
  }

  public void actionPerformed(ActionEvent event){
    if(event.getActionCommand().equals("Title sort")){
      this.sortByTitle();
      GUI.getInstance().hideMainScreen();
      GUI.getInstance().showMainScreen();
    }else if(event.getActionCommand().equals("Rating sort")){
      this.sortByRating();
      GUI.getInstance().hideMainScreen();
      GUI.getInstance().showMainScreen();
    }
  }

  public void sortByTitle(){
    ArrayList<Track> toSort = new ArrayList<Track>(GUI.getInstance().trackList);

    for(int i = 0; i < toSort.size() - 1; i++){
      for(int j = 0; j < toSort.size() - i - 1; j++){
        if(this.ascendingTitle && this.compareTracksByTitle(toSort.get(j), toSort.get(j + 1))){
          Track tempTrack = toSort.get(j);
          toSort.set(j, toSort.get(j + 1));
          toSort.set(j + 1, tempTrack);
        }else if(!this.ascendingTitle && this.compareTracksByTitle(toSort.get(j + 1), toSort.get(j))){
          Track tempTrack = toSort.get(j);
          toSort.set(j, toSort.get(j + 1));
          toSort.set(j + 1, tempTrack);
        }
      }
    }
    this.ascendingTitle = !this.ascendingTitle;
    GUI.getInstance().trackList = toSort;
  }

  public boolean compareTracksByTitle(Track track1, Track track2){
    int i = 0;
    while(i < track1.getTitle().length() - 1 && i < track2.getTitle().length() - 1 && track1.getTitle().charAt(i) == track2.getTitle().charAt(i)){
      i++;
    }
    if(track1.getTitle().charAt(i) < track2.getTitle().charAt(i)){
      return true;
    }
    return false;
  }

  public void sortByRating(){
    ArrayList<Track> toSort = new ArrayList<Track>(GUI.getInstance().trackList);

    for(int i = 0; i < toSort.size() - 1; i++){
      for(int j = 0; j < toSort.size() - i - 1; j++){
        if(this.ascendingRating && this.compareTracksByRating(toSort.get(j), toSort.get(j + 1))){
          Track tempTrack = toSort.get(j);
          toSort.set(j, toSort.get(j + 1));
          toSort.set(j + 1, tempTrack);
        }else if(!this.ascendingRating && this.compareTracksByRating(toSort.get(j + 1), toSort.get(j))){
          Track tempTrack = toSort.get(j);
          toSort.set(j, toSort.get(j + 1));
          toSort.set(j + 1, tempTrack);
        }
      }
    }
    this.ascendingRating = !this.ascendingRating;
    GUI.getInstance().trackList = toSort;
  }

  public boolean compareTracksByRating(Track track1, Track track2){
    if(track1.getRating() < track2.getRating()){
      return true;
    }
    return false;
  }
}
