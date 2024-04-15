import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

public class GUI extends JFrame{

  private static GUI instance;

  private Spotify spotify;
  private JPanel mainPanel = new JPanel(null);
  private JPanel leftPanel = new JPanel(new BorderLayout());
  private JPanel rightPanel = new JPanel(new GridLayout(10, 1));
  private JPanel trackPanel = new JPanel(new BorderLayout());
  private JPanel addTrackPanel = new JPanel(new GridLayout(7, 1));
  private JLabel pageTitle = new JLabel();
  private JSlider trackProgressBar = new JSlider(0, 1000, 0);
  private EventManager eventManager = new EventManager();
  private AudioPlayer audioPlayer = new AudioPlayer();
  private JPanel scrollPanel;

  public GUI(Spotify spotify){
    this.spotify = spotify;
    this.setTitle("SpotifyLibrary");
    this.setLayout(new BorderLayout());

    ComponentUtils.setSizeRelativeTo(this, Toolkit.getDefaultToolkit().getScreenSize(), 70);
    ComponentUtils.setLocationRelativeTo(this, Toolkit.getDefaultToolkit().getScreenSize());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setBackground(Color.BLACK);
    this.mainPanel.setBackground(Color.BLACK);
    this.leftPanel.setBackground(Color.BLACK);
    this.rightPanel.setBackground(Color.BLACK);
    GUI.instance = this;
  }

  public static GUI getInstance(){
    return GUI.instance;
  }

  public JPanel getMainPanel(){
    return this.mainPanel;
  }

  public JPanel getLeftPanel(){
    return this.leftPanel;
  }

  public JPanel getRightPanel(){
    return this.rightPanel;
  }

  public EventManager getEventManager(){
    return this.eventManager;
  }

  public AudioPlayer getAudioPlayer(){
    return this.audioPlayer;
  }

  public void showUpdated(){
    this.revalidate();
    this.repaint();
  }

  public void addDefaultListeners(Component component){
    this.eventManager.addListener(new RemoveLabelOnMouseOver(component));
    this.eventManager.addListener(new UnselectionOnMouseOver(component));
  }

