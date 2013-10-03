import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class PlugInLoader {
	
	private static ArrayList<IPlugin> listOfPlugins = new ArrayList<IPlugin>();
	private static String pluginDirectory;
	private static PlatformGui GUI;
	private static EventManager EM;

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
		System.out.println(System.getProperty("user.dir") + File.separator + pluginDirectory);
		File dir = new File(System.getProperty("user.dir") + File.separator + pluginDirectory);
		ClassLoader cl = new PluginClassLoader(dir);
		if (dir.exists()) {
			if(dir.isDirectory()){
				String[] files = dir.list();
				for (int i=0; i<files.length; i++) {
					try {
						// only consider files ending in ".class"
						if (! files[i].endsWith(".class"))
							continue;

						Class<?> c = cl.loadClass(files[i].substring(0, files[i].indexOf(".")));
						Class[] intf = c.getInterfaces();
						for (int j=0; j<intf.length; j++) {
							if (intf[j].getName().equals("IPlugin")) {
								// the following line assumes that PluginFunction has a no-argument constructor
								IPlugin ip = (IPlugin) c.newInstance();
								listOfPlugins.add(ip);
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

		Iterator<IPlugin> iter = listOfPlugins.iterator();
		while (iter.hasNext()) {
			IPlugin pf = iter.next();
			runPlugin(pf);
		}
	}
	
	public void receiveGUI(PlatformGui GUI){
		this.GUI = GUI;
	
		
	}
	
	public static void runPlugin(IPlugin pf) {
		try {
			PluginAPI api = new PluginAPI(pf, EM, GUI);
			pf.load(api);
			/*System.out.print(pf.getPluginName());
			System.out.print(" ( "+count+" ) = ");*/
			/*if (pf.hasError()) {
				System.out.println("there was an error during plugin initialization");
				continue;
			}
			int result = pf.getResult();
			if (pf.hasError())
				System.out.println("there was an error during plugin execution");
			else
				System.out.println(result);
			count++;*/
		} catch (SecurityException secEx) {
			System.err.println("plugin '"+pf.getClass().getName()+"' tried to do something illegal");
		}
	}
	
	/*
	 * xml or txt for dependencies reading
	 * jar or class for running
	 */
	
	/*
	 * Swaps the place of two of the plugins in the ArrayList
	 */
	
	/*public static Boolean swap(int position1, int position2){
		
		String[] temp = listOfPlugins.get(position1);
		listOfPlugins.set(position1, listOfPlugins.get(position2));
		listOfPlugins.set(position2, temp);
		
		return true;
	}*/
	
	/*
	 * Turns a single plugin off and returns true if it works, false otherwise
	 */
	
	/*public static Boolean turnPluginOff(int position){
		//Include way to check if any others are dependent
		String[] plugin = listOfPlugins.get(position);
		plugin[1] = "off";
		listOfPlugins.set(position, plugin);
		return true;
	}*/
	
	/*
	 * Turns a single plugin on and returns true if it works, false otherwise
	 */
	
	/*public static Boolean turnPluginOn(int position){
		//Include way to check if any others depend on it
		String[] plugin = listOfPlugins.get(position);
		plugin[1] = "on";
		listOfPlugins.set(position, plugin);
		return true;
	}*/
	
	/*
	 * Turns all plugins off and returns true if all plugins turned off properly
	 * False otherwise
	 */
	
	/*public static Boolean turnAllPluginsOff(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			//Include way to check if any others are dependent
			String[] plugin = listOfPlugins.get(i);
			plugin[1] = "off";
			listOfPlugins.set(i, plugin);
		}

		return true;
	}*/
	
	/*
	 * Turns all plugins on and returns the new list of plugins
	 */
	
	/*public static Boolean turnAllPluginsOn(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			//Include way to check if any others depend on it
			String[] plugin = listOfPlugins.get(i);
			plugin[1] = "on";
			listOfPlugins.set(i, plugin);
		}

		return true;
	}*/

	/*
	 * Does all of the adding to the plugin list stuff
	 */
	
	/*private static void addPluginToList(final File folder, final File fileEntry) throws IOException{
       	String fileName = fileEntry.getName();
       	String dependenciesFileName = fileName.substring(0,fileName.lastIndexOf('.')) + ".xml";
       	File dependencyFile = new File(folder+dependenciesFileName);
       	
        if(fileName.substring(fileName.lastIndexOf('.') + 1).equals("jar") &&
        		dependencyFile.exists()){

        	String[] plugin = new String[3];
        	plugin[0] = fileName;
        	plugin[1] = "off";
        	
        	String dependencies = readFromFile(dependencyFile);
        	plugin[2] = dependencies;
        	listOfPlugins.add(plugin);
        	
       	}
	}*/
	
	/*
	 * Reads from a file
	 */
	
	private static String readFromFile(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		String fileText = "";
		
		while ((line = reader.readLine()) != null){
			fileText += line + "\n";
		}
		
		return fileText;
	}
	
	/*
	 * Updates plugin list with all plugins within a single file
	 * Does not search in deeper files
	 */
	
	/*public static ArrayList<String[]> findPluginsInSingleFolder(final File folder) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	    	addPluginToList(folder, fileEntry);
	    }
	    
		return listOfPlugins;
	}*/
	
	/*
	 * Updates plugin list with all plugins within a file and
	 * every directory that it holds
	 */

	/*public static ArrayList<String[]> findPluginsRecursiveSearch(final File folder) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (fileEntry.isDirectory()) {
	        	findPluginsRecursiveSearch(fileEntry);
	        } else {
	        	addPluginToList(folder, fileEntry);
	        }
	    }
	    
		return listOfPlugins;
	}*/
	
	/*public static void main(String[] args) throws IOException {

		findPlugins();
		

		
		runPlugins();*/
		
		//System.out.println(System.getProperty("user.dir"));
		
		//final File file = new File("C:/Users/Administrator/Desktop/bonfire stuff/dur.txt");
		//System.out.println(readFromFile(file));
		/*findPluginsRecursiveSearch(folder);
		for(String[] s : listOfPlugins){
			System.out.println(s[0]);
		}*/
	/*}*/

}
