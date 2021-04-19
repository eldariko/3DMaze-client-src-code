package presenter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Command objects.
 */
public class CommandFactory {

/** The command table. */
private HashMap<String, Command> commandTable;
	

	/**
	 * Instantiates a new command factory.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public CommandFactory(View view ,Model model) {
	commandTable=new HashMap<String, Command>();
	commandTable.put("dir",new DirCommand(view, model));
	commandTable.put("generate 3d maze", new GenerateMazeCommand(view, model));
    commandTable.put("display", new DisplayCommand(view, model));
	commandTable.put("display cross section",new DisplayCrossSectionCommand(view, model));
    commandTable.put("save maze",new SaveMazeCommand(view, model));
    commandTable.put("load maze",new LoadMazeCommand(view, model));
    commandTable.put("maze size",new MazeSizeCommand(view, model));
    commandTable.put("file size",new FileSizeCommand(view, model));
    commandTable.put("solve",new SolveCommand(view, model));
    commandTable.put("display solution",new SolutionCommand(view, model));
    commandTable.put("get save mazes", new SaveMazesCommand(view, model));
    commandTable.put("get maze", new GetMaze(view, model));
    commandTable.put("exit", new ExitCommand(view, model));
	
	}
	
	/**
	 * Gets the command table.
	 *
	 * @return the command table
	 */
	public HashMap<String, Command> getCommandTable() {
		return commandTable;
	}
	
	/**
	 * Sets the command table.
	 *
	 * @param commandTable the command table
	 */
	public void setCommandTable(HashMap<String, Command> commandTable) {
		this.commandTable = commandTable;
	}
	

	/**
	 * The Class AbstractCommand.
	 */
	public abstract class AbstractCommand implements Command {

		/** The view. */
		protected View view;
		
		/** The model. */
		protected Model model;
		
		/**
		 * Instantiates a new abstract command.
		 *
		 * @param view2 the view
		 * @param model2 the model
		 */
		public AbstractCommand(View view2 ,Model model2) {
	        this.view=view2;
	        this.model=model2;
		}
		
		/* (non-Javadoc)
		 * @see controller.Command#doCommand(java.lang.String[])
		 */
		public abstract void doCommand(String[] args) ;

	}
	/**
	 * The Class DirCommand.
	 */
	public class DirCommand extends AbstractCommand {

		/**
		 * Instantiates a new dir command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public DirCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
			if(args.length>=2){
			view.showDirContent(args);
			}else System.err.println("no valid input . please type according to This structure - dir <path name> ");

		}
	}
	
	/**
	 * The Class DisplayCommand.
	 */
	public class DisplayCommand extends AbstractCommand {

		/**
		 * Instantiates a new display command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public DisplayCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	     if(args.length==2){
			String name=args[1];
		  model.getMaze3d(name.toLowerCase());
	     }
	     else System.err.println("no valid input . please type according to This structure - display <maze name> ");
		}
	}

	/**
	 * The Class DisplayCrossSectionCommand.
	 */
	public class DisplayCrossSectionCommand extends AbstractCommand {

		/**
		 * Instantiates a new display cross section command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public DisplayCrossSectionCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	      if(args.length==8){
			char axis=args[4].toLowerCase().charAt(0);
	      int index=Integer.parseInt(args[5].toLowerCase()); 
		  String mazeName=args[7].toLowerCase();
		  model.getCrossSection(axis, index, mazeName);
		
	      }else System.err.println("no valid input . please type according to This structure - "
	      		+ "display cross section by {X,Y,Z} <index> for <name> ");
		
		}
		
	}

	/**
	 * The Class ExitCommand.
	 */
	public class ExitCommand extends AbstractCommand {

		/**
		 * Instantiates a new exit command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public ExitCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[])  {
		 	view.displayChange("exit now...");
			try {
				model.finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			System.runFinalization();
			System.gc();
			

		}

	}
	
	/**
	 * The Class FileSizeCommand.
	 */
	public class FileSizeCommand extends AbstractCommand {

		/**
		 * Instantiates a new file size command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public FileSizeCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
			if(args.length==3){
		    	  String mazeName=args[2].toLowerCase();
		    	  model.getFileSize(mazeName);
		      }
		      else System.err.println("no valid input . please type according to This structure - file size <maze name>");	}

	}
	
	/**
	 * The Class GenerateMazeCommand.
	 */
	public class GenerateMazeCommand extends AbstractCommand {

