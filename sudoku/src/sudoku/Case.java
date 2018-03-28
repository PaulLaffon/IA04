package sudoku;

import java.util.ArrayList;

public class Case {
	private int value; // 0 à 9
	
	private ArrayList<Integer> possibleValues;
	
	public Case() { } // To make Case serializable
	
	public Case(int v) {
		value = v;
		possibleValues = new ArrayList<Integer>();
		
		if(value == 0) {
			for(int i = 1; i <= 9; i++) {
				possibleValues.add(i);
			}
		}
	}
	
	public int getValue() {return value;}
	public void setValue(Integer _value) { value = _value; }
	
	public ArrayList<Integer> getPossibleValues() { return possibleValues; }
	
	public void merge(Case c) {
		
		if(this.value != 0)
			return;
		
		ArrayList<Integer> newList = new ArrayList<Integer>();
		for(Integer i : possibleValues) {
			if(c.possibleValues.contains(i)) {
				newList.add(i);
			}
		}
		possibleValues = newList;

		this.value = c.getValue();
	}	
}
