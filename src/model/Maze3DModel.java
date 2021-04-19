package model;

import java.util.Date;
import java.util.Observable;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import view.ClientProperties;
import boot.Client;
import boot.Problem;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze3DModel.
 */
public class Maze3DModel extends Observable implements Model {
	/** The maze table. */
	private HashMap<String, Maze3d> mazeTable;

	/** The maze name to maze file size. */
	private HashMap<String, Long> mazeNameToMazeFileSize;

	/** The file name to maze size. */
	private HashMap<String, Integer> fileNameToMazeSize;

	/** The solution table. */
	private HashMap<Maze3d, Solution> solutionTable;

	/** The save mazes and last save. */
	private HashMap<String, Date> saveMazesAndLastSave;
	
	/** The solution manager. */
	private HashMapManager<Maze3d, Solution> solutionManager;

	/** The maze table manager. */
	private HashMapManager<String, Maze3d> mazeTableManager;
	
	/** The save mazes and lase save manager. */
	private HashMapManager<String, Date> saveMazesAndLaseSaveManager;
	
	/** The client. */
	private Client client=null;
	
	

	/** The list of objects. */
	private Object[] listOfObjects;

	/** The cp. */
	private ClientProperties cp;
	
	/**
	 * Instantiates a new maze3 d model.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Maze3DModel() throws IOException {
		this.mazeTable = new HashMap<String, Maze3d>();
		this.mazeNameToMazeFileSize = new HashMap<String, Long>();
		this.fileNameToMazeSize = new HashMap<String, Integer>();
		this.solutionTable = new HashMap<Maze3d, Solution>();
		this.saveMazesAndLastSave=new HashMap<String, Date>();
		this.solutionManager = new HashMapManager<Maze3d, Solution>("solutionManagerFile", solutionTable);
		this.solutionTable = solutionManager.loadHashMapFromFile();
		this.mazeTableManager = new HashMapManager<String, Maze3d>("mazeTableManagerFile", mazeTable);
		this.mazeTable = mazeTableManager.loadHashMapFromFile();
		this.saveMazesAndLaseSaveManager=new HashMapManager<String, Date>("saveMazesManagerFile", getSaveData());
		this.saveMazesAndLastSave=saveMazesAndLaseSaveManager.loadHashMapFromFile();
		this.listOfObjects = new Object[2];
	}

	/**
	 * Generate maze.
	 *
	 * @param name
	 *            the name
	 * @param Generatoralgorithm
	 *            the generatoralgorithm
	 * @param height
	 *            the height
	 * @param length
	 *            the length
	 * @param width
	 *            the width
	 */
	@Override
	public void generateMaze(String name, String Generatoralgorithm,
			final int height, final int length, final int width) {
		Callable<Maze3d> callAbleMaze3D = new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				if (Generatoralgorithm.equals("prim")) {
					mazeTable.put(name, new MyMaze3dGenerator().generate(
							height, length, width));
					listOfObjects[0] = 1;
					listOfObjects[1] = "maze " + name + " is ready(prim maze)";
				} else if (Generatoralgorithm.equals("simple")) {
					mazeTable.put(name, new SimpleMaze3dGenerator().generate(
							height, length, width));
					listOfObjects[0] = 1;
					listOfObjects[1] = "maze " + name
							+ " is ready(simple maze)";

				} else {
					listOfObjects[0] = 2;
					listOfObjects[1] = "algorithm Generator is incorrect";

					return null;
				}
				return mazeTable.get(name);
			}
		};
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Maze3d> commandObject = executor.submit(callAbleMaze3D);
		try {
			commandObject.get();
			sendChoiceAndArgs(listOfObjects);
		getMaze3d(name);
		} catch (final InterruptedException ex) {
			ex.printStackTrace();
		} catch (final ExecutionException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Save maze in file.
	 *
	 * @param mazeName
	 *            the maze name
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void saveMazeInFile(String mazeName, String fileName)
			throws IOException {
		if (mazeTable.containsKey(mazeName) || mazeTable.containsKey("") ) {
			Date date = new Date();
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
			Maze3d maze=null;
//			if(mazeTable.containsKey(mazeName))
//				mazeTable.remove(mazeName);
				 if(mazeTable.containsKey("")) 
					out.write((maze=mazeTable.remove("")).toByteArray());
			out.flush();
			out.close();
			File file = new File(fileName);
			if (file.exists()) {
				mazeTable.put(mazeName, maze);
				mazeNameToMazeFileSize.put(mazeName, file.length());
				fileNameToMazeSize.put(fileName, maze.toByteArray().length);
				saveMazesAndLastSave.put(mazeName, date);
				sendChoiceAndArgs(new Object[] {1,"maze name -" + mazeName + "- saved in file name -"+ fileName + "-" });
			} else {
				sendChoiceAndArgs(new Object[] { 2,"file " + fileName + " does not exist" });
			}

		} else {
			sendChoiceAndArgs(new Object[] { 2,mazeName + " does not exist in data base" });
		}
	}

	/**
	 * Load maze from file.
	 *
	 * @param fileName
	 *            the file name
	 * @param mazeName
	 *            the maze name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void loadMazeFromFile(String fileName, String mazeName)
			throws IOException {
		InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
		byte b[] = new byte[fileNameToMazeSize.get(fileName) - 1];
		in.read(b);
		in.close();
		mazeTable.put(mazeName, new Maze3d(b));
		sendChoiceAndArgs(new Object[] {1,"maze data from -" + fileName + "- loaded to maze name -"+ mazeName + "-" });
	}

	/**
	 * Solve maze3 d.
	 *
	 * @param mazeName
	 *            the maze name
	 * @param solverAlgorithm
	 *            the solver algorithm
	 */
	@Override
	public void solveMaze3D(String mazeName, String solverAlgorithm) {
//		if (mazeTable.containsKey(mazeName)) {
//			if (!(solutionTable.containsKey(mazeTable.get(mazeName)))) {
//				Callable<Solution> callableSolution = new Callable<Solution>() {
//
//					@Override
//					public Solution call() throws Exception {
//						switch (solverAlgorithm) {
//						case "bfs":
//							solutionTable.put(mazeTable.get(mazeName),
//									new BFSSearcher()
//											.search(new SearchableMaze(
//													mazeTable.get(mazeName))));
//							break;
//						case "astar e":
//							solutionTable.put(mazeTable.get(mazeName),
//									new AstarSearcher(
//											new MazeEucledeanDistance())
//											.search(new SearchableMaze(
//													mazeTable.get(mazeName))));
//							break;
//						case "astar m":
//							solutionTable.put(mazeTable.get(mazeName),
//									new AstarSearcher(
//											new MazeManhattanDistance())
//											.search(new SearchableMaze(
//													mazeTable.get(mazeName))));
//
//							break;
//						default:
//							sendChoiceAndArgs(new Object[] { 2,
//									"no valid algorithm" });
//							return null;
//						}
//						listOfObjects[0] = 1;
//						listOfObjects[1] = "solution for " + mazeName
//								+ " is ready(" + solverAlgorithm + ")";
//						return solutionTable.get(mazeTable.get(mazeName));
//
//					}
//				};
//				ExecutorService executor = Executors.newFixedThreadPool(1);
//				Future<Solution> commandObject = executor.submit(callableSolution);
//				try {
//					commandObject.get();
//					sendChoiceAndArgs(listOfObjects);
//					getSolution(mazeName);
//				} catch (final InterruptedException ex) {
//					ex.printStackTrace();
//				} catch (final ExecutionException ex) {
//					ex.printStackTrace();
//				}
//			} else {// if solution exist , just return Without solving again
//				getSolution(mazeName);
//			}
//
//		} else {// if maze name does not exist in data base
//			sendChoiceAndArgs(new Object[] { 2,
//					mazeName + " maze does not exist in data base" });
//		}
		if (mazeTable.containsKey(mazeName)) {
			if (!(solutionTable.containsKey(mazeTable.get(mazeName)))) {
			 
				Callable<Solution> callable = new Callable<Solution>() {
					
					@Override
			public Solution call() throws IOException, ClassNotFoundException   {

				Problem prob = new Problem(); // Type to wrap the problem to simplify the request to the server
				prob.setAlgoName(solverAlgorithm);
				prob.setMaze(mazeTable.get(mazeName));
				Solution sol=null;
				client = new Client(cp.getIp(), Integer.parseInt(cp.getPort()));
						sol=client.getSolutionFromServer(prob);
					
					solutionTable.put(mazeTable.get(mazeName),sol);
					// you need to implement exceptions.
					listOfObjects[0] = 1;
					listOfObjects[1] = "solution for " + mazeName
							+ " is ready(" + solverAlgorithm + ")";
					
		
				return sol;

			}
		};
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Solution> commandObject = executor.submit(callable);
		try { // need to change it .!
			
			commandObject.get();
			sendChoiceAndArgs(listOfObjects);
			sendChoiceAndArgs(new Object[] { 5, mazeName,solutionTable.get(mazeTable.get(mazeName))});
		} catch (InterruptedException | ExecutionException e) {
			sendChoiceAndArgs(new Object[] { 2,"no connection to server ! go to File->Edit Properties to connect to server" });
		}
		} else {// if solution exist , just return Without solving again
				getSolution(mazeName);
		}
		}else {// if maze name does not exist in data base
		sendChoiceAndArgs(new Object[] { 2,
				mazeName + " maze does not exist in data base" });
	}
	
	}

	/* (non-Javadoc)
	 * @see model.Model#getMaze3d(java.lang.String)
	 */
	@Override
	public void getMaze3d(String mazeName) {
		if ((mazeTable.containsKey(mazeName))) {
			sendChoiceAndArgs(new Object[] { 3, mazeName,
					mazeTable.get(mazeName) });
		} else {
			sendChoiceAndArgs(new Object[] {
					2,
					"error. The name - " + mazeName
							+ " - is no longer available in the data base\n" });

		}

	}

	/* (non-Javadoc)
	 * @see model.Model#getSolution(java.lang.String)
	 */
	@Override
	public void getSolution(String mazeName) {
		if ((solutionTable.containsKey(mazeTable.get(mazeName)))) {
			sendChoiceAndArgs(new Object[] { 5, mazeName,
					solutionTable.get(mazeTable.get(mazeName)) });
		} else {
			sendChoiceAndArgs(new Object[] {
					2,
					"error. The name - " + mazeName
							+ " - is no longer available in solution table\n" });
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getCrossSection(char, int, java.lang.String)
	 */
	@Override
	public void getCrossSection(char axis, int index, String mazeName) {
		if (mazeTable.containsKey(mazeName)) {
			int crossSection[][] = null;
			switch (axis) {
			case 'z':
				crossSection = mazeTable.get(mazeName)
						.getCrossSectionByZ(index);
				break;
			case 'x':
				crossSection = mazeTable.get(mazeName)
						.getCrossSectionByX(index);
				break;
			case 'y':
				crossSection = mazeTable.get(mazeName)
						.getCrossSectionByY(index);
				break;
			default:
				sendChoiceAndArgs(new Object[] { 2, "no valid axis" });
				return;
			}
			sendChoiceAndArgs(new Object[] { 4, axis, index, mazeName,
					crossSection });
		} else {
			sendChoiceAndArgs(new Object[] {
					2,
					"error. The name - " + mazeName
							+ " - is no longer available in the data base\n" });
		}

	}

	/* (non-Javadoc)
	 * @see model.Model#getMazeSize(java.lang.String)
	 */
	@Override
	public void getMazeSize(String mazeName) {
		if (mazeTable.containsKey(mazeName)) {
			int height = mazeTable.get(mazeName).getMaze3d().length;
			int length = mazeTable.get(mazeName).getMaze3d()[0].length;
			int width = mazeTable.get(mazeName).getMaze3d()[0][0].length;
			sendChoiceAndArgs(new Object[] { 6, height, length, width });
		} else {
			sendChoiceAndArgs(new Object[] {
					2,
					"error. The name - " + mazeName
							+ " - is no longer available in the data base\n" });
		}
	}

	/* (non-Javadoc)
	 * @see model.Model#getFileSize(java.lang.String)
	 */
	@Override
	public void getFileSize(String mazeName) {
		if (mazeNameToMazeFileSize.containsKey(mazeName)) {
			sendChoiceAndArgs(new Object[] { 7, mazeName,
					mazeNameToMazeFileSize.get(mazeName) });
		} else {
			sendChoiceAndArgs(new Object[] {
					2,
					"error. The name - " + mazeName
							+ " - is no longer available in the data base\n" });
		}
	}
	
	/* (non-Javadoc)
	 * @see model.Model#getData(java.lang.Object)
	 */
	public void getData(Object params){
		Object[] args= (Object[]) params;
		int choice=(int)args[0] ;
		switch(choice){
		case 1: 
			sendChoiceAndArgs(new Object[] {3,args[1],mazeTable.get(args[1])});
		break;
		}
	}

	/**
	 * Gets the maze table.
	 *
	 * @return the maze table
	 */
	public HashMap<String, Maze3d> getMazeTable() {
		return mazeTable;
	}

	/**
	 * Sets the maze table.
	 *
	 * @param mazeTable
	 *            the maze table
	 */
	public void setMazeTable(HashMap<String, Maze3d> mazeTable) {
		this.mazeTable = mazeTable;
	}

	/**
	 * Gets the maze name to maze file size.
	 *
	 * @return the maze name to maze file size
	 */
	public HashMap<String, Long> getMazeNameToMazeFileSize() {
		return mazeNameToMazeFileSize;
	}

	/**
	 * Sets the maze name to maze file size.
	 *
	 * @param mazeNameToMazeFileSize
	 *            the maze name to maze file size
	 */
	public void setMazeNameToMazeFileSize(
			HashMap<String, Long> mazeNameToMazeFileSize) {
		this.mazeNameToMazeFileSize = mazeNameToMazeFileSize;
	}

	/**
	 * Gets the file name to maze size.
	 *
	 * @return the file name to maze size
	 */
	public HashMap<String, Integer> getFileNameToMazeSize() {
		return fileNameToMazeSize;
	}

	/**
	 * Sets the file name to maze size.
	 *
	 * @param fileNameToMazeSize
	 *            the file name to maze size
	 */
	public void setFileNameToMazeSize(
			HashMap<String, Integer> fileNameToMazeSize) {
		this.fileNameToMazeSize = fileNameToMazeSize;
	}

	/**
	 * Gets the solution table.
	 *
	 * @return the solution table
	 */
	public HashMap<Maze3d, Solution> getSolutionTable() {
		return solutionTable;
	}

	/**
	 * Sets the solution table.
	 *
	 * @param solutionTable
	 *            the solution table
	 */
	public void setSolutionTable(HashMap<Maze3d, Solution> solutionTable) {
		this.solutionTable = solutionTable;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		solutionManager.setHashMap(solutionTable);
		solutionManager.saveHashMap();
		mazeTableManager.setHashMap(mazeTable);
		mazeTableManager.saveHashMap();
		saveMazesAndLaseSaveManager.setHashMap(getSaveData());
		saveMazesAndLaseSaveManager.saveHashMap();
	}

	/* (non-Javadoc)
	 * @see model.Model#sendChoiceAndArgs(java.lang.Object)
	 */
	@Override
	public void sendChoiceAndArgs(Object args) {
		setChanged();
		notifyObservers(args);
	}

	/* (non-Javadoc)
	 * @see model.Model#getSaveData()
	 */
	public HashMap<String, Date> getSaveData() {
		return saveMazesAndLastSave;
	}

	/* (non-Javadoc)
	 * @see model.Model#setSaveData(java.util.HashMap)
	 */
	public void setSaveData(HashMap<String, Date> saveMazesAndLastSave) {
		this.saveMazesAndLastSave = saveMazesAndLastSave;
	}

/* (non-Javadoc)
 * @see model.Model#getCp()
 */
@Override
	public ClientProperties getCp() {
		return cp;
	}

/* (non-Javadoc)
 * @see model.Model#setCp(view.ClientProperties)
 */
@Override
	public void setCp(ClientProperties cp) {
		this.cp = cp;
	}

}
