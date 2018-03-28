package behaviour;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import sudoku.Constants;

public class ParallelSimulation extends ParallelBehaviour {
	
	public ParallelSimulation(Agent a, ArrayList<AID> agents) {
		
		super(a, ParallelBehaviour.WHEN_ANY);
		
		addSubBehaviour(new ReceiveFinished());
		addSubBehaviour(new GoToWork(a, Constants.tickerTime, agents));
	}
}
