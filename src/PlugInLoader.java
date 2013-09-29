import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class PlugInLoader {
	
	private static ArrayList<String[]> listOfPlugins;
	
	public static ArrayList<String[]> getPlugins(){
		return listOfPlugins;
	}
	
	/*
	 * xml or txt for dependencies reading
	 * jar or class for running
	 */
	
	/*
	 * Swaps the place of two of the plugins in the ArrayList
	 */
	
	public static Boolean swap(int position1, int position2){
		
		String[] temp = listOfPlugins.get(position1);
		listOfPlugins.set(position1, listOfPlugins.get(position2));
		listOfPlugins.set(position2, temp);
		
		return true;
	}
	
	/*
	 * Turns a single plugin off and returns true if it works, false otherwise
	 */
	
	public static Boolean turnPluginOff(int position){
		//Include way to check if any others are dependent
		String[] plugin = listOfPlugins.get(position);
		plugin[1] = "off";
		listOfPlugins.set(position, plugin);
		return true;
	}
	
	/*
	 * Turns a single plugin on and returns true if it works, false otherwise
	 */
	
	public static Boolean turnPluginOn(int position){
		//Include way to check if any others depend on it
		String[] plugin = listOfPlugins.get(position);
		plugin[1] = "on";
		listOfPlugins.set(position, plugin);
		return true;
	}
	
	/*
	 * Turns all plugins off and returns true if all plugins turned off properly
	 * False otherwise
	 */
	
	public static Boolean turnAllPluginsOff(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			//Include way to check if any others are dependent
			String[] plugin = listOfPlugins.get(i);
			plugin[1] = "off";
			listOfPlugins.set(i, plugin);
		}

		return true;
	}
	
	/*
	 * Turns all plugins on and returns the new list of plugins
	 */
	
	public static Boolean turnAllPluginsOn(){
		for(int i = 0; i < listOfPlugins.size(); i++){
			//Include way to check if any others depend on it
			String[] plugin = listOfPlugins.get(i);
			plugin[1] = "on";
			listOfPlugins.set(i, plugin);
		}

		return true;
	}

	/*
	 * Does all of the adding to the plugin list stuff
	 */
	
	private static void addPluginToList(final File folder, final File fileEntry) throws IOException{
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
	}
	
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
	
	public static ArrayList<String[]> findPluginsInSingleFolder(final File folder) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	    	addPluginToList(folder, fileEntry);
	    }
	    
		return listOfPlugins;
	}
	
	/*
	 * Updates plugin list with all plugins within a file and
	 * every directory that it holds
	 */

	public static ArrayList<String[]> findPluginsRecursiveSearch(final File folder) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (fileEntry.isDirectory()) {
	        	findPluginsRecursiveSearch(fileEntry);
	        } else {
	        	addPluginToList(folder, fileEntry);
	        }
	    }
	    
		return listOfPlugins;
	}
	
	public static void main(String[] args) throws IOException {

		final File file = new File("C:/Users/Administrator/Desktop/bonfire stuff/dur.txt");
		System.out.println(readFromFile(file));
		/*findPluginsRecursiveSearch(folder);
		for(String[] s : listOfPlugins){
			System.out.println(s[0]);
		}*/
	}

}
