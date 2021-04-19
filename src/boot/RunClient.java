package boot;

import java.io.IOException;
import presenter.Presenter;
import model.Maze3DModel;
import view.MyGuiView;
// TODO: Auto-generated Javadoc

/**
 * The Class RunClient.
 */
public class RunClient {
	
	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 * @wbp.parser.entryPoint 
	 */

	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		MyCliView mazeView=new MyCliView();
//        Maze3DModel mazeModel=new Maze3DModel();
//        Presenter presenter=new Presenter(mazeView, mazeModel);
//        mazeView.addObserver(presenter);
//        mazeModel.addObserver(presenter); 
//        mazeView.start
//        (new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out,true),presenter.getCommandTable());
        
		//
		try {
			MyGuiView window = new MyGuiView("maze game",688,574);
			Maze3DModel mazeModel=new Maze3DModel();
			Presenter presenter=new Presenter(window, mazeModel);
			window.addObserver(presenter);
            mazeModel.addObserver(presenter); 
		    window.setCommandTable(presenter.getCommandTable());
            window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
