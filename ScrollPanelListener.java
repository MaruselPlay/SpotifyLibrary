import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ScrollPanelListener extends EventListener implements ActionListener, DocumentListener{

  private Track track;
  private JButton sortByTitle = new JButton("Title sort");
  private JButton sortByRating = new JButton("Rating sort");
  private JTextField searchBox = new JTextField(40);

  public ScrollPanelListener(JPanel mainScrollPanel, JScrollPane currentPane){
    super(mainScrollPanel);

    this.sortByTitle.setForeground(new Color(180, 180, 180));
    this.sortByTitle.setBackground(Color.BLACK);
    this.sortByTitle.setFocusPainted(false);
    this.sortByTitle.setFocusable(false);
    this.sortByTitle.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.sortByTitle.setBorderPainted(false);
    this.sortByTitle.setLocation(950, 67);
    this.sortByTitle.setSize(this.sortByTitle.getPreferredSize());
    GUI.getInstance().getEventManager().addListener(new SelectionOnMouseOver(this.sortByTitle));
    GUI.getInstance().addDefaultListeners(this.sortByTitle);
    mainScrollPanel.add(this.sortByTitle);

    this.sortByRating.setForeground(new Color(180, 180, 180));
    this.sortByRating.setBackground(Color.BLACK);
    this.sortByRating.setFocusPainted(false);
    this.sortByRating.setFocusable(false);
    this.sortByRating.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.sortByRating.setLocation(1100, 67);
    this.sortByRating.setBorderPainted(false);
    this.sortByRating.setSize(this.sortByRating.getPreferredSize());
    GUI.getInstance().getEventManager().addListener(new SelectionOnMouseOver(this.sortByRating));
    GUI.getInstance().addDefaultListeners(this.sortByRating);
    mainScrollPanel.add(this.sortByRating);

    this.searchBox.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.searchBox.setSize(this.searchBox.getPreferredSize());
    this.searchBox.setLocation(0, 67);
    this.searchBox.setForeground(new Color(200, 200, 200));
    this.searchBox.setOpaque(false);
    GUI.getInstance().getEventManager().addListener(new TextFieldChangeListener(this.searchBox, "Search"));
    mainScrollPanel.add(this.searchBox);

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
    L
    String character = this.searchBox.getText().charAt(this.searchBox.getText().length() - 1) + "";
    System.out.println(character);
  }

  public void actionPerformed(ActionEvent event){
    GUI.getInstance().getAudioPlayer().play(this.track.getFile().getPath());
  }
}
