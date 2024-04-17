package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarUI extends BasicScrollBarUI{

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds){
    g.setColor(Color.BLACK);
    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

    if(trackHighlight == DECREASE_HIGHLIGHT){
      paintDecreaseHighlight(g);
    }else if(trackHighlight == INCREASE_HIGHLIGHT){
      paintIncreaseHighlight(g);
    }
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds){
    if(thumbBounds.isEmpty() || !scrollbar.isEnabled())     {
      return;
    }

    int w = thumbBounds.width;
    int h = thumbBounds.height;

    g.translate(thumbBounds.x, thumbBounds.y);

    g.setColor(new Color(110, 110, 110));
    g.fillRect(0, 0, w - 1, h - 1);

    g.translate(-thumbBounds.x, -thumbBounds.y);
  }
}