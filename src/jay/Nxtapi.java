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
		int[][] summ = new int[2][];
		int prev = 0;
		for(int i=0;i < nodes.length; i++)
		{
			//summ[i][0] = get(nodes[i], req, opt).hashCode();
			//if(now != prev && prev != 0)
			//{
			//	return new JSONObject("{\"errorCode\":\"42\"}");
			//}
			//prev = now;
			System.out.println(new BigInteger(1, Crypto.sha256().digest(get(nodes[i], req, opt).toString().getBytes())));
			
		}
		return get(nodes[0], req, opt);
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
