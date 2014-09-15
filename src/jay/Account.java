package jay;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.json.simple.JSONObject;

import crypto.Constants;
import crypto.Convert;
import crypto.Crypto;

public class Account {
	public String rs;
	public long id;
	public byte[] pub;
	public String sec;
	
	public Account(String phr, String rsa)
	{
		if(phr != null)
		{
			sec = phr;
			pub = Crypto.getPublicKey(phr);
			id = Convert.fullHashToId(Crypto.sha256().digest(pub));
			rs = Convert.rsAccount(id);
		}
		else
		{
			rs = rsa;
			id = Convert.parseAccountId(rsa);
			JSONObject pubj = Constants.JSON_DEF;
			try {
				pubj = Nxtapi.consensus("getAccountPublicKey", "account="+rsa);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!pubj.containsKey("errorCode")) pub = Convert.parseHexString(pubj.get("publicKey").toString());
			System.out.println(pub.length);
			sec = "NOT APPLICABLE";
		}
		
	}
	
	public Account(String phr)
	{
		this(phr, null);
	}
	
	
	String checkTransaction(String recipientValue, String amountValue) throws IOException
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
	     if(bal.containsKey("errorCode")) return "sender account not found";
	
	    if (Convert.safeAdd(amountNQT, feeNQT) > (Long) bal.get("unconfirmedBalanceNQT")) {
	
	        return "Not enough NXT";
	
	    } else {
	
	        return "Processing...";
	    }

    }
	
	int getSize() {
        return ((1 + 1 + 4 + 2 + 32 + 8 + (8 + 8 + 32)) + 64 + (4 + 4 + 8));
	}
    
    private int getFlags() {
        int flags = 0;
        /*int position = 1;
        if (message != null) {
            flags |= position;
        }
        position <<= 1;
        if (encryptedMessage != null) {
            flags |= position;
        }
        position <<= 1;
        if (publicKeyAnnouncement != null) {
            flags |= position;
        }
        position <<= 1;
        if (encryptToSelfMessage != null) {
            flags |= position;
        }*/
        return flags;
    }
	
	public byte[] getBytes(byte type, byte subtype, int timestamp, short deadline, byte[] pubkey, long recid, long amt, long fee, int ecblock, byte[] sig) {
        ByteBuffer buffer = ByteBuffer.allocate(getSize());

		try {
            buffer = ByteBuffer.allocate(getSize());
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.put(type);
            buffer.put((byte) ((1 << 4) | subtype));
            buffer.putInt(timestamp);
            buffer.putShort(deadline);
            buffer.put(pubkey);
            buffer.putLong(recid);

                buffer.putLong(amt * Constants.ONE_NXT);
                buffer.putLong(fee * Constants.ONE_NXT);

                 //referenced transactions
                buffer.put(new byte[32]);


            buffer.put(sig != null ? sig : new byte[64]);
                buffer.putInt(getFlags());
                buffer.putInt(ecblock);
                buffer.putLong(Convert.parseUnsignedLong((String) Nxtapi.consensus("getBlockId", "height="+ecblock).get("block")));
                //buffer.put(new byte[12]);
            /*for (Appendix appendage : appendages) {
                appendage.putBytes(buffer);
            }*/
        } catch (RuntimeException e) {
            //Logger.logDebugMessage("Failed to get transaction bytes for transaction: " + getJSONObject().toJSONString());
            throw e;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                    return buffer.array();

    }
	
	public byte[] signed(byte type, byte subtype, int timestamp, short deadline, byte[] pubkey, long recid, long amt, long fee, int ecblock)
	{
		byte[] sig = Crypto.sign(getBytes(type, subtype, timestamp, deadline, pubkey, recid, amt, fee, ecblock, null), this.sec);
		return getBytes(type, subtype, timestamp, deadline, pubkey, recid, amt, fee, ecblock, sig);
	}
	
	public byte[] sendMoney(Account recipient, long amt)
	{
		return signed(Constants.TYPE_PAYMENT, Constants.SUBTYPE_PAYMENT_ORDINARY_PAYMENT, getTimestamp(), (short)24, this.pub, recipient.id, amt, 1, getecblock());
	}
	
	int getTimestamp()
	{
		return (int) Convert.getEpochTime();
	}
	
	int getecblock()
	{
		JSONObject st = new JSONObject(Constants.JSON_DEF);
		try {
			st = Nxtapi.consensus("getBlockchainStatus", "", "numberOfBlocks");			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (int) st.get("numberOfBlocks")-5;
	}
	

}
