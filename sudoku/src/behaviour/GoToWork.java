package behaviour;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class GoToWork extends TickerBehaviour{
	
	private ArrayList<AID> analyseAgents;

	public GoToWork(Agent a, long period, ArrayList<AID> agents) {
		super(a, period);
		analyseAgents = agents;
	}

	@Override
	protected void onTick() {
		System.out.println(analyseAgents.size() + " agents go to work !");
		for(int i = 0; i < analyseAgents.size(); i++) {
			ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
			message.addReceiver(new AID("Environement", AID.ISLOCALNAME));
			message.addReplyTo(analyseAgents.get(i));
			myAgent.send(message);
		}
	}
}
