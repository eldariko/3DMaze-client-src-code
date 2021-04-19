package view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze3DDisplay.
 */
public class Maze3DDisplay extends MazeDisplay {

	/** The start map. */
	HashMap<Integer, Double> startMap=new HashMap<Integer, Double>();
	
	/** The w0 map. */
	HashMap<Integer, Double> w0Map=new HashMap<Integer, Double>();
	
	/** The w1 map. */
	HashMap<Integer, Double> w1Map=new HashMap<Integer, Double>();
	
	/** The h. */
	double h;
	
	/** The w. */
	double w;
	
	/** The w1. */
	double start,start1,w0,w1;
	
	/** The canvas. */
	//PaintEvent e;
	Canvas canvas;
	
	/**
	 * Instantiates a new maze3 d display.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param startC the start c
	 * @param goalC the goal c
	 */
	public Maze3DDisplay(Composite parent, int style,GameCharacter startC,GameCharacter goalC) {
		super(parent, style,startC,goalC);
		final Color white=new Color(null, 255, 255, 255);
		
		counter=0;
		actions=new ArrayList<String>();
		positions=new ArrayList<Position>();
		sc=new SolutionCharacter3D();
		setBackground(white);
		// canvas = new Canvas(this,SWT.NONE);
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				 //Maze3DDisplay.this.e=e;
				if (maze3d != null) {  
					
					e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));

				   int width=getSize().x;
				   int height=getSize().y;
				   
				   int mx=width/2;
				    h=(double)height/mazeData[0].length;
				    w=(double)width/mazeData[0][0].length;
				   

				    
				    for( x=0;x<mazeData[0].length;x++){
					    w0=0.7*w +0.3*w*x/mazeData[0].length;
					    w1=0.7*w +0.3*w*(x+1)/mazeData[0].length;
					    start=mx-w0*mazeData[0][0].length/2;
					    start1=mx-w1*mazeData[0][0].length/2;
				        startMap.put(x,start);
				        w0Map.put(x,w0);
				        w1Map.put(x,w1);
				       for( y=0;y<mazeData[0][0].length;y++){
//				    	  double []dpoints={     X1   | Y1|     X2       |Y2 |     X3        | Y3    |     X4    | Y4}
				    	  double []dpoints={start+y*w0,x*h,start+(y+1)*w0,x*h,start1+(y+1)*w1,(x+1)*h,start1+y*w1,(x+1)*h};
				          double cheight=h/2;
				          
				         if(x==maze3d.getStartPosition().getX() && y==maze3d.getStartPosition().getY()
				        		 && characterZ==maze3d.getStartPosition().getZ()){
                         startC.setColors(new Color(null,200,0,0), new Color(null,255,0,0));
				         startC.paint(e, start+y*w0, x*h-cheight/2,(w0+w1)/2, h); 					  		 			         
				         }
				         if(x==maze3d.getGoalPosition().getX() && y==maze3d.getGoalPosition().getY() 
				        		 && characterZ==maze3d.getGoalPosition().getZ()){
				        	goalC.setColors(new Color(null,255,210,0), new Color(null,255,250,0));
					  		goalC.paint(e, start+y*w0, x*h-cheight/2,(w0+w1)/2, h); 					  		 
				         }
				         if(mazeData[characterZ][x][y]!=0)
				        	  paintCube(dpoints, cheight,e);
				       }
				   }
//				    if(characterZ==maze3d.getStartPosition().getZ()){
//				    	int x=maze3d.getStartPosition().getX();int y=maze3d.getStartPosition().getY();
//				    	c.setColors(new Color(null,200,0,0), new Color(null,255,0,0));
//				    	c.paint(e, startMap.get(x)+y*w0Map.get(x), x*h-h/4,(w0Map.get(x)+w1Map.get(x))/2, h);
//				    }
//				    if(characterZ==maze3d.getGoalPosition().getZ()){
//				    	int x=maze3d.getGoalPosition().getX();int y=maze3d.getGoalPosition().getY();
//				    	c.setColors(new Color(null,200,0,0), new Color(null,255,0,0));
//				    	c.paint(e, startMap.get(x)+y*w0Map.get(x), x*h-h/4,(w0Map.get(x)+w1Map.get(x))/2, h);
//				    }
				   
				    if(maze3d.getStartPosition().equals(maze3d.getGoalPosition()))
						displayWonMessage();
				
//				    if(displaySolution  ){
//						sc.setPos(positions);
//						sc.setColors( new Color(null,0,200,0),new Color(null,0,255,0));
//						sc.setMap0(w0Map);
//						sc.setMap1(w1Map);
//						sc.setMap2(startMap);
//						sc.paint(e ,w, h);	
//		        	 
//					}
				  
				}
				
			}
			
		});
		
	}
	
	
	
    
	/**
	 * Paint cube.
	 *
	 * @param p the p
	 * @param h the h
	 * @param e the e
	 */
	private void paintCube(double[] p,double h,PaintEvent e){
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        

        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}


	
	






}