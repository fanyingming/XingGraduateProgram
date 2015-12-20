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
	private String aboutContent = "1. 支持基于DDS中间件分布式系统的共性功能建模，即实现DDS中间件模型部分的重用，主要包括消息发布模型、消息发送模型、消息接收模型等。\n"
                                + "2. 支持基于DDS中间件分布式系统的整体模型建立，并提供非功能属性参数对性能进行量化，即有助于分析分布式系统部署模型的建立。\n"
                                + "3. 支持模拟真实场景，以模型的方式描述在现实环境中所执行的场景，从而保证分析结果的正确性。\n"
                                + "4. 支持面向性能模型的实时性分析和可靠性分析功能。实时性分析主要是分析系统从数据源接收到信息，经过多层节点对数据流进行处理，直至流向显示终端所消耗的时间。可靠性分析是指分析显示终端接收到来自数据源中数据的概率。\n"
                                + "5. 人机交互界面：以可视化的方式输出分析结果。";

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

