package jay;

import java.math.BigInteger;
import java.net.*;
import java.util.Arrays;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import crypto.Constants;
import crypto.Crypto;


public class Nxtapi {
	public static final int SERVERS = 3;
	
	
	static class Ping extends Thread
	{
		public String node;
		public String req;
		public String opt;
		public String key;
		public int i;
		
		public static JSONObject[] jsons = new JSONObject[SERVERS];
		public static BigInteger[] summ = new BigInteger[SERVERS];
		
		public Ping(String nd, String rq, String pt, String ky, int ival)
		{
			node = nd;
			req = rq;
			opt = pt;
			key = ky;
			i = ival;
		}
		
		public void run()
		{
			JSONObject j = (JSONObject) JSONValue.parse("{\"errorOn\":\"" + i + "\"}");
			try {
				j = get(node, req, opt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jsons[i] = j;
			
			if(key != "") summ[i]= new BigInteger(1, Crypto.sha256().digest(j.get(key).toString().getBytes()));
			else summ[i] = new BigInteger(1, Crypto.sha256().digest(j.toJSONString().getBytes()));
			//System.out.println(summ[i].toString(16));
			this.interrupt();
		}
		
		
		
	}
	
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
		        if(res == "" || res.charAt(0) != '{') res = Constants.JSON_DEF.toJSONString();
		        
} catch(java.net.SocketTimeoutException e) {
			
	return Constants.JSON_DEF;
		}
		 catch(java.net.UnknownHostException e)
	{
			 return Constants.JSON_DEF;
	}
	return (JSONObject) JSONValue.parse(res);
	
	}
	public static JSONObject consensus(String req, String opt) throws IOException
	{
		return consensus(req, opt, "");
	}
	
	public static JSONObject consensus(String req, String opt, String key) throws IOException
	{
		JSONObject fin;
		int loops = 0;
		do {
			String[] nodes = listnodes();
			BigInteger[] summ = new BigInteger[SERVERS];
			JSONObject[] jsons = new JSONObject[SERVERS];
			Ping[] pings = new Ping[SERVERS];
			for(int i=0;i < SERVERS; i++)
			{
				pings[i] = new Ping(nodes[i], req, opt, key, i);
				pings[i].start();
			}
			for(int i=0;i < SERVERS; i++)
			{
				try {
					pings[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=0;i < SERVERS; i++)
			{
				pings[i].interrupt();
			}
			summ = Ping.summ;
			jsons = Ping.jsons;
			Arrays.sort(summ);
			Ping.summ = new BigInteger[SERVERS];
			Ping.jsons = new JSONObject[SERVERS];
			fin = jsons[Arrays.asList(summ).indexOf(mode(summ))];
		}
		while(!fin.containsKey("errorOn") && ++loops == 3);
		return fin;
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
	
	public static void broadcast(String data) throws IOException
	{
		String urlParameters = "requestType=broadcastTransaction&transactionBytes="+data;
		
		String[] nodes = listnodes();
		//Jay.setinfo("Broadcasting");
		for(int i=0;i < nodes.length; i++)
		{
			URL obj = new URL("http://"+nodes[i]+":7876/nxt");

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 		con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	 
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
	 
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			System.out.println(response.toString());
	 
		}
		//Jay.setinfo("Transaction sent (:");
		
	}
	
}
