package container;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class MainContainer {
	
	public static String MAIN_PROPERTIES_FILE = "src/config/main_container.txt";

	public static void main(String[] args) {
		
		Runtime rt = Runtime.instance();
		Profile p = null;
		try{
			p = new ProfileImpl(MAIN_PROPERTIES_FILE);
			AgentContainer mc = rt.createMainContainer(p);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
