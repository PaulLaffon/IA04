package main;

import sim.display.Console;

public class Main {

	public static void main(String[] args) {
		
		Simulation simulation = new Simulation(System.currentTimeMillis());
		UI gui = new UI(simulation);
		Console console = new Console(gui);
		console.setVisible(true);
	}

}
