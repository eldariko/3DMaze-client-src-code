package view;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze2DDisplay.
 */
public class Maze2DDisplay extends MazeDisplay {
	
	/**
	 * Instantiates a new maze2 d display.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param startC the start c
	 * @param goalC the goal c
	 */
	public Maze2DDisplay(Composite parent, int style,GameCharacter startC,GameCharacter goalC) {
		super(parent, style,startC,goalC);
		// set a white background (red, green, blue)
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		Image image=new Image(getDisplay(),"./resources/wall.jpg");
		counter=0;
		actions=new ArrayList<String>();
		positions=new ArrayList<Position>();
		
		sc=new SolutionCharacter2D();
		addPaintListener(new PaintListener() {
			

			@Override
			public void paintControl(PaintEvent e) {
				if (maze3d != null) {
					e.gc.setBackground(new Color(null, 0, 0, 0));
					e.gc.setForeground(new Color(null, 0, 0, 0));

					int height = getSize().y;
					int width = getSize().x;
					h = height / mazeData[0].length;
					w = width / mazeData[0][0].length;
					// e.gc.fillPolygon(new int[]{0,0,w,0,w,w,0,w});
					for (int i = 0; i < mazeData[0].length; i++) {
						for (int j = 0; j < mazeData[0][0].length; j++) {
							 x = j * w;
							 y = i * h;
							if (mazeData[characterZ][i][j] == 1) {
//								e.gc.fillRectangle(x, y, w, h);
                                e.gc.drawImage(image, 0, 0, w, h, x, y, w, h);							
							
							}
						}
					}
                if(characterZ==maze3d.getStartPosition().getZ()){
					startC.setColors(new Color(null, 255, 0, 0), new Color(null, 255, 0, 0));
					startC.paint(e,maze3d.getStartPosition().getY() * w,maze3d.getStartPosition().getX() * h,w, h);
                }
					startC.setBackground(new Color(null, 0, 255, 0));
				if(characterZ==maze3d.getGoalPosition().getZ()){
					goalC.setColors(new Color(null, 0, 255, 0), (new Color(null, 0, 255, 0)));
					goalC.paint(e,goalPosition.getY() * w,goalPosition.getX() * h, w, h);
				}
				
				if(maze3d.getStartPosition().equals(maze3d.getGoalPosition()))
						displayWonMessage();
				}
			}
		});
	}
	

	
//	public void start() {
//		timer = new Timer();
//		task = new TimerTask() {
//
//			@Override
//			public void run() {
//
//				getDisplay().syncExec(new Runnable() {
//
//					@Override
//					public void run() {
//						Random r = new Random();
//						c.x = r.nextInt(200);
//						c.y = r.nextInt(200);
//						redraw();
//					}
//				});
//			}
//
//		};
//		timer.scheduleAtFixedRate(task, 0, 500);
//
//	}
//
//	public void stop() {
//		task.cancel();
//		timer.cancel();
//	}

	
}
