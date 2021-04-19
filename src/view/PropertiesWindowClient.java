package view;


import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertiesWindowClient.
 */
public class PropertiesWindowClient {
	
	/** The shell. */
	Shell shell;
	
	/** The display. */
	Display display;
	
	/** The first run. */
	static boolean firstRun=true;
    
    /** The view. */
    MyGuiView view;
    
    /** The cp. */
    ClientProperties cp;
    
    /**
     * Instantiates a new properties window client.
     *
     * @param view the view
     */
    public PropertiesWindowClient(MyGuiView view)
	{
	this.view=view;	
	}
	
	/**
	 * Open.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		//display.dispose();
		//System.exit(0);
	}

	/**
	 * Create contents of the window.
	 * 
	 
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(578, 389);
		shell.setLayout(new GridLayout(1, true));
        shell.setText("client Properties");
		Composite composite = new Composite(shell, SWT.BORDER);
		GridData gd_composite = new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1);
		gd_composite.heightHint = 336;
		gd_composite.widthHint = 567;
		composite.setLayoutData(gd_composite);
		composite.setFont(SWTResourceManager.getFont("Comic Sans MS", 10,SWT.BOLD));
		composite.setLayout(new GridLayout(2, false));

		Group grpGeneratorAlgorithm = new Group(composite, SWT.NONE);
		GridData gd_grpGeneratorAlgorithm = new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1);
		gd_grpGeneratorAlgorithm.heightHint = 134;
		gd_grpGeneratorAlgorithm.widthHint = 277;
		grpGeneratorAlgorithm.setLayoutData(gd_grpGeneratorAlgorithm);
		grpGeneratorAlgorithm.setFont(SWTResourceManager.getFont(
				"Comic Sans MS", 10, SWT.BOLD));
		grpGeneratorAlgorithm.setText("Generator Algorithm");
		grpGeneratorAlgorithm.setLayout(new GridLayout(3, false));
		Button btnPrim = new Button(grpGeneratorAlgorithm, SWT.RADIO);
		btnPrim.setFont(SWTResourceManager
				.getFont("Comic Sans MS", 9, SWT.BOLD));
		btnPrim.setAlignment(SWT.CENTER);
		btnPrim.setSelection(true);
		btnPrim.setText("Prim");
		new Label(grpGeneratorAlgorithm, SWT.NONE);

		Button btnSimple = new Button(grpGeneratorAlgorithm, SWT.RADIO);
		btnSimple.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		btnSimple.setText("Simple");

		Group grpSolutionAlgorithm = new Group(composite, SWT.NONE);
		grpSolutionAlgorithm.setFont(SWTResourceManager.getFont(
				"Comic Sans MS", 10, SWT.BOLD));
		grpSolutionAlgorithm.setTouchEnabled(true);
		grpSolutionAlgorithm.setLayout(new FormLayout());
		GridData gd_grpSolutionAlgorithm = new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1);
		gd_grpSolutionAlgorithm.heightHint = 134;
		gd_grpSolutionAlgorithm.widthHint = 228;
		grpSolutionAlgorithm.setLayoutData(gd_grpSolutionAlgorithm);
		grpSolutionAlgorithm.setText("Solution Algorithm");
		grpSolutionAlgorithm.setBounds(0, 0, 119, 82);

		Label lblAStar = new Label(grpSolutionAlgorithm, SWT.NONE);
		lblAStar.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		FormData fd_lblAStar = new FormData();
		fd_lblAStar.top = new FormAttachment(0, 5);
		fd_lblAStar.left = new FormAttachment(0, 5);
		lblAStar.setLayoutData(fd_lblAStar);
		lblAStar.setText("A Star");

		Button btnEuclidean = new Button(grpSolutionAlgorithm, SWT.RADIO);
		btnEuclidean.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		FormData fd_btnEuclidean = new FormData();
		fd_btnEuclidean.top = new FormAttachment(0, 25);
		fd_btnEuclidean.left = new FormAttachment(0, 5);
		btnEuclidean.setLayoutData(fd_btnEuclidean);
		btnEuclidean.setSelection(true);
		btnEuclidean.setText("Euclidean");

		Button btnManhattan = new Button(grpSolutionAlgorithm, SWT.RADIO);
		btnManhattan.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		FormData fd_btnManhattan = new FormData();
		fd_btnManhattan.right = new FormAttachment(0, 221);
		fd_btnManhattan.top = new FormAttachment(0, 25);
		fd_btnManhattan.left = new FormAttachment(0, 108);
		btnManhattan.setLayoutData(fd_btnManhattan);
		btnManhattan.setText("Manhattan");

		Label lblBfs = new Label(grpSolutionAlgorithm, SWT.NONE);
		lblBfs.setFont(SWTResourceManager.getFont("Comic Sans MS", 9, SWT.BOLD));
		FormData fd_lblBfs = new FormData();
		fd_lblBfs.top = new FormAttachment(0, 46);
		fd_lblBfs.left = new FormAttachment(0, 5);
		lblBfs.setLayoutData(fd_lblBfs);
		lblBfs.setText("BFS");

		Button btnBfs = new Button(grpSolutionAlgorithm, SWT.RADIO);
		btnBfs.setFont(SWTResourceManager.getFont("Comic Sans MS", 9, SWT.BOLD));
		FormData fd_btnBfs = new FormData();
		fd_btnBfs.top = new FormAttachment(0, 66);
		fd_btnBfs.left = new FormAttachment(0, 5);
		btnBfs.setLayoutData(fd_btnBfs);
		btnBfs.setText("BFS");

		
		Group grpNetworking = new Group(composite, SWT.NONE);
		grpNetworking.setFont(SWTResourceManager.getFont("Comic Sans MS", 10,
				SWT.BOLD));
		grpNetworking.setLayout(new GridLayout(4, false));
		GridData gd_grpNetworking = new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 1, 1);
		gd_grpNetworking.heightHint = 118;
		gd_grpNetworking.widthHint = 290;
		grpNetworking.setLayoutData(gd_grpNetworking);
		grpNetworking.setText("Networking");
		grpNetworking.setBounds(0, 0, 70, 82);

		Label lblPort = new Label(grpNetworking, SWT.NONE);
		lblPort.setFont(SWTResourceManager
				.getFont("Comic Sans MS", 9, SWT.BOLD));
		lblPort.setText("Port :");

		Spinner spPort = new Spinner(grpNetworking, SWT.BORDER);
		GridData gd_spPort = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_spPort.widthHint = 37;
		spPort.setLayoutData(gd_spPort);
		spPort.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		spPort.setPageIncrement(1);
		spPort.setMaximum(8000);
		spPort.setMinimum(1024);
		spPort.setSelection(4000);
		new Label(grpNetworking, SWT.NONE);
		new Label(grpNetworking, SWT.NONE);

		Label lblIp = new Label(grpNetworking, SWT.NONE);
		lblIp.setFont(SWTResourceManager.getFont("Comic Sans MS", 9, SWT.BOLD));
		lblIp.setText("IP :");
		new Label(grpNetworking, SWT.NONE);
		new Label(grpNetworking, SWT.NONE);
		new Label(grpNetworking, SWT.NONE);

		Spinner spIp1 = new Spinner(grpNetworking, SWT.BORDER);
		spIp1.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		spIp1.setMaximum(255);
		spIp1.setSelection(127);

		Spinner spIp2 = new Spinner(grpNetworking, SWT.BORDER);
		GridData gd_spIp2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_spIp2.widthHint = 27;
		spIp2.setLayoutData(gd_spIp2);
		spIp2.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
				SWT.BOLD));
		spIp2.setPageIncrement(1);
		spIp2.setMaximum(255);
				
						Spinner spIp3 = new Spinner(grpNetworking, SWT.BORDER);
						GridData gd_spIp3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
						gd_spIp3.widthHint = 24;
						spIp3.setLayoutData(gd_spIp3);
						spIp3.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
								SWT.BOLD));
						spIp3.setPageIncrement(1);
						spIp3.setMaximum(255);
				
						Spinner spIp4 = new Spinner(grpNetworking, SWT.BORDER);
						GridData gd_spIp4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
						gd_spIp4.widthHint = 30;
						spIp4.setLayoutData(gd_spIp4);
						spIp4.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,
								SWT.BOLD));
						spIp4.setPageIncrement(1);
						spIp4.setMaximum(255);
						spIp4.setSelection(1);

		if(firstRun)
			grpNetworking.setVisible(true);
		else grpNetworking.setVisible(false);
		
		
		Group grpSubmitButton = new Group(composite, SWT.NONE);
		grpSubmitButton.setFont(SWTResourceManager.getFont("Comic Sans MS", 10,
				SWT.BOLD));
		GridData gd_grpSubmitButton = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_grpSubmitButton.heightHint = 120;
		gd_grpSubmitButton.widthHint = 216;
		grpSubmitButton.setLayoutData(gd_grpSubmitButton);
		grpSubmitButton.setText("Submit Button");

		Button btnSubmit = new Button(grpSubmitButton, SWT.FLAT | SWT.CENTER);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				firstRun=false;
				 cp=new ClientProperties();
				if(btnPrim.getSelection())
				cp.setGeneratorAlgorithm("prim");
				else cp.setGeneratorAlgorithm("simple");
				if(btnEuclidean.getSelection()){
					cp.setSolutionAlgorithm("astar");
				    cp.setHeuristic("e");
				}
				else if(btnManhattan.getSelection()){
					cp.setSolutionAlgorithm("astar");
				    cp.setHeuristic("m");
				}
				else {
					cp.setSolutionAlgorithm("bfs");
					cp.setHeuristic("");
				}
			cp.setPort(""+spPort.getSelection());
			cp.setIp(spIp1.getSelection()+"."+spIp2.getSelection()+"."+spIp3.getSelection()+"."+spIp4.getSelection());
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream("properties.xml");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			XMLEncoder xmlEncoder = new XMLEncoder(bos);
			xmlEncoder.writeObject(cp);
			xmlEncoder.close();
			view.setDefaultProp(cp);
			shell.close();	
			}
		});
		btnSubmit.setGrayed(true);
		btnSubmit
				.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnSubmit.setSelection(true);
		btnSubmit.setFont(SWTResourceManager.getFont("Comic Sans MS", 9,SWT.BOLD));
		btnSubmit.setBounds(20, 42, 176, 74);
		btnSubmit.setText("Submit");

		
		
		

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		PropertiesWindowClient p = new PropertiesWindowClient(new MyGuiView("s", 12, 1));
		p.open();
	}
}
