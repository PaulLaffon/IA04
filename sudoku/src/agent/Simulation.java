package agent;

import java.util.ArrayList;

import behaviour.GoToWork;
import behaviour.ParallelSimulation;
import behaviour.ReceiveSubscrition;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Simulation extends Agent {
	
	private ArrayList<AID> analyseAgents;
	
	@Override
	protected void setup() {
		analyseAgents = new ArrayList<AID>();
		
		addBehaviour(new ReceiveSubscrition(analyseAgents));
		addBehaviour(new ParallelSimulation(this, analyseAgents));
	}
}
