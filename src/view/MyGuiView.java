
package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

import algorithms.mazeGenerators.Maze3d;
import algorithms.search.Solution;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import presenter.Command;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
// TODO: Auto-generated Javadoc

/**
 * The Class MyGuiView.
 */
public class MyGuiView extends BasicWindow implements View {
    
    /** The Constant HINT. */
    public final static int HINT=1;
    
    /** The Constant SOLUTION. */
    public final static int SOLUTION=2;
	
	/** The shell. */
	private Shell shell;
	
	/** The display. */
	private Display display;
	
	/** The load maze table. */
	private Table loadMazeTable;
	
	/** The text for name. */
	private Text textForName;
    
    /** The maze display. */
    private MazeDisplay mazeDisplay;
	
	/** The command table. */
	private HashMap<String, Command> commandTable;
	
	/** The save data. */
	private HashMap<String, Date> saveData;
    
    /** The table name column. */
    private TableColumn tableNameColumn;
    
    /** The table last save column. */
    private TableColumn tableLastSaveColumn;
	
	/** The date format. */
	private DateFormat dateFormat;
	
	/** The text for updates. */
	private Text textForUpdates;
	
	/** The choice. */
	private int choice;
	
	/** The sol hint choice. */
	private int solHintChoice;
	
	/** The sorter map. */
	private TreeMap<String, Date> sorterMap;
	
	/** The num of moves spinner. */
	private Spinner numOfMovesSpinner;
	
	/** The button down. */
	Button buttonUp,buttonDown;
	
	/** The cp. */
	private ClientProperties cp;
	
