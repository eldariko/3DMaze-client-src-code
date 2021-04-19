package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class GameCharacter.
 */
public abstract class GameCharacter  {
	   
   	/** The y. */
   	int z,x,y;
	    
    	/** The background. */
    	Color background;
		
		/** The forground. */
		Color forground;
		 
 		/** The Image path. */
 		String ImagePath;
	   
   	/**
   	 * Instantiates a new game character.
   	 */
   	public GameCharacter(){};
	   
	/**
	 * Paint.
	 *
	 * @param e the e
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 */
	abstract void paint(PaintEvent e,double x, double y,double w,double h);
	
	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}
    
    /**
     * Sets the zxy.
     *
     * @param z the z
     * @param x the x
     * @param y the y
     */
    public void setZXY(int z,int x,int y){
    	this.z=z;
    	this.x=x;
    	this.y=y;
    }
	
	/**
	 * Gets the background.
	 *
	 * @return the background
	 */
	public Color getBackground() {
		return background;
	}
	
	/**
	 * Sets the background.
	 *
	 * @param background the new background
	 */
	public void setBackground(Color background) {
		this.background = background;
	}
	
	/**
	 * Gets the forground.
	 *
	 * @return the forground
	 */
	public Color getForground() {
		return forground;
	}
	
	/**
	 * Sets the forground.
	 *
	 * @param forground the new forground
	 */
	public void setForground(Color forground) {
		this.forground = forground;
	}
	
	/**
	 * Sets the colors.
	 *
	 * @param backGround the back ground
	 * @param forGround the for ground
	 */
	public void setColors(Color backGround,Color forGround){
		this.background=backGround;
		this.forground=forGround;
	}

	/**
	 * Gets the image path.
	 *
	 * @return the image path
	 */
	public String getImagePath() {
		return ImagePath;
	}

	/**
	 * Sets the image path.
	 *
	 * @param imagePath the new image path
	 */
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	
}
