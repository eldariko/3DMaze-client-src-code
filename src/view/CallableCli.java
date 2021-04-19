package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import presenter.Command;
// TODO: Auto-generated Javadoc

/**
 * The Class CallableCli.
 */
public class CallableCli implements Callable<ArrayList<Object>> {

	/** The in. */
	private BufferedReader in;
	
	/** The out. */
	private PrintWriter out;
	
	/** The command table. */
	private HashMap<String, Command> commandTable;
	
	/**
	 * Instantiates a new callable cli.
	 */
	public CallableCli()
	{
		
	}
	
	/**
	 * Instantiates a new callable cli.
	 *
	 * @param in the in
	 * @param out the out
	 * @param commandTable the command table
	 */
	public CallableCli(BufferedReader in,PrintWriter out,HashMap<String, Command> commandTable) {
		this.in=in;
		this.out=out;
		this.commandTable=commandTable;
	}
	
	/**
	 * Start.
	 *
	 * @return the array list
	 */
	public ArrayList<Object> start()
	{
		ExecutorService executor =Executors.newFixedThreadPool(1);
		Future<ArrayList<Object>> commandObject=executor.submit(this);
		final ArrayList<Object> command;
		try {
			command=commandObject.get();
			return command;
		}  catch(final InterruptedException ex) {
            ex.printStackTrace();
        } catch(final ExecutionException ex) {
            ex.printStackTrace();
        }
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public ArrayList<Object> call()  {
		ArrayList<Object> listOfObjects = new ArrayList<Object>();
		String args[] = null;
		Command command=null;
		String commands[]=new String[3];
		String input;
		int i=0;
		for ( i = 0; i < commands.length; i++) {
			commands[i]=new String("");
		}
		i=0;
		this.printInstructions();
		try {
			while(!(input=in.readLine()).equals("exit")){
				args=input.split(" ");
				while(i<3 &&  i<args.length) {
					commands[i]=args[i];
					i++;
				}
				if((command=commandTable.get((commands[0].toLowerCase()+" "+commands[1].toLowerCase()+" "+commands[2].toLowerCase())))!=null)
				{
					listOfObjects.add(command);
					listOfObjects.add(args);
					return listOfObjects;
				}
				else if((command=commandTable.get((commands[0].toLowerCase()+" "+commands[1].toLowerCase())))!=null)
				{
					listOfObjects.add(command);
					listOfObjects.add(args);
					return listOfObjects;
				}
				else if((command=commandTable.get((commands[0].toLowerCase())))!=null)
				{
					listOfObjects.add(command);
					listOfObjects.add(args);
					return listOfObjects;
				}
				else 
				{
					out.println("Error in input value.no valid command");
				}
				listOfObjects.clear();
				for ( i = 0; i < commands.length; i++) {
					commands[i]="";
				}
				i=0;
			}
			commandTable.get("exit").doCommand(args);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Prints the instructions.
	 */
	private void printInstructions()
	{
		System.out.printf("** Welcome . Please select command and add the necessary parameters **\n\n");
		String command;
		command="display files and directories of specific path :";
		System.out.printf("%-60s dir <path nazme>\n",command);
		command="generate 3d maze with given algorithm :";
		System.out.printf("%-60s generate 3d maze <maze name> <{prim/simple}> <height> <length> <width>\n",command);
		command="display maze by specific name :";
		System.out.printf("%-60s display <maze name>\n",command);
		command="display cross section of specific maze name :";
		System.out.printf("%-60s display cross section by <{X/Y/Z> <index> for <maze name>\n",command);
		command="save specific maze (by is name) in file :";
		System.out.printf("%-60s save maze <maze name> <file name>\n",command);
		command="load maze from available file into new maze name :";
		System.out.printf("%-60s load maze <file name> <maze name>\n",command);
		command="display maze size of specific name :";
		System.out.printf("%-60s maze size <maze name>\n",command);
		command="display file size of specific name (if it is save) :";
		System.out.printf("%-60s file size <maze name>\n",command);
		command="solving given maze with given algorithm by is name :";
		System.out.printf("%-60s solve <name> <{bfs/Astar e/Astar m}>\n",command);
		command="display solution of maze by is name(is solution exist) :";
		System.out.printf("%-60s display solution <name>\n",command);
		command="exit from the program :";
		System.out.printf("%-60s exit\n\n",command);
		System.out.println("waiting for command ...");
	}	

}
