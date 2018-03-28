package behaviour;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Subscribe extends OneShotBehaviour {

	@Override
	public void action() {
		ACLMessage message = new ACLMessage(ACLMessage.SUBSCRIBE);
		message.addReceiver(new AID("Simulation",  AID.ISLOCALNAME));
		System.out.println(myAgent);
		myAgent.send(message);
	}
	
}
