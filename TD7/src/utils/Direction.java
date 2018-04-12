package utils;

public enum Direction { 
	UP_LEFT(0),
	UP(1),
	UP_RIGHT(2),
	LEFT(3),
	RIGHT(5),
	DOWN_LEFT(6),
	DOWN(7),
	DOWN_RIGHT(8);

	private final int value;
    Direction(final int newValue) {
        value = newValue;
    }
    public int getValue() { return value; }
    public String toString() {
    	switch(value) {
    	case 0:
    		return "UP_LEFT";
    	case 1:
    		return "UP";
    	case 2:
    		return "UP_RIGHT";
    	case 5:
    		return "RIGHT";
    	case 8:
    		return "DOWN_RIGHT";
    	case 7:
    		return "DOWN";
    	case 6:
    		return "DOWN_LEFT";
    	case 3:
    		return "LEFT";
    	default:
    		return "";
    	}
    }
    public static Direction getRandom() {
    	int rnd = (int)Math.round(Math.random()*7);
		return Direction.values()[rnd];
    };
    public Direction right() {
    	switch(value) {
    	case 0:
    		return UP;
    	case 1:
    		return UP_RIGHT;
    	case 2:
    		return RIGHT;
    	case 5:
    		return DOWN_RIGHT;
    	case 8:
    		return DOWN;
    	case 7:
    		return DOWN_LEFT;
    	case 6:
    		return LEFT;
    	case 3:
    		return UP_LEFT;
    	}
    	return null;
    }
    public Direction left() {
    	switch(value) {
    	case 0:
    		return LEFT;
    	case 1:
    		return UP_LEFT;
    	case 2:
    		return UP;
    	case 5:
    		return UP_RIGHT;
    	case 8:
    		return RIGHT;
    	case 7:
    		return DOWN_RIGHT;
    	case 6:
    		return DOWN;
    	case 3:
    		return DOWN_LEFT;
    	}
    	return null;
    }
};