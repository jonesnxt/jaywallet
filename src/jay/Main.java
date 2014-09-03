/**
 * 
 */
package jay;

import java.io.IOException;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
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
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) { 
		
		Display display = new Display ();
		Shell shell = new Shell (display);
		Label label = new Label (shell, SWT.NONE);
		label.setText ("Enter your name:");
		Text text = new Text (shell, SWT.BORDER);
		text.setLayoutData (new RowData (100, SWT.DEFAULT));
		Button ok = new Button (shell, SWT.PUSH);
		ok.setText ("OK");
		ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("OK");
			}
		});
		Button cancel = new Button (shell, SWT.PUSH);
		cancel.setText ("Cancel");
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Cancel");
			}
		});
		shell.setDefaultButton (cancel);
		shell.setLayout (new RowLayout ());
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();



		
		
		    }
		
}

