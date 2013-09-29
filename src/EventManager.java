
public class EventManager {
	
	/**
	 * 
	 * @param eventName Name of the event to register for
	 * @param pluginId Id of the registering plugin (for management purposes such as unregister on unload)
	 * @param action Handler for the event
	 * @return true if registration is successful, false otherwise
	 */
	public boolean registerEvent(String eventName, String pluginId, /*TODO*/Object action) {
		return false;//TODO
	}
	
	/**
	 * Unregisters all handlers registered by given plugin for given event. 
	 * 
	 * @param eventName Name of the event to register for
	 * @param pluginId Id of the unregistering plugin. Only events registered by this plugin are unregistered
	 * @return true if unregistration is successful, false otherwise
	 */
	public boolean unRegisterEvent(String eventName, String pluginId) {
		return false;//TODO
	}
	
	public void handleTheButton() {} //TODO: set up ActionListener for the button
	
	public void setUpHandlers(/*TODO*/Object ui) {
		/*TODO*/
	}
}
