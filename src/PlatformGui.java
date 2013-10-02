

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class PlatformGui {
   private static void createAndShowUI() {
	   
	   JPanel container = new JPanel();
	   JPanel c2 = new JPanel();
	   container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
	   c2.setLayout(new BoxLayout(c2, BoxLayout.Y_AXIS));
      STDrawPanel drawPanel = new STDrawPanel();
      ExecuteButton button = new ExecuteButton();
      StatusPanel status = new StatusPanel();
      
 
      ListPanel list = new ListPanel();
      c2.add(drawPanel);
      c2.add(button);
      container.add(list);
      container.add(c2);
       container.add(status);
 


      JFrame frame = new JFrame("Drawing");
      frame.getContentPane().add(container);
  
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(true);
      frame.setSize(900, 500);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}

@SuppressWarnings("serial")

class ExecuteButton extends JButton{
	
	ExecuteButton(){
		super("Execute");
	}
}


@SuppressWarnings("serial")
class ListPanel extends JScrollPane{
	
	   private static final int ST_WIDTH = 200;
	   private static final int ST_HEIGHT = 500;
	   private static final Color BACKGROUND_COLOR = Color.blue;
	   private BufferedImage bImage = new BufferedImage(ST_WIDTH, ST_HEIGHT,
	            BufferedImage.TYPE_INT_RGB);
	   private String[] rawList;
	   private JList<String> list;
	   
	   
	   @SuppressWarnings({ "unchecked", "rawtypes" })
	public ListPanel() {
		      Graphics g = bImage.getGraphics();
		      g.setColor(BACKGROUND_COLOR);
		      g.fillRect(0, 0, ST_WIDTH, ST_HEIGHT);
		      String[] listData = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
		      this.list = new JList(listData);
		     
		      this.setViewportView(this.list);

		      g.dispose();
		      
		   }
	   
	   public ListPanel(String[] list){
		   Graphics g = bImage.getGraphics();
		      g.setColor(BACKGROUND_COLOR);
		      g.fillRect(0, 0, ST_WIDTH, ST_HEIGHT);
		      
		      this.list = new JList<String>(list);
		      this.rawList = list;
		      this.setViewportView(this.list);
		      
		   
		      g.dispose();
		   
	   }
	   
	   public void updateList(String[] newlist){
		   
		   Graphics g = bImage.getGraphics();
		   this.list = new JList<String>(newlist);
		   this.rawList = newlist;
		   this.setViewportView(this.list);
		   g.dispose();
	   }
	   @Override
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      g.drawImage(bImage, 0, 0, null);
	      g.setColor(BACKGROUND_COLOR);
	    
	     
	   }
	   
}


@SuppressWarnings("serial")

class StatusPanel extends JTextArea{
	
	public StatusPanel(){
		super();
	}
	
	public void writeToStatus(String message){
		this.append(message);
	}

}

@SuppressWarnings("serial")
class STDrawPanel extends JPanel {
   private static final int ST_WIDTH = 700;
   private static final int ST_HEIGHT = 500;
   private static final Color BACKGROUND_COLOR = Color.white;
   private static final float STROKE_WIDTH = 6f;
   private static final Stroke STROKE = new BasicStroke(STROKE_WIDTH,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


   private BufferedImage bImage = new BufferedImage(ST_WIDTH, ST_HEIGHT,
            BufferedImage.TYPE_INT_RGB);
   private Color color = Color.black;
   private ArrayList<Point> points = new ArrayList<Point>();
 

   public STDrawPanel() {
      Graphics g = bImage.getGraphics();
      g.setColor(BACKGROUND_COLOR);
      g.fillRect(0, 0, ST_WIDTH, ST_HEIGHT);
      g.dispose();
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(bImage, 0, 0, null);
      Graphics2D g2 = (Graphics2D) g;
      drawCurve(g2);
   }


   private void drawCurve(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
               RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setStroke(STROKE);
      g2.setColor(color);
      if (points != null && points.size() > 1) {
         for (int i = 0; i < points.size() - 1; i++) {
            int x1 = points.get(i).x;
            int y1 = points.get(i).y;
            int x2 = points.get(i + 1).x;
            int y2 = points.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
         }
      }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(ST_WIDTH, ST_HEIGHT);
   }
}