package container;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import sudoku.Constants;

public class SimulationContainer {

	public static String SECONDARY_PROPERTIES_FILE = "src/config/simulation_container.txt";

	public static ContainerController cc;

	public static void main(String[] args) {

		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);

			AgentController simulation = cc.createNewAgent("Simulation", "agent.Simulation", null);
			simulation.start();

			AgentController environement = cc.createNewAgent("Environement", "agent.Environement", null);
			environement.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
