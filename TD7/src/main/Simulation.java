package main;

import model.Field;
import model.Food;
import model.Insect;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
import strategy.AI;
import strategy.Genetic;
import strategy.InvalidGeneticException;

public class Simulation extends SimState  {
	private SparseGrid2D grid = new SparseGrid2D(Constants.WIDTH, Constants.HEIGHT);
	private Field field;
	
	public Simulation(long seed) {
		super(seed);
	}
	
	public void start() {
		System.out.println("Simulation started");
		super.start();
		
		grid.clear();
		field = new Field(grid);
		for(int i = 0; i < Constants.NUM_INSECT; i++) {
			int x = (int)Math.round(Math.random()*(field.WIDTH-1));
			int y = (int)Math.round(Math.random()*(field.HEIGHT-1));
			try {
				Insect insect = new Insect(x, y, field, new Genetic(3,3,4), new AI.CarryFood(), schedule);
			} catch (InvalidGeneticException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < Constants.NUM_FOOD_CELL; i++) {
			Food food = new Food(field);
		}
	}
	
	public SparseGrid2D getGrid() {
		return grid;
	}
	
	public int getNumInsect() {
		return Insect.getNumInstance();
	}
	public int getTotalEnergy() {
		return Insect.getTotalEnergy();
	}
	public float getEnergyRelativeInsect() {
		return Insect.getTotalEnergy()/(float)Constants.MAX_ENERGY;
	}
	public int getTotalLoad() {
		return Insect.getTotalLoad();
	}
	public float getEnergyAvailRelativeInsect() {
		return getEnergyRelativeInsect()+(Insect.getTotalLoad()*Constants.FOOD_ENERGY/(float)Constants.MAX_ENERGY);
	}
}
