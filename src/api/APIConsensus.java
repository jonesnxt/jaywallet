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
	
	public static class GetAccountPublicKey extends APIServlet.APIRequestHandler {

	    static final GetAccountPublicKey instance = new GetAccountPublicKey();

	    private GetAccountPublicKey() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAccountPublicKey");

	    }

	}
	
	public static class GetAlias extends APIServlet.APIRequestHandler {

	    static final GetAlias instance = new GetAlias();

	    private GetAlias() {
	        super(new APITag[] {APITag.ALIASES}, "alias", "aliasName");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAlias");

	    }

	}
	
	public static class GetAccountBlockIds extends APIServlet.APIRequestHandler {

	    static final GetAccountBlockIds instance = new GetAccountBlockIds();

	    private GetAccountBlockIds() {
	        super(new APITag[] {APITag.ACCOUNTS}, "account", "timestamp");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAccountBlockIds");

	    }

	}
	
	public static class GetDGSGoods extends APIServlet.APIRequestHandler {

	    static final GetDGSGoods instance = new GetDGSGoods();

	    private GetDGSGoods() {
	        super(new APITag[] {APITag.DGS}, "seller", "firstIndex", "lastIndex", "inStockOnly");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetDGSGoods");

	    }

	}
	
	public static class GetAssetIds extends APIServlet.APIRequestHandler {

	    static final GetAssetIds instance = new GetAssetIds();

	    private GetAssetIds() {
	        super(new APITag[] {APITag.AE});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAssetIds");

	    }

	}
	
	public static class GetDGSGood extends APIServlet.APIRequestHandler {

	    static final GetDGSGood instance = new GetDGSGood();

	    private GetDGSGood() {
	        super(new APITag[] {APITag.DGS}, "goods");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetDGSGood");

	    }

	}
	
	public static class GetConstants extends APIServlet.APIRequestHandler {

	    static final GetConstants instance = new GetConstants();

	    private GetConstants() {
	        super(new APITag[] {APITag.INFO});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetConstants");

	    }

	}
	
	public static class GetDGSPurchases extends APIServlet.APIRequestHandler {

	    static final GetDGSPurchases instance = new GetDGSPurchases();

	    private GetDGSPurchases() {
	        super(new APITag[] {APITag.DGS}, "seller", "buyer", "firstIndex", "lastIndex", "completed");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetDGSPurchases");

	    }

	}
	
	public static class GetDGSPurchase extends APIServlet.APIRequestHandler {

	    static final GetDGSPurchase instance = new GetDGSPurchase();

	    private GetDGSPurchase() {
	        super(new APITag[] {APITag.DGS}, "purchase");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetDGSPurchase");

	    }

	}
	
	public static class GetDGSPendingPurchases extends APIServlet.APIRequestHandler {

	    static final GetDGSPendingPurchases instance = new GetDGSPendingPurchases();

	    private GetDGSPendingPurchases() {
	        super(new APITag[] {APITag.DGS}, "seller", "firstIndex", "lastIndex");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetDGSPendingPurchases");

	    }

	}
	
	public static class GetGuaranteedBalance extends APIServlet.APIRequestHandler {

	    static final GetGuaranteedBalance instance = new GetGuaranteedBalance();

	    private GetGuaranteedBalance() {
	        super(new APITag[] {APITag.ACCOUNTS, APITag.FORGING}, "account", "numberOfConfirmations");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetGuaranteedBalance");

	    }

	}
	
	public static class GetMyInfo extends APIServlet.APIRequestHandler {

	    static final GetMyInfo instance = new GetMyInfo();

	    private GetMyInfo() {
	        super(new APITag[] {APITag.INFO});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetMyInfo");

	    }

	}
	
	public static class GetAssetsByIssuer extends APIServlet.APIRequestHandler {

	    static final GetAssetsByIssuer instance = new GetAssetsByIssuer();

	    private GetAssetsByIssuer() {
	        super(new APITag[] {APITag.AE, APITag.ACCOUNTS}, "account", "account", "account");
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAssetsByIssuer");

	    }

	}
	 
	public static class GetState extends APIServlet.APIRequestHandler {

	    static final GetState instance = new GetState();

	    private GetState() {
	        super(new APITag[] {});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetState");

	    }

	}
	
	public static class GetTime extends APIServlet.APIRequestHandler {

	    static final GetTime instance = new GetTime();

	    private GetTime() {
	        super(new APITag[] {APITag.INFO});
	    }

	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetTime");

	    }

	}
	
	public static class GetAllTrades extends APIServlet.APIRequestHandler {

	    static final GetAllTrades instance = new GetAllTrades();

	    private GetAllTrades() {
	        super(new APITag[] {APITag.AE}, "timestamp");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAllTrades");

	    }

	}
	
	public static class GetTrades extends APIServlet.APIRequestHandler {

	    static final GetTrades instance = new GetTrades();

	    private GetTrades() {
	        super(new APITag[] {APITag.AE}, "asset", "firstIndex", "lastIndex");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetTrades");

	    }

	}
	
	public static class GetTransaction extends APIServlet.APIRequestHandler {

	    static final GetTransaction instance = new GetTransaction();

	    private GetTransaction() {
	        super(new APITag[] {APITag.TRANSACTIONS}, "transaction", "fullHash");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetTransaction");

	    }

	}
	
	public static class GetTransactionBytes extends APIServlet.APIRequestHandler {

	    static final GetTransactionBytes instance = new GetTransactionBytes();

	    private GetTransactionBytes() {
	        super(new APITag[] {APITag.TRANSACTIONS}, "transaction");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetTransactionBytes");

	    }

	}
	
	public static class GetUnconfirmedTransactionIds extends APIServlet.APIRequestHandler {

	    static final GetUnconfirmedTransactionIds instance = new GetUnconfirmedTransactionIds();

	    private GetUnconfirmedTransactionIds() {
	        super(new APITag[] {APITag.TRANSACTIONS, APITag.ACCOUNTS}, "account");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetUnconfirmedTransactionIds");

	    }

	}
	
	public static class GetUnconfirmedTransactions extends APIServlet.APIRequestHandler {

	    static final GetUnconfirmedTransactions instance = new GetUnconfirmedTransactions();

	    private GetUnconfirmedTransactions() {
	        super(new APITag[] {APITag.TRANSACTIONS}, "account");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetUnconfirmedTransactions");

	    }

	}
	
	public static class GetAccountCurrentAskOrderIds extends APIServlet.APIRequestHandler {

	    static final GetAccountCurrentAskOrderIds instance = new GetAccountCurrentAskOrderIds();

	    private GetAccountCurrentAskOrderIds() {
	        super(new APITag[] {APITag.ACCOUNTS, APITag.AE}, "account", "asset");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAccountCurrentAskOrderIds");

	    }

	}
	
	public static class GetAccountCurrentBidOrderIds extends APIServlet.APIRequestHandler {

	    static final GetAccountCurrentBidOrderIds instance = new GetAccountCurrentBidOrderIds();

	    private GetAccountCurrentBidOrderIds() {
	        super(new APITag[] {APITag.ACCOUNTS, APITag.AE}, "account", "asset");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAccountCurrentBidOrderIds");

	    }

	}
	
	public static class GetAllOpenOrders extends APIServlet.APIRequestHandler {

	    static final GetAllOpenOrders instance = new GetAllOpenOrders();

	    private GetAllOpenOrders() {
	        super(new APITag[] {APITag.AE});
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAllOpenOrders");

	    }

	}
	
	public static class GetAskOrder extends APIServlet.APIRequestHandler {

	    static final GetAskOrder instance = new GetAskOrder();

	    private GetAskOrder() {
	        super(new APITag[] {APITag.AE}, "order");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAskOrder");

	    }

	}
	
	public static class GetAskOrderIds extends APIServlet.APIRequestHandler {

	    static final GetAskOrderIds instance = new GetAskOrderIds();

	    private GetAskOrderIds() {
	        super(new APITag[] {APITag.AE}, "asset", "limit");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAskOrderIds");

	    }

	}
	
	public static class GetAskOrders extends APIServlet.APIRequestHandler {

	    static final GetAskOrders instance = new GetAskOrders();

	    private GetAskOrders() {
	        super(new APITag[] {APITag.AE}, "asset", "limit");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetAskOrders");

	    }

	}
	
	public static class GetBidOrder extends APIServlet.APIRequestHandler {

	    static final GetBidOrder instance = new GetBidOrder();

	    private GetBidOrder() {
	        super(new APITag[] {APITag.AE}, "order");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetBidOrder");

	    }

	}
	
	public static class GetBidOrderIds extends APIServlet.APIRequestHandler {

	    static final GetBidOrderIds instance = new GetBidOrderIds();

	    private GetBidOrderIds() {
	        super(new APITag[] {APITag.AE}, "asset", "limit");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetBidOrderIds");

	    }

	}
	
	public static class GetBidOrders extends APIServlet.APIRequestHandler {

	    static final GetBidOrders instance = new GetBidOrders();

	    private GetBidOrders() {
	        super(new APITag[] {APITag.AE}, "asset", "limit");
	    }
	    
	    @Override
	    JSONStreamAware processRequest(HttpServletRequest req) {
	        return process(req, "GetBidOrders");

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
