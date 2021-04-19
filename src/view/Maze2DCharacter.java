package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Maze2DCharacter.
 */
public class Maze2DCharacter extends GameCharacter {

	
	
//	public Maze2DCharacter(Color backGround, Color forGround) {
//		this.background=backGround;
//		this.forground=forGround;
//	}

	/* (non-Javadoc)
 * @see view.GameCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double, double, double)
 */
@Override
	void paint(PaintEvent e,double x, double y,double w, double h) {
		e.gc.setBackground(background);
		e.gc.setForeground(forground);
		e.gc.fillOval((int)x,(int)y, (int)w, (int)h);
		e.gc.setBackground(new Color(null,0,0,0));
		e.gc.setForeground(new Color(null,0,0,0));
//		sc.setColors(new Color(null,0,200,0),new Color(null,0,255,0));
//		sc.setZ(characterZ);
//		sc.setX(characterX);
//		sc.setY(characterY);
//		sc.setPos(positions);
//		sc.paint(e, w, h);
	}

}
