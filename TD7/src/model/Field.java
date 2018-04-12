package model;

import java.util.ArrayList;

import sim.field.grid.SparseGrid2D;
import sim.util.Bag;

public class Field {
	public final int WIDTH; 
	public final int HEIGHT; 
	
	private SparseGrid2D grid;
	
	public Field(SparseGrid2D grid) {
		this.grid = grid;
		WIDTH = grid.getWidth();
		HEIGHT = grid.getHeight();
	}

	public void updateLocation(Fieldable object) {
		grid.setObjectLocation(object, object.x(), object.y());
	}

	public void remove(Fieldable object) {
		grid.remove(object);
	}
	
	public <T> ArrayList<T> get(int x, int y, Class<T> c) {
		ArrayList<T> list = new ArrayList<T>();
		Bag bag = grid.getObjectsAtLocation(x % WIDTH, y % HEIGHT);
		if(bag == null)
			return list;
		for(int i = 0; i < bag.size(); i++)
			if(c.isInstance(bag.objs[i]))
				list.add((T)bag.objs[i]);
		return list;	
	}
	
	public <T> ArrayList<T> getNeighbour(int x, int y, int dist, Class<T> c, boolean excludeCenter) {
		ArrayList<T> list = new ArrayList<T>();
		for(int i = -dist; i <= dist; i++) {
			for(int j = -dist; j <= dist; j++) {
				if(excludeCenter && i == 0 && j == 0)
					continue;
				int x1 = (x+i)%WIDTH;
				if(x1 < 0) x1 += WIDTH;
				int y1 = (y+j)%HEIGHT;
				if(y1 < 0) y1 += HEIGHT;
				Bag bag = grid.getObjectsAtLocation(x1,y1);
				if(bag == null)
					continue;
				for(int id = 0; id < bag.size(); id++)
					if(c.isInstance(bag.objs[id]))
						list.add((T)bag.objs[id]);
			}
		}
		return list;
	}
}
