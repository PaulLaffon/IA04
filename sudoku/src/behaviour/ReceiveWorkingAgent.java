package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sudoku.Sudoku;

public class ReceiveWorkingAgent extends CyclicBehaviour {
	
	private int compteur = 0;
	private Sudoku sudoku;

	public ReceiveWorkingAgent(Sudoku s) {
		sudoku = s;
	}
	
	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		if(message != null) {
			myAgent.addBehaviour(new SendToAnalyse(myAgent, message.createReply(), sudoku, compteur));
			
			compteur++;
			compteur %= 27;
		}
		else {
			block();
		}
	}
}