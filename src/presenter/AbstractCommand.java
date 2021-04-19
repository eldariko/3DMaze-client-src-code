package presenter;


import model.Model;
import view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractCommand.
 */
public abstract class AbstractCommand implements Command {

	/** The view. */
	protected View view;
	
	/** The model. */
	protected Model model;
	
	/**
	 * Instantiates a new abstract command.
	 *
	 * @param view2 the view
	 * @param model2 the model
	 */
	public AbstractCommand(View view2 ,Model model2) {
        this.view=view2;
        this.model=model2;
	}
	
	/* (non-Javadoc)
	 * @see controller.Command#doCommand(java.lang.String[])
	 */
	public abstract void doCommand(String[] args) ;

}
