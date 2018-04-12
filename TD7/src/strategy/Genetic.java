package strategy;

import main.Constants;
import sim.engine.Schedule;

public class Genetic {
	public final int MAX_MOVE;
	public final int MAX_LOAD;
	public final int MAX_PERCEPTION;
	
	public Genetic(int maxMove, int maxLoad, int maxPerception) throws InvalidGeneticException {
		if(maxMove < 1 || maxLoad < 1 || maxPerception < 1)
			throw new InvalidGeneticException("");
		if(maxMove + maxLoad + maxPerception > Constants.CAPACITY)
			throw new InvalidGeneticException("");
		if(maxLoad > Constants.MAX_LOAD)
			throw new InvalidGeneticException("");
		
		MAX_MOVE = maxMove;
		MAX_LOAD = maxLoad;
		MAX_PERCEPTION = maxPerception;
	}
	
	public static Genetic random() throws InvalidGeneticException {
		int maxMove = 1;
		int maxLoad = 1;
		int maxPerception = 1;
		int points = Constants.CAPACITY - 3;
		
		maxMove += Math.round(Math.random()*points);
		points -= maxMove-1;
		
		maxLoad += Math.round(Math.random()*Math.min(points, Constants.MAX_LOAD-1));
		points -= maxLoad-1;
		
		maxPerception += points;
		
		return new Genetic(maxMove, maxLoad, maxPerception);
	}
}
