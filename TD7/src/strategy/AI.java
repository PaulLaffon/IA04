package strategy;

import java.util.ArrayList;

import main.Constants;
import model.Food;
import model.Insect;
import model.NoActionPointsException;
import model.NotAllowException;
import sim.util.Int2D;
import utils.Direction;

public interface AI {
	public void strategy(Insect insect);
	
	public class Dumb implements AI {
		@Override
		public void strategy(Insect insect) {
			try {
				insect.eatFood();
			} catch (NoActionPointsException e) {
				return;
			} catch (NotAllowException e) {}
			try {
				insect.move(Direction.getRandom(), (int)Math.round(Math.random()*insect.MAX_MOVE));
			} catch (NoActionPointsException e) {
				return;
			}
		}
	}
	
	public class EatClosestFood implements AI {
		Direction lastDirection = Direction.getRandom();
		
		public int distance(int x1, int y1, int x2, int y2) {
			int dx = ((x2-x1+Constants.WIDTH/2) % Constants.WIDTH)-Constants.WIDTH/2;
			int dy = ((y2-y1+Constants.HEIGHT/2) % Constants.HEIGHT)-Constants.HEIGHT/2;
			return Math.max(Math.abs(dx), Math.abs(dy));
		}
		
		@Override
		public void strategy(Insect insect) {
			Food nearest = null;
			ArrayList<Food> foods = insect.lookAround(Food.class);
			for(Food food : foods) {
				if(nearest == null || distance(insect.x(), insect.y(), food.x(), food.y()) < distance(insect.x(), insect.y(), nearest.x(), nearest.y()))
					nearest = food;
			}
			if(nearest != null ) {
				if(distance(insect.x(), insect.y(), nearest.x(), nearest.y()) == 1) {
					try {
						insect.eatFood();
					} catch (NoActionPointsException | NotAllowException e) {
					}
				}
				else {
					int dx = ((nearest.x()-insect.x()+Constants.WIDTH/2) % Constants.WIDTH)-Constants.WIDTH/2;
					int dy = ((nearest.y()-insect.y()+Constants.HEIGHT/2) % Constants.HEIGHT)-Constants.HEIGHT/2;
					dx -= dx > 0 ? 1 : dx < 0 ? -1 : 0;
					dy -= dy > 0 ? 1 : dy < 0 ? -1 : 0;
					if(dx == 0 && dy == 0) {
						dx++;
					}
					try {
						insect.move(dx, dy);
					} catch (NoActionPointsException | NotAllowException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					double rnd = Math.random();
					if(rnd < 0.2)
						lastDirection = lastDirection.right();
					else if(rnd < 0.4)
						lastDirection = lastDirection.left();
					insect.move(lastDirection, (int)Math.round(Math.random()*insect.MAX_MOVE));
				} catch (NoActionPointsException e) {
					return;
				}
			}
		}	
	}
	
	public class CarryFood implements AI {
		boolean debug;
		
		Direction lastDirection = Direction.getRandom();
		
		public CarryFood() {
			this(false);
		}
		public CarryFood(boolean debug) {
			this.debug = debug;
		}
		
