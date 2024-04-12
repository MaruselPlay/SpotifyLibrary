import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame{

  private static GUI instance;

  private Spotify spotify;
  private JPanel mainPanel = new JPanel(null);
  private JPanel leftPanel = new JPanel(new BorderLayout());
  private JPanel rightPanel = new JPanel(new GridLayout(10, 1));
  private EventManager eventManager = new EventManager();

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

  public void showUpdated(){
    this.revalidate();
    this.repaint();
  }

  public void showMainScreen(){
    JLabel title = new JLabel("My Tracks");
    title.setFont(new Font("Arial", Font.PLAIN, (int) this.getWidth() / 40));
    title.setForeground(Color.WHITE);
    title.setLocation(this.getWidth() / 10, this.getHeight() / 10);
    title.setSize(title.getPreferredSize());
    this.mainPanel.add(title);

    JPanel scrollPanel = new JPanel(null);
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
    scrollPanel.setBackground(Color.BLACK);

    Font font = new Font("Arial", Font.PLAIN, (int) this.getWidth() / 50);
    for(Track playlist : this.spotify.getTracks()){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.BLACK);

      buttonPanel.add(ComponentUtils.makeButton(playlist.getTitle(), font));
      scrollPanel.add(buttonPanel);
    }

    JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(25);
    scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
    scrollPane.setBorder(null);
    this.leftPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initMainPanel(){
    this.showMainScreen();


    /*JPanel scrollPanel = new JPanel(null);
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
    scrollPanel.setBackground(Color.BLACK);

    Font font = new Font("Arial", Font.PLAIN, (int) this.getWidth() / 50);
    for(Playlist playlist : this.spotify.getPlaylists()){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.BLACK);

      buttonPanel.add(this.makeButton(playlist.getName(), font));
      scrollPanel.add(buttonPanel);
    }

    JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(25);
    scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
    scrollPane.setBorder(null);
    this.leftPanel.add(scrollPane, BorderLayout.CENTER);*/
    this.mainPanel.add(ComponentUtils.makeTrackButton());
  }

  public void initLeftPanel(){
    JPanel scrollPanel = new JPanel(null);
    scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
    scrollPanel.setBackground(Color.BLACK);

    Font font = new Font("Arial", Font.PLAIN, (int) this.getWidth() / 50);
    for(Playlist playlist : this.spotify.getPlaylists()){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.BLACK);

      buttonPanel.add(ComponentUtils.makeButton(playlist.getName(), font, new Dimension(GUI.getInstance().getWidth() / 8, GUI.getInstance().getHeight() / 15)));
      scrollPanel.add(buttonPanel);
    }

    JScrollPane scrollPane = new JScrollPane(scrollPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(25);
    scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
    scrollPane.setBorder(null);
    this.leftPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initRightPanel(){
    JButton buttonAddTrack = ComponentUtils.makeButton(
      "Add            ", //uhhh
      new Font("Arial", Font.PLAIN, (int) this.getWidth() / 80),
      new Dimension(GUI.getInstance().getWidth() / 8, GUI.getInstance().getHeight() / 15)
    );
    buttonAddTrack.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("plus.png"))));
    this.eventManager.addListener(new AddLabelOnMouseOver("Add new track", buttonAddTrack));
    this.rightPanel.add(buttonAddTrack);
  }

  public void init(){
    this.initMainPanel();
    this.initLeftPanel();
    this.initRightPanel();
    this.rightPanel.setBackground(Color.GREEN);
    this.leftPanel.setBackground(Color.GRAY);
    this.mainPanel.setBackground(Color.RED);

    this.add(this.mainPanel, BorderLayout.CENTER);
    this.add(this.rightPanel, BorderLayout.EAST);
    this.add(this.leftPanel, BorderLayout.WEST);

    this.eventManager.addListener(new RemoveLabelOnMouseOver(this.mainPanel));
    this.eventManager.addListener(new RemoveLabelOnMouseOver(this.rightPanel));
    this.eventManager.addListener(new RemoveLabelOnMouseOver(this.leftPanel));
    this.eventManager.addListener(new RemoveLabelOnMouseOver(this));

    this.getRootPane().setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

    this.setVisible(true);
  }
}