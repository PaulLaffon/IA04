package agent;

import behaviour.ReceiveAnalyse;
import behaviour.Subscribe;
import jade.core.Agent;

public class Analyse extends Agent {
	
	@Override
	protected void setup() {
		addBehaviour(new Subscribe());
		addBehaviour(new ReceiveAnalyse());
	}
}
