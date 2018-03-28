package behaviour;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveFinished extends Behaviour {

	boolean over = false;
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		if(message != null) {
			over = true;
		}
		else {
			block();
		}
	}

	@Override
	public boolean done() {
		return over;
	}
}
