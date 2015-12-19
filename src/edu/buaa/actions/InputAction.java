package edu.buaa.actions;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.osgi.framework.Bundle;

import edu.buaa.Activator;
import edu.buaa.sei.dialog.*;

public class InputAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		InputDialog inputdlg = new InputDialog(window.getShell());
		int rv = inputdlg.open();
		if ( rv == Window.OK ) {
			
		} else {
			
		}
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		this.window = window;
	}
}

class InputDialog extends TitleAreaDialog {
	private Shell shell;
	private Image image = null;
	
	private Text dataSizeText1;
	private Text delayText1;
	private Text dataSizeText_reliability1;
	private Text reliabilityText1;
	private Text dataSizeText2;
	private Text delayText2;
	private Text dataSizeText_reliability2;
	private Text reliabilityText2;
	
	Text text;

	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public InputDialog(Shell shell) {
		super(shell);
		this.shell = shell;
		Bundle bundle = Platform.getBundle("edu.buaa.dspemat");
		final URL fullPathString = FileLocator.find(bundle, new Path(
				"icons/logo.png"), null);

		ImageDescriptor imageDesc = ImageDescriptor
				.createFromURL(fullPathString);

		image = imageDesc.createImage();
	}

	/**
	 * Closes the dialog box Override so we can dispose the image we created
	 */
	public boolean close() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(Activator.PREF_PARAM_1, this.dataSizeText1.getText());
        store.setDefault(Activator.PREF_PARAM_2, this.delayText1.getText());
        store.setDefault(Activator.PREF_PARAM_3, this.dataSizeText_reliability1.getText());
        store.setDefault(Activator.PREF_PARAM_4, this.reliabilityText1.getText());
        
        store.setDefault(Activator.PREF_PARAM_5, this.dataSizeText2.getText());
        store.setDefault(Activator.PREF_PARAM_6, this.delayText2.getText());
        store.setDefault(Activator.PREF_PARAM_7, this.dataSizeText_reliability2.getText());
        store.setDefault(Activator.PREF_PARAM_8, this.reliabilityText2.getText());
        
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
		setTitle("配置性能指标");

		// Set the message
		// String titleContent =
		// "Performance Modeling and Analysis Tool Based on DDS Middleware for Disributed System";
		// setMessage(titleContent, IMessageProvider.INFORMATION);
		if (image != null)
			setTitleImage(image);

		return contents;
	}
	
	private void createInput1(Composite composite) {
		Group testGroup = new Group(composite, SWT.NONE);
		testGroup.setText("实时性(节点之间)");
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginWidth = 30;
		layout.marginHeight = 10;
		testGroup.setLayout(layout);
		testGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		{
			Composite composite1 = new Composite(testGroup, SWT.NONE);
			GridLayout layoutComposite = new GridLayout();
			layoutComposite.numColumns = 4;
			layoutComposite.marginHeight = 5;
			composite1.setLayout(layoutComposite);
			composite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
					true, 4, 1));
		}
		{
			Label dataSizeLabel1 = new Label(testGroup, SWT.NONE);
			dataSizeLabel1.setText("数据大小1(KB): ");

			dataSizeText1 = new Text(testGroup, SWT.BORDER);
			dataSizeText1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Label delayLabel1 = new Label(testGroup, SWT.NONE);
			delayLabel1.setText("最大时延1(ms): ");

			delayText1 = new Text(testGroup, SWT.BORDER);
			delayText1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			//new
			Label dataSizeLabel2 = new Label(testGroup, SWT.NONE);
			dataSizeLabel2.setText("数据大小2(KB): ");

			dataSizeText2 = new Text(testGroup, SWT.BORDER);
			dataSizeText2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Label delayLabel2 = new Label(testGroup, SWT.NONE);
			delayLabel2.setText("最大时延2(ms): ");

			delayText2 = new Text(testGroup, SWT.BORDER);
			delayText2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
	}
	
	private void createInput2(Composite composite) {
		Group testGroup = new Group(composite, SWT.NONE);
		testGroup.setText("可靠性(节点之间)");
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginWidth = 30;
		layout.marginHeight = 10;
		testGroup.setLayout(layout);
		testGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		{
			Composite composite1 = new Composite(testGroup, SWT.NONE);
			GridLayout layoutComposite = new GridLayout();
			layoutComposite.numColumns = 4;
			layoutComposite.marginHeight = 5;
			composite1.setLayout(layoutComposite);
			composite1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
					true, 4, 1));
		}
		{
			Label dataSizeLabel1 = new Label(testGroup, SWT.NONE);
			dataSizeLabel1.setText("数据大小1(KB): ");

			dataSizeText_reliability1 = new Text(testGroup, SWT.BORDER);
			dataSizeText_reliability1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
			Label delayLabel1 = new Label(testGroup, SWT.NONE);
			delayLabel1.setText("可靠性1(%): ");

			reliabilityText1 = new Text(testGroup, SWT.BORDER);
			reliabilityText1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			//new
			Label dataSizeLabel2 = new Label(testGroup, SWT.NONE);
			dataSizeLabel2.setText("数据大小2(KB): ");

			dataSizeText_reliability2 = new Text(testGroup, SWT.BORDER);
			dataSizeText_reliability2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
			Label delayLabel2 = new Label(testGroup, SWT.NONE);
			delayLabel2.setText("可靠性2(%): ");

			reliabilityText2 = new Text(testGroup, SWT.BORDER);
			reliabilityText2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
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

		createInput1(composite);
		createInput2(composite);
		
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		dataSizeText1.setText(store.getString(Activator.PREF_PARAM_1));
		delayText1.setText(store.getString(Activator.PREF_PARAM_2));
		dataSizeText_reliability1.setText(store.getString(Activator.PREF_PARAM_3));
		reliabilityText1.setText(store.getString(Activator.PREF_PARAM_4));
		
		dataSizeText2.setText(store.getString(Activator.PREF_PARAM_5));
		delayText2.setText(store.getString(Activator.PREF_PARAM_6));
		dataSizeText_reliability2.setText(store.getString(Activator.PREF_PARAM_7));
		reliabilityText2.setText(store.getString(Activator.PREF_PARAM_8));

		return composite;
	}
}