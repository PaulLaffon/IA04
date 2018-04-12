package model;

import main.Constants;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.grid.SparseGrid2D;

public class Food implements Fieldable {
	private int units;
	private int x, y;
	private Field field;
	
	
	public Food(Field field) {
		this.field = field;
		spawn();
	}

	private void spawn() {
		units = Constants.MAX_FOOD;
		x = (int)Math.round(Math.random()*(field.WIDTH-1));
		y = (int)Math.round(Math.random()*(field.HEIGHT-1));
		field.updateLocation(this);
	}
	
	public void removeOneUnit() {
		units--;
		if(units <= 0) {
			spawn();
		}
	}

	@Override
	public int x() {
		return x;
	}

	@Override
	public int y() {
		return y;
	}
	
}
