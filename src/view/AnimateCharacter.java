package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import org.eclipse.swt.events.PaintEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimateCharacter.
 */
public class AnimateCharacter extends GameCharacter {

	/** The image data array. */
	private ImageData[] imageDataArray;
	
	/** The e. */
	PaintEvent e;
	
	/** The animate thread. */
	private Thread animateThread;
	
	/** The shell background. */
	Color shellBackground;
	
	/* (non-Javadoc)
	 * @see view.GameCharacter#paint(org.eclipse.swt.events.PaintEvent, double, double, double, double)
	 */
	@Override
	void paint(PaintEvent e, double x, double y, double w, double h) {
	      ImageLoader loader = new ImageLoader();
	      this.e=e;
	      //shellGC=new GC(e.display.getActiveShell()); 
	      imageDataArray =loader.load("C:/Users/eldar/Desktop/caveman_run.gif");
	        if (imageDataArray.length > 1) {
	          animateThread = new Thread("Animation") {
	            private Color shellBackground;
				private Image image;
				private Image fillImage;
				
				public void run() {
					e.display.asyncExec(new Runnable() {
						
						@Override
						public void run() {
							
							shellBackground=e.display.getActiveShell().getBackground();
				              /* Create an off-screen image to draw on, and fill it with the shell background. */
				              Image offScreenImage = new Image(e.display, loader.logicalScreenWidth, loader.logicalScreenHeight);
				              GC offScreenImageGC = new GC(offScreenImage);
				              offScreenImageGC.setBackground(shellBackground);
				              offScreenImageGC.fillRectangle(0, 0, loader.logicalScreenWidth, loader.logicalScreenHeight);
							try {
				                /* Create the first image and draw it on the off-screen image. */
				                int imageDataIndex = 0;  
				                ImageData imageData = imageDataArray[imageDataIndex];
				                if (image != null && !image.isDisposed() && !fillImage.isDisposed()) 
				                {image.dispose();fillImage.dispose();}
				                image = new Image(e.display, imageData);
				                fillImage=new Image(e.display, image.getImageData().scaledTo((int)Math.round(w),(int)Math.round(h)));
				                offScreenImageGC.drawImage(fillImage,imageData.x,imageData.y);
				                /* Now loop through the images, creating and drawing each one
				                 * on the off-screen image before drawing it on the shell. */
				                int repeatCount = loader.repeatCount;
				                while (loader.repeatCount == 0 || repeatCount > 0) {
				                	switch (imageData.disposalMethod) {
				                  case SWT.DM_FILL_BACKGROUND:
				                    /* Fill with the background color before drawing. */
				                    Color bgColor = null;
				                    offScreenImageGC.setBackground(bgColor != null ? bgColor : shellBackground);
				                    offScreenImageGC.fillRectangle(imageData.x, imageData.y, 100, 80);
				                    break;
				                  case SWT.DM_FILL_PREVIOUS:
				                    /* Restore the previous image before drawing. */
				                   offScreenImageGC.drawImage(fillImage,imageData.x,imageData.y);
				                    break;
				                  }
				                            
				                  imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
				                  imageData = imageDataArray[imageDataIndex];
				                  image.dispose();
				                  fillImage.dispose();
				                  image = new Image(e.display, imageData);
				                  fillImage=new Image(e.display, image.getImageData().scaledTo((int)Math.round(w),(int)Math.round(h)));
				                  offScreenImageGC.drawImage(fillImage,imageData.x,imageData.y);
				                  
				                  /* Draw the off-screen image to the shell. */
				                  e.gc.drawImage(offScreenImage, 0, 0);
				                  
				                  /* Sleep for the specified delay time (adding commonly-used slow-down fudge factors). */
				                  try {
				                    int ms = imageData.delayTime * 10;
				                    if (ms < 20) ms += 30;
				                    if (ms < 30) ms += 10;
				                    Thread.sleep(ms);
				                  } catch (InterruptedException e) {
				                	  System.out.println("InterruptedException");
				                  }
				                  
				                  /* If we have just drawn the last image, decrement the repeat count and start again. */
				                  if (imageDataIndex == imageDataArray.length - 1) 
				                	  repeatCount--;
				                }
				              } catch (SWTException ex) {
				                ex.printStackTrace();
				            	  System.out.println("There was an error animating the GIF");
				              } finally {
				                if (offScreenImage != null && !offScreenImage.isDisposed()) offScreenImage.dispose();
				                if (offScreenImageGC != null && !offScreenImageGC.isDisposed()) offScreenImageGC.dispose();
				                if (image != null && !image.isDisposed() &&!fillImage.isDisposed()) 
				                {image.dispose();fillImage.dispose();}
				              }
				            }
					    });
	              }
	        };
	    animateThread.setDaemon(true);
        animateThread.start();
	     
	        
	    }
	  }
	
    

}
