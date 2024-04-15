import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldChangeListener extends EventListener implements DocumentListener{

  private JLabel label = new JLabel();
  private String text;

  public TextFieldChangeListener(JTextField component, String text){
    super(component);
    this.text = text;
    this.label.setText(text + "                            ");
    this.label.setForeground(new Color(120, 120, 120));
    this.label.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.label.setSize(this.label.getPreferredSize());
    component.add(this.label);
  }

  public TextFieldChangeListener(JTextField component, String text, int offsetX, int offsetY){
    super(component);
    this.text = text;
    this.label.setText(text + "                            ");
    this.label.setForeground(new Color(120, 120, 120));
    this.label.setFont(new Font("Sans-serif", Font.PLAIN, (int) GUI.getInstance().getWidth() / 80));
    this.label.setSize(this.label.getPreferredSize());
    this.label.setLocation(offsetX, offsetY);
    component.add(this.label);
  }

  public void addListenerToComponent(){
    ((JTextField) this.component).getDocument().addDocumentListener(this);
  }

  public JLabel getLabel(){
    return this.label;
  }

  public void changedUpdate(DocumentEvent event){
    this.actionPerformed();
  }

  public void removeUpdate(DocumentEvent event){
    this.actionPerformed();
  }

  public void insertUpdate(DocumentEvent event){
    this.actionPerformed();
  }

  public void actionPerformed(){
    if(this.label.getForeground().getRGB() == -65536){
      this.label.setForeground(new Color(120, 120, 120));
      this.label.setText(null);
      GUI.getInstance().showUpdated();
    }
    
    if(((JTextField) this.component).getText().length() > 0){
      this.label.setText(null);
      GUI.getInstance().showUpdated();
    }else{
      this.label.setText(this.text);
      GUI.getInstance().showUpdated();
    }
  }
}
