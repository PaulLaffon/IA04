package behaviour;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveSubscription extends Behaviour {
	
	int timeout;
	long startTime;
	
	private ArrayList<AID> analyseAgents;
	
	public ReceiveSubscription(ArrayList<AID> agents, int timeout) {
		analyseAgents = agents;
		this.timeout = timeout;
	}
	
	@Override
	public void onStart() {
		System.out.println("Wait for subscriptions...");
		startTime = System.currentTimeMillis();
	}

	@Override
	public int onEnd() {
		System.out.println("Subscription timeout");
		return super.onEnd();
	}

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE));
		
		if(message != null) {
			analyseAgents.add(message.getSender());
		}
	}

	@Override
	public boolean done() {
		//System.out.println("Time " + System.currentTimeMillis() + " wait " + (startTime+timeout));
		//System.out.println((Boolean)((System.currentTimeMillis()-startTime) > timeout));
		return (System.currentTimeMillis()-startTime) > timeout;
	}
}
