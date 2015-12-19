package edu.buaa.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.crypto.Data;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.*;
import org.xml.sax.SAXException;

import edu.buaa.Activator;
import edu.buaa.sei.run.*;
import edu.buaa.sei.utils.*;
import edu.buaa.sei.datamodel.*;
import edu.buaa.sei.dialog.*;

import org.swtchart.*;
import org.swtchart.ISeries.SeriesType;



;
/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
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
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		DirDialog dirDlg = new DirDialog(window.getShell());
		int rv =  dirDlg.open();
		if ( rv == Window.OK ) {
			String basePath = dirDlg.getBasePath();
			if (basePath.length() == 0) {
				MessageDialog.openInformation(window.getShell(), "ERROR",
						"Can't find model file.");
			} else {
				showResultDialog(basePath);
				return;
			}
		} else {
			//click cancel.
		}
	}

	void showResultDialog(String base) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(Activator.PREF_PARAM_MODEL_PATH, base);
		String nodeSendPath = base + "/NodeSend.uml";
		String sendPath = base + "/send.uml";
		String reveivePath = base + "/receive.uml";
		String transportPath = base + "/transport.uml";
		String publisherPath = base + "/publisher.uml";

		ArrayList<Result> resList = new ArrayList<Result>();
		ArrayList<internalResult> internalList = new ArrayList<internalResult>();
		String schedPolicy = null;
		try {
			Wrapper wrapper = new Wrapper();
			wrapper.run(nodeSendPath, sendPath, reveivePath, transportPath,
					publisherPath);
			resList = wrapper.getFinalResults();
			internalList = wrapper.getFinalInternalResults();
			schedPolicy = wrapper.getSchedPolicy();
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
		
		ProgressBarDlg progressBarDlg = new ProgressBarDlg();
		progressBarDlg.open();
		
		ResultDialog dlg = new ResultDialog(window.getShell());
		dlg.resList = resList;
		dlg.internalList = internalList;
		dlg.schedPolicy = schedPolicy;
		int rv = dlg.open();
		if (rv != Window.OK ) {
			ChartDialog chartDlg = new ChartDialog(window.getShell());
			chartDlg.resList = dlg.resList;
			
			chartDlg.open();
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}




