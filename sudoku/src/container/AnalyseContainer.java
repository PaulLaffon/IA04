package container;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import sudoku.Constants;

public class AnalyseContainer {

	public static String SECONDARY_PROPERTIES_FILE = "src/config/analyse_container.txt";

	public static ContainerController cc;

	public static void main(String[] args) {

		Runtime rt = Runtime.instance();
		ProfileImpl p = null;
		try {
			p = new ProfileImpl(SECONDARY_PROPERTIES_FILE);
			cc = rt.createAgentContainer(p);

			for (int i = 0; i < Constants.numberAgentPerContainer; i++) {
				AgentController a = cc.createNewAgent(cc.getContainerName() + ":Analyse" + i, "agent.Analyse", null);
				a.start();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
