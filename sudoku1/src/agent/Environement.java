package agent;

import behaviour.ReceiveWorkingAgent;
import behaviour.SendFinished;
import jade.core.Agent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sudoku.Sudoku;

public class Environement extends Agent {
	
	private Sudoku sudoku;
	
	@Override
	protected void setup() {
		sudoku = new Sudoku("src/grid/grille5.txt");
		
		addBehaviour(new ReceiveWorkingAgent(sudoku));	
		
		sudoku.getFinishedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				addBehaviour(new SendFinished());
			}
		});
	}
}
