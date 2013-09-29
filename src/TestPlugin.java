
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class TestPlugin extends TemplatePlugin {

	public String getId() {
		return "TestPlugin";
	}

	public String getVersion() {
		return "0.0.0";
	}

	protected HashMap<String, ActionListener> getEventListeners() {
		HashMap<String, ActionListener> events = new HashMap<String, ActionListener>();
		events.put("onTheButton", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.toString());
			}
		});
		return events;
		
	}

	

}
