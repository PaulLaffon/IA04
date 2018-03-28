package behaviour;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

// TODO : Behaviour temporel
public class ReceiveSubscrition extends CyclicBehaviour {
	
	private ArrayList<AID> analyseAgents;
	
	public ReceiveSubscrition(ArrayList<AID> agents) {
		analyseAgents = agents;
	}
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE));
		
		if(message != null) {
			analyseAgents.add(message.getSender());
		}
		else {
			block();
		}
	}
}
