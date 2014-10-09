package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;

import jay.Jay;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import api.APIConsensus.GetBalance;

public class APIAccounts {
	
	public static class ListAccountFiles extends APIServlet.APIRequestHandler {

	    static final ListAccountFiles instance = new ListAccountFiles();

	    private ListAccountFiles() {
	        super(new APITag[] {APITag.ACCOUNTS});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	File folder = new File("accounts/");
	    	File[] listOfFiles = folder.listFiles();
	    	JSONObject rtn = new JSONObject();
	    	JSONArray fnames = new JSONArray();
	    	    for (int i = 0; i < listOfFiles.length; i++) {
	    	      if (listOfFiles[i].isFile()) {
	    	        fnames.add(listOfFiles[i].getName());
	    	        
	    	      }
	    	    }
	    	rtn.put("accounts", fnames);
	    	return rtn;
	    }

	}
	
	public static class GetAccountFile extends APIServlet.APIRequestHandler {

	    static final GetAccountFile instance = new GetAccountFile();

	    private GetAccountFile() {
	        super(new APITag[] {APITag.ACCOUNTS});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	File account = new File("accounts/" + req.getParameter("name"));
	    	JSONObject rtn = new JSONObject();
	    	try {
				rtn.put("data", new String(Files.readAllBytes(account.toPath()))) ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				rtn.put("error", "file not found");
				e.printStackTrace();
			}
	    	return rtn;
	    }

	}
	
	public static class WriteNewAccountFile extends APIServlet.APIRequestHandler {

	    static final WriteNewAccountFile instance = new WriteNewAccountFile();
	    
	    private WriteNewAccountFile() {
	        super(new APITag[] {APITag.ACCOUNTS});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	String fname = "accounts/"+req.getParameter("name")+".pass";
	    	PrintWriter writer;
			JSONObject response = new JSONObject();
			if(new File(fname).exists())
			{
				response.put("result", "exists");
				return response;
			}
			try {
				writer = new PrintWriter(fname, "UTF-8");
				writer.println(req.getParameter("data"));
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.put("result", "error");
				return response;
			}
			response.put("result", "success");
			return response;
	    }

	}
	
	public static class GetLoggedAddress extends APIServlet.APIRequestHandler {

	    static final GetLoggedAddress instance = new GetLoggedAddress();
	    
	    private GetLoggedAddress() {
	        super(new APITag[] {APITag.ACCOUNTS});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
			JSONObject response = new JSONObject();
	    	response.put("address", Jay.master.rs);
			return response;
	    }

	}
	
	
}
