package agent;

import java.util.ArrayList;

import behaviour.ParallelSimulation;
import behaviour.ReceiveSubscription;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import sudoku.Constants;

public class Simulation extends Agent {
	
	private ArrayList<AID> analyseAgents;
	
	@Override
	protected void setup() {
		analyseAgents = new ArrayList<AID>();
		
		SequentialBehaviour sequBehaviour = new SequentialBehaviour();
		sequBehaviour.addSubBehaviour(new ReceiveSubscription(analyseAgents, Constants.subscriptionTimeout));
		sequBehaviour.addSubBehaviour(new ParallelSimulation(this, analyseAgents));

		addBehaviour(sequBehaviour);
	}
}