		/**
		 * Instantiates a new generate maze command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public GenerateMazeCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	    if(args.length==8){
			String name=args[3];
	    String Generatoralgorithm=args[4];
	    int floors=Integer.parseInt(args[5]) ;
	    int height=Integer.parseInt(args[6]);
	    int width=Integer.parseInt(args[7]);
		model.generateMaze(name, Generatoralgorithm.toLowerCase(), floors, height, width);
	    }
	    else System.err.println("no valid input . please type according to This structure - "
	    		+ "generate 3d maze <maze name> <genrator> <floors> <height> <width>");
	    }

	}
	
	/**
	 * The Class LoadMazeCommand.
	 */
	public class LoadMazeCommand extends AbstractCommand {

		/**
		 * Instantiates a new load maze command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public LoadMazeCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
			if(args.length>=4){
				StringBuilder fileName=new StringBuilder();
				for (int i = 2; i < args.length-2; i++) {
					fileName.append(args[i]+" ");
				}
			fileName.append(args[args.length-2]);
			String mazeName=args[args.length-1];
		    try {
				model.loadMazeFromFile(fileName.toString(), mazeName.toLowerCase());
			 }catch (IOException e) {
				 System.err.println("error . no valid path name");	
				 e.printStackTrace();
			     }
			}else System.err.println("no valid input . please type according to This structure - load maze <file name> <maze name>");
			
		}

	}

	/**
	 * The Class MazeSizeCommand.
	 */
	public class MazeSizeCommand extends AbstractCommand {

		/**
		 * Instantiates a new maze size command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public MazeSizeCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	      if(args.length==3){
	    	  String mazeName=args[2].toLowerCase();
	    	  model.getMazeSize(mazeName);
	      }
	      else System.err.println("no valid input . please type according to This structure - maze size <maze name>");
		}

	}
	
	/**
	 * The Class SaveMazeCommand.
	 */
	public class SaveMazeCommand extends AbstractCommand {

		/**
		 * Instantiates a new save maze command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public SaveMazeCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
			
			if(args.length>=4){
				StringBuilder fileName=new StringBuilder();
				for (int i = 3; i < args.length-1; i++) {
					fileName.append(args[i]+" ");
				}
			fileName.append(args[args.length-1]);
			String mazeName=args[2];
	    try {
			model.saveMazeInFile(mazeName.toLowerCase(), fileName.toString());
		 }catch (IOException e) {
				e.printStackTrace();
		     }
		}else System.err.println("no valid input . please type according to This structure - save maze <maze name> <file name>");
		}
	}

	
	/**
	 * The Class SolutionCommand.
	 */
	public class SolutionCommand extends AbstractCommand {

		/**
		 * Instantiates a new solution command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public SolutionCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	    if(args.length==3)
	    {
	    	String mazeName=args[2].toLowerCase();
	    	 model.getSolution(mazeName);
	    }else System.err.println("no valid input . please type according to This structure - display solution <maze name>");
		}

	}

	/**
	 * The Class SolveCommand.
	 */
	public class SolveCommand extends AbstractCommand {

		/**
		 * Instantiates a new solve command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public SolveCommand(View view ,Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see controller.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String args[]) {
	    if(args.length>2 && args.length<5){
		String mazeName=args[1].toLowerCase();
	    String solverAlgorithm = args[2].toLowerCase();
	    if(args.length>3 && !args[3].equals(""))
	    solverAlgorithm +=" "+args[3].toLowerCase();
		model.solveMaze3D(mazeName, solverAlgorithm);
	    }else System.err.println("no valid input . please type according to This structure - solve <maze name> <algorithm> <Heuristic(if necessary)>");
		}

	}
	
	/**
	 * The Class SaveMazesCommand.
	 */
	public class SaveMazesCommand extends AbstractCommand{

		/**
		 * Instantiates a new save mazes command.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public SaveMazesCommand(View view, Model model) {
			super(view, model);
		}

		/* (non-Javadoc)
		 * @see presenter.CommandFactory.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			HashMap<String, Date> saveData=model.getSaveData();
			view.setSaveData(saveData);
		}
	}
	
	/**
	 * The Class GetMaze.
	 */
	public class GetMaze extends AbstractCommand{

		/**
		 * Instantiates a new gets the maze.
		 *
		 * @param view the view
		 * @param model the model
		 */
		public GetMaze(View view, Model model) {
			super(view, model);
			
		}

		/* (non-Javadoc)
		 * @see presenter.CommandFactory.AbstractCommand#doCommand(java.lang.String[])
		 */
		@Override
		public void doCommand(String[] args) {
			model.getData(new Object[]{1,args[0]});
			
		}
		
	}
	
	
}
