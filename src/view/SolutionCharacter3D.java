package view;

import java.util.HashMap;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class SolutionCharacter3D.
 */
public class SolutionCharacter3D extends SolutionCharacter {

/** The ha. */
HashMap<Integer, String> ha;
	
/* (non-Javadoc)
 * @see view.SolutionCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double)
 */
@Override
	public void paint(PaintEvent e, double w, double h) {
		for (Position p:pos) {
			e.gc.setBackground(background);
			e.gc.fillOval((int) Math.round((double)map2.get(p.getX())+(int)p.getY()*(double)map0.get(p.getX())),
					(int) (p.getX()*h-h/4),(int)Math.round(((double)(map0.get(p.getX()))+(double)map1.get(p.getX()))/2),(int)Math.round(h));
			e.gc.setBackground(forground);

			e.gc.fillOval((int) Math.round((double)map2.get(p.getX())+(int)p.getY()*(double)map0.get(p.getX())+2)
					,(int)Math.round(p.getX()*h-h/4+2) , (int)Math.round(((double)(map0.get(p.getX()))+(double)map1.get(p.getX()))/2/1.5), (int)Math.round(h/1.5));
			e.gc.setBackground(new Color(null,0,0,0));
		    
		}
		
		
	}

	
}
