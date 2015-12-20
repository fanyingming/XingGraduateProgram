package edu.buaa.actions;

import java.awt.Window;
import java.net.URL;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;


public class AboutAction implements IWorkbenchWindowActionDelegate{
	private IWorkbenchWindow window;
	
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		AboutDialog aboutdlg = new AboutDialog(window.getShell());
		aboutdlg.open();
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

class AboutDialog extends TitleAreaDialog {
	private Shell shell;
	private Image image = null;
	private String aboutContent = "1. ֧�ֻ���DDS�м���ֲ�ʽϵͳ�Ĺ��Թ��ܽ�ģ����ʵ��DDS�м��ģ�Ͳ��ֵ����ã���Ҫ������Ϣ����ģ�͡���Ϣ����ģ�͡���Ϣ����ģ�͵ȡ�\n"
                                + "2. ֧�ֻ���DDS�м���ֲ�ʽϵͳ������ģ�ͽ��������ṩ�ǹ������Բ��������ܽ����������������ڷ����ֲ�ʽϵͳ����ģ�͵Ľ�����\n"
                                + "3. ֧��ģ����ʵ��������ģ�͵ķ�ʽ��������ʵ��������ִ�еĳ������Ӷ���֤�����������ȷ�ԡ�\n"
                                + "4. ֧����������ģ�͵�ʵʱ�Է����Ϳɿ��Է������ܡ�ʵʱ�Է�����Ҫ�Ƿ���ϵͳ������Դ���յ���Ϣ���������ڵ�����������д���ֱ��������ʾ�ն������ĵ�ʱ�䡣�ɿ��Է�����ָ������ʾ�ն˽��յ���������Դ�����ݵĸ��ʡ�\n"
                                + "5. �˻��������棺�Կ��ӻ��ķ�ʽ������������";

	Text text;

	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public AboutDialog(Shell shell) {
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
		setTitle("About");

		// Set the message
	//	String titleContent = "Performance Modeling and Analysis Tool Based on DDS Middleware for Disributed System";
	//	setMessage(titleContent, IMessageProvider.INFORMATION);
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
	
		Text content = new Text(shell, SWT.WRAP|SWT.V_SCROLL|SWT.READ_ONLY); 
		content.setText(aboutContent);
		return composite;
	}
/*	
	@Override
	public Point getInitialSize() {
		Point p = new Point(500, 400);
		return p;
	}
	*/
}

