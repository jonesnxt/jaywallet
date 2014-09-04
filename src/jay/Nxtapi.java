package jay;

import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import crypto.Crypto;
import json.JSONObject;

public class Nxtapi {

	public static JSONObject get(String node, String req, String opt) throws IOException
	{
		
		URL nd = new URL("http://"+node+":7876/nxt?requestType="+req+"&"+opt);

		URLConnection yc = nd.openConnection();
		BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                yc.getInputStream()));
		        String inputLine;
		        String res = "";
		        while ((inputLine = in.readLine()) != null) 
		        	res += inputLine;
		        in.close();
		        
		        
		return new JSONObject(res);
	}
	
	public static JSONObject consensus(String req, String opt) throws IOException
	{
		String[] nodes = listnodes();
		BigInteger[] summ = new BigInteger[nodes.length];
		JSONObject[] jsons = new JSONObject[nodes.length];
		
		for(int i=0;i < nodes.length; i++)
		{
			JSONObject j = get(nodes[i], req, opt);
			jsons[i] = j;
			summ[i]= new BigInteger(1, Crypto.sha256().digest(j.toString().getBytes()));		
		}
		
		
		return jsons[mode(summ).intValue()];
	}
	
	public static BigInteger mode(final BigInteger[] n) {
	    BigInteger maxKey = BigInteger.ZERO;
	    BigInteger maxCounts = BigInteger.ZERO;

	    BigInteger[] counts = new BigInteger[n.length];

	    for (int i=1; i < n.length; i++) {
	        counts[i] = counts[i].add(BigInteger.ONE);
	        if (maxCounts.intValue() < counts[i].intValue()) {
	            maxCounts = counts[i];
	            maxKey = n[i];
	        }
	    }
	    return maxKey;
	}

	
	static String[] listnodes()
	{
		File file = new File("nodes.txt");
		BufferedReader reader = null;
		String acc = "";

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    while ((text = reader.readLine()) != null) {
		        acc += text;
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		

		//print out the list
		return acc.split(";");
	}
	
}
