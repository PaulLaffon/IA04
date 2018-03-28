package agent;

import behaviour.ReceiveWorkingAgent;
import jade.core.Agent;
import sudoku.Sudoku;

public class Environement extends Agent {
	
	private Sudoku sudoku;
	
	@Override
	protected void setup() {
		/*sudoku = new Sudoku(new Integer[][]{
			{5,0,0,0,0,4,0,0,8},
			{0,1,0,9,0,7,0,0,0},
			{0,9,2,8,5,0,7,0,6},
			{7,0,0,3,0,1,0,0,4},
			{0,0,0,0,0,0,0,0,0},
			{6,0,0,2,0,8,0,0,1},
			{1,0,8,0,3,2,4,9,0},
			{0,0,0,1,0,6,0,5,0},
			{3,0,0,7,0,0,0,0,2}
		}); */ 
		
		sudoku = new Sudoku("src/grid/grille1.txt");
		
		addBehaviour(new ReceiveWorkingAgent(sudoku));		
	}
}
