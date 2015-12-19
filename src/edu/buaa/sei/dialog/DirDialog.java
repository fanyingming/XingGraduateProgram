package edu.buaa.sei.dialog;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

import edu.buaa.Activator;

public class DirDialog extends TitleAreaDialog {
	private Shell shell;
	private String basePath;
	private Image image = null;
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
		Bundle bundle = Platform.getBundle("edu.buaa.dspemat");
		final URL fullPathString = FileLocator.find(bundle, new Path("icons/logo.png"), null);

		ImageDescriptor imageDesc = ImageDescriptor.createFromURL(fullPathString);

		image = imageDesc.createImage();
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
		if (image != null)
			setTitleImage(image);
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
		//load memory path.
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		text.setText(store.getString(Activator.PREF_PARAM_MODEL_PATH));
		
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
