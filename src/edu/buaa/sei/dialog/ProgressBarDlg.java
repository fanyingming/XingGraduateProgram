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
    	barContent.add("1. 正在解析模型...");
    	barContent.add("2. 模型转换为指令...");
    	barContent.add("3. 指令计算中...");
  /*  	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");
    	barContent.add("3. 指令计算中...");*/
    }
 
    public void open() {
    	setBarContent();
              display = Display.getDefault();
              
              shell = new Shell(SWT.NO_TRIM);
              shell.setSize(width+10, height);
              // ---------创建窗口中的其他界面组件-------------
              shell.setLayout(new GridLayout());
              createStatusbar(shell);
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
                                    try {  Thread.sleep(1000);          } catch (Throwable e2) {} //停一秒
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
              //设置工具栏在Shell中的形状为水平抢占充满，并高19像素
              GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
              gridData.heightHint = height;
              statusbar.setLayoutData(gridData);
              //设置为用行列式布局管理状态栏里的组件
              RowLayout layout = new RowLayout();
              layout.marginLeft = layout.marginTop = 0; //无边距
              statusbar.setLayout(layout);
              progressBar = createProgressBar(statusbar);
              //创建一个用于显示文字的标签
              statusbarLabel = new Label(statusbar, SWT.None);
              statusbarLabel.setLayoutData(new RowData(width, -1));
              
              statusbar.layout();// 重新布局一下工具栏，使进度条显示出来
    }
    //创建进度条
    private ProgressBar createProgressBar(Composite parent) {
              ProgressBar progressBar = new ProgressBar(parent, SWT.SMOOTH);
              progressBar.setMinimum(0); // 最小值
              progressBar.setMaximum(100);// 最大值
              progressBar.setLayoutData(new RowData(width, -1));
              return progressBar;
    }

}
