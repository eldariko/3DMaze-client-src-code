package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class Presenter.
 */
public class Presenter implements Observer {

	/** The view. */
	private View view;
	
	/** The model. */
	private Model model;
	
	/** The command factory. */
	private CommandFactory commandFactory;
	
	/**
	 * Instantiates a new presenter.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public Presenter(View view,Model model) {
	this.setView(view);
	this.setModel(model);
	this.commandFactory=new CommandFactory(view, model);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	synchronized public void update(Observable o, Object arg) {
		if(o==view)
		{	
			if(arg==null)
			{
				getProp();
			}
			else{Object[] array=null;
			array=(Object[])arg;
			Command command= (Command) array[0];
			String[] param=(String[]) array[1];
			command.doCommand(param);
			}
			}
		else if(o==model)
		{
			view.displayData(arg);
			
		}
		
	}
	
	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(Model model) {
		this.model = model;
	}
	
	/**
	 * Gets the command table.
	 *
	 * @return the command table
	 */
	public HashMap<String, Command> getCommandTable() {
		return this.commandFactory.getCommandTable();
	}
    
    /**
     * Gets the prop.
     *
     * @return the prop
     */
    private void getProp(){
    	model.setCp(view.getCp()); 
    }

	

}
