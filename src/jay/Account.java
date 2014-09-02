package jay;

import java.io.IOException;

import json.JSONObject;
import crypto.Constants;
import crypto.Convert;
import crypto.Crypto;

public class Account {
	public String rs;
	public long id;
	public byte[] pub;
	public String sec;
	
	public Account(String phr)
	{
		sec = phr;
		pub = Crypto.getPublicKey(phr);
		id = Convert.fullHashToId(Crypto.sha256().digest(pub));
		rs = Convert.rsAccount(id);
	}
	
	String transaction(String recipientValue, String amountValue) throws IOException
	{

	    Long recipient;
	    long amountNQT = 0;
	    long feeNQT = 1;
	    short deadline = 24;
	
	    try {
	
	        recipient = Convert.parseAccountId(recipientValue);
	        if (recipient == null) throw new IllegalArgumentException("invalid recipient");
	        amountNQT = Convert.parseNXT(amountValue.trim());
	        feeNQT = feeNQT * Constants.ONE_NXT;
	        deadline = (short)(deadline * 60);
	
	    } catch (RuntimeException e) {
	
	        return "Incorrect Data";
	    }
	
	     if (amountNQT <= 0 || amountNQT > Constants.MAX_BALANCE_NQT) {
	
	
	        return "amount needs to not be 0";
	
	    } else if (feeNQT < Constants.ONE_NXT || feeNQT > Constants.MAX_BALANCE_NQT) {
	
	        return "fee too low";
	
	    } else if (deadline < 1 || deadline > 1440) {
	
	        return "deadline error";
	
	    }
	     
	     // calculate the account account balance
	     
	     JSONObject bal = Nxtapi.get("127.0.0.1", "getBalance", "account="+this.rs);
	     if(bal.has("errorCode")) return "sender account not found";
	
	    if (Convert.safeAdd(amountNQT, feeNQT) > bal.getLong("unconfirmedBalanceNQT")) {
	
	        return "Not enough NXT";
	
	    } else {
	
	        return "Processing...";
	    }

    }
	
	
	public byte[] buildTransaction(long type, long subtype, long timestamp, long deadline, String senderPublicKey, long amountNQT, long feeNQT, long signature, long version)
	{
		return new byte['d'];
				
	          
	}
	
}
