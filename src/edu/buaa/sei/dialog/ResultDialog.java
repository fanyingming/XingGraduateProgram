package edu.buaa.sei.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import edu.buaa.sei.datamodel.Result;
import edu.buaa.sei.datamodel.internalResult;

public class ResultDialog extends TitleAreaDialog {
	public ArrayList<Result> resList;
	public ArrayList<internalResult> internalList;
	public String schedPolicy;


	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public ResultDialog(Shell shell) {
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
				"LostPackage1st", "2nd", "time(s)", "reliability(%)" };

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
		String[] titles = { "Name", "Reliability(%)", "Time(s)" };
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
