/**
 * This is the plugin interface. All plugins must implement it. 
 * All plugins require an Id and a version. 
 * Additionally, plugins must have a load and unload method available. 
 * 
 * Plugins recive a PluginAPI object when they are loaded. Before they are
 * loaded, they can't do anything.
 * 
 * When they are unloaded, the PluginAPI object should be disabled. 
 * 
 * @author cooperra
 *
 */
public interface IPlugin {
	public String getId();
	public String getVersion();
	
	public boolean load();
	public boolean unload();
}
