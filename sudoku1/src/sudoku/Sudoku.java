package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Sudoku {
	
	private Case[][] grille;
	private BooleanProperty finished;
	
	public Sudoku(String filename) {
		grille = new Case[9][9];
		Scanner scanner = null;
		
		finished = new SimpleBooleanProperty(false);
		
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				grille[i][j] = new Case(scanner.nextInt());
			}
		}
	}
	
	public BooleanProperty getFinishedProperty() { return finished; }
	
	public void check() {
		for(int i = 0; i < 27; i++) {
			if(!getGroup(i).check())
				return;
		}
		
		finished.setValue(true);
	}
	
	public void draw() {
		
		System.out.println("");
		System.out.println("");
		
		for(int i = 0; i < 9; i++) {
			
			System.out.println("");
			
			if(i % 3 == 0) {
				System.out.println("  -----------------");
			}
			
			for(int j = 0; j < 9; j++) {
				
				if(j % 3 == 0)
					System.out.print(" | ");
				
				System.out.print(grille[i][j].getValue());
			}
			System.out.print(" |");
		}
		System.out.print("\n  -----------------");
		System.out.println(finished.getValue());
	}
	
	public Group9Cases getGroup(int index) {
		
		// Ligne
		if(index < 9) {
			return new Group9Cases(grille[index]);
		}
		else if(index < 18) { // Colonnes
			Case[] col = new Case[9];
			
			for(int i = 0; i < 9; i++) {
				col[i] = grille[i][index - 9];
			}
			
			return new Group9Cases(col); 
		}
		else { // Carres
			index = index - 18;
			Case[] carre = new Case[9];
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					carre[i * 3 + j] = grille[(index / 3)*3 + i][(index % 3)*3 + j];
				}
			}
			return new Group9Cases(carre);
		}
	}
	
	public void setFromGroup(int index, Group9Cases group) {
		
		if(index < 9) { // Ligne
			for(int i = 0; i < 9; i++) {
				grille[index][i].merge(group.getCase(i));
			}
		}
		else if(index < 18) { // Colonnes			
			for(int i = 0; i < 9; i++) {
				grille[i][index - 9].merge(group.getCase(i));
			}
		}
		else { // Carres
			index = index - 18;
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					grille[(index / 3)*3 + i][(index % 3)*3 + j].merge(group.getCase(i * 3 + j));
				}
			}
		}
		
		check();
	}
}
