package edu.buaa.sei.dialog;

import org.eclipse.swt.graphics.Color;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;

import edu.buaa.Activator;
import edu.buaa.sei.datamodel.Dependency;
import edu.buaa.sei.datamodel.Result;
import edu.buaa.sei.datamodel.internalResult;
import edu.buaa.sei.run.Publisher;

public class ResultDialog extends TitleAreaDialog {
	Shell shell;
	public ArrayList<Result> resList;
	public ArrayList<internalResult> internalList;
	public ArrayList<Dependency> dependencyList;
	public ArrayList<Publisher> publisherList;
	public String schedPolicy;
	private Image image = null;
	private int realtime_dataSize1;
	private int realtime_dataSize2;
	private int reliability_dataSize1;
	private int reliability_dataSize2;
	private double max_delay1;
	private double max_delay2;
	private double max_reliability1;
	private double max_reliability2;

	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public ResultDialog(Shell shell) {
		super(shell);
		Bundle bundle = Platform.getBundle("edu.buaa.dspemat");
		final URL fullPathString = FileLocator.find(bundle, new Path(
				"icons/logo.png"), null);

		ImageDescriptor imageDesc = ImageDescriptor
				.createFromURL(fullPathString);

		image = imageDesc.createImage();
		getConfig();
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
		setTitle("·ÂÕæ½á¹û");

		// Set the message
		String titleContent = "Schedule Policy: " + schedPolicy;
		setMessage(titleContent, IMessageProvider.INFORMATION);
		if (image != null)
			setTitleImage(image);
		return contents;
	}

	private void getConfig() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String realtime_datasize1_str = store.getString(Activator.PREF_PARAM_1);
		String max_delay1_str = store.getString(Activator.PREF_PARAM_2);
		String reliability_dataSize1_str = store
				.getString(Activator.PREF_PARAM_3);
		String max_reliability1_str = store.getString(Activator.PREF_PARAM_4);

		realtime_dataSize1 = Integer.parseInt(realtime_datasize1_str);
		max_delay1 = Double.parseDouble(max_delay1_str);
		reliability_dataSize1 = Integer.parseInt(reliability_dataSize1_str);
		max_reliability1 = Double.parseDouble(max_reliability1_str);

		String realtime_datasize2_str = store.getString(Activator.PREF_PARAM_5);
		String max_delay2_str = store.getString(Activator.PREF_PARAM_6);
		String reliability_dataSize2_str = store
				.getString(Activator.PREF_PARAM_7);
		String max_reliability2_str = store.getString(Activator.PREF_PARAM_8);

		realtime_dataSize2 = Integer.parseInt(realtime_datasize2_str);
		max_delay2 = Double.parseDouble(max_delay2_str);
		reliability_dataSize2 = Integer.parseInt(reliability_dataSize2_str);
		max_reliability2 = Double.parseDouble(max_reliability2_str);
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
			int dataSize = internalList.get(i).sendDataSize;
			String sendDataSize = String.format("%d", dataSize);
			String number = String.format("%d", internalList.get(i).number);
			String lostPackage1st = String.format("%d",
					internalList.get(i).lostPackage1st);
			String lostPackage2st = String.format("%d",
					internalList.get(i).lostPackage2nd);
			double time_used = internalList.get(i).time;
			double reliability_cur = internalList.get(i).reliability;
			String time = String.format("%.2f", time_used);
			String reliability = String.format("%.2f", reliability_cur);
			TableItem item = new TableItem(table1, SWT.NONE);
			item.setText(0, from);
			item.setText(1, to);
			item.setText(2, sendDataSize);
			item.setText(3, number);
			item.setText(4, lostPackage1st);
			item.setText(5, lostPackage2st);
			item.setText(6, time);
			item.setText(7, reliability);

			Color red = Display.getDefault().getSystemColor(SWT.COLOR_RED);
			if (this.realtime_dataSize1 > 0 && this.realtime_dataSize2 > 0) {
				if (dataSize <= this.realtime_dataSize1) {
					if (time_used >= this.max_delay1) {
						item.setBackground(6, red);
					}
				} else {
					if (time_used >= this.max_delay2) {
						item.setBackground(6, red);
					}
				}
			}

			if (this.reliability_dataSize1 > 0
					&& this.reliability_dataSize2 > 0) {
				if (dataSize <= this.reliability_dataSize1) {
					if (reliability_cur < this.max_reliability1) {
						item.setBackground(7, red);
					}
				} else {
					if (reliability_cur < this.max_reliability2) {
						item.setBackground(7, red);
					}
				}
			}

		}
		for (int i = 0; i < titles1.length; i++) {
			table1.getColumn(i).pack();
		}

		Table table = new Table(composite, SWT.SINGLE | SWT.BORDER
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
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				String string = "";
		        TableItem[] selection = table.getSelection();
		        if (selection == null || selection.length == 0)
		        	return;
		        String nodeName = selection[0].getText(0);
		        System.out.println(nodeName);
		        NodeChartDialog nodeChartDialog = new NodeChartDialog(shell);
		        nodeChartDialog.title = nodeName;
		        nodeChartDialog.internalList = internalList;
		        nodeChartDialog.resList = resList;
		        nodeChartDialog.dependencyList = dependencyList;
		        nodeChartDialog.publisherList = publisherList;
		        nodeChartDialog.open();
			}
		});
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
		createButton(parent, IDialogConstants.CANCEL_ID, "Show Chart", true);
	}
}
