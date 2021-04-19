package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import presenter.Command;
import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class MyCliView.
 */
public class MyCliView extends Observable implements View {

	/**
	 * Start.
	 *
	 * @param in the in
	 * @param out the out
	 * @param commandTable the command table
	 */
	public void start(BufferedReader in,PrintWriter out,HashMap<String, Command> commandTable)
	{
	    MyCliView.CLI cli=new CLI(in, out, commandTable);
	    cli.start();
	}
	
	/* (non-Javadoc)
	 * @see view.View#displayData(java.lang.Object)
	 */
	@Override
	public void displayData( Object params) {
		Object[] args= (Object[]) params;
		int choice=(int)args[0];
		switch(choice){
		case 1:
			displayChange((String) args[1]);
			break;
		case 2:
			displayError((String) args[1]);
			break;
		case 3:
			displayMaze3d((String)args[1], (Maze3d)args[2]);
			break;
		case 4:
			displayCrossSection((char)(args[1]), (int)args[2],(String)args[3],(int [][])args[4]);
		break;
		case 5:
			displaySolution((String)args[1], (Solution)args[2]);
			break;
		case 6:
			displayMazeSize((int)args[1], (int)args[2], (int)args[3]);
			break;
		case 7:
			displayFileSize((String)args[1], (long)args[2]);
			break;
		default:
			displayError("error in choise !");
			break;
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#displayChange(java.lang.String)
	 */
	@Override
	public void displayChange(String change) {
		System.out.println(change);
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String error) {
		System.err.println(error);
		
	}

	/* (non-Javadoc)
	 * @see view.View#showDirContent(java.lang.String[])
	 */
	@Override
	public void showDirContent(String[] pathName) {
		StringBuilder sb=new StringBuilder();
		for (int i = 1; i < pathName.length-1; i++) {
			sb.append(pathName[i]+" ");
		}
		sb.append(pathName[pathName.length-1]);
		File folder = new File(sb.toString());
		if (folder.exists()){
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println("File: " + listOfFiles[i].getName());
				} else if (listOfFiles[i].isDirectory()) {
					System.out.println("Directory: " + listOfFiles[i].getName());
				}
			}
		}
		else System.err.println("file does not exist. try again");


		
	}

	/* (non-Javadoc)
	 * @see view.View#displayMaze3d(java.lang.String, algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze3d(String mazeName,Maze3d maze) {
			System.out.println(mazeName+"'s maze");
			System.out.println(maze); 
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(java.lang.String, algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(String mazeName,Solution solution) {
		System.out.println("Solution for "+mazeName+":");
		System.out.println(solution);
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayCrossSection(char, int, java.lang.String, int[][])
	 */
	@Override
	public void displayCrossSection(char axis, int index, String mazeName,int[][] crossSection) {
		System.out.println("cross section for "+mazeName+" in axis "+axis+" and index "+index);
		for (int[] i : crossSection) {
			for (int j : i) {
				System.out.print(j+" ");
			}
			System.out.println();		
	}
	}

	/* (non-Javadoc)
	 * @see view.View#displayMazeSize(int, int, int)
	 */
	@Override
	public void displayMazeSize(int height,int length,int width) {
		System.out.format("height=%d , length=%d , width=%d\n", height,length,width);
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayFileSize(java.lang.String, long)
	 */
	@Override
	public void displayFileSize(String mazeName,long size) {
		System.out.println("file size for "+mazeName+"'s maze is "+size+" Bytes");
		
	}

	/**
	 * The Class CLI.
	 */
	public class CLI extends Thread  {
		
		/** The in. */
		private BufferedReader in;
		
		/** The out. */
		private PrintWriter out;
		
		/** The command table. */
		private HashMap<String, Command> commandTable;

		/**
		 * Instantiates a new cli.
		 *
		 * @param in the in
		 * @param out the out
		 * @param commandTable the command table
		 */
		public CLI(BufferedReader in,PrintWriter out,HashMap<String, Command> commandTable) {
			this.in=in;
			this.out=out;
			this.commandTable=commandTable;
			this.setName("cli thread");
			
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		
				
		public void run() {			
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
			printInstructions();
			try {
				while(!(input=in.readLine()).equals("exit")){
					args=input.split(" ");
					while(i<3 &&  i<args.length) {
						commands[i]=args[i];
						i++;
					}
					if((command=commandTable.get((commands[0].toLowerCase()+" "+commands[1].toLowerCase()+" "+commands[2].toLowerCase())))!=null)
					{
						listOfObjects.add(0,command);
						listOfObjects.add(1,args);
					}
					else if((command=commandTable.get((commands[0].toLowerCase()+" "+commands[1].toLowerCase())))!=null)
					{
						listOfObjects.add(0,command);
						listOfObjects.add(1,args);
					}
					else if((command=commandTable.get((commands[0].toLowerCase())))!=null)
					{
						listOfObjects.add(0,command);
						listOfObjects.add(1,args);
					}
					else 
					{
						out.println("Error in input value.no valid command");
						listOfObjects.clear();
						for ( i = 0; i < commands.length; i++) {
							commands[i]="";
						}
						i=0;
						continue;
					} 
					MyCliView.this.setChanged();
					MyCliView.this.notifyObservers(listOfObjects);
					listOfObjects.clear();
					for ( i = 0; i < commands.length; i++) {
						commands[i]="";
					}
					i=0;
				}
				 
				commandTable.get("exit").doCommand(args);
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
	
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

	/* (non-Javadoc)
	 * @see view.View#notifyPresenter(java.lang.Object)
	 */
	@Override
	public void notifyPresenter(Object commandAndArgs) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#setSaveData(java.util.HashMap)
	 */
	@Override
	public void setSaveData(HashMap<String, Date> saveData) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#getSaveData()
	 */
	@Override
	public HashMap<String, Date> getSaveData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see view.View#getCp()
	 */
	@Override
	public ClientProperties getCp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setCp(view.ClientProperties)
	 */
	@Override
	public void setCp(ClientProperties cp) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see view.View#getCommandTable()
	 */
	@Override
	public HashMap<String, Command> getCommandTable() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setCommandTable(java.util.HashMap)
	 */
	@Override
	public void setCommandTable(HashMap<String, Command> commandTable) {
		// TODO Auto-generated method stub
		
	}


	
	
}
