package boot;

import java.io.Serializable;

import algorithms.mazeGenerators.Maze3d;

// TODO: Auto-generated Javadoc
/**
 * The Class Problem.
 */
public class Problem implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The algo name. */
	String algoName;
	
	/** The maze. */
	Maze3d  maze ;
	
	/**
	 * Gets the algo name.
	 *
	 * @return the algo name
	 */
	public String getAlgoName() {
		return algoName;
	}
	
	/**
	 * Sets the algo name.
	 *
	 * @param algoName the new algo name
	 */
	public void setAlgoName(String algoName) {
		this.algoName = algoName;
	}
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}
	
	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	} 
	
}
