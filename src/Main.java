
public class Main {
	
	
	public static void main(String[] arg0){
		EventManager EM = new EventManager();
		PlugInLoader PLoader = new PlugInLoader(EM, "bin");
		PlatformGui GUI = new PlatformGui(PLoader);
		PLoader.receiveGUI(GUI);
		GUI.GetListPanel().updateList(PLoader.getNamesOfPlugins());
		

	
	}

}
