/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

class HoverListCellRenderer extends DefaultListCellRenderer {  
  
  private static final Color HOVER_COLOR = new Color(222,222,222);  
  private int hoverIndex = -1;  
  private MouseAdapter handler;  
  
  @Override  
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {  
    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);  
    Component component = (Component) value;
    if (!isSelected) {  
        component.setBackground (index == hoverIndex  
              ? HOVER_COLOR : list.getBackground());
    }  
    else{
        component.setForeground (Color.white);
        component.setBackground (isSelected ? new Color(200, 200, 200) : Color.WHITE);
    }
    return component;

  }  
  
  public MouseAdapter getHandler(JList list) {  
    if (handler == null) {  
      handler = new HoverMouseHandler(list);  
    }  
    return handler;  
  }  
  
  class HoverMouseHandler extends MouseAdapter {  
  
    private final JList list;  
  
    public HoverMouseHandler(JList list) {  
      this.list = list;  
    }  
  
    @Override  
    public void mouseExited(MouseEvent e) {  
      setHoverIndex(-1);  
    }  
  
    @Override  
    public void mouseMoved(MouseEvent e) {  
        if(list.getFirstVisibleIndex() >= 0){
            int index = list.locationToIndex(e.getPoint());  
            setHoverIndex(list.getCellBounds(index, index).contains(e.getPoint())  
              ? index : -1);  
        }
    }  
  
    private void setHoverIndex(int index) {  
      if (hoverIndex == index) return;  
      hoverIndex = index;  
      list.repaint();  
    }  
  }  
}
