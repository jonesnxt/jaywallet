/**
 * 
 */
package jay;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import crypto.Convert;
 

/**
 * @author jones
 *
 */
public class Jay {

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
	        acc.setLayoutData(format(370,20, "bottom", lacc, 5));
	        
	        final Label lamt = new Label(shell, SWT.NONE);
	        lamt.setText("Amount:");
	        lamt.setLayoutData(format(60,20, "bottom", acc, 10));

	        
	        final Text amt = new Text(shell, SWT.BORDER);
	        amt.setLocation(100, 10);
	        amt.setLayoutData(format(370,20, "bottom", lamt, 5));
	        
	        final Label lpass = new Label(shell, SWT.NONE);
	        lpass.setText("Secret Phrase:");
	        lpass.setLayoutData(format(120,20, "bottom", amt, 10));
	        
	        final Text pass = new Text(shell, SWT.PASSWORD+SWT.BORDER);
	        pass.setLocation(100, 10);
	        pass.setLayoutData(format(370,20, "bottom", lpass, 5));

	        Jay.info = new Label(shell, SWT.NONE);
	        info.setText("Initialized");
	        info.setLayoutData(format(400,20, "bottom", pass, 10));

	        


	        Button bsend = new Button(shell, SWT.PUSH);
	        bsend.setText("Send Money");

	        FormData fbsend = new FormData(100, 30);
	        fbsend.right = new FormAttachment(98);
	        fbsend.bottom = new FormAttachment(95);
	        bsend.setLayoutData(fbsend);
	        
	        bsend.addSelectionListener(new SelectionAdapter() {
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	                String bts = Convert.toHexString(new Account(pass.getText()).sendMoney(new Account(null, acc.getText()), Convert.parseLong(amt.getText())));
	                System.out.println(bts);
	                try {
						Nxtapi.broadcast(bts);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						 e1.printStackTrace();
					}
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
		if(att.contains("left")) f.left = new FormAttachment(to, offset, SWT.RIGHT);
		if(att.contains("right")) f.left = new FormAttachment(to, offset, SWT.LEFT);
		if(att.contains("bottom")) f.top = new FormAttachment(to, offset, SWT.BOTTOM);
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
		Jay.info.setText(dat);
		Jay.info.update();
	}
		
}

