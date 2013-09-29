import java.awt.Canvas;
import java.awt.event.ActionListener;


public class PluginAPI {
	private IPlugin plugin;
	private EventManager em;
	private /*TODO*/Object ui;
	
	public PluginAPI(IPlugin plugin, EventManager em, /*TODO*/Object ui) {
		this.plugin = plugin;
		this.em = em;
		this.ui = ui;
	}
	
	public boolean registerEvent(String eventName, ActionListener al) {
		return em.registerEvent(eventName, plugin.getId(), al);
	}
	
	public boolean unRegisterEvent(String eventName) {
		return em.unRegisterEvent(eventName, plugin.getId());
	}
	
	public Canvas getCanvas() {
		return null; //TODO return ui.canvas;
	}
	
	public void displayStatus(String status) {
		//TODO ui.statusbar.setText(status);
	}
	
}
