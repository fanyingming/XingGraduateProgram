package edu.buaa.sei.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.buaa.sei.utils.LayoutUtil;

public class InputDialog {
	private Display display;
	private Shell shell;

	public void open() {

		display = Display.getDefault();

		shell = new Shell(SWT.NO_TRIM);

		shell.setLayout(new GridLayout());
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// display.dispose();
	}
}
