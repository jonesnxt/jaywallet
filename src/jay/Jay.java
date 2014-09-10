/**
 * 
 */
package jay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

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
		System.out.println("hello?");
		Display display = Display.getDefault();
		
		startup(display);
		
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
		
		new Fgetaddress (browser, "getaddress");
		new Fgettransactions (browser, "gettransactions");
		new Fgetamount (browser, "getamount");
		
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
	
	public static String timeago(int timestamp)
	{
		Date abc = new Date();
		
		long fromnow =  (System.currentTimeMillis()/1000) - (Constants.EPOCH_BEGINNING/1000) - timestamp;
		
		int days = (int) Math.floor(fromnow/86400);
		int hours = (int) Math.floor((fromnow%86400)/3600);
		int minutes = (int) Math.floor((fromnow%3600)/60);
		int seconds = (int) Math.floor(fromnow&60);
		String acc = "";
		if(days != 0 && days != 1) acc = days + " days ago";
		else if(days == 1) acc = " 1 day ago";
		else if(hours != 0 && hours != 1) acc = hours + " hours ago";
		else if(hours == 1) acc = "1 hour ago";
		else if(minutes != 0 && minutes != 1) acc = minutes + " minutes ago";
		else if(minutes == 1) acc = "1 minute ago";
		else if(seconds != 0 && seconds != 1) acc = seconds + " seconds ago";
		else if(seconds == 1) acc = "1 second ago";
		else acc = "just now";
		
		return acc;
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
	
	static class Fgetamount extends BrowserFunction {
		Fgetamount (Browser browser, String name) {
			super (browser, name);
		}
		@Override
		public String function (Object[] arguments) {
			return data.getString("unconfirmedBalanceNQT");
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
			System.out.println(tra.toString());
			JSONArray arr = tra.getJSONArray("transactions");
			String acc = "";
			System.out.println("abc");

			for(int a=0; a != arr.length(); a++)
			{
				JSONObject ind = arr.getJSONObject(a);
				acc += "<tr>";
				acc += "<td>" + ind.getLong("amountNQT")/Constants.ONE_NXT + "</td>";
				acc += "<td>" + ind.getString("senderRS") + "</td>";
				acc += "<td>" + timeago(ind.getInt("timestamp")) + "</td>";
				acc += "<td>" + ind.getInt("confirmations") + "</td>";
				acc += "</tr>";
				
			}
			
			System.out.println(acc);
			return acc;
			
		}
	}
	
		
}



