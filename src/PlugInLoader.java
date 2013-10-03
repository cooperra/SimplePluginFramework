import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class PlugInLoader {
	
	private static ArrayList<IPlugin> listOfPlugins = new ArrayList<IPlugin>();
	private static ArrayList<Boolean> pluginIsOn = new ArrayList<Boolean>();
	private static String pluginDirectory;
	private static PlatformGui GUI;
	private static EventManager EM;
	
	public static void swapPlugins(int position1, int position2){
		IPlugin tempPlugin = listOfPlugins.get(position1);
		Boolean tempOnOff = pluginIsOn.get(position1);
		
		listOfPlugins.set(position1, listOfPlugins.get(position2));
		pluginIsOn.set(position1, pluginIsOn.get(position2));
		
		listOfPlugins.set(position2, tempPlugin);
		pluginIsOn.set(position2, tempOnOff);
		
		GUI.GetListPanel().updateList(getNamesOfPlugins());
		
	}

	public static ArrayList<IPlugin> getPlugins(){
		return listOfPlugins;
	}
	
	
	public PlugInLoader(EventManager EM, String dir){
		pluginDirectory = dir;
		this.EM = EM;
		findPlugins();
	}
	
	public static String[] getNamesOfPlugins(){
		String[] returnArray = new String[listOfPlugins.size()];
		
		for (int i = 0; i < listOfPlugins.size(); i++) {
			returnArray[i] = listOfPlugins.get(i).getId();
		}
		
		return returnArray;
	}
	
	public static Boolean findPlugins(){
		File dir = new File(System.getProperty("user.dir") + File.separator + pluginDirectory);
		ClassLoader cl = new PluginClassLoader(dir);
		if (dir.exists()) {
			if(dir.isDirectory()){
				String[] files = dir.list();
				for (int i=0; i<files.length; i++) {
					try {
						if (! files[i].endsWith(".class"))
							continue;

						Class<?> c = cl.loadClass(files[i].substring(0, files[i].indexOf(".")));
						Class[] intf = c.getInterfaces();
						
						for (int j=0; j<intf.length; j++) {
							if (intf[j].getName().equals("IPlugin")) {

								IPlugin ip = (IPlugin) c.newInstance();
								listOfPlugins.add(ip);
								pluginIsOn.add(false);
								continue;
							}
						}
					} catch (Exception ex) {
						System.err.println("File " + files[i] + " does not contain a valid PluginFunction class.");
						return false;
					}				
				}
			}else{
				System.out.println("File is not a directory");
				return false;
			}
		}else{
			System.out.println("Directory does not exist");
			return false;
		}
		
		if(listOfPlugins.isEmpty()){
			System.out.println("There are no plugins");
			return false;
		}
		return true;
	}
	
	public static void runPlugins() {

		for(int i = 0; i < listOfPlugins.size(); i++){
			if(pluginIsOn.get(i)){
				IPlugin pf = listOfPlugins.get(i);
				runPlugin(pf);
			}
		}
	}
	
	public void receiveGUI(PlatformGui GUI){
		this.GUI = GUI;
	
		
	}
	
	public static void runPlugin(IPlugin pf) {
		try {
			PluginAPI api = new PluginAPI(pf, EM, GUI);
			pf.load(api);
		} catch (SecurityException secEx) {
			System.err.println("plugin '"+pf.getClass().getName()+"' tried to do something illegal");
		}
	}
	
	/*
	 * Turns a single plugin off
	 */

	
	public static void turnPluginOff(int position){
		pluginIsOn.set(position,false);
	}
	
	/*
	 * Turns a single plugin on
	 */
	
	public static void turnPluginOn(int position){
		//Include way to check if any others depend on it
		pluginIsOn.set(position,true);
	}
	
	/*
	 * Turns all plugins off
	 */
	
	public static void turnAllPluginsOff(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			pluginIsOn.set(i, false);
		}

	}
	
	/*
	 * Turns all plugins on
	 */
	
	public static void turnAllPluginsOn(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			pluginIsOn.set(i, true);
		}
	}
}
