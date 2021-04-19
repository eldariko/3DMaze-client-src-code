package view;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze3DCharacter.
 */
public class Maze3DCharacter extends GameCharacter {

//	public Maze3DCharacter(Color backGround, Color forGround) {
//		this.background=backGround;
//		this.forground=forGround;
/* (non-Javadoc)
 * @see view.GameCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double, double, double)
 */
//	}
	@Override
	void paint(PaintEvent e,double x, double y, double w, double h) {
		 e.gc.setBackground(background);
		   e.gc.fillOval((int)Math.round(x),(int)Math.round(y),(int)Math.round(w),(int)Math.round(h));
		   e.gc.setBackground(forground);
		   e.gc.fillOval((int)Math.round(x+2),(int)Math.round(y+2),(int)Math.round(w/1.5),(int)Math.round(h/1.5));
		   e.gc.setBackground(new Color(null,0,0,0));
//	          if(i==characterY && j==characterX){
//				   e.gc.setBackground(new Color(null,200,0,0));
//				   e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
//				   e.gc.setBackground(new Color(null,255,0,0));
//				   e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
//				   e.gc.setBackground(new Color(null,0,0,0));				        	  
//	          }
	}

}
