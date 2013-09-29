
public interface IPlugin {
	public String getId();
	public String getVersion();
	
	public boolean load(PluginAPI api);
	public boolean unload();
}
