package view;

import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class SolutionCharacter.
 */
public abstract class SolutionCharacter {

	 /** The actions. */
 	ArrayList<String> actions;
	 
 	/** The pos. */
 	ArrayList<Position> pos;
	 
 	/** The forground. */
 	Color background,forground;
	 
 	/** The z. */
 	private int z;
	
	/** The x. */
	int x;
	
	/** The y. */
	int y;
	   
   	/** The map0. */
   	HashMap<?,?> map0;   
   /** The map1. */
   HashMap<?,?> map1;  
  /** The map2. */
  HashMap<?,?> map2;

	/**
	 * Paint.
	 *
	 * @param e the e
	 * @param w the w
	 * @param h the h
	 */
	public abstract void paint(PaintEvent e, double w, double h) ;
		
	/**
	 * Removes the pos.
	 *
	 * @param position the position
	 * @return true, if successful
	 */
	public boolean removePos(Position position){
		return pos.remove(position);
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public ArrayList<String> getActions() {
		return actions;
	}

	/**
	 * Sets the actions.
	 *
	 * @param actions the new actions
	 */
	@SuppressWarnings("unchecked")
	public void setActions(ArrayList<String> actions) {
		this.actions = (ArrayList<String>) actions.clone();
	}

	/**
	 * Gets the pos.
	 *
	 * @return the pos
	 */
	public ArrayList<Position> getPos() {
		return pos;
	}

	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	public void setPos(ArrayList<Position> pos) {
		this.pos = pos;
	}
	
	/**
	 * Sets the colors.
	 *
	 * @param backGround the back ground
	 * @param forGround the for ground
	 */
	public void setColors(Color backGround,Color forGround){
		this.background=backGround;
		this.forground=forGround;
	}

	/**
	 * Gets the map0.
	 *
	 * @return the map0
	 */
	public HashMap<?,?> getMap0() {
		return map0;
	}

	/**
	 * Sets the map0.
	 *
	 * @param map0 the map0
	 */
	public void setMap0(HashMap<?,?> map0) {
		this.map0 = map0;
	}

	/**
	 * Gets the map1.
	 *
	 * @return the map1
	 */
	public HashMap<?,?> getMap1() {
		return map1;
	}

	/**
	 * Sets the map1.
	 *
	 * @param map1 the map1
	 */
	public void setMap1(HashMap<?,?> map1) {
		this.map1 = map1;
	}

	/**
	 * Gets the map2.
	 *
	 * @return the map2
	 */
	public HashMap<?,?> getMap2() {
		return map2;
	}

	/**
	 * Sets the map2.
	 *
	 * @param map2 the map2
	 */
	public void setMap2(HashMap<?,?> map2) {
		this.map2 = map2;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}




}
