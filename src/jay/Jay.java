/**
 * 
 */
package jay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import json.JSONArray;
import json.JSONObject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import crypto.Constants;
 

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
	
	public static Account master;
	public static JSONObject trans;
	public static JSONObject data;
	public static void startup(Display display)
	{
		System.out.println("Jay wallet startup (:");
		Shell shell = new Shell(display);
        shell.setText("Jay Wallet");
        shell.setSize(300, 100);
		shell.setLayout(new FillLayout());
		
        Jay.info = new Label(shell, SWT.NONE);
        Jay.setinfo("startup");
        
        shell.open();
        trans = new JSONObject(Constants.JSON_DEF);
        data = new JSONObject(Constants.JSON_DEF);
        master = new Account(getFile("nxt.pass"));
		try {
			trans = Nxtapi.consensus("getAccountTrasactions", "account="+master.rs);
			data = Nxtapi.consensus("getAccount", "account="+master.rs);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(trans.toString());
		
		shell.close();
		
		
	}
	
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) { 
		Display display = Display.getDefault();
		
		//startup(display);
		
		//Display display = new Display();
        //newlayout(display);
        //display.dispose();
		Shell shell2 = new Shell(display);
		shell2.setLayout(new FillLayout());
		shell2.setText("Jay Wallet");
		final Browser browser;
		try {
			browser = new Browser(shell2, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		shell2.open();
		browser.setText(getFile("html/index.html"));
		
		final BrowserFunction getaddress = new Fgetaddress (browser, "getaddress ");
		final BrowserFunction gettransaction = new Fgettransactions (browser, "gettransactions");

		
		while (!shell2.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		
		
}
	
	static String getFile(String f)
	{
			File file = new File(f);
			BufferedReader reader = null;
			String acc = "";

			try {
			    reader = new BufferedReader(new FileReader(file));
			    String text = null;
			    while ((text = reader.readLine()) != null) {
			        acc += text;
			    }
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    try {
			        if (reader != null) {
			            reader.close();
			        }
			    } catch (IOException e) {
			    }
			}
			

			//print out the list
			return acc;
	}
	
	public static void setinfo(String dat)
	{
		Jay.info.setText(dat);
		Jay.info.update();
	}
	
	
	static class Fgetaddress extends BrowserFunction {
		Fgetaddress (Browser browser, String name) {
			super (browser, name);
		}
		@Override
		public String function (Object[] arguments) {
			return master.rs;
		}
	}
	
	static class Fgettransactions extends BrowserFunction {
		Fgettransactions (Browser browser, String name) {
			super (browser, name);
		}
		@Override
		public String function (Object[] arguments) {
			System.out.println("abc");
			JSONObject tra = new JSONObject(getFile("testtrans.txt"));
			JSONArray arr = tra.getJSONArray("transaction");
			String acc = "";
			System.out.println("abc");

			for(int a=0; a <= arr.length(); a++)
			{
				JSONObject ind = arr.getJSONObject(a);
				acc += "<tr>";
				acc += "<td>" + ind.getLong("amountNQT")/Constants.ONE_NXT + "</td>";
				acc += "<td>" + ind.getString("senderRS") + "</td>";
				acc += "<td>" + "10 sec ago" + "</td>";
				acc += "<td>" + "0 Confs" + "</td>";
				acc += "</tr>";
				
			}
			
			System.out.println(acc);
			return acc;
			
		}
	}
		
}



