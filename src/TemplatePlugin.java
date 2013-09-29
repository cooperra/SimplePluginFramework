
import java.awt.event.ActionListener;
import java.util.HashMap;


public abstract class TemplatePlugin implements IPlugin {
	protected PluginAPI api;
	
	public abstract String getId();
	
	public abstract String getVersion(); 

	public boolean load(PluginAPI api) {
		this.api = api;
		this.registerEvents();
		return true;
	}

	public boolean unload() {
		this.api = null;
		return true;
	}
	
	private void registerEvents() {
		HashMap<String, ActionListener> listeners = this.getEventListeners();
		for (String event : listeners.keySet()) {
			this.api.registerEvent(event, listeners.get(event));
		}
	}
	
	/**
	 * 
	 * @return A mappings of event names to event listeners
	 */
	abstract protected HashMap<String, ActionListener> getEventListeners();

}
