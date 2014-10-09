package jay;

import java.util.Random;

import org.bouncycastle.crypto.prng.RandomGenerator;
import org.json.simple.JSONObject;

public class HoldSigning {
	
	public static long registerWait(JSONObject params)
	{
		Random a = new Random();
		long checksum = a.nextLong();
		return a.nextLong();
		
	}
	
}
