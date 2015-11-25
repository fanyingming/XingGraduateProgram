package edu.buaa.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.crypto.Data;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.*;
import org.xml.sax.SAXException;

import edu.buaa.sei.run.*;
import edu.buaa.sei.datamodel.*;

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
	//	 base = "/Users/fanyingming/GitRepo/XingGraduateProgram/CaseStudy";
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

		MyTitleAreaDialog dlg = new MyTitleAreaDialog(window.getShell());
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

class DirDialog extends TitleAreaDialog {
	private Shell shell;
	private String basePath;
	Text text;

	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public DirDialog(Shell shell) {
		super(shell);
		this.shell = shell;
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
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createContents(Composite parent) {
		Control contents = super
				.createContents((org.eclipse.swt.widgets.Composite) parent);

		// Set the title
		setTitle("SELECT");

		// Set the message
		String titleContent = "Please tell us where the UML model located.";
		setMessage(titleContent, IMessageProvider.INFORMATION);
		return contents;
	}

	/**
	 * Creates the gray area
	 * 
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		new Label(composite, SWT.NONE).setText("Directory:");

		// Create the text box extra wide to show long paths
		text = new Text(composite, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		text.setLayoutData(data);
		
		// Clicking the button will allow the user
				// to select a directory
				Button button = new Button(composite, SWT.PUSH);
				button.setText("Browse ...");
				GridData data1 = new GridData(GridData.HORIZONTAL_ALIGN_END);
				button.setLayoutData(data1);
				button.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						DirectoryDialog dlg = new DirectoryDialog(shell);

						// Set the initial filter path according
						// to anything they've selected or typed in
						dlg.setFilterPath(text.getText());

						// Change the title bar text
						dlg.setText("SWT's DirectoryDialog");

						// Customizable message displayed in the dialog
						dlg.setMessage("Select a directory");

						// Calling open() will open and run the dialog.
						// It will return the selected directory, or
						// null if user cancels
						String dir = dlg.open();
						if (dir != null) {
							// Set the text box to the new selection
							text.setText(dir);
						}
					}
				});
	
		return composite;
	}

	/**
	 * Creates the buttons for the button bar
	 * 
	 * @param parent
	 *            the parent composite
	 */

	public void okPressed() {
		basePath = text.getText();
		super.okPressed();
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
}

class MyTitleAreaDialog extends TitleAreaDialog {
	public ArrayList<Result> resList;
	public ArrayList<internalResult> internalList;
	public String schedPolicy;


	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
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
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createContents(Composite parent) {
		Control contents = super
				.createContents((org.eclipse.swt.widgets.Composite) parent);

		// Set the title
		setTitle("RESULT");

		// Set the message
		String titleContent = "Schedule Policy: " + schedPolicy;
		setMessage(titleContent, IMessageProvider.INFORMATION);

		return contents;
	}

	
	
	/**
	 * Creates the gray area
	 * 
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Table table1 = new Table(composite, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		table1.setLayoutData(new GridData(GridData.FILL_BOTH));
		table1.setLinesVisible(true);
		table1.setHeaderVisible(true);
		String[] titles1 = { "From", "To", "SendDataSize(KB)", "Number",
				"LostPackage1st", "2nd", "time(ms)", "reliability(%)" };

		for (int i = 0; i < titles1.length; i++) {
			TableColumn column = new TableColumn(table1, SWT.RIGHT);
			column.setText(titles1[i]);
		}
		for (int i = 0; i < internalList.size(); i++) {
			String from = internalList.get(i).from;
			String to = internalList.get(i).to;
			String sendDataSize = String.format("%d",
					internalList.get(i).sendDataSize);
			String number = String.format("%d", internalList.get(i).number);
			String lostPackage1st = String.format("%d",
					internalList.get(i).lostPackage1st);
			String lostPackage2st = String.format("%d",
					internalList.get(i).lostPackage2nd);
			String time = String.format("%.2f", internalList.get(i).time);
			String reliability = String.format("%.2f",
					internalList.get(i).reliability);
			TableItem item = new TableItem(table1, SWT.NONE);
			item.setText(0, from);
			item.setText(1, to);
			item.setText(2, sendDataSize);
			item.setText(3, number);
			item.setText(4, lostPackage1st);
			item.setText(5, lostPackage2st);
			item.setText(6, time);
			item.setText(7, reliability);
		}
		for (int i = 0; i < titles1.length; i++) {
			table1.getColumn(i).pack();
		}

		Table table = new Table(composite, SWT.MULTI | SWT.BORDER
				| SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		String[] titles = { "Name", "Reliability(%)", "Time(ms)" };
		for (int i = 0; i < titles.length; i++) {
			TableColumn column = new TableColumn(table, SWT.RIGHT);
			column.setText(titles[i]);
		}
		for (int i = 0; i < resList.size(); i++) {
			String name = resList.get(i).name;
			String relia = String.format("%.2f", resList.get(i).reliability);
			String time = String.format("%.2f", resList.get(i).time);
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, name);
			item.setText(1, relia);
			item.setText(2, time);
		}
		for (int i = 0; i < titles.length; i++) {
			table.getColumn(i).pack();
		}
		return composite;
	}
	
	public void okPressed() {
		super.okPressed();
	}
	
	/**
	 * Creates the buttons for the button bar
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID, "Show Chart",
				true);
	}
}

class ChartDialog extends TitleAreaDialog {
	private Shell shell;
	public ArrayList<Result> resList;
	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public ChartDialog(Shell shell) {
		super(shell);
		this.shell = shell;
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
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createContents(Composite parent) {
		Control contents = super
				.createContents((org.eclipse.swt.widgets.Composite) parent);

		// Set the title
		setTitle("CHART");

		// Set the message
		String titleContent = "...";
		setMessage(titleContent, IMessageProvider.INFORMATION);
		return contents;
	}


	private Chart createChart(final Composite parent)
    {
		// create a chart
        Chart chart = new Chart(parent, SWT.NONE);

        // set titles
        chart.getTitle().setText("Reliability Chart");
        chart.getAxisSet().getXAxis(0).getTitle().setText("Node Name");
        chart.getAxisSet().getYAxis(0).getTitle().setText("Reliability(%)");
        

        String[] strList = new String[resList.size()];
        double[] reliaList = new double[resList.size()];
        double[] timeList = new double[resList.size()];
        
        for (int i = 0; i < resList.size(); i++) {
        	strList[i] = resList.get(i).name;
        	reliaList[i] = resList.get(i).reliability;
        	timeList[i] = resList.get(i).time;
        }
        // set category
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(strList);
        
        // create bar series
        IBarSeries barSeries1 = (IBarSeries) chart.getSeriesSet().createSeries(
                SeriesType.BAR, "Reliability");
        barSeries1.setYSeries(reliaList);
        
        barSeries1.setBarPadding(50);
        Color color = new Color(parent.getDisplay().getDefault(), 80, 240, 180);
        barSeries1.setBarColor(color);
 
        chart.getAxisSet().adjustRange();
        
        //another chart.
        chart = new Chart(parent, SWT.NONE);
        
     // set titles
        chart.getTitle().setText("Time Chart");
        chart.getAxisSet().getXAxis(0).getTitle().setText("Node Name");
        chart.getAxisSet().getYAxis(0).getTitle().setText("Time(ms)");
        
        for (int i = 0; i < resList.size(); i++) {
        	strList[i] = resList.get(i).name;
        	reliaList[i] = resList.get(i).reliability;
        	timeList[i] = resList.get(i).time;
        }
        // set category
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(strList);

        // create bar series
        IBarSeries barSeries11 = (IBarSeries) chart.getSeriesSet().createSeries(
                SeriesType.BAR, "Time");
        barSeries11.setYSeries(timeList);
        barSeries11.setBarPadding(50);

        // adjust the axis range
        chart.getAxisSet().adjustRange();
        return chart;
    }
	
	/**
	 * Creates the gray area
	 * 
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	protected Control createDialogArea(Composite parent) {
		final Composite dialogArea = (Composite) super.createDialogArea(parent);

        final Composite container = new Composite(dialogArea, SWT.NULL);
        container.setLayout(new FillLayout());
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        createChart(container);

        return dialogArea;
	}	
	
	@Override
	
    protected void setShellStyle(final int newShellStyle)
    {
        super.setShellStyle(newShellStyle | SWT.RESIZE);    
    }
}

