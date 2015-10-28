package edu.buaa.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.*;
import org.xml.sax.SAXException;

import edu.buaa.sei.run.*;
import edu.buaa.sei.datamodel.*;

;/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		String base = "/Users/fanyingming/GitRepo/XingGraduateProgram/CaseStudy";
		String nodeSendPath = base + "/NodeSend.uml";
		String sendPath = base + "/send.uml";
		String reveivePath = base +"/receive.uml";
		String transportPath = base + "/transport.uml";
		String publisherPath = base + "/publisher.uml";
		
		ArrayList<Result> resList = new ArrayList<Result>();
		try {
			resList = Wrapper.run(nodeSendPath, sendPath, reveivePath, transportPath, publisherPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MyTitleAreaDialog dlg = new MyTitleAreaDialog(window.getShell());
		dlg.resList = resList;
        dlg.open();
        /*
		MessageDialog.openInformation(
			window.getShell(),
			"Buaa",
			displayStr);*/
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}

class MyTitleAreaDialog extends TitleAreaDialog {
	 public ArrayList<Result> resList;
	  /**
	   * MyTitleAreaDialog constructor
	   * 
	   * @param shell the parent shell
	   */
	  public MyTitleAreaDialog(Shell shell) {
	    super(shell);
	  }

	  /**
	   * Closes the dialog box Override so we can dispose the image we created
	   */
	  public boolean close() {
	    return super.close();
	  }

	  /**
	   * Creates the dialog's contents
	   * 
	   * @param parent the parent composite
	   * @return Control
	   */
	  protected Control createContents(Composite parent) {
	    Control contents = super.createContents((org.eclipse.swt.widgets.Composite) parent);

	    // Set the title
	    setTitle("RESULT");

	    // Set the message
	    setMessage("write what", IMessageProvider.INFORMATION);

	    return contents;
	  }

	  /**
	   * Creates the gray area
	   * 
	   * @param parent the parent composite
	   * @return Control
	   */
	  protected Control createDialogArea(Composite parent) {
	    Composite composite = (Composite) super.createDialogArea(parent);

	    Table table=new Table(composite,SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	    table.setLayoutData(new GridData(GridData.FILL_BOTH));
	    table.setLinesVisible(true);
	    table.setHeaderVisible(true);
	    String[] titles={"Name","Reliability(%)","Time(ms)"};
	    for (int i=0; i < titles.length; i++) {
	      TableColumn column=new TableColumn(table,SWT.LEFT);
	      column.setText(titles[i]);
	    }
	    for (int i = 0; i < resList.size(); i++) {
	    	String name  = resList.get(i).name;
	    	String relia = String.format("%.2f", resList.get(i).reliability);
	    	String time  = String.format("%.2f", resList.get(i).time);
	    	TableItem item = new TableItem(table, SWT.NONE);
		    item.setText(0, name);
		    item.setText(1, relia);
		    item.setText(2, time);
	    }
	    for (int i=0; i < titles.length; i++) {
	      table.getColumn(i).pack();
	    }
	    return composite;
	  }

	  /**
	   * Creates the buttons for the button bar
	   * 
	   * @param parent the parent composite
	   */
	  protected void createButtonsForButtonBar(Composite parent) {
	    createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	  }
	}