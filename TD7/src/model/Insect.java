package model;

import java.util.ArrayList;

import main.Constants;
import sim.engine.Schedule;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import strategy.AI;
import strategy.Genetic;
import utils.Direction;

public class Insect implements Steppable, Fieldable {
	public final int MAX_MOVE;
	public final int MAX_LOAD;
	public final int MAX_PERCEPTION;
	public final AI INSECT_AI;
	
	private static int numInstance = 0;
	public static int getNumInstance() { return numInstance; }
	
	private Field field;
	private Stoppable stoppable;
	
	private int x, y;
	int actionPoints = 0;
	
	int energy = 0;
	private static int totalEnergy = 0;
	public static int getTotalEnergy() { return totalEnergy; }
	private void addEnergy(int delta) {
		energy += delta;
		totalEnergy += delta;
		if(energy > Constants.MAX_ENERGY) {
			totalEnergy -= energy - Constants.MAX_ENERGY;
			energy = Constants.MAX_ENERGY;
		}
	}
	
	int load = 0;
	private static int totalLoad = 0;
	public static int getTotalLoad() { return totalLoad; }
	private void addLoad(int delta) {
		load += delta;
		totalLoad += delta;
		if(load > MAX_LOAD) {
			totalLoad -= load - MAX_LOAD;
			load = MAX_LOAD;
		}
	}
	
	public Insect(int x, int y, Field field, Genetic gen, AI ai, Schedule schedule) {
		MAX_MOVE = gen.MAX_MOVE;
		MAX_LOAD = gen.MAX_LOAD;
		MAX_PERCEPTION = gen.MAX_PERCEPTION;
		
		INSECT_AI = ai;
		
		this.x = x;
		this.y = y;
		
		this.field = field;
		this.field.updateLocation(this);

		stoppable = schedule.scheduleRepeating(this);
		
		numInstance++;
		addEnergy(Constants.MAX_ENERGY);
	}

	public void move(Direction dir, int distance) throws NoActionPointsException {
		if(actionPoints < 1)
			throw new NoActionPointsException("Not enougth actionPoints");
		if(distance > MAX_MOVE)
			distance = MAX_MOVE;
		int newX = x + (dir.getValue() % 3 - 1) * distance;
		int newY = y + (dir.getValue() / 3 - 1) * distance;
		newX = newX % field.WIDTH;
		if(newX < 0) newX += field.WIDTH;
		newY = newY % field.HEIGHT;
		if(newY < 0) newY += field.HEIGHT;
		x = newX;
		y = newY;
		addEnergy(-1);
		actionPoints--;
	}
	
	public void move(int dx, int dy) throws NoActionPointsException, NotAllowException {
		if(actionPoints < 1)
			throw new NoActionPointsException("Not enougth actionPoints");
		if(dx < -MAX_MOVE)
			dx = -MAX_MOVE;
		if(dx > MAX_MOVE)
			dx = MAX_MOVE;
		if(dy < -MAX_MOVE)
			dy = -MAX_MOVE;
		if(dy > MAX_MOVE)
			dy = MAX_MOVE;
		int newX = x + dx;
		int newY = y + dy;
		newX = newX % field.WIDTH;
		if(newX < 0) newX += field.WIDTH;
		newY = newY % field.HEIGHT;
		if(newY < 0) newY += field.HEIGHT;
		x = newX;
		y = newY;
		addEnergy(-1);
		actionPoints--;
	}
	
	public void takeFood() throws NoActionPointsException, NotAllowException {
		if(actionPoints < 1)
			throw new NoActionPointsException("Not enougth actionPoints");
		ArrayList<Food> foodList = field.get(x, y, Food.class);
		if(foodList.size() == 0)
			throw new NotAllowException("No food to take");
		foodList.get(0).removeOneUnit();
		addLoad(+1);
		addEnergy(-1);
		actionPoints--;
	}
	
	public void eatFood() throws NoActionPointsException, NotAllowException {
		if(actionPoints < 1)
			throw new NoActionPointsException("Not enougth actionPoints");
		if(energy == Constants.MAX_ENERGY)
			throw new NotAllowException("Max energy reached");
		ArrayList<Food> foodList = field.getNeighbour(x, y, 1, Food.class, true);
		if(foodList.size() == 0)
			throw new NotAllowException("No food arround to eat");
		foodList.get(0).removeOneUnit();
		addEnergy(Constants.FOOD_ENERGY);
		actionPoints--;
	}
	
	public void eatLoad() throws NoActionPointsException, NotAllowException {
		if(actionPoints < 1)
			throw new NoActionPointsException("Not enougth actionPoints");
		if(energy == Constants.MAX_ENERGY)
			throw new NotAllowException("Max energy reached");
		if(load == 0)
			throw new NotAllowException("No loaded food to eat");
		addLoad(-1);
		addEnergy(Constants.FOOD_ENERGY);
		actionPoints--;
	}
	
	public <T> ArrayList<T> lookAround(Class<T> c) {
		return field.getNeighbour(x, y, MAX_PERCEPTION, c, false);
	}
	
	@Override
	public void step(SimState arg0) {
		actionPoints = 1;
		INSECT_AI.strategy(this);
		if(actionPoints == 1) {
			try {
				move(0,0);
			} catch (NoActionPointsException | NotAllowException e) {
			}
		}
		if(energy <= 0) {
			System.out.println("Died: MAX_MOVE " + MAX_MOVE + ", MAX_LOAD " + MAX_LOAD + ", MAX_PERCEPTION " + MAX_PERCEPTION);
			field.remove(this);
			stoppable.stop();
			numInstance--;
		}
		else
			field.updateLocation(this);
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}

	public int energy() {
		return energy;
	}
	
	public int load() {
		return load;
	}
}
