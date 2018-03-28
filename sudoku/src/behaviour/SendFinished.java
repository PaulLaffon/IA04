package behaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendFinished extends OneShotBehaviour {

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(new AID("Simulation", AID.ISLOCALNAME));
		
		myAgent.send(message);
	}
}
