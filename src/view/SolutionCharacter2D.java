package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class SolutionCharacter2D.
 */
public class SolutionCharacter2D extends SolutionCharacter {

	/* (non-Javadoc)
	 * @see view.SolutionCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double)
	 */
	@Override
	public void paint(PaintEvent e, double w, double h) {
		e.gc.setForeground(e.gc.getDevice().getSystemColor(SWT.COLOR_YELLOW));
		e.gc.setBackground(e.gc.getDevice().getSystemColor(SWT.COLOR_DARK_BLUE));
		for (Position position : pos) {
			e.gc.fillOval((int)(position.getY() * w),(int) (position.getX() * h), (int)w,(int) h);
		}
		
		
	}

}
