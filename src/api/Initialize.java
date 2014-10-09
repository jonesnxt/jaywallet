package api;

import javax.servlet.http.HttpServletRequest;

import jay.Account;
import jay.Jay;
import json.JSON;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import api.APISigned.SendMoney;
import crypto.Constants;

public class Initialize extends APIServlet.APIRequestHandler {

    static final Initialize instance = new Initialize();

    private Initialize() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.CREATE_TRANSACTION}, "secretPhrase");
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {
    	if(Jay.logged)
    	{
    		JSONObject response = new JSONObject();
    		response.put("errorCode", 12);
    		response.put("errorDescription", "Already Logged In");
    		return response;
    	}
    	Jay.master = new Account(req.getParameter("secretPhrase"));
    	Jay.logged = true;
    		
    	JSONObject response = new JSONObject();
		response.put("success", "success");
		response.put("account", Jay.master.rs);
		return response;
    }

}