	/**
	 * Instantiates a new my gui view.
	 *
	 * @param title the title
	 * @param width the width
	 * @param height the height
	 */
	public MyGuiView(String title, int width,int height) {
		super(title,width,height);
		saveData=new HashMap<String, Date>();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		sorterMap=new TreeMap<String, Date>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return saveData.get(o1).compareTo(saveData.get(o2));
				
			}
		});
     setCp(new ClientProperties());
     this.loadDefaultProp();
	}
	/**
	 * Open the window.
	 */
	public void open() {
		this.setCommandTable(commandTable);
		display = Display.getCurrent();
		createContents();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}display.dispose();
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(688, 640);
		shell.setLayout(new GridLayout(3, false));
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				exitEvent();
			}
		   
		});
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem fileSubMenu = new MenuItem(menu, SWT.CASCADE);
		fileSubMenu.setText("File");
		
		Menu subMenu = new Menu(fileSubMenu);
		fileSubMenu.setMenu(subMenu);
		
		MenuItem openPropertiesMNTM = new MenuItem(subMenu, SWT.NONE);
		openPropertiesMNTM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					propertyEvent();
				} catch (FileNotFoundException e1) {
					displayError("file not found");
					e1.printStackTrace();
				}
			}
		});
		openPropertiesMNTM.setText("Open properties");
		
		MenuItem mntmEditProperties = new MenuItem(subMenu, SWT.NONE);
		mntmEditProperties.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PropertiesWindowClient propWin=new PropertiesWindowClient(MyGuiView.this);
				propWin.open();
			}
		});
		mntmEditProperties.setSelection(true);
		mntmEditProperties.setText("Edit properties");

		MenuItem exitMNTM = new MenuItem(subMenu, SWT.NONE);
		exitMNTM.setText("Exit\r\n");
		exitMNTM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				exitEvent();
			}
		});
		Label toplineLeftSeparator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		toplineLeftSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(shell, SWT.NONE);
		
		Label toplineRightSeparator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		toplineRightSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite toolsComposite = new Composite(shell, SWT.NONE);
		toolsComposite.setTouchEnabled(true);
		toolsComposite.setToolTipText("tools");
		toolsComposite.setLayout(new FormLayout());
		GridData gd_toolsComposite = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 7);
		gd_toolsComposite.heightHint = 486;
		gd_toolsComposite.widthHint = 305;
		gd_toolsComposite.minimumWidth = 150;
		gd_toolsComposite.minimumHeight = 200;
		toolsComposite.setLayoutData(gd_toolsComposite);
		
		CTabFolder NSLTab = new CTabFolder(toolsComposite, SWT.BORDER);
		FormData fd_NSLTab = new FormData();
		fd_NSLTab.bottom = new FormAttachment(0, 178);
		fd_NSLTab.right = new FormAttachment(0, 300);
		fd_NSLTab.top = new FormAttachment(0, 5);
		fd_NSLTab.left = new FormAttachment(0, 5);
		NSLTab.setLayoutData(fd_NSLTab);
		NSLTab.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem NewMazeTabIeitem = new CTabItem(NSLTab, SWT.NONE);
		NewMazeTabIeitem.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		NewMazeTabIeitem.setText("New Maze");
		
		Group groupNewMaze = new Group(NSLTab, SWT.NONE);
		groupNewMaze.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		NewMazeTabIeitem.setControl(groupNewMaze);
		groupNewMaze.setLayout(new GridLayout(3, false));
		
		Label floorsLabel = new Label(groupNewMaze, SWT.NONE);
		GridData gd_floorsLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_floorsLabel.widthHint = 78;
		floorsLabel.setLayoutData(gd_floorsLabel);
		floorsLabel.setFont(SWTResourceManager.getFont("Segoe Print", 10, SWT.NORMAL));
		floorsLabel.setText("Floors");
		
		Label heightLabel = new Label(groupNewMaze, SWT.NONE);
		GridData gd_heightLabel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_heightLabel.widthHint = 79;
		heightLabel.setLayoutData(gd_heightLabel);
		heightLabel.setFont(SWTResourceManager.getFont("Segoe Print", 10, SWT.NORMAL));
		heightLabel.setTouchEnabled(true);
		heightLabel.setText("Height");
		
		Label widthLabel = new Label(groupNewMaze, SWT.NONE);
		GridData gd_widthLabel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_widthLabel.widthHint = 81;
		widthLabel.setLayoutData(gd_widthLabel);
		widthLabel.setTouchEnabled(true);
		widthLabel.setFont(SWTResourceManager.getFont("Segoe Print", 10, SWT.NORMAL));
		widthLabel.setText("Width");
		
		Spinner floorsSpinner = new Spinner(groupNewMaze, SWT.BORDER);
		floorsSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		floorsSpinner.setMinimum(1);
		floorsSpinner.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		
		Spinner heightSpinner = new Spinner(groupNewMaze, SWT.BORDER);
		heightSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		heightSpinner.setMaximum(300);
		heightSpinner.setMinimum(1);
		heightSpinner.setSelection(20);
		heightSpinner.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		
		
		Spinner widthSpinner = new Spinner(groupNewMaze, SWT.BORDER);
		widthSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		widthSpinner.setMaximum(300);
		widthSpinner.setMinimum(1);
		widthSpinner.setSelection(20);
		widthSpinner.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		new Label(groupNewMaze, SWT.NONE);
		
		Button CreateMazeButton = new Button(groupNewMaze, SWT.FLAT | SWT.CENTER);
		CreateMazeButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		CreateMazeButton.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		CreateMazeButton.setText("Create");
		CreateMazeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int floorsNum=floorsSpinner.getSelection();
				int heightNum=heightSpinner.getSelection();
				int widthNum=widthSpinner.getSelection();
				notifyPresenter(new Object[]
				{getCommandTable().get("generate 3d maze"), 
				new String[]{"generate","3d","maze","",getCp().getGeneratorAlgorithm(),""+floorsNum,""+heightNum,""+widthNum}});
				buttonDown.setEnabled(true);
				buttonUp.setEnabled(true);
				mazeDisplay.setFocus();
			}
		});
		new Label(groupNewMaze, SWT.NONE);
		
		CTabItem SaveMazeTabIeItem = new CTabItem(NSLTab, SWT.NONE);
		SaveMazeTabIeItem.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		SaveMazeTabIeItem.setText("Save Maze");
		
		Composite saveMazeComposite = new Composite(NSLTab, SWT.NONE);
		SaveMazeTabIeItem.setControl(saveMazeComposite);
		saveMazeComposite.setLayout(new GridLayout(1, false));
		
		Label mazeNameLabel = new Label(saveMazeComposite, SWT.WRAP | SWT.CENTER);
		mazeNameLabel.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		GridData gd_mazeNameLabel = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_mazeNameLabel.widthHint = 92;
		gd_mazeNameLabel.heightHint = 26;
		mazeNameLabel.setLayoutData(gd_mazeNameLabel);
		mazeNameLabel.setText("Maze Name");
		
		textForName = new Text(saveMazeComposite, SWT.BORDER);
		GridData gd_textForName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textForName.heightHint = 25;
		textForName.setLayoutData(gd_textForName);
		
		Button SaveNewMazeBtn = new Button(saveMazeComposite, SWT.FLAT | SWT.CENTER);
		SaveNewMazeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(mazeDisplay.getMaze3d()!=null){
				if(textForName.getText().equals(""))
					textForName.forceFocus();
				else {
					notifyPresenter(new Object[]{getCommandTable().get("save maze"),
							new String[]{"save","maze",textForName.getText(),""+textForName.getText()+".txt"}});
					mazeDisplay.setMazeName(textForName.getText());
				}		
			}
			}
		});
		SaveNewMazeBtn.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		SaveNewMazeBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		SaveNewMazeBtn.setText("Save New Maze");
		
		
		CTabItem loadMazeTabIeItem = new CTabItem(NSLTab, SWT.NONE);
		loadMazeTabIeItem.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		loadMazeTabIeItem.setText("Load Maze");
		
		loadMazeTable = new Table(NSLTab, SWT.FULL_SELECTION);
		loadMazeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(((Table)e.getSource()).getSelectionIndex()!=-1){
				String mazeName=((Table)e.getSource()).getItem(((Table)e.getSource()).getSelectionIndex()).getText();
				notifyPresenter(new Object[]{getCommandTable().get("get maze"),new String[]{mazeName}});  
				displayChange("maze "+mazeName+" loaded !");
				buttonDown.setEnabled(true);
				buttonUp.setEnabled(true);
				mazeDisplay.setFocus();
				}
			}
		});
		loadMazeTable.setLinesVisible(true);
		loadMazeTable.setSelection(new int[] {2});
		loadMazeTable.setSelection(2);
		loadMazeTable.setHeaderVisible(true);
		loadMazeTabIeItem.setControl(loadMazeTable);
		
	    tableNameColumn = new TableColumn(loadMazeTable, SWT.NONE);
		tableNameColumn.setWidth(110);
		tableNameColumn.setText("Maze Name");
		
	    tableLastSaveColumn = new TableColumn(loadMazeTable, SWT.NONE);
		tableLastSaveColumn.setWidth(168);
		tableLastSaveColumn.setText("Last Save");
		tableLastSaveColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				displaySaveData(choice);
			}});
		
		NSLTab.addSelectionListener(new SelectionAdapter()  {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(NSLTab.getSelection().getText().equals("Load Maze")){
				notifyPresenter(new Object[]{getCommandTable().get("get save mazes"),null});
				}
			}});
		Label separator3 = new Label(toolsComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_separator3 = new FormData();
		fd_separator3.right = new FormAttachment(0, 300);
		fd_separator3.top = new FormAttachment(0, 183);
		fd_separator3.left = new FormAttachment(0, 5);
		separator3.setLayoutData(fd_separator3);
		
		Group helpMeSolveGrp = new Group(toolsComposite, SWT.NONE);
		FormData fd_helpMeSolveGrp = new FormData();
		fd_helpMeSolveGrp.bottom = new FormAttachment(0, 257);
		fd_helpMeSolveGrp.right = new FormAttachment(0, 300);
		fd_helpMeSolveGrp.top = new FormAttachment(0, 190);
		fd_helpMeSolveGrp.left = new FormAttachment(0, 5);
		helpMeSolveGrp.setLayoutData(fd_helpMeSolveGrp);
		helpMeSolveGrp.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		helpMeSolveGrp.setText("Give Me A Hint");
		helpMeSolveGrp.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		CLabel numOfMovesLabel = new CLabel(helpMeSolveGrp, SWT.SHADOW_NONE);
		numOfMovesLabel.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		numOfMovesLabel.setText("num of moves :");
		
		numOfMovesSpinner = new Spinner(helpMeSolveGrp, SWT.BORDER);
		numOfMovesSpinner.setMaximum(500);
		numOfMovesSpinner.setMinimum(1);
		numOfMovesSpinner.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		numOfMovesSpinner.setLayoutData(new RowData(SWT.DEFAULT, 21));
		
		Button GoBtnForHint = new Button(helpMeSolveGrp, SWT.NONE);
		GoBtnForHint.setLayoutData(new RowData(29, 27));
		GoBtnForHint.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		GoBtnForHint.setText("Go");
		GoBtnForHint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                solHintChoice=HINT;
                if(mazeDisplay.getMaze3d()!=null){
                notifyPresenter(new Object[]
			    {getCommandTable().get("solve"),
                new String[]{"solve",mazeDisplay.getMazeName(),getCp().getSolutionAlgorithm(),getCp().getHeuristic()}});
                }
                else displayError("no maze to solve !");    
			}
		});
		
		Label separator2 = new Label(toolsComposite, SWT.SEPARATOR | SWT.WRAP | SWT.HORIZONTAL);
		FormData fd_separator2 = new FormData();
		fd_separator2.right = new FormAttachment(0, 300);
		fd_separator2.top = new FormAttachment(0, 262);
		fd_separator2.left = new FormAttachment(0, 5);
		separator2.setLayoutData(fd_separator2);
		
		Group solveMazeGrp = new Group(toolsComposite, SWT.NONE);
		FormData fd_solveMazeGrp = new FormData();
		fd_solveMazeGrp.bottom = new FormAttachment(0, 340);
		fd_solveMazeGrp.right = new FormAttachment(0, 300);
		fd_solveMazeGrp.top = new FormAttachment(0, 269);
		fd_solveMazeGrp.left = new FormAttachment(0, 5);
		solveMazeGrp.setLayoutData(fd_solveMazeGrp);
		solveMazeGrp.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		solveMazeGrp.setText("Solve Maze");
		RowLayout rl_solveMazeGrp = new RowLayout(SWT.HORIZONTAL);
		rl_solveMazeGrp.fill = true;
		solveMazeGrp.setLayout(rl_solveMazeGrp);
		
		Button solveBtn = new Button(solveMazeGrp, SWT.NONE);
		solveBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	 
				solHintChoice=SOLUTION;
				if(mazeDisplay.getMaze3d()!=null){	
				 notifyPresenter(new Object[]
				    {getCommandTable().get("solve"),
					new String[]{"solve",mazeDisplay.getMazeName(),getCp().getSolutionAlgorithm(),getCp().getHeuristic()}});
				}
				else displayError("no maze to solve !");
			}
		});
		solveBtn.setFont(SWTResourceManager.getFont("Segoe Print", 9, SWT.NORMAL));
		solveBtn.setLayoutData(new RowData(267, SWT.DEFAULT));
		solveBtn.setText("I surrender ! Solve My maze");
		
		textForUpdates = new Text(toolsComposite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		FormData fd_textForUpdates = new FormData();
		fd_textForUpdates.right = new FormAttachment(NSLTab, 0, SWT.RIGHT);
		fd_textForUpdates.left = new FormAttachment(NSLTab, 0, SWT.LEFT);
		textForUpdates.setLayoutData(fd_textForUpdates);
		textForUpdates.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textForUpdates.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		textForUpdates.setToolTipText("updates");
		textForUpdates.setFont(SWTResourceManager.getFont("System", 8, SWT.BOLD));
		fd_textForUpdates.top = new FormAttachment(0, 463);
		
		Image arrowUp=new Image(display, ".\\resources\\arrow_up.png");
		
		
		
		 buttonUp = new Button(toolsComposite, SWT.NONE);
		 buttonUp.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		if(mazeDisplay.getMaze3d()!=null)
		 	    mazeDisplay.showUp();
		 	}
		 });
		buttonUp.setEnabled(false);
		FormData fd_buttonUp = new FormData();
		fd_buttonUp.bottom = new FormAttachment(solveMazeGrp, 51, SWT.BOTTOM);
		fd_buttonUp.top = new FormAttachment(solveMazeGrp, 6);
		fd_buttonUp.left = new FormAttachment(0, 111);
		fd_buttonUp.right = new FormAttachment(0, 175);
		buttonUp.setLayoutData(fd_buttonUp);
		Image fillArrowUp=new Image(display, arrowUp.getImageData().scaledTo(70,50));
		buttonUp.setImage(fillArrowUp);
		
		
		 buttonDown = new Button(toolsComposite, SWT.NONE);
		 buttonDown.addSelectionListener(new SelectionAdapter() {
		 	@Override
		 	public void widgetSelected(SelectionEvent e) {
		 		if(mazeDisplay.getMaze3d()!=null)
			 	    mazeDisplay.showDown();
		 	}
		 });
		buttonDown.setEnabled(false);
		FormData fd_buttonDown = new FormData();
		fd_buttonDown.top = new FormAttachment(textForUpdates, -48, SWT.TOP);
		fd_buttonDown.bottom = new FormAttachment(textForUpdates, -6);
		fd_buttonDown.right = new FormAttachment(buttonUp, 0, SWT.RIGHT);
		fd_buttonDown.left = new FormAttachment(0, 111);
		ImageData imageData = new ImageData(".\\resources\\arrow_down.png");
		Image arrowDown=new Image(display, imageData);
		Image fillArrowDown=new Image(display, arrowDown.getImageData().scaledTo(70,50));
		buttonDown.setLayoutData(fd_buttonDown);
		buttonDown.setImage(fillArrowDown);
		
		
		
		
		
		Label VerticalLineSeparates = new Label(shell, SWT.SEPARATOR | SWT.VERTICAL);
		VerticalLineSeparates.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 7));
		//can replace characters and displayer
		 mazeDisplay = new Maze2DDisplay(shell, SWT.DOUBLE_BUFFERED, 
				 new Maze3DCharacter(),new ImageCharacter("./resources/Hamburger.jpg"));
		 mazeDisplay.setMazeName("");
		 mazeDisplay.addKeyListener(new KeyAdapter() {
		 	@Override
		 	public void keyPressed(KeyEvent e) {
		 		if(!mazeDisplay.displaySolution){
		 		mazeDisplay.setUserPress(e.keyCode);	
		 		mazeDisplay.checkUserPress();
		 		}
		 	}
		 });
		 GridData gd_mazeDisplay = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7);
		 gd_mazeDisplay.heightHint = 443;
		 mazeDisplay.setLayoutData(gd_mazeDisplay);
		 if(!mazeDisplay.getMazeName().equals("")){
				buttonDown.setEnabled(true);
				buttonUp.setEnabled(true);
			}
	   

		
	}

	/**
	 * Property event.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	private void propertyEvent() throws FileNotFoundException{
		FileDialog fd=new FileDialog(shell,SWT.OPEN);
		fd.setText("open");
		fd.setFilterPath("");
		String[] filterExt = { "*.xml" };
		fd.setFilterExtensions(filterExt);
		String fileName = fd.open();
		if(fileName!=null){
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		@SuppressWarnings("resource")
		XMLDecoder xmlDecoder = new XMLDecoder(bis);
		setCp((ClientProperties) xmlDecoder.readObject());
		setChanged();
		notifyObservers();
		}
		}
	
	/**
	 * Load default prop.
	 */
	private void loadDefaultProp(){
		cp.setGeneratorAlgorithm("prim");
		cp.setHeuristic("e");
		cp.setIp("127.0.0.1");
		cp.setPort("4000");
		cp.setSolutionAlgorithm("astar");
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Sets the default prop.
	 *
	 * @param cp the new default prop
	 */
	public void setDefaultProp(ClientProperties cp){
		this.setCp(cp);
		setChanged();
		notifyObservers();
	}
	
    /**
     * Exit event.
     */
    private void exitEvent(){
    	if(mazeDisplay.displaySolution){
    		mazeDisplay.stopSolutionTask();
		}
    	notifyPresenter(new Object[]{getCommandTable().get("exit"),null});
		shell.dispose();
		return;
	}

	/* (non-Javadoc)
	 * @see view.View#displayData(java.lang.Object)
	 */
	@Override
	public void displayData(Object params) {
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
		textForUpdates.setText(change);
	}

	/* (non-Javadoc)
	 * @see view.View#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String error) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.ERROR);
        messageBox.setText("Information");
        messageBox.setMessage(error);
		messageBox.open();
		
	}

	/* (non-Javadoc)
	 * @see view.View#showDirContent(java.lang.String[])
	 */
	@Override
	public void showDirContent(String[] pathName) {	}

	/* (non-Javadoc)
	 * @see view.View#displayMaze3d(java.lang.String, algorithms.mazeGenerators.Maze3d)
	 */
	@Override
	public void displayMaze3d(String mazeName, Maze3d maze) {
		mazeDisplay.setMazeName(mazeName);
		mazeDisplay.setMaze3d(maze);
	}

	/* (non-Javadoc)
	 * @see view.View#displaySolution(java.lang.String, algorithms.search.Solution)
	 */
	@Override
	public void displaySolution(String mazeName, Solution solution) {
		switch (solHintChoice) {
		case HINT:
			mazeDisplay.displaySolOrHint(numOfMovesSpinner.getSelection(),solution);
			break;
		case SOLUTION:
		mazeDisplay.displaySolOrHint(0,solution);
			break;
		default:
			break;
		}
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayCrossSection(char, int, java.lang.String, int[][])
	 */
	@Override
	public void displayCrossSection(char axis, int index, String mazeName,
			int[][] crossSection) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayMazeSize(int, int, int)
	 */
	@Override
	public void displayMazeSize(int height, int length, int width) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see view.View#displayFileSize(java.lang.String, long)
	 */
	@Override
	public void displayFileSize(String mazeName, long size) {
		// TODO Auto-generated method stub
		
	}

	
    /**
     * Display save data.
     *
     * @param order the order
     */
    public void displaySaveData(int order){
    	loadMazeTable.removeAll();
    	switch (order) {
		case 1:
			for (String name :sorterMap.keySet()){
	            new TableItem(loadMazeTable, SWT.NONE, 0).setText(new String[]{ name,dateFormat.format(sorterMap.get(name))});
	    	}
			choice=2;
			break;
		case 2:
			@SuppressWarnings("unchecked")
			TreeMap<String, Date> reverseSorter= (TreeMap<String, Date>) sorterMap.clone();
			reverseSorter.descendingMap();
			for (String name :reverseSorter.descendingMap().keySet()){
	            new TableItem(loadMazeTable, SWT.NONE, 0).setText(new String[]{ name,dateFormat.format(reverseSorter.get(name))});
	    	}
			choice=1;
			break;
		default:
			break;
		}
    	
    	
        
    }

	/* (non-Javadoc)
	 * @see view.View#getSaveData()
	 */
	public HashMap<String, Date> getSaveData() {
       return saveData;
	}

	/* (non-Javadoc)
	 * @see view.View#setSaveData(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void setSaveData(HashMap<String, Date> saveData) {
		if(!(this.saveData.keySet().equals(saveData.keySet())))
		{
		this.saveData =(HashMap<String, Date>) saveData.clone();
		this.sortMap();
		displaySaveData(1);
		}
	}
	
	/**
	 * Sort map.
	 */
	public void sortMap(){
		
		sorterMap.putAll(saveData);
	}
	
	/* (non-Javadoc)
	 * @see view.View#notifyPresenter(java.lang.Object)
	 */
	@Override
	public void notifyPresenter(Object commandAndArgs) {
		setChanged();
		notifyObservers(commandAndArgs);
		
	}
	
	/**
	 * Inits the widgets.
	 *
	 * @wbp.parser.entryPoint 
	 */
	
	public void initWidgets() {

		
	}
	
	/* (non-Javadoc)
	 * @see view.View#getCp()
	 */
	@Override
	public ClientProperties getCp() {
		return cp;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setCp(view.ClientProperties)
	 */
	@Override
	public void setCp(ClientProperties cp) {
		this.cp = cp;
	}
	
	/* (non-Javadoc)
	 * @see view.View#getCommandTable()
	 */
	@Override
	public HashMap<String, Command> getCommandTable() {
		return commandTable;
	}
	
	/* (non-Javadoc)
	 * @see view.View#setCommandTable(java.util.HashMap)
	 */
	@Override
	public void setCommandTable(HashMap<String, Command> commandTable) {
		this.commandTable = commandTable;
	}
}
