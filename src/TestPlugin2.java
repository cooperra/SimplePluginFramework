
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * This is a sample plugin. It extends TemplatePlugin (which is abstract) and doesn't do much. 
 * @author cooperra
 *
 */
public class TestPlugin2 implements IPlugin {

	public String getId() {
		return "Draw Blue Rectangle";
	}

	public String getVersion() {
		return "0.0.0";
	}

	/**
	 * This associates events with action listeners (which are defined inline)
	 */
	protected HashMap<String, ActionListener> getEventListeners() {
		HashMap<String, ActionListener> events = new HashMap<String, ActionListener>();
		events.put("onTheButton", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.toString());
			}
		});
		return events;
		
	}

	@Override
	public boolean load(PluginAPI api) {
	Graphics g = api.getGUI().GetDrawPanel().getGraphics();
	g.setColor(Color.BLUE);
	g.fillRect(100, 100, 300, 30);
		
	
		
		return false;
	}

	@Override
	public boolean unload() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
