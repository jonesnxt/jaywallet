package api;

import crypto.Constants;
import json.JSON;
import json.JSONResponses;

import org.json.simple.JSONStreamAware;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static json.JSONResponses.ERROR_INCORRECT_REQUEST;
import static json.JSONResponses.ERROR_NOT_ALLOWED;
import static json.JSONResponses.POST_REQUIRED;

public final class APIServlet extends HttpServlet {

	private static final long serialVersionUID = 2431785857956923632L;

	abstract static class APIRequestHandler {

        private final List<String> parameters;
        private final Set<APITag> apiTags;

        APIRequestHandler(APITag[] apiTags, String... parameters) {
            this.parameters = Collections.unmodifiableList(Arrays.asList(parameters));
            this.apiTags = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(apiTags)));
        }

        final List<String> getParameters() {
            return parameters;
        }

        final Set<APITag> getAPITags() {
            return apiTags;
        }

        abstract JSONStreamAware processRequest(HttpServletRequest request);

        boolean requirePost() {
            return false;
        }

    }

    private static final boolean enforcePost = true;

    static final Map<String,APIRequestHandler> apiRequestHandlers;

    static {

        Map<String,APIRequestHandler> map = new HashMap<>();
        
        // initialize account with secretphrase (needed for signed transactions)
        map.put("initialize", Initialize.instance);
        //map.put("logOut", LogOut.instance);
        map.put("signingPopup", SigningPopup.instance);
        map.put("verifySigning", VerifySigning.instance);
        //map.put("writeConfig", writeConfig.instance);
        
        // deal with account files
        map.put("listAccountFiles", APIAccounts.ListAccountFiles.instance);
        map.put("getAccountFile", APIAccounts.GetAccountFile.instance);
        map.put("writeNewAccountFile", APIAccounts.WriteNewAccountFile.instance);
        
        //implemented api calls - forwarding
        map.put("getBlockchainStatus", APIConsensus.GetBlockchainStatus.instance);
        map.put("getBalance", APIConsensus.GetBalance.instance);
        map.put("getAccount",api.APIConsensus.GetAccount.instance);
        map.put("getAccountTransactionIds", APIConsensus.GetAccountTransactionIds.instance);
        map.put("getAccountTransactions", APIConsensus.GetAccountTransactions.instance);
        map.put("getBlock", APIConsensus.GetBlock.instance);
        map.put("getAsset", APIConsensus.GetAsset.instance);
        map.put("getAliases", APIConsensus.GetAliases.instance);
        map.put("getAssets", APIConsensus.GetAssets.instance);
        map.put("getAllAssets", APIConsensus.GetAllAssets.instance);
        map.put("getBlockId", APIConsensus.GetBlockId.instance);
        map.put("getAccountPublicKey", APIConsensus.GetAccountPublicKey.instance);
        map.put("getAlias", APIConsensus.GetAlias.instance);
        //map.put("getAccountId", APIConsensus.GetAccountId.instance);
        map.put("getAccountBlockIds", APIConsensus.GetAccountBlockIds.instance);
        map.put("getDGSGoods", APIConsensus.GetDGSGoods.instance);
        map.put("getAssetIds", APIConsensus.GetAssetIds.instance);
        map.put("getDGSGood", APIConsensus.GetDGSGood.instance);
        map.put("getConstants", APIConsensus.GetConstants.instance);
        map.put("getDGSPurchases", APIConsensus.GetDGSPurchases.instance);
        map.put("getDGSPurchase", APIConsensus.GetDGSPurchase.instance);
        map.put("getDGSPendingPurchases", APIConsensus.GetDGSPendingPurchases.instance);
        map.put("getGuaranteedBalance", APIConsensus.GetGuaranteedBalance.instance);
        map.put("getMyInfo", APIConsensus.GetMyInfo.instance);
        map.put("getAssetsByIssuer", APIConsensus.GetAssetsByIssuer.instance);
        
        //map.put("getPeer", APIConsensus.GetPeer.instance);
        //map.put("getPeers", APIConsensus.GetPeers.instance);
        //map.put("getPoll", APIConsensus.GetPoll.instance);
        //map.put("getPollIds", APIConsensus.GetPollIds.instance);
        map.put("getState", APIConsensus.GetState.instance);
        map.put("getTime", APIConsensus.GetTime.instance);
        map.put("getTrades", APIConsensus.GetTrades.instance);
        map.put("getAllTrades", APIConsensus.GetAllTrades.instance);
        map.put("getTransaction", APIConsensus.GetTransaction.instance);
        map.put("getTransactionBytes", APIConsensus.GetTransactionBytes.instance);
        map.put("getUnconfirmedTransactionIds", APIConsensus.GetUnconfirmedTransactionIds.instance);
        map.put("getUnconfirmedTransactions", APIConsensus.GetUnconfirmedTransactions.instance);
        map.put("getAccountCurrentAskOrderIds", APIConsensus.GetAccountCurrentAskOrderIds.instance);
        map.put("getAccountCurrentBidOrderIds", APIConsensus.GetAccountCurrentBidOrderIds.instance);
        map.put("getAllOpenOrders", APIConsensus.GetAllOpenOrders.instance);
        map.put("getAskOrder", APIConsensus.GetAskOrder.instance);
        map.put("getAskOrderIds", APIConsensus.GetAskOrderIds.instance);
        map.put("getAskOrders", APIConsensus.GetAskOrders.instance);
        map.put("getBidOrder", APIConsensus.GetBidOrder.instance);
        map.put("getBidOrderIds", APIConsensus.GetBidOrderIds.instance);
        map.put("getBidOrders", APIConsensus.GetBidOrders.instance);
        
        
        //begin the transaction signing
        map.put("sendMoney", APISigned.SendMoney.instance);

        /* need to make byte data for these
         * 
         * map.put("broadcastTransaction", BroadcastTransaction.instance);
        map.put("calculateFullHash", CalculateFullHash.instance);
        map.put("cancelAskOrder", CancelAskOrder.instance);
        map.put("cancelBidOrder", CancelBidOrder.instance);
        map.put("castVote", CastVote.instance);
        map.put("createPoll", CreatePoll.instance);
        map.put("decryptFrom", DecryptFrom.instance);
        map.put("dgsListing", DGSListing.instance);
        map.put("dgsDelisting", DGSDelisting.instance);
        map.put("dgsDelivery", DGSDelivery.instance);
        map.put("dgsFeedback", DGSFeedback.instance);
        map.put("dgsPriceChange", DGSPriceChange.instance);
        map.put("dgsPurchase", DGSPurchase.instance);
        map.put("dgsQuantityChange", DGSQuantityChange.instance);
        map.put("dgsRefund", DGSRefund.instance);
        map.put("decodeHallmark", DecodeHallmark.instance);
        map.put("decodeToken", DecodeToken.instance);
        map.put("encryptTo", EncryptTo.instance);
        map.put("generateToken", GenerateToken.instance);
        
        map.put("sellAlias", SellAlias.instance);
        map.put("buyAlias", BuyAlias.instance);
        
        
        if (Constants.isTestnet) {
            map.put("getNextBlockGenerators", GetNextBlockGenerators.instance);
        }

        map.put("issueAsset", IssueAsset.instance);
        map.put("leaseBalance", LeaseBalance.instance);
        map.put("markHost", MarkHost.instance);
        map.put("parseTransaction", ParseTransaction.instance);
        map.put("placeAskOrder", PlaceAskOrder.instance);
        map.put("placeBidOrder", PlaceBidOrder.instance);
        map.put("rsConvert", RSConvert.instance);
        map.put("readMessage", ReadMessage.instance);
        map.put("sendMessage", SendMessage.instance);
        
        map.put("setAccountInfo", SetAccountInfo.instance);
        map.put("setAlias", SetAlias.instance);
        map.put("signTransaction", SignTransaction.instance); // the root of all evil
        map.put("startForging", StartForging.instance);
        map.put("stopForging", StopForging.instance);
        map.put("getForging", GetForging.instance);
        map.put("transferAsset", TransferAsset.instance);*/
 
        apiRequestHandlers = Collections.unmodifiableMap(map);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, private");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        JSONStreamAware response = JSON.emptyJSON;

        try {

            /*if (API.allowedBotHosts != null && ! API.allowedBotHosts.contains(req.getRemoteHost())) {
                response = ERROR_NOT_ALLOWED;
                return;
            }*/

            String requestType = req.getParameter("requestType");
            if (requestType == null) {
                response = ERROR_INCORRECT_REQUEST;
                return;
            }

            APIRequestHandler apiRequestHandler = apiRequestHandlers.get(requestType);
            if (apiRequestHandler == null) {
                response = ERROR_INCORRECT_REQUEST;
                return;
            }

            if (enforcePost && apiRequestHandler.requirePost() && ! "POST".equals(req.getMethod())) {
                response = POST_REQUIRED;
                return;
            }

            try {
                response = apiRequestHandler.processRequest(req);

            } catch (RuntimeException e) {
                
                response = ERROR_NOT_ALLOWED;
                System.out.println("yep");
            }

        } finally {
            resp.setContentType("text/plain; charset=UTF-8");
            try (Writer writer = resp.getWriter()) {
                response.writeJSONString(writer);
            }
        }

    }

}