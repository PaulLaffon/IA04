package container;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import sudoku.Constants;

public class SecondaryContainer {

	public static String SECONDARY_PROPERTIES_FILE = "src/config/secondary_container.txt";

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

			for (int i = 0; i < Constants.numberAgent; i++) {
				AgentController a = cc.createNewAgent("Analyse" + i, "agent.Analyse", null);
				a.start();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
