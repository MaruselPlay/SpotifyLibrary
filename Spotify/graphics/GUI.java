package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import listener.AddLabelOnMouseOverListener;
import listener.BarChangeListener;
import listener.ButtonAddTrackPressListener;
import listener.ButtonChooseFilePressListener;
import listener.ButtonSubmitPressListener;
import listener.EventManager;
import listener.PlayButtonPressListener;
import listener.RemoveLabelOnMouseOverListener;
import listener.ScrollPanelListener;
import listener.UnselectionOnMouseOverListener;
import listener.SelectionOnMouseOverListener;
import listener.SongButtonPressListener;
import listener.TextFieldChangeListener;
import spotify.Spotify;
import spotify.AudioPlayer;
import spotify.Track;
import utils.ComponentUtils;

public class GUI extends JFrame{

  private static GUI instance;

  private Spotify spotify;
  private JPanel mainPanel = new JPanel(null);
  private JPanel rightPanel = new JPanel(new GridLayout(10, 1));
  private JPanel trackPanel = new JPanel(new BorderLayout());
  private JPanel addTrackPanel = new JPanel(new GridLayout(7, 1));
  private JLabel pageTitle = new JLabel();
  private JSlider trackProgressBar = new JSlider(0, 1000, 0);
  private EventManager eventManager = new EventManager();
  private AudioPlayer audioPlayer = new AudioPlayer();
  private JPanel scrollPanel = new JPanel(null);
  private JPanel scrollHelperPanel = new JPanel();
  private JScrollPane scrollPane;

  public ArrayList<Track> trackList;
  