		public int distance(int x1, int y1, int x2, int y2) {
			int dx = x2-x1;
			if(dx < -Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))+Constants.WIDTH/2;
			else if(dx > Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))-Constants.WIDTH/2;
			int dy = y2-y1;
			if(dy < -Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))+Constants.HEIGHT/2;
			else if(dy > Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))-Constants.HEIGHT/2;
			return Math.max(Math.abs(dx), Math.abs(dy));
		}
		
		public void explore(Insect insect) {
			float chageEvry = 10;
			float changeDirProb = 1/(chageEvry/insect.MAX_MOVE);
			try {
				double rnd = Math.random();
				if(rnd < changeDirProb) {
					if(rnd < changeDirProb/2) 
						lastDirection = lastDirection.right();
					else
						lastDirection = lastDirection.left();
				}
				insect.move(lastDirection, insect.MAX_MOVE);
				if(debug) System.out.println("Explore : " + lastDirection.toString() + " " + insect.MAX_MOVE);
			} catch (NoActionPointsException e) {
				e.printStackTrace();
				return;
			}
		}
		
		public void take(Insect insect, Food food) {
			if(distance(insect.x(), insect.y(), food.x(), food.y()) == 0) {
				try {
					insect.takeFood();
					if(debug) System.out.println("Load food : " + food.x() + "," + food.y());
				} catch (NoActionPointsException | NotAllowException e) {
					e.printStackTrace();
				}
			}
			else {
				int dx = food.x()-insect.x();
				if(dx < -Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))+Constants.WIDTH/2;
				else if(dx > Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))-Constants.WIDTH/2;
				int dy = food.y()-insect.y();
				if(dy < -Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))+Constants.HEIGHT/2;
				else if(dy > Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))-Constants.HEIGHT/2;
				try {
					insect.move(dx, dy);
					if(debug) System.out.println("Move to load food : " + food.x() + "," + food.y() + " (" + dx + "," + dy + ")");
				} catch (NoActionPointsException | NotAllowException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void eat(Insect insect, Food food) {
			if(distance(insect.x(), insect.y(), food.x(), food.y()) == 1) {
				if(insect.energy() > Constants.MAX_ENERGY-Constants.FOOD_ENERGY)
					rest(insect);
				else {
					try {
						insect.eatFood();
						if(debug) System.out.println("Eat food : " + food.x() + "," + food.y());
					} catch (NoActionPointsException | NotAllowException e) {
						//e.printStackTrace();
					}
				}
			}
			else {
				int dx = food.x()-insect.x();
				if(dx < -Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))+Constants.WIDTH/2;
				else if(dx > Constants.WIDTH/2) dx = (dx%(Constants.WIDTH/2))-Constants.WIDTH/2;
				int dy = food.y()-insect.y();
				if(dy < -Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))+Constants.HEIGHT/2;
				else if(dy > Constants.HEIGHT/2) dy = (dy%(Constants.HEIGHT/2))-Constants.HEIGHT/2;
				dx -= dx > 0 ? 1 : dx < 0 ? -1 : 0;
				dy -= dy > 0 ? 1 : dy < 0 ? -1 : 0;
				if(dx == 0 && dy == 0) {
					dx++;
				}
				try {
					insect.move(dx, dy);
					if(debug) System.out.println("Move to eat food : " + food.x() + "," + food.y() + " (" + dx + "," + dy + ")");
				} catch (NoActionPointsException | NotAllowException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void eat(Insect insect) {
			try {
				insect.eatLoad();
				if(debug) System.out.println("Eat load");
			} catch (NoActionPointsException | NotAllowException e) {
				e.printStackTrace();
			}
		}
		
		public void rest(Insect insect) {
			try {
				insect.move(0, 0);
				if(debug) System.out.println("Rest");
			} catch (NoActionPointsException | NotAllowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void strategy(Insect insect) {
			if(debug) System.out.print("[" + insect.x() + "," + insect.y() + "] ");
				
			if(insect.energy() == 1 && insect.load() > 0) {
				eat(insect);
				return;
			}
			
			Food nearest = null;
			ArrayList<Food> foods = insect.lookAround(Food.class);
			for(Food food : foods) {
				if(nearest == null || distance(insect.x(), insect.y(), food.x(), food.y()) < distance(insect.x(), insect.y(), nearest.x(), nearest.y()))
					nearest = food;
			}
			if(nearest != null ) {
				if(insect.energy() > Constants.MAX_ENERGY-Constants.FOOD_ENERGY && insect.load() < insect.MAX_LOAD)
					take(insect, nearest);
				else
					eat(insect, nearest);
			} else {
				explore(insect);
			}
		}	
	}
}
