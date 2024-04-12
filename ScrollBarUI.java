import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import static sun.swing.SwingUtilities2.drawHLine;
import static sun.swing.SwingUtilities2.drawRect;
import static sun.swing.SwingUtilities2.drawVLine;

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
    drawRect(g, 0, 0, w - 1, h - 1);
    g.fillRect(0, 0, w - 1, h - 1);
    drawVLine(g, 1, 1, h - 2);
    drawHLine(g, 2, w - 3, 1);
    drawHLine(g, 2, w - 2, h - 2);
    drawVLine(g, w - 2, 1, h - 3);

    g.translate(-thumbBounds.x, -thumbBounds.y);
  }
}