package api;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

public class VerifySigning extends APIServlet.APIRequestHandler {
	public static void popupRequest(JSONObject info)
	{

	}
	
    static final VerifySigning instance = new VerifySigning();

    private VerifySigning() {
        super(new APITag[] {});
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {
    	
		return null;
    }

}