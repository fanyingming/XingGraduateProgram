package edu.buaa.sei.dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import edu.buaa.sei.utils.LayoutUtil;

public class ProgressBarDlg {
	private Display display;
    private Shell shell;
    private Composite statusbar;
    private Label statusbarLabel;
    private ProgressBar progressBar;
    private Button hideProbarButton;
    private ArrayList<String> barContent;
    private int width = 300;
    private int height = 80;
    
    public void setBarContent() {
    	barContent = new ArrayList<String>();
    	barContent.add("prepare");
    	barContent.add("1. 指令转换...");
    	barContent.add("2. 指令计算...");
    	barContent.add("3. 结果生成...");
    }
 
    public void open() {
    	setBarContent();
              display = Display.getDefault();
              
              shell = new Shell(SWT.NO_TRIM);
              
      
              shell.setLayout(new GridLayout());
              createStatusbar(shell);
              shell.setSize(width+10, statusbarLabel.getSize().y + progressBar.getSize().y + 10);
              // -----------------END------------------------
              shell.layout();
              LayoutUtil.centerShell(display, shell);
              shell.open();
              showProgress(shell);
              while (!shell.isDisposed()) {
                       if (!display.readAndDispatch())
                                display.sleep();
              }
    //          display.dispose();
    }

    private void showProgress(Composite parent) {
        
        new Thread() {
                  public void run() {
                           for (int i = 1; i < barContent.size(); i++) {
                                    moveProgressBar(i);
                                    try {  Thread.sleep(1000);          } catch (Throwable e2) {} 
                           }
                           disposeProgressBar();
                  }
                  private void moveProgressBar(final int i) {
                           display.asyncExec(new Runnable() {
                                    public void run() {
                                              if (!statusbarLabel.isDisposed())
                                                       statusbarLabel.setText(barContent.get(i));
                                              if (!progressBar.isDisposed())
                                                       progressBar.setSelection((int)(((double)i)/(barContent.size()-1) * 100));
                                    }
                           });
                  }
                  private void disposeProgressBar() {
                           if (display.isDisposed())   return;
                           display.asyncExec(new Runnable() {
                                    public void run() {
                                              progressBar.dispose();
                                              shell.dispose();
                                    }
                           }); 
                  }
        }.start();
    }
 
    private void createStatusbar(Composite parent) {
              statusbar = new Composite(parent, SWT.NONE);
          
              GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
              gridData.heightHint = height;
              statusbar.setLayoutData(gridData);
            
              RowLayout layout = new RowLayout();
              layout.marginLeft = layout.marginTop = 0; 
              statusbar.setLayout(layout);
              progressBar = createProgressBar(statusbar);
         
              statusbarLabel = new Label(statusbar, SWT.None);
              statusbarLabel.setLayoutData(new RowData(width, -1));
              
              statusbar.layout();
    }
  
    private ProgressBar createProgressBar(Composite parent) {
              ProgressBar progressBar = new ProgressBar(parent, SWT.SMOOTH);
              progressBar.setMinimum(0); 
              progressBar.setMaximum(100);
              progressBar.setLayoutData(new RowData(width, -1));
              return progressBar;
    }

}
