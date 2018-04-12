package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.Food;
import model.Insect;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;

public class UI extends GUIState {
	public static int FRAME_SIZE = 600;
	public Display2D display;
	public JFrame displayFrame;
	SparseGridPortrayal2D portrayal = new SparseGridPortrayal2D();
	
	public UI(SimState state) {
		super(state);
	}
	public static String getName() {
		return "Insect simulation"; 
	}
	public void start() {
	  super.start();
	  setupPortrayals();
	}

	public void load(SimState state) {
	  super.load(state);
	  setupPortrayals();
	}
	public void setupPortrayals() {
	  portrayal.setField(((Simulation)state).getGrid());
	  portrayal.setPortrayalForClass(Insect.class, getPortrayal(Color.RED));
	  portrayal.setPortrayalForClass(Food.class, getPortrayal(Color.GREEN));
	  display.reset();
	  display.setBackdrop(Color.orange);
	  display.repaint();
	}
	private OvalPortrayal2D getPortrayal(Color color) {
		OvalPortrayal2D r = new OvalPortrayal2D();
		r.paint = color;
		r.filled = true;
		return r;
	}
	public void init(Controller c) {
		  super.init(c);
		  display = new Display2D(FRAME_SIZE,FRAME_SIZE,this);
		  display.setClipping(false);
		  displayFrame = display.createFrame();
		  displayFrame.setTitle("Insects");
		  c.registerFrame(displayFrame); // so the frame appears in the "Display" list
		  displayFrame.setVisible(true);
		  display.attach( portrayal, "Field");
	}
	
	public  Object  getSimulationInspectedObject()  {  return  state;  }
	public  Inspector  getInspector() {
	Inspector  i  =  super.getInspector();
	  i.setVolatile(true);
	  return  i;
	}
}
