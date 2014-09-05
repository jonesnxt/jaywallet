package jay;

import java.math.BigInteger;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

import crypto.Crypto;
import json.JSONObject;

public class Nxtapi {

	public static JSONObject get(String node, String req, String opt) throws IOException
	{
		
		URL nd = new URL("http://"+node+":7876/nxt?requestType="+req+"&"+opt);

		URLConnection yc = nd.openConnection();
		
		String res = "";
		try {
			yc.setConnectTimeout(1000);
		
		
		BufferedReader in = new BufferedReader(
		                                new InputStreamReader(
		                                yc.getInputStream()));
		        String inputLine;
		        
		        while ((inputLine = in.readLine()) != null) 
		        	res += inputLine;
		        in.close();
		        if(res == "") res = "{\"e\":42}";
		        
		        
} catch(java.net.SocketTimeoutException e) {
			
	return new JSONObject("{\"e\":42}");
		}
		 catch(java.net.UnknownHostException e)
	{
			 return new JSONObject("{\"e\":42}");
	}
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
			System.out.println(new BigInteger(1, Crypto.sha256().digest(j.toString().getBytes())));
		}
		
		Arrays.sort(summ);
		return jsons[Arrays.asList(summ).indexOf(mode(summ))];
	}
	
	public static BigInteger mode(final BigInteger[] a) {
		int popularity1 = 0;
		  int popularity2 = 0;
		  BigInteger popularity_item = BigInteger.ZERO;
		  BigInteger array_item = BigInteger.ZERO; //Array contains integer value. Make it String if array contains string value.
		  for(int i =0;i<a.length;i++){
		      array_item = a[i];
		      for(int j =0;j<a.length;j++){
		          if(array_item == a[j])
		             popularity1 ++;
		          {
		      if(popularity1 >= popularity2){
		          popularity_item = array_item;
		          popularity2 = popularity1;
		      }
		      popularity1 = 0;
		          }
		      }
		  }
		  return popularity_item;
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
