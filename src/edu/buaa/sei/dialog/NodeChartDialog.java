package edu.buaa.sei.dialog;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;

import edu.buaa.sei.datamodel.Dependency;
import edu.buaa.sei.datamodel.Result;
import edu.buaa.sei.datamodel.internalResult;
import edu.buaa.sei.run.Publisher;

public class NodeChartDialog extends TitleAreaDialog{
	private Shell shell;
	private Image image = null;
	public String title = "Chart";
	public ArrayList<Result> resList;
	public ArrayList<internalResult> internalList;
	public ArrayList<Dependency> dependencyList;
	public ArrayList<Publisher> pubList;
	public ArrayList<Publisher> publisherList;
	
	public NodeChartDialog(Shell shell) {
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
		setTitle(title);

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
		final Composite dialogArea = (Composite) super.createDialogArea(parent);

        final Composite container = new Composite(dialogArea, SWT.NULL);
        container.setLayout(new FillLayout());
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        container.setSize(500, 500);
        getMostTimeConsumingPath();
		showTimeChart(container);
        return dialogArea;
	}
	private void showTimeChart(Composite composite) {
		// create a chart
        Chart chart = new Chart(composite, SWT.NONE);

        // set titles
        chart.getTitle().setText("Path Time");
        chart.getAxisSet().getXAxis(0).getTitle().setText("Node Name");
        chart.getAxisSet().getYAxis(0).getTitle().setText("time(ms)");
        

        String[] nameList = new String[pubList.size()];
        double[] timeList = new double[pubList.size()];
        int j = 0;
        for (int i = pubList.size()-1; i >=0 ; i--, j++) {
        	nameList[j] = pubList.get(i).getPublisherName();
        	timeList[j] = pubList.get(i).getUpLevelTime();
        }
        // set category
        chart.getAxisSet().getXAxis(0).enableCategory(true);
        chart.getAxisSet().getXAxis(0).setCategorySeries(nameList);
        
        // create line series
        ILineSeries lineSeries = (ILineSeries) chart.getSeriesSet()
            .createSeries(SeriesType.LINE, "most time consuming");
        lineSeries.setYSeries(timeList);
        
        
 
     // adjust the axis range
        chart.getAxisSet().adjustRange();
     
	}
	
	private void getMostTimeConsumingPath() {
		Publisher pub = null;
		if (publisherList == null)
			return;
		for (int i = 0; i < publisherList.size(); i++) {
			Publisher p = publisherList.get(i);
			if (p.getPublisherName().equals(title)) {
				pub = p;
				break;
			}
		}
		if (pub == null)
			return;
		
		this.pubList = new ArrayList<Publisher>();
		while (pub != null) {
			this.pubList.add(pub);
			System.out.println(pub.getPublisherName() + ", " + pub.getUpLevelTime());
			int i;
			for (i = 0; i < publisherList.size(); i++) {
				Publisher p = publisherList.get(i);
				if (p.getPublisherId().equals(pub.getUpLevelPublisherId())) {
					pub = p;
					break;
				}
			}
			if (i == publisherList.size())
				break;
		}
	}
	
	@Override
	
    protected void setShellStyle(final int newShellStyle)
    {
        super.setShellStyle(newShellStyle | SWT.RESIZE);    
    }
}