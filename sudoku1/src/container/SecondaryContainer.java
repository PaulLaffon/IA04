package container;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class SecondaryContainer {
	
	public static String SECONDARY_PROPERTIES_FILE = "src/config/secondary_container.txt";
	
	public static ContainerController cc;

	public static void main(String[] args) {
		
		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);
			
			AgentController simulation = cc.createNewAgent("Simulation", "agent.Simulation", new Object[] {"Simulation au rapport !"});
			simulation.start();
			
			AgentController environement = cc.createNewAgent("Environement", "agent.Environement", new Object[] {"Environement au rapport !"});
			environement.start();
			
			for(int i = 0; i < 1; i++) {
				AgentController a = cc.createNewAgent("Analyse" + i, "agent.Analyse", null);
				a.start();
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
