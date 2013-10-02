import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The EventManager handles events accessable from the plugin framework.
 * 
 * These are the kinds of events that are supported:
 *  - theButton
 * 
 * On program start, the EventManager binds event listeners to all UI elements that have plugin-accessible events.
 * These behavior of these handlers is modified by the EventManager during runtime. 
 * 
 * @author cooperra
 *
 */
public class EventManager {
	
	ListenerStore eventRegistry = new ListenerStore();
	
	private class ListenerStore {
		// A map of event name -> plugin id -> list of ActionListeners
		HashMap<String, LinkedHashMap<String, ArrayList<ActionListener>>> data = new HashMap<String, LinkedHashMap<String, ArrayList<ActionListener>>>();
		
		public void initEventType(String eventName) {
			data.put("theButtonEvent", new LinkedHashMap<String, ArrayList<ActionListener>>());
		}
		
		/**
		 * Unregisters all events of a given type owned by a given plugin
		 * @param pluginId
		 * @param eventName
		 */
		public void unregisterEvent(String pluginId, String eventName) {
			data.get(eventName).remove(pluginId);
		}
		
		/**
		 * Unregisters all events owned by a particular plugin
		 * @param pluginId
		 */
		public void unregisterPlugin(String pluginId) {
			Collection<LinkedHashMap<String, ArrayList<ActionListener>>> pluginEvents = data.values();
			// Iterate through plugin -> [ActionListener] maps
			for (HashMap<String, ArrayList<ActionListener>> e : pluginEvents) {
				if (e.containsKey(pluginId)) {
					// if a list of ActionListeners exists for this plugin, remove it
					e.remove(pluginId);
				}
			}
		}
		
		// this is separated into a helper in case we split the event data structure into another class
		public boolean registerEvent(String eventName, String pluginId, ActionListener al) {
			//make sure eventName is valid
			if (eventName == null || !data.containsKey(eventName)) {
				return false;
			} else {
				HashMap<String, ArrayList<ActionListener>> mappingsForCurrentEvent = data.get(eventName);
				// make sure pluginId is not null
				if (pluginId == null) {
					return false;
				}
				// if plugin does not have entry here, create it
				if (!mappingsForCurrentEvent.containsKey(pluginId)) {
					mappingsForCurrentEvent.put(pluginId, new ArrayList<ActionListener>());
				}
				// Add plugin's ActionListener
				ArrayList<ActionListener> listenerList = mappingsForCurrentEvent.get(pluginId);
				return listenerList.add(al); // should return true
			}
		}
		
		public ArrayList<ActionListener> getAllListenersByEvent(String eventName) {
			ArrayList<ActionListener> allListeners = new ArrayList<ActionListener>(); 
			for (ArrayList<ActionListener> partialList : data.get(eventName).values()) {
				allListeners.addAll(partialList);
			}
			return allListeners;
		}
	}
	
	public EventManager() {
		// Set up default event types
		// This is where they are defined, by the way
		eventRegistry.initEventType("theButtonEvent"); //TODO add more
	}
	
	//TODO consider splitting class here
			
	/**
	 * 
	 * @param eventName Name of the event to register for
	 * @param pluginId Id of the registering plugin (for management purposes such as unregister on unload)
	 * @param action Handler for the event
	 * @return true if registration is successful, false otherwise
	 */
	public boolean registerEvent(String eventName, String pluginId, ActionListener action) {
		return eventRegistry.registerEvent(eventName, pluginId, action);
	}
	
	/**
	 * Unregisters all handlers registered by given plugin for given event. 
	 * 
	 * @param eventName Name of the event to register for
	 * @param pluginId Id of the unregistering plugin. Only events registered by this plugin are unregistered
	 * @return true if unregistration is successful, false otherwise
	 */
	public boolean unregisterEvent(String eventName, String pluginId) {
		eventRegistry.unregisterEvent(pluginId, eventName);
		return true;
	}
	
	/**
	 * Unregisters all handlers registered by given plugin for given event. 
	 * 
	 * @param eventName Name of the event to register for
	 * @param pluginId Id of the unregistering plugin. Only events registered by this plugin are unregistered
	 * @return true if unregistration is successful, false otherwise
	 */
	public boolean unregisterPlugin(String pluginId) {
		eventRegistry.unregisterPlugin(pluginId);
		return true;
	}
	
	//public void handleTheButton() {} //TODO: set up ActionListener for the button
	
	/**
	 * Set up handlers in the UI. These handlers forward events to EventManager.
	 * @param ui the ui module
	 */
	public void setUpHandlers(PlatformGui ui) {
		/*TODO*/
		// "theButtonEvent"
		// Bind a handler to theButton
		ui.GetExecuteButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			//hoogabooga TODO
		});
	}
	
}
