package view;

import java.util.Date;
import java.util.HashMap;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Interface View.
 */
public interface View {
	
	/**
	 * Display data.
	 *
	 * @param args the args
	 */
	public void displayData(Object args);
	
	/**
	 * Display change.
	 *
	 * @param change the change
	 */
	public void displayChange(String change);
	
	/**
	 * Display error.
	 *
	 * @param error the error
	 */
	public void displayError(String error);
	
	/**
	 * Show dir content.
	 *
	 * @param pathName the path name
	 */
	public void showDirContent(String pathName[]);
	
	/**
	 * Display maze3d.
	 *
	 * @param mazeName the maze name
	 * @param maze the maze
	 */
	public void displayMaze3d(String mazeName,Maze3d maze);
	
	/**
	 * Display solution.
	 *
	 * @param mazeName the maze name
	 * @param solution the solution
	 */
	public void displaySolution(String mazeName,Solution solution);
	
	/**
	 * Display cross section.
	 *
	 * @param axis the axis
	 * @param index the index
	 * @param mazeName the maze name
	 * @param crossSection the cross section
	 */
	public void displayCrossSection(char axis,int index,String mazeName,int[][] crossSection);
	
	/**
	 * Display maze size.
	 *
	 * @param height the height
	 * @param length the length
	 * @param width the width
	 */
	public void displayMazeSize(int height,int length,int width);
	
	/**
	 * Display file size.
	 *
	 * @param mazeName the maze name
	 * @param size the size
	 */
	public void displayFileSize(String mazeName,long size);
	
	/**
	 * Notify presenter.
	 *
	 * @param commandAndArgs the command and args
	 */
	public void notifyPresenter(Object commandAndArgs);
	
	/**
	 * Sets the save data.
	 *
	 * @param saveData the save data
	 */
	public void setSaveData(HashMap<String, Date> saveData);
	
	/**
	 * Gets the save data.
	 *
	 * @return the save data
	 */
	public HashMap<String, Date> getSaveData();
	
	/**
	 * Gets the cp.
	 *
	 * @return the cp
	 */
	ClientProperties getCp();
	
	/**
	 * Sets the cp.
	 *
	 * @param cp the new cp
	 */
	void setCp(ClientProperties cp);
	
	/**
	 * Gets the command table.
	 *
	 * @return the command table
	 */
	HashMap<String, Command> getCommandTable();
	
	/**
	 * Sets the command table.
	 *
	 * @param commandTable the command table
	 */
	void setCommandTable(HashMap<String, Command> commandTable);
	
}
