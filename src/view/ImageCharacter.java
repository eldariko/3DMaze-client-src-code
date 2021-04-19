package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageCharacter.
 */
public class ImageCharacter extends GameCharacter {

	/**
	 * Instantiates a new image character.
	 *
	 * @param imagePath the image path
	 */
	public ImageCharacter(String imagePath) {
	this.ImagePath=imagePath;
}
	
	/* (non-Javadoc)
	 * @see view.GameCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double, double, double)
	 */
	@Override
	void paint(PaintEvent e, double x, double y, double w, double h) {
		Image image=null;
		if(ImagePath !=null){
			image=new Image(e.display, getImagePath()); 
            e.gc.drawImage(image, 0, 0, 
   image.getBounds().width,image.getBounds().height, (int)Math.round(x),(int)Math.round(y),(int)Math.round(w),(int)Math.round(h));		
		}
	}

}
