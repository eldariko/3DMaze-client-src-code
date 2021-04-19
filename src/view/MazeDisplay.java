package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Action;
import algorithms.search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeDisplay.
 */
public abstract class MazeDisplay extends Canvas {
	
	/** The maze data. */
	protected int[][][] mazeData;
	
	/** The maze3d. */
	protected Maze3d maze3d;
	
	/** The character y. */
	protected int characterZ,characterX,characterY;
	
	/** The goal position. */
	protected Position goalPosition;
	
	/** The display solution. */
	protected boolean displaySolution;
	
	/** The counter. */
	protected int counter;
	
	/** The actions. */
	protected ArrayList<String> actions;
	
	/** The positions. */
	protected ArrayList<Position> positions;
	
	/** The sc. */
	protected SolutionCharacter sc;
	
	/** The h. */
	protected int h;
	
	/** The w. */
	protected int w;
	
	/** The y. */
	protected int x,y;
	
	/** The goal c. */
	protected GameCharacter startC,goalC;
	
	/** The maze name. */
	protected String mazeName;
	
	/** The user press. */
	protected int userPress;
	
	/** The next pos. */
	protected Iterator<Position> nextPos;
	 
 	/** The timer. */
 	Timer timer;
	 
 	/** The task. */
 	TimerTask task;

/**
 * Instantiates a new maze display.
 *
 * @param parent the parent
 * @param style the style
 * @param startC the start c
 * @param goalC the goal c
 */
//	protected Image image;
	public MazeDisplay(Composite parent, int style,GameCharacter startC,GameCharacter goalC) {
		super(parent, style);
		this.startC=startC;
		this.goalC=goalC;
		
	}
	
	
	/**
	 * Sets the maze3d.
	 *
	 * @param maze3d the new maze3d
	 */
	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
		this.goalPosition = maze3d.getGoalPosition();
		mazeData = maze3d.getMaze3d();
		h = getSize().y / mazeData[0].length;
		w = getSize().x / mazeData[0][0].length;
		characterZ=maze3d.getStartPosition().getZ();
		characterX=maze3d.getStartPosition().getY();
		characterY=maze3d.getStartPosition().getX();
		startC.setZXY(maze3d.getStartPosition().getZ(), maze3d.getStartPosition().getY() * w, maze3d.getStartPosition().getX() * h);
		displaySolution=false;
		counter=0;
		actions.clear();
		redraw();
	}
	
	/**
	 * Check user press.
	 */
	public void checkUserPress() {
		int z = maze3d.getStartPosition().getZ();
		int x = maze3d.getStartPosition().getX();
		int y = maze3d.getStartPosition().getY();
		switch (userPress) {
		case SWT.ARROW_UP:
			if (isValidMove(z, x - 1, y)&& mazeData[z][x-1][y] == 0) {
				maze3d.getStartPosition().setX(x - 1);
				break;
			}
			return;
		case SWT.ARROW_DOWN:
			if (isValidMove(z, x + 1, y)&& mazeData[z][x+1][y] == 0) {
				maze3d.getStartPosition().setX(x + 1);
				
				break;
			}
			return;
		case SWT.ARROW_LEFT:
			if (isValidMove(z, x, y - 1)&& mazeData[z][x][y-1] == 0) {
				maze3d.getStartPosition().setY(y - 1);
				
				break;
			}
			return;
		case SWT.ARROW_RIGHT:
			if (isValidMove(z, x, y + 1)&& mazeData[z][x][y+1] == 0) {
				maze3d.getStartPosition().setY(y + 1);
				
				break;
			}
			return;
		case SWT.PAGE_UP:
			if (isValidMove(z + 1, x, y)&& mazeData[z+1][x][y] == 0) {
				maze3d.getStartPosition().setZ(z + 1);	
				characterZ=z + 1;
//				displaySolution=false;
				break;
			}
			return;
		case SWT.PAGE_DOWN:
			if (isValidMove(z - 1, x, y)&& mazeData[z-1][x][y] == 0) {
				maze3d.getStartPosition().setZ(z - 1);	
				characterZ=z - 1;
//				displaySolution=false;
				break;
			}
			return;
			
		default:
			return;

		}
		if(displaySolution && positions.remove(maze3d.getStartPosition()));
		redraw();
	  
	}
	

	/**
	 * Checks if is valid move.
	 *
	 * @param z the z
	 * @param x the x
	 * @param y the y
	 * @return true, if is valid move
	 */
	public boolean isValidMove(int z, int x, int y) {
		if (x > 0 && y > 0 && z >= 0 && x < mazeData[0].length
				&& y < mazeData[0][0].length && z < mazeData.length
				)
			return true;
		else
			return false;
	}
	
	/**
	 * Show up.
	 */
	public void showUp(){
		if(characterZ+1<mazeData.length){
			characterZ+=1;
			displaySolution=false;
			redraw();
		}
		else return;
	}
	
	/**
	 * Show down.
	 */
	public void showDown(){
		if(characterZ-1>=0){
			characterZ-=1;
			displaySolution=false;
		redraw();
		}
		else return;
	}

	/**
	 * Replace actions to positions.
	 */
	public void replaceActionsToPositions(){
		int z=characterZ,x =characterY ,y = characterX;
		for (int i=0;i<actions.size();i++) {
			switch (actions.get(i)) {
			case "Forward":
				x+=1;
				break;
			case "Backward":
				x-=1;
				break;
			case "Right":
				y+=1;
				break;
			case "Left":
				y-=1;
				break;
			case "Up":
				positions.add(new Position(z+1, x, y));
				actions.clear();
				nextPos = positions.iterator();
				moveCharacter();
				return;
			case "Down":
				positions.add(new Position(z-1, x, y));
				actions.clear();
				nextPos = positions.iterator();
				moveCharacter();
				return;
			default:
			
			}
			positions.add(new Position(z, x, y));
		}
		actions.clear();
		nextPos = positions.iterator();
		moveCharacter();
	}
	
	/**
	 * Display sol or hint.
	 *
	 * @param numOfmoves the num ofmoves
	 * @param solution the solution
	 */
	public void displaySolOrHint(int numOfmoves,Solution solution){
		actions.clear();positions.clear();
		int i = 1;
		int minMoves;
		ArrayList<Action> listOfActions = solution.getActions();
		if(numOfmoves!=0){
		minMoves = Math.min(numOfmoves , listOfActions.size() - 1);
		}else minMoves=listOfActions.size()-1;
		while (i <= minMoves) {
			actions.add(listOfActions.get(listOfActions.size() - i-1).getDescription()); 		
			i++;
		}
	  characterZ=maze3d.getStartPosition().getZ();
	  characterX=maze3d.getStartPosition().getY();
	  characterY=maze3d.getStartPosition().getX();
	  this.replaceActionsToPositions();
	  sc.setPos(positions);
	  displaySolution=true;
	  redraw();
	  
	}
	
	/**
	 * Move character.
	 */
	private void moveCharacter(){
		if(timer!=null && task!=null){
			stopSolutionTask();	
		}
		
		timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {					
					getDisplay().syncExec(new Runnable() {
						
						@Override
						public void run() {
							if(nextPos.hasNext()){
							Position p=nextPos.next();
							characterZ=p.getZ();
							maze3d.setStartPosition(characterZ, p.getX(), p.getY()); 
							if(MazeDisplay.this.isDisposed()){
								displaySolution=false;
								stopSolutionTask();
								return;
							}
								
							getDisplay().syncExec(new Runnable() {
								
								@Override
								public void run() {
									redraw();
								}
							});
							}
							else {stopSolutionTask();displaySolution=false;return;};
							}
					});
				}

			};
		
			timer.scheduleAtFixedRate(task, 0, 100);	
