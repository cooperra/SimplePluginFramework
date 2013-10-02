

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListDataListener;

 class PlatformGui implements ActionListener {
	 
	 private STDrawPanel drawPanel;
	 private ExecuteButton button;
	 private StatusPanel status;
	 private ListPanel PluginList;
	 private JPanel container1;
	 private JPanel container2;
	 
	 
	 public PlatformGui(){
	  
	   this.container1 = new JPanel();
	 this.container2 = new JPanel();
	   this.container1.setLayout(new BoxLayout(this.container1, BoxLayout.X_AXIS));
	   this.container2.setLayout(new BoxLayout(this.container2, BoxLayout.Y_AXIS));
	   this.drawPanel = new STDrawPanel();
      this.button = new ExecuteButton();
      
      this.button.addActionListener(this);
       this.status = new StatusPanel();
      
 
      this.PluginList = new ListPanel();
      this.container2.add(drawPanel);
      this.container2.add(button);
      this.container1.add(PluginList);
      this.container1.add(this.container2);
       this.container1.add(status);
 


      JFrame frame = new JFrame("Drawing");
      frame.getContentPane().add(this.container1);
  
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(true);
      frame.setSize(900, 500);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
	 public StatusPanel GetStatusPanel(){
		 return this.status;
	 }
	 
	 public ListPanel GetListPanel(){
		 return this.PluginList;

	 }
	 public STDrawPanel GetDrawPanel(){
		 return this.drawPanel;
	 }
	 public ExecuteButton GetExecuteButton(){
		 return this.button;
	 }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            PlatformGui g = new PlatformGui();
            
          
         }
      });
   }
@Override
public void actionPerformed(ActionEvent e) {
	this.status.writeToStatus("Hello World!! \n");
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
	   
	   public int[] SendSelected(){
		   return this.list.getSelectedIndices();
		   
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
		this.setWrapStyleWord(true);
	
		
	}
	
	public void writeToStatus(String message){
		this.append(message + "\n");
	}

}

@SuppressWarnings("serial")
class STDrawPanel extends JPanel {
   private static final int ST_WIDTH = 700;
   private static final int ST_HEIGHT = 500;
   private static final Color BACKGROUND_COLOR = Color.white;
 
 

   private BufferedImage bImage = new BufferedImage(ST_WIDTH, ST_HEIGHT,
            BufferedImage.TYPE_INT_RGB);

 

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
      
     
   }



   @Override
   public Dimension getPreferredSize() {
      return new Dimension(ST_WIDTH, ST_HEIGHT);
   }
}