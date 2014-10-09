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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import api.APIServlet;
import crypto.Constants;
import crypto.Convert;

import org.eclipse.jetty.http.HttpTester.Request;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.server.nio.*;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;



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
	public static boolean logged;
	public static JSONObject trans;
	public static JSONObject data;
	
	
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) throws InterruptedException { 
		
		startjetty();
		
		
		System.out.println("hello?");
		Display display = Display.getDefault();
		
		//startup(display);
		
		Shell shell2 = new Shell(display);
		shell2.setLayout(new FillLayout());
		shell2.setText("Jay - NXT Modular");
		
		//Display display = new Display();
        //newlayout(display);
        //display.dispose();
		
		final Browser browser;
		try {
			browser = new Browser(shell2, SWT.WEBKIT);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			display.dispose();
			return;
		}
		
		
		
		//Thread.sleep(500000);
		shell2.open();
		

		browser.setUrl("http://127.0.0.1:9987/");
		
		while (!shell2.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
}
	
	static String getfile(String f)
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

	static void startjetty()
	{	
		
		Server server = new Server();
		 
        ServerConnector connector;
        connector = new ServerConnector(server);
		connector.setPort(9987);
		connector.setHost("127.0.0.1");
        connector.setIdleTimeout(30000);
        connector.setReuseAddress(true);
        server.addConnector(connector);
        HandlerList apiHandlers = new HandlerList();
        
        ServletContextHandler apiHandler = new ServletContextHandler();

        ServletHolder defaultServletHolder = new ServletHolder(new DefaultServlet());
        defaultServletHolder.setInitParameter("dirAllowed", "false");
        defaultServletHolder.setInitParameter("resourceBase", "plugins");
        defaultServletHolder.setInitParameter("welcomeServlets", "true");
        defaultServletHolder.setInitParameter("redirectWelcome", "true");
        defaultServletHolder.setInitParameter("gzip", "false");
        apiHandler.addServlet(defaultServletHolder, "/*");
        apiHandler.setWelcomeFiles(new String[]{"index.html"});
        apiHandler.addServlet(APIServlet.class, "/api");
        
        apiHandlers.addHandler(apiHandler);
        apiHandlers.addHandler(new DefaultHandler());

        server.setHandler(apiHandlers);
        server.setStopAtShutdown(true);
        
        try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("???");
		}
        
        
	}
	
	
	
		
}





