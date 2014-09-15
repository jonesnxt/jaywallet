package api;

import java.io.IOException;
import java.util.Enumeration;

import crypto.Constants;
import crypto.Convert;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import jay.Nxtapi;

public class APIConsensus {

	public static class GetBlockchainStatus extends APIServlet.APIRequestHandler {

	    static final GetBlockchainStatus instance = new GetBlockchainStatus();

	    private GetBlockchainStatus() {
	        super(new APITag[] {APITag.BLOCKS, APITag.INFO});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req)  {
	    	
	         JSONObject response = Constants.JSON_DEF;
	       	try {
	       		
				response = Nxtapi.consensus("getBlockchainStatus", "", "numberOfBlocks");
				System.out.println("maybe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("nope");
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	public static class GetBalance extends APIServlet.APIRequestHandler {

	    static final GetBalance instance = new GetBalance();

	    private GetBalance() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		
				response = Nxtapi.consensus("getBalance", "account="+req.getParameter("account"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	public static class GetAccount extends APIServlet.APIRequestHandler {

	    static final GetAccount instance = new GetAccount();

	    private GetAccount() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		
				response = Nxtapi.consensus("getAccount", "account="+req.getParameter("account"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	public static class GetAccountTransactionIds extends APIServlet.APIRequestHandler {

	    static final GetAccountTransactionIds instance = new GetAccountTransactionIds();

	    private GetAccountTransactionIds() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account", "timestamp", "type", "subtype", "firstIndex", "lastIndex", "numberOfConfirmations");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		Enumeration<String> params = req.getParameterNames();
	       		String param = "";
	       		String total = "";
	       		while(params.hasMoreElements())
	       		{
	       			param = params.nextElement();
	       			total += param + "=" + req.getParameter(param) + "&";
	       		}
				response = Nxtapi.consensus("getAccountTransactionIds", total);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	public static class GetAccountTransactions extends APIServlet.APIRequestHandler {

	    static final GetAccountTransactions instance = new GetAccountTransactions();

	    private GetAccountTransactions() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account", "timestamp", "type", "subtype", "firstIndex", "lastIndex", "numberOfConfirmations");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		Enumeration<String> params = req.getParameterNames();
	       		String param = "";
	       		String total = "";
	       		while(params.hasMoreElements())
	       		{
	       			param = params.nextElement();
	       			total += param + "=" + req.getParameter(param) + "&";
	       		}
				response = Nxtapi.consensus("getAccountTransactions", total);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	public static class GetBlock extends APIServlet.APIRequestHandler {

	    static final GetBlock instance = new GetBlock();

	    private GetBlock() {
	        super(new APITag[] {APITag.BLOCKS}, "block", "height", "includeTransactions");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		Enumeration<String> params = req.getParameterNames();
	       		String param = "";
	       		String total = "";
	       		while(params.hasMoreElements())
	       		{
	       			param = params.nextElement();
	       			total += param + "=" + req.getParameter(param) + "&";
	       		}
				response = Nxtapi.consensus("getBlock", total);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}

	public static class GetAsset extends APIServlet.APIRequestHandler {

	    static final GetAsset instance = new GetAsset();

	    private GetAsset() {
	        super(new APITag[] {APITag.AE}, "asset");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	    	JSONObject response = Constants.JSON_DEF;
	       	try {
	       		Enumeration<String> params = req.getParameterNames();
	       		String param = "";
	       		String total = "";
	       		while(params.hasMoreElements())
	       		{
	       			param = params.nextElement();
	       			total += param + "=" + req.getParameter(param) + "&";
	       		}
				response = Nxtapi.consensus("getAsset", total);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	       	
	        return response;
	    }

	}
	
	
}
