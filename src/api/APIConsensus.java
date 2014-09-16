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
	        return process(req, "getBalance");
	    }

	}
	
	public static class GetAccount extends APIServlet.APIRequestHandler {

	    static final GetAccount instance = new GetAccount();

	    private GetAccount() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getAccount");
	    }

	}
	
	public static class GetAccountTransactionIds extends APIServlet.APIRequestHandler {

	    static final GetAccountTransactionIds instance = new GetAccountTransactionIds();

	    private GetAccountTransactionIds() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account", "timestamp", "type", "subtype", "firstIndex", "lastIndex", "numberOfConfirmations");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getAccountTransactionIds");

	    }

	}
	
	public static class GetAccountTransactions extends APIServlet.APIRequestHandler {

	    static final GetAccountTransactions instance = new GetAccountTransactions();

	    private GetAccountTransactions() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account", "timestamp", "type", "subtype", "firstIndex", "lastIndex", "numberOfConfirmations");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getAccountTransactions");

	    }

	}
	
	public static class GetBlock extends APIServlet.APIRequestHandler {

	    static final GetBlock instance = new GetBlock();

	    private GetBlock() {
	        super(new APITag[] {APITag.BLOCKS}, "block", "height", "includeTransactions");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getBlock");

	    }

	}

	public static class GetAsset extends APIServlet.APIRequestHandler {

	    static final GetAsset instance = new GetAsset();

	    private GetAsset() {
	        super(new APITag[] {APITag.AE}, "asset");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getAsset");

	    }

	}
	
	public static class GetAliases extends APIServlet.APIRequestHandler {

	    static final GetAliases instance = new GetAliases();

	    private GetAliases() {
	        super(new APITag[] {APITag.ALIASES}, "timestamp", "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "getAliases");

	    }

	}
	
	public static class GetAssets extends APIServlet.APIRequestHandler {

	    static final GetAssets instance = new GetAssets();

	    private GetAssets() {
	        super(new APITag[] {APITag.AE}, "assets", "assets", "assets"); // limit to 3 for testing
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAssets");

	    }

	}
	
	public static class GetAllAssets extends APIServlet.APIRequestHandler {

	    static final GetAllAssets instance = new GetAllAssets();

	    private GetAllAssets() {
	        super(new APITag[] {APITag.AE});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAllAssets");

	    }

	}
	
	public static class GetBlockId extends APIServlet.APIRequestHandler {

	    static final GetBlockId instance = new GetBlockId();

	    private GetBlockId() {
	        super(new APITag[] {APITag.BLOCKS}, "height");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetBlockId");

	    }

	}
	
	
	// process all of the API forward requests
	public static JSONStreamAware process(HttpServletRequest req, String call)
	{
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
			response = Nxtapi.consensus(call, total);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
       	
        return response;
	}
	
	
}
