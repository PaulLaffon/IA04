package behaviour;

import java.util.ArrayList;
import java.util.List;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sudoku.Case;
import sudoku.Group9Cases;

public class ReceiveAnalyse extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		if(message != null) {
			
			Group9Cases g  = Group9Cases.fromJSON(message.getContent());
			
			algo1(g);
			
			algo2(g);
			//algo1(g);
			
			algo3(g);
			//algo1(g);
			
			//algo4(g);
			//algo1(g);
			
			ACLMessage reply = message.createReply();
			reply.setContent(g.toJSON());
			reply.setPerformative(ACLMessage.INFORM);
			
			myAgent.send(reply);
		}
		else {
			block();
		}
	}
	
	
	private void algo1(Group9Cases cases) {
		
		for(Case c : cases.getCases()) {
			if(c.getPossibleValues().size() == 1) {
				c.setValue(c.getPossibleValues().get(0));
				c.getPossibleValues().clear();
			}
		}
	}
	
	private void algo2(Group9Cases cases) {
		
		List<Integer> alreadyPresent = new ArrayList<Integer>();
		
		for(Case c : cases.getCases()) {
			alreadyPresent.add(c.getValue());
		}
		
		for(Case c : cases.getCases()) {
			c.getPossibleValues().removeAll(alreadyPresent);
		}
	}
	
	private void algo3(Group9Cases cases) {
		
		Integer[] count = new Integer[10];
		
		for(int i = 0; i < 10; i++) {
			count[i] = 0;
		}
		
		for(Case c : cases.getCases()) {
			for(Integer i : c.getPossibleValues()) {
				count[i] += 1;
			}
		}
		
	
		for(int i = 1; i < 10; i++) {
			if(count[i] == 1) {
				for(Case c : cases.getCases()) {
					if(c.getPossibleValues().contains(i)) {
						c.setValue(i);
						c.getPossibleValues().clear();
						//c.getPossibleValues().add(i);
						break;
					}
				}
			}
		}
	}
	
	private void algo4(Group9Cases cases) {
		
		for(int i = 0; i < 9; i++) {
			ArrayList<Integer> possibleValueI = cases.getCase(i).getPossibleValues();
			if(possibleValueI.size() != 2)
				continue;
			
			for(int j = i + 1; j < 9; j++) {
				ArrayList<Integer> possibleValueJ = cases.getCase(j).getPossibleValues();
	
				if(possibleValueI.equals(possibleValueJ)) {
					for(int k = 0; k < 9; k++) {
						if(k != i && k != j) {
							cases.getCase(k).getPossibleValues().removeAll(possibleValueI);
						}
					}
				}
			}
		}
	}
}
