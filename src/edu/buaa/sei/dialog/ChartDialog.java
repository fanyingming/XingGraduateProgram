package edu.buaa.sei.dialog;

import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

import edu.buaa.sei.datamodel.Result;

public class ChartDialog extends TitleAreaDialog {
	private Shell shell;
	public ArrayList<Result> resList;
	private Image image = null;
	
	/**
	 * MyTitleAreaDialog constructor
	 * 
	 * @param shell
	 *            the parent shell
	 */
	public ChartDialog(Shell shell) {
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
		if (image != null)
			setTitleImage(image);
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
        container.setSize(500, 500);
        createChart(container);
        return dialogArea;
	}	
	
	@Override
	
    protected void setShellStyle(final int newShellStyle)
    {
        super.setShellStyle(newShellStyle | SWT.RESIZE);    
    }
	
	@Override
	
	protected org.eclipse.swt.graphics.Point getInitialSize() {
	    final org.eclipse.swt.graphics.Point size = super.getInitialSize();

	    size.x = size.x*5/4;

	    size.y = size.y*3/2;

	    return size;
	}
}
