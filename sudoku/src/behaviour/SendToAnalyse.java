package behaviour;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sudoku.Case;
import sudoku.Group9Cases;
import sudoku.Sudoku;

public class SendToAnalyse extends Behaviour {
	private MessageTemplate template;
	private boolean over = false;
	private int index;
	private Sudoku sudoku;
	
	
	public SendToAnalyse(Agent a, ACLMessage message, Sudoku _sudoku, int _index) {
		super(a);
		
		index = _index;
		sudoku = _sudoku;
		
		String id = UUID.randomUUID().toString();
		template = MessageTemplate.MatchConversationId(id);
		message.setConversationId(id);
		
		message.setContent(sudoku.getGroup(index).toJSON());

		
		myAgent.send(message);
	}
	
	public void action() {
		ACLMessage message = myAgent.receive(template);
		if(message != null) {
			
			Group9Cases g = Group9Cases.fromJSON(message.getContent());
			
			sudoku.setFromGroup(index, g);
			
			sudoku.draw();
			
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