  public GUI(Spotify spotify){
    this.spotify = spotify;
    this.setTitle("SpotifyLibrary");
    this.setLayout(new BorderLayout());

    ComponentUtils.setSizeRelativeTo(this, new Dimension(2560, 1440), 70);
    ComponentUtils.setLocationRelativeTo(this, new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 1.05)));
    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new WindowListener(){
      public void windowClosing(WindowEvent event){
        Spotify.getInstance().saveTracksToFile();
        event.getWindow().setVisible(false);
        System.exit(0);
      }

      public void windowActivated(WindowEvent event){}
      public void windowClosed(WindowEvent event){}
      public void windowDeactivated(WindowEvent event){}
      public void windowDeiconified(WindowEvent event){}
      public void windowIconified(WindowEvent event){}
      public void windowOpened(WindowEvent event){}
    });

    this.setBackground(Color.BLACK);
    this.mainPanel.setBackground(Color.BLACK);
    this.rightPanel.setBackground(Color.BLACK);
    GUI.instance = this;
  }

  /**
   * Retrieves the singleton instance of the GUI class.
   * This method provides global access to the single instance of the GUI class,
   * ensuring that only one instance of the GUI is created throughout the application.
   * If an instance does not exist, it should be created elsewhere before calling this method.
   * 
   * @return The single instance of the GUI class.
   */
  public static GUI getInstance(){
      return GUI.instance;
  }

  /**
   * Retrieves the main panel of the GUI.
   * This method provides access to the main panel of the application's GUI,
   * allowing for modifications and updates to the panel's components.
   * 
   * @return the main JPanel of the GUI
   */
  public JPanel getMainPanel(){
      return this.mainPanel;
  }

  /**
   * The getRightPanel() function returns the rightPanel JPanel.
   * 
   * @return The method `getRightPanel` is returning a `JPanel` object named `rightPanel`.
   */
  public JPanel getRightPanel(){
    return this.rightPanel;
  }

  /**
   * The function returns the EventManager associated with the current object.
   * 
   * @return An `EventManager` object is being returned.
   */
  public EventManager getEventManager(){
    return this.eventManager;
  }

  /**
   * The function returns the AudioPlayer object associated with the current instance.
   * 
   * @return An AudioPlayer object is being returned.
   */
  public AudioPlayer getAudioPlayer(){
    return this.audioPlayer;
  }

  /**
   * The `showUpdated` function in Java revalidates and repaints the component.
   */
  public void showUpdated(){
    this.revalidate();
    this.repaint();
  }

  /**
   * The `addDefaultListeners` function adds two default event listeners to a given component.
   * 
   * @param component The `component` parameter is an instance of a `Component` class, which represents
   * a GUI component in a Java Swing application. It could be a button, label, panel, or any other
   * graphical element that the user interacts with on the screen.
   */
  public void addDefaultListeners(Component component){
    this.eventManager.addListener(new RemoveLabelOnMouseOverListener(component));
    this.eventManager.addListener(new UnselectionOnMouseOverListener(component));
  }

  /**
   * The `initTrackPanel` function sets up a panel with a progress bar and buttons for previous, play,
   * and next actions in a Java GUI application.
   */
  public void initTrackPanel(){
    trackPanel.setBackground(Color.BLACK);
    trackPanel.setSize((int) (this.getWidth() * 0.4), (int) (this.getHeight() * 0.085));
    trackPanel.setLocation((int) (this.getWidth() / 7), (int) (this.getHeight() / 1.21));

		this.trackProgressBar.setPaintTrack(true);
		this.trackProgressBar.setPaintTicks(true);
    this.trackProgressBar.setBackground(Color.BLACK);
		this.trackProgressBar.setPaintLabels(true);
    this.eventManager.addListener(new BarChangeListener(this.trackProgressBar));
    this.addDefaultListeners(this.trackProgressBar);
    this.trackPanel.add(this.trackProgressBar, BorderLayout.SOUTH);

    JPanel panel = new JPanel();
    panel.setBackground(Color.BLACK);
    this.addDefaultListeners(panel);

    /*JButton buttonPrevious = new JButton();
    buttonPrevious.setBorder(null);
    buttonPrevious.setBackground(Color.BLACK);
    this.addDefaultListeners(buttonPrevious);
    JLabel previous = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/previous.png"))));
    buttonPrevious.add(previous);
    panel.add(buttonPrevious);*/

    JButton buttonPlay = new JButton();
    buttonPlay.setBorder(null);
    buttonPlay.setBackground(Color.BLACK);
    JLabel play = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/play.png")));
    buttonPlay.add(play);
    panel.add(buttonPlay);
    this.addDefaultListeners(buttonPlay);
    this.eventManager.addListener(new PlayButtonPressListener(buttonPlay, play));
    this.eventManager.addListener(new SelectionOnMouseOverListener(buttonPlay));

    /*JButton buttonNext = new JButton();
    buttonNext.setBorder(null);
    buttonNext.setBackground(Color.BLACK);
    this.addDefaultListeners(buttonNext);
    JLabel next = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/next.png"))));
    buttonNext.add(next);
    panel.add(buttonNext);*/

    this.trackPanel.add(panel);
    this.mainPanel.add(this.trackPanel);
  }

  /**
   * The `initMainScreen` function sets up the main screen by updating the page title, setting its font
   * and color, adding a scroll pane, and positioning it within the main panel.
   */
  public void initMainScreen(){
    this.pageTitle.setText("My Tracks");
    this.pageTitle.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 40));
    this.pageTitle.setForeground(Color.WHITE);
    this.pageTitle.setLocation(this.getWidth() / 10, this.getHeight() / 30);
    this.pageTitle.setSize(this.pageTitle.getPreferredSize());
    this.mainPanel.add(this.pageTitle);

    this.buildScrollPane();

    ComponentUtils.setSizeRelativeTo(this.scrollPanel, this.getSize(), 70.3);
    this.eventManager.addListener(new ScrollPanelListener(this.scrollPanel));
    this.scrollPanel.setLocation(100, (int) (this.getHeight() / 8));

    this.mainPanel.add(this.scrollPanel);
  }

  /**
   * The function `buildScrollPane` creates and configures a JScrollPane component with a vertical
   * scrollbar for a track list panel.
   */
  public void buildScrollPane(){
    this.updateTrackListPanel();

    if(this.scrollPane != null){
      this.scrollPane.setVisible(false);
      this.scrollPanel.remove(this.scrollPane);
    }

    this.scrollPane = new JScrollPane(this.scrollHelperPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    this.scrollPane.getVerticalScrollBar().setUnitIncrement(25);
    this.scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
    this.scrollPane.setBorder(null);
    this.scrollPane.setSize((int) (this.getWidth() * 0.703), (int) (this.getHeight() * 0.603));
    this.scrollPane.setLocation(0, 100);
    this.addDefaultListeners(this.scrollPane);

    this.scrollPanel.add(this.scrollPane);
  }

  // The above code is a Java method `updateTrackListPanel()` that updates the track list panel in a
  // GUI application. Here's a breakdown of what the code is doing:
  public void updateTrackListPanel(){
    this.scrollHelperPanel = new JPanel();
    this.scrollHelperPanel.setLayout(new GridLayout(this.spotify.getTracks().size() < 7 ? 7 : this.spotify.getTracks().size(), 1));
    this.scrollHelperPanel.setBackground(Color.BLACK);
    this.addDefaultListeners(this.scrollHelperPanel);
    
    Font fontTitle = new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 80);
    Font fontArtist = new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 90);
    for(Track track : this.trackList){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.BLACK);
      this.addDefaultListeners(buttonPanel);

      JButton button = ComponentUtils.makeButton("", new Dimension((int) (this.getSize().getWidth() * 0.67), (int) (this.getSize().getHeight() / 13)));
      button.setLayout(null);
      button.setBackground(Color.BLACK);
      this.eventManager.addListener(new SelectionOnMouseOverListener(button));
      this.eventManager.addListener(new SongButtonPressListener(button, track));
      this.addDefaultListeners(button);

      JLabel title = new JLabel(track.getTitle());
      title.setFont(fontTitle);
      title.setForeground(Color.WHITE);
      title.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
      title.setLocation(title.getWidth() / 25, (int) (-title.getHeight() / 3.3));

      JLabel artist = new JLabel(track.getArtist());
      artist.setFont(fontArtist);
      artist.setForeground(new Color(180, 180, 180));
      artist.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
      artist.setLocation(artist.getWidth() / 25, -artist.getHeight() / 20);

      JLabel rating = new JLabel("Rating: " + track.getRating());
      rating.setFont(fontArtist);
      rating.setForeground(new Color(180, 180, 180));
      rating.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
      rating.setLocation((int) (rating.getWidth() / 1.7), -rating.getHeight() / 7);

      JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resources/unknownTrackIcon.png")));
      icon.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
      icon.setLocation((int) (-icon.getWidth() / 2.08), (int) (-icon.getHeight() / 6));

      button.add(title);
      button.add(artist);
      button.add(rating);
      button.add(icon);
      buttonPanel.add(button);
      this.scrollHelperPanel.add(buttonPanel);
    }
  }

  /**
   * The `initAddTrackScreen` function sets up a panel for adding a new track with input fields for
   * title, artist, duration, rating, listens, and a button to submit the track information.
   */
  public void initAddTrackScreen(){
    this.addTrackPanel.setBackground(Color.BLACK);
    ComponentUtils.setSizeRelativeTo(this.addTrackPanel, this.getSize(), 70.3);
    this.addTrackPanel.setLocation((int) (this.getWidth() / 7), (int) (this.getHeight() / 8));

    JPanel chooseFilePanel = new JPanel();
    chooseFilePanel.setBackground(Color.BLACK);
    JButton chooseFilebutton = ComponentUtils.makeButton("Select file", new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    chooseFilebutton.setBorderPainted(true);
    this.eventManager.addListener(new SelectionOnMouseOverListener(chooseFilebutton));
    JLabel errorLabel = new JLabel();
    //errorLabel.setText("");
    errorLabel.setForeground(Color.RED);
    errorLabel.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    errorLabel.setLocation(10, 10);
    chooseFilePanel.add(chooseFilebutton);
    chooseFilePanel.add(errorLabel);
    this.addTrackPanel.add(chooseFilePanel);

    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(Color.BLACK);
    JTextField titleTextField = new JTextField(20);
    titleTextField.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    titleTextField.setForeground(Color.WHITE);
    titleTextField.setOpaque(false);
    this.eventManager.addListener(new TextFieldChangeListener(titleTextField, "Title", 10, 10));
    titlePanel.add(titleTextField);
    this.addTrackPanel.add(titlePanel);

    JPanel artistPanel = new JPanel();
    artistPanel.setBackground(Color.BLACK);
    JTextField artistTextField = new JTextField(20);
    artistTextField.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    artistTextField.setForeground(Color.WHITE);
    artistTextField.setOpaque(false);
    this.eventManager.addListener(new TextFieldChangeListener(artistTextField, "Artist", 10, 10));
    artistPanel.add(artistTextField);
    this.addTrackPanel.add(artistPanel);

    JPanel durationPanel = new JPanel();
    durationPanel.setBackground(Color.BLACK);
    JTextField durationTextField = new JTextField(20);
    durationTextField.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    durationTextField.setForeground(Color.WHITE);
    durationTextField.setOpaque(false);
    this.eventManager.addListener(new TextFieldChangeListener(durationTextField, "Track duration in seconds", 10, 10));
    durationPanel.add(durationTextField);
    this.addTrackPanel.add(durationPanel);
    
    JPanel ratingPanel = new JPanel();
    ratingPanel.setBackground(Color.BLACK);
    JTextField ratingTextField = new JTextField(20);
    ratingTextField.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    ratingTextField.setForeground(Color.WHITE);
    ratingTextField.setOpaque(false);
    this.eventManager.addListener(new TextFieldChangeListener(ratingTextField, "Rating from 1 to 5", 10, 10));
    ratingPanel.add(ratingTextField);
    this.addTrackPanel.add(ratingPanel);
    
    JPanel listensPanel = new JPanel();
    listensPanel.setBackground(Color.BLACK);
    JTextField listensTextField = new JTextField(20);
    listensTextField.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    listensTextField.setForeground(Color.WHITE);
    listensTextField.setOpaque(false);
    this.eventManager.addListener(new TextFieldChangeListener(listensTextField, "Total number of listens", 10, 10));
    listensPanel.add(listensTextField);
    this.addTrackPanel.add(listensPanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.BLACK);
    JButton button = ComponentUtils.makeButton("Add", new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    button.setBorderPainted(true);
    this.eventManager.addListener(new SelectionOnMouseOverListener(button));
    buttonPanel.add(button);
    this.addTrackPanel.add(buttonPanel);

    ButtonChooseFilePressListener chooseFilePressListener = new ButtonChooseFilePressListener(chooseFilebutton, durationTextField, errorLabel);
    this.eventManager.addListener(chooseFilePressListener);
    this.eventManager.addListener(new ButtonSubmitPressListener(button, titleTextField, artistTextField, durationTextField, ratingTextField, listensTextField, chooseFilePressListener));
    
    this.mainPanel.add(this.addTrackPanel);
  }

  /**
   * The function `showMainScreen` sets the title, font, color, location, and visibility of a page
   * title and scroll panel in a Java GUI.
   */
  public void showMainScreen(){
    this.pageTitle.setText("My Tracks");
    this.pageTitle.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 40));
    this.pageTitle.setForeground(Color.WHITE);
    this.pageTitle.setLocation(this.getWidth() / 10, this.getHeight() / 30);
    this.pageTitle.setSize(this.pageTitle.getPreferredSize());
    this.buildScrollPane();
    this.scrollPanel.setVisible(true);
  }

  /**
   * The function `showAddTrackScreen` sets up the UI to display an "Add new" track screen in a Java
   * application.
   */
  public void showAddTrackScreen(){
    this.pageTitle.setText("Add new");
    this.pageTitle.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 40));
    this.pageTitle.setForeground(Color.WHITE);
    this.pageTitle.setLocation(this.getWidth() / 10, this.getHeight() / 30);
    this.pageTitle.setSize(this.pageTitle.getPreferredSize());
    this.mainPanel.add(this.pageTitle);
    this.addTrackPanel.setVisible(true);
  }

  /**
   * The function `showTrackPanel` sets the visibility of the track panel to true.
   */
  public void showTrackPanel(){
    this.trackPanel.setVisible(true);
  }

  /**
   * The `hideMainScreen` function sets the visibility of a scroll panel and scroll pane to false.
   */
  public void hideMainScreen(){
    this.scrollPanel.setVisible(false);
    this.scrollPane.setVisible(false);
  }

  /**
   * The function `hideAddTrackScreen` sets the visibility of the `addTrackPanel` to false in Java.
   */
  public void hideAddTrackScreen(){
    this.addTrackPanel.setVisible(false);
  }

  /**
   * The function `hideTrackPanel` sets the visibility of the trackPanel to false.
   */
  public void hideTrackPanel(){
    this.trackPanel.setVisible(false);;
  }

  /**
   * The `initMainPanel` function initializes various panels and screens while hiding the add track
   * screen.
   */
  public void initMainPanel(){
    this.initAddTrackScreen();
    this.initTrackPanel();
    this.initMainScreen();
    this.hideAddTrackScreen();
    //this.showMainScreen();
    //this.showTrackPanel();
    //this.showAddTrackScreen();
  }

  /**
   * The `initRightPanel` function creates a button with text and an icon for adding a new track in a
   * graphical user interface.
   */
  public void initRightPanel(){
    JButton button = ComponentUtils.makeButton("", new Dimension(this.getWidth() / 8, this.getHeight() / 15));
    button.setLayout(null);
    button.setBackground(Color.BLACK);
    this.eventManager.addListener(new SelectionOnMouseOverListener(button));
    this.addDefaultListeners(button);

    JLabel text = new JLabel("Add new");
    text.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 80));
    text.setForeground(Color.WHITE);
    text.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
    text.setLocation(text.getWidth() / 20, (int) (-text.getHeight() / 6));
    this.eventManager.addListener(new ButtonAddTrackPressListener(button, text));
    this.eventManager.addListener(new AddLabelOnMouseOverListener("Add new track", "Main screen", text, button));
    button.add(text);
    JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\resources\\plus.png")));
    icon.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 80));
    icon.setForeground(Color.WHITE);
    icon.setSize(90, 90);
    icon.setLocation(icon.getWidth() / 10, -icon.getHeight() / 15);
    button.add(icon);

    this.rightPanel.add(button);
  }

  /**
   * The `init` method initializes panels, sets backgrounds, adds components, adds listeners, sets
   * border, and makes the frame visible in a Java GUI application.
   */
  public void init(){
    this.trackList = new ArrayList<>(Spotify.getInstance().getTracks());

    this.initMainPanel();
    this.initRightPanel();
    this.rightPanel.setBackground(Color.BLACK);
    this.mainPanel.setBackground(Color.BLACK);

    this.add(this.mainPanel, BorderLayout.CENTER);
    this.add(this.rightPanel, BorderLayout.EAST);

    this.addDefaultListeners(this.mainPanel);
    this.addDefaultListeners(this.rightPanel);
    this.addDefaultListeners(this.addTrackPanel);
    this.addDefaultListeners(this);

    this.getRootPane().setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

    this.setVisible(true);
  }
}