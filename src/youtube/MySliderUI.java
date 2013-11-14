/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;
import java.awt.*;  
import javax.swing.*;  
import javax.swing.plaf.metal.MetalSliderUI;  
  
  
public class MySliderUI extends MetalSliderUI {  
  
  private static final float[] fractions = {0.0f, 0.5f};  
  private static final Color[] fillColors = {  
      new Color(111,111,111),
      new Color(111,111,111)
  };  
  private static final Color[] backColors = {  
      new Color(195,195,195),
      new Color(195,195,195)
  };  
  private static final Paint hFillGradient = new LinearGradientPaint(0, 0, 0, 11,  
          fractions, fillColors, MultipleGradientPaint.CycleMethod.NO_CYCLE);  
  private static final Paint hBackGradient = new LinearGradientPaint(0, 0, 0, 11,  
          fractions, backColors, MultipleGradientPaint.CycleMethod.NO_CYCLE);  
  private static final Paint vFillGradient = new LinearGradientPaint(0, 0, 11, 0,  
          fractions, fillColors, MultipleGradientPaint.CycleMethod.NO_CYCLE);  
  private static final Paint vBackGradient = new LinearGradientPaint(0, 0, 11, 0,  
          fractions, backColors, MultipleGradientPaint.CycleMethod.NO_CYCLE);  
  private static final Stroke roundEndStroke = new BasicStroke(8,  
          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);  
  
  @Override  
  public Dimension getPreferredSize(JComponent c) {  
    Dimension d = super.getPreferredSize(c);  
    if (slider.getOrientation() == JSlider.HORIZONTAL) {  
      d.height += 10;  
    } else {  
      d.width += 10;  
    }  
    return d;  
  }  
  
  @Override  
  public void paintTrack(Graphics g) {  
    Graphics2D g2 = (Graphics2D) g;  
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
  
    if (slider.getOrientation() == JSlider.HORIZONTAL) {  
      int cy = (trackRect.height / 2) - 2;  
      g.translate(trackRect.x, trackRect.y + cy);  
  
      g2.setStroke(roundEndStroke);  
      g2.setPaint(hBackGradient);  
      g2.drawLine(thumbRect.x, 2, trackRect.width, 2);  
      g2.setPaint(hFillGradient);  
      g2.drawLine(0, 2, thumbRect.x, 2);  
  
      g.translate(-trackRect.x, -(trackRect.y + cy));  
    } else {  
      int cx = (trackRect.width / 2) - 2;  
      g.translate(trackRect.x + cx, trackRect.y);  
  
      g2.setStroke(roundEndStroke);  
      g2.setPaint(vBackGradient);  
      g2.drawLine(2, 0, 2, thumbRect.y);  
      g2.setPaint(vFillGradient);  
      g2.drawLine(2, thumbRect.y, 2, trackRect.height);  
  
      g.translate(-(trackRect.x + cx), -trackRect.y);  
    }  
  }  
}  
class SpecialThumbIcon implements Icon {  
  
  private static final float[] fractions = {0.1f, 0.35f};  
  private static final Color[] colors = {  
    new Color(255,255,255),  
    new Color(74,74,74)  
  };  
  private static final Paint thumbGradient = new RadialGradientPaint(5, 3, 15,  
          fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);  
  
  @Override  
  public void paintIcon(Component c, Graphics g, int x, int y) {  
    Graphics2D g2 = (Graphics2D) g;  
    g2.setPaint(thumbGradient);  
    g.fillOval(x + 3, y + 3, 14, 14);  
  }  
  
  @Override  
  public int getIconWidth() {  
    return 20;  
  }  
  
  @Override  
  public int getIconHeight() {  
    return 20;  
  }  
}  
 