  public void initTrackPanel(){
    trackPanel.setBackground(Color.BLACK);
    trackPanel.setSize((int) (this.getWidth() * 0.4), (int) (this.getHeight() * 0.085));
    trackPanel.setLocation((int) (this.getWidth() / 7), (int) (this.getHeight() / 1.21));

		this.trackProgressBar.setPaintTrack(true);
		this.trackProgressBar.setPaintTicks(true);
    this.trackProgressBar.setBackground(Color.CYAN);
		this.trackProgressBar.setPaintLabels(true);
    this.eventManager.addListener(new BarChangeListener(this.trackProgressBar));
    this.addDefaultListeners(this.trackProgressBar);
    this.trackPanel.add(this.trackProgressBar, BorderLayout.SOUTH);

    JPanel panel = new JPanel();
    panel.setBackground(Color.ORANGE);
    this.addDefaultListeners(panel);

    JButton buttonPrevious = new JButton();
    buttonPrevious.setBorder(null);
    buttonPrevious.setBackground(Color.BLACK);
    this.addDefaultListeners(buttonPrevious);
    JLabel previous = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("previous.png"))));
    buttonPrevious.add(previous);
    panel.add(buttonPrevious);

    JButton buttonPlay = new JButton();
    buttonPlay.setBorder(null);
    buttonPlay.setBackground(Color.BLACK);
    JLabel play = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("play.png"))));
    buttonPlay.add(play);
    panel.add(buttonPlay);
    this.addDefaultListeners(buttonPlay);
    this.eventManager.addListener(new PlayButtonPressListener(buttonPlay, play));

    JButton buttonNext = new JButton();
    buttonNext.setBorder(null);
    buttonNext.setBackground(Color.BLACK);
    this.addDefaultListeners(buttonNext);
    JLabel next = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("next.png"))));
    buttonNext.add(next);
    panel.add(buttonNext);

    this.trackPanel.add(panel);
    this.mainPanel.add(this.trackPanel);
  }

  public void initMainScreen(){
    this.pageTitle.setText("My Tracks");
    this.pageTitle.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 40));
    this.pageTitle.setForeground(Color.WHITE);
    this.pageTitle.setLocation(this.getWidth() / 10, this.getHeight() / 30);
    this.pageTitle.setSize(this.pageTitle.getPreferredSize());
    this.mainPanel.add(this.pageTitle);

    JScrollPane pane = this.buildScrollPane(this.spotify.getTracks());

    this.scrollPanel = new JPanel(null);
    ComponentUtils.setSizeRelativeTo(this.scrollPanel, this.getSize(), 70.3);
    this.eventManager.addListener(new ScrollPanelListener(this.scrollPanel, pane));
    this.scrollPanel.setLocation(100, (int) (this.getHeight() / 8));
    this.scrollPanel.add(pane);

    this.mainPanel.add(this.scrollPanel);
  }

  public JScrollPane buildScrollPane(List<Track> tracks){
    JPanel scrollPanel = new JPanel(new GridLayout(this.spotify.getTracks().size(), 1));
    scrollPanel.setBackground(Color.BLACK);
    scrollPanel.setPreferredSize(new Dimension(1000, 7700));
    this.addDefaultListeners(scrollPanel);

    Font fontTitle = new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 80);
    Font fontArtist = new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 90);
    for(Track track : tracks){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.YELLOW);
      this.addDefaultListeners(buttonPanel);

      JButton button = ComponentUtils.makeButton("", new Dimension((int) (this.getSize().getWidth() * 0.67), (int) (this.getSize().getHeight() / 13)));
      button.setLayout(null);
      button.setBackground(Color.BLACK);
      this.eventManager.addListener(new SelectionOnMouseOver(button));
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

      JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("unknownTrackIcon.png"))));
      icon.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
      icon.setLocation((int) (-icon.getWidth() / 2.08), (int) (-icon.getHeight() / 6));

      button.add(title);
      button.add(artist);
      button.add(icon);
      buttonPanel.add(button);
      scrollPanel.add(buttonPanel);
    }

    JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(25);
    scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
    scrollPane.setBorder(null);
    scrollPane.setSize((int) (this.getWidth() * 0.703), (int) (this.getHeight() * 0.603));
    //ComponentUtils.setSizeRelativeTo(scrollPane, this.getSize(), 60.3);
    scrollPane.setLocation(0, 100);
    this.addDefaultListeners(scrollPane);

    return scrollPane;
  }

  public void initAddTrackScreen(){
    this.addTrackPanel.setBackground(Color.BLACK);
    ComponentUtils.setSizeRelativeTo(this.addTrackPanel, this.getSize(), 70.3);
    this.addTrackPanel.setLocation((int) (this.getWidth() / 7), (int) (this.getHeight() / 8));

    JPanel chooseFilePanel = new JPanel();
    chooseFilePanel.setBackground(Color.BLACK);
    JButton chooseFilebutton = ComponentUtils.makeButton("Select file", new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 50));
    chooseFilebutton.setBorderPainted(true);
    this.eventManager.addListener(new SelectionOnMouseOver(chooseFilebutton));
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
    this.eventManager.addListener(new SelectionOnMouseOver(button));
    buttonPanel.add(button);
    this.addTrackPanel.add(buttonPanel);

    ButtonChooseFilePressListener chooseFilePressListener = new ButtonChooseFilePressListener(chooseFilebutton, titleTextField, artistTextField, durationTextField, ratingTextField, listensTextField, errorLabel);
    this.eventManager.addListener(chooseFilePressListener);
    this.eventManager.addListener(new ButtonSubmitPressListener(button, titleTextField, artistTextField, durationTextField, ratingTextField, listensTextField, chooseFilePressListener));
    
    this.mainPanel.add(this.addTrackPanel);
  }

  public void showMainScreen(){
    this.initMainScreen();
  }

  public void showAddTrackScreen(){
    this.pageTitle.setText("Add new");
    this.pageTitle.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 40));
    this.pageTitle.setForeground(Color.WHITE);
    this.pageTitle.setLocation(this.getWidth() / 10, this.getHeight() / 30);
    this.pageTitle.setSize(this.pageTitle.getPreferredSize());
    this.mainPanel.add(this.pageTitle);
    this.addTrackPanel.setVisible(true);
  }

  public void showTrackPanel(){
    this.trackPanel.setVisible(true);
  }

  public void hideMainScreen(){
    this.mainPanel.remove(this.scrollPanel);
  }

  public void hideAddTrackScreen(){
    this.addTrackPanel.setVisible(false);
  }

  public void hideTrackPanel(){
    this.trackPanel.setVisible(false);;
  }

  public void initMainPanel(){
    this.initAddTrackScreen();
    this.initTrackPanel();

    this.hideAddTrackScreen();
    this.showMainScreen();
    //this.showTrackPanel();
    //this.showAddTrackScreen();
  }

  public void initRightPanel(){
    JButton button = ComponentUtils.makeButton("", new Dimension(this.getWidth() / 8, this.getHeight() / 15));
    button.setLayout(null);
    button.setBackground(Color.BLACK);
    this.eventManager.addListener(new SelectionOnMouseOver(button));
    this.addDefaultListeners(button);

    JLabel text = new JLabel("Add new");
    text.setFont(new Font("Sans-serif", Font.BOLD, (int) this.getWidth() / 80));
    text.setForeground(Color.WHITE);
    text.setSize(new Dimension((int) (this.getSize().getWidth() * 1.05), (int) (this.getSize().getHeight() / 9)));
    text.setLocation(text.getWidth() / 20, (int) (-text.getHeight() / 6));
    this.eventManager.addListener(new ButtonAddTrackPressListener(button, text));
    this.eventManager.addListener(new AddLabelOnMouseOver("Add new track", "Main screen", text, button));
    button.add(text);

    JLabel icon = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("plus.png"))));
    icon.setFont(new Font("Sans-serif", Font.PLAIN, (int) this.getWidth() / 80));
    icon.setForeground(Color.WHITE);
    icon.setSize(90, 90);
    icon.setLocation(icon.getWidth() / 10, -icon.getHeight() / 15);
    button.add(icon);

    this.rightPanel.add(button);
  }

  public void init(){
    this.initMainPanel();
    this.initRightPanel();
    this.rightPanel.setBackground(Color.GREEN);
    this.leftPanel.setBackground(Color.GRAY);
    this.mainPanel.setBackground(Color.RED);

    this.add(this.mainPanel, BorderLayout.CENTER);
    this.add(this.rightPanel, BorderLayout.EAST);
    this.add(this.leftPanel, BorderLayout.WEST);

    this.addDefaultListeners(this.mainPanel);
    this.addDefaultListeners(this.rightPanel);
    this.addDefaultListeners(this.leftPanel);
    this.addDefaultListeners(this.addTrackPanel);
    this.addDefaultListeners(this);

    this.getRootPane().setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

    this.setVisible(true);
  }
}