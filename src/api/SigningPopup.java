package api;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

public class SigningPopup extends APIServlet.APIRequestHandler {
	public static void popupRequest(JSONObject info)
	{
		call = true;
		callinfo = info;
	}
	
	public static boolean locked = false;
	public static boolean call = false;
	public static JSONObject callinfo;
    static final SigningPopup instance = new SigningPopup();

    private SigningPopup() {
        super(new APITag[] {});
    }

    @Override
    JSONStreamAware processRequest(HttpServletRequest req) {
    	if(locked)
    	{
    		JSONObject response = new JSONObject();
    		response.put("errorCode", "15");
    		response.put("errorDescription", "Signing popup already has a hanging response");
    		return response;
    	}
		locked = true;
		
		while(!call)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		locked = false;
		call = false;
		//callinfo
		return callinfo;
    }

}