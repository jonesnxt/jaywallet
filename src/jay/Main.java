/**
 * 
 */
package jay;

import java.io.IOException;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
 

/**
 * @author jones
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static Label info;
	public static void dealwapi(Display display)
	{
		       
	        Shell shell = new Shell(display);
	        shell.setText("Jay Wallet");
	        shell.setSize(400, 300);
	        
	        
	        
	        FormLayout layout = new FormLayout();
	        shell.setLayout(layout);
	        
	        final Label lacc = new Label(shell, SWT.NONE);
	        lacc.setText("Account:");
	        //lacc.setLayoutData(new FormData(100,30));
	        
	        final Text acc = new Text(shell, SWT.BORDER);
	        acc.setLocation(100, 10);
	        acc.setLayoutData(format(230,20, "left", lacc, 5));
	        
	        final Text amt = new Text(shell, SWT.BORDER);
	        amt.setLocation(100, 10);
	        amt.setLayoutData(format(230,20, "bottom", acc, 10));
	        
	        final Text pass = new Text(shell, SWT.PASSWORD+SWT.BORDER);
	        pass.setLocation(100, 10);
	        pass.setLayoutData(format(230,20, "bottom", amt, 10));

	        Main.info = new Label(shell, SWT.NONE);
	        info.setText("Initialized");
	        info.setLayoutData(format(230,20, "bottom", pass, 10));

	        

	        Button bgetbal = new Button(shell, SWT.PUSH);
	        bgetbal.setText("Get Balance");

	        Button bsend = new Button(shell, SWT.PUSH);
	        bsend.setText("Send Money");

	        FormData fdbal = new FormData(100, 30);
	        fdbal.right = new FormAttachment(98);
	        fdbal.bottom = new FormAttachment(95);
	        bgetbal.setLayoutData(fdbal);

	        FormData fdsend = new FormData(110, 30);
	        fdsend.right = new FormAttachment(bgetbal, -5, SWT.LEFT);
	        fdsend.bottom = new FormAttachment(bgetbal, 0, SWT.BOTTOM);
	        bsend.setLayoutData(fdsend);
	        
	        bgetbal.addSelectionListener(new SelectionAdapter() {
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	                try {
						Nxtapi.consensus("getBalance", "account="+acc.getText()).getString("balanceNQT").toString();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                // payment stuff
	            }
	        });

	        shell.open();

	        while (!shell.isDisposed()) {
	          if (!display.readAndDispatch()) {
	            display.sleep();
	          }
	        }
	}
	
	public static FormData format(int wid, int hei, String att, Control to, int offset)
	{
		FormData f = new FormData(wid, hei);
		if(att == "left") f.left = new FormAttachment(to, offset, SWT.RIGHT);
		if(att == "right") f.left = new FormAttachment(to, offset, SWT.LEFT);
		if(att == "bottom") f.top = new FormAttachment(to, offset, SWT.BOTTOM);
		return f;
	}
	
	
	public static void startup()
	{
		System.out.println("Jay wallet startup (:");
		
	}
	
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) { 
		startup();
		
		Display display = new Display();
        dealwapi(display);
        display.dispose();



		
		
		    }
	
	public static void setinfo(String dat)
	{
		Main.info.setText(dat);
		Main.info.update();
	}
		
}