//			ExecutorService executor = Executors.newFixedThreadPool(1);
//			executor.execute(task);
		}
	/**
	 * Stop solution task.
	 */
	public void stopSolutionTask(){task.cancel();timer.cancel();}
	/**
	 * Display won message.
	 */
	protected void displayWonMessage(){
		MessageBox messageBox = new MessageBox(getShell(),SWT.ICON_INFORMATION|SWT.ICON_WORKING );
        messageBox.setText("You Won !");
        messageBox.setMessage("well done ! you solved the maze");
		messageBox.open();
	}
	
	
	
	/**
	 * Gets the maze3d.
	 *
	 * @return the maze3d
	 */
	public Maze3d getMaze3d() {
		return maze3d;
	}
	
	/**
	 * Gets the maze name.
	 *
	 * @return the maze name
	 */
	public String getMazeName() {
		return mazeName;
	}
	
	/**
	 * Sets the maze name.
	 *
	 * @param mazeName the new maze name
	 */
	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}
	
	/**
	 * Gets the user press.
	 *
	 * @return the user press
	 */
	public int getUserPress() {
		return userPress;
	}

	/**
	 * Sets the user press.
	 *
	 * @param userPress the new user press
	 */
	public void setUserPress(int userPress) {
		this.userPress = userPress;
	}
	
}
