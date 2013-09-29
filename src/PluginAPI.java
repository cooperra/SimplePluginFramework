import java.awt.event.ActionListener;


public class PluginAPI {
	private IPlugin plugin;
	private EventManager em;
	
	public PluginAPI(IPlugin plugin, EventManager em) {
		this.plugin = plugin;
		this.em = em;
	}
	
	public boolean registerEvent(String eventName, ActionListener al) {
		return em.registerEvent(eventName, plugin.getId(), al);
	}
}
