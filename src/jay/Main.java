/**
 * 
 */
package jay;

import java.io.IOException;

import com.swt.moonsand;
 

/**
 * @author jones
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) throws IOException { 
		System.out.println("hello");
		
		
		

		        JEditorPane website = new JEditorPane("http://nxtforum.org/");
		        website.setEditable(false);

		        JFrame frame = new JFrame("Google");
		        frame.add(new JScrollPane(website));
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setVisible(true);
		        frame.pack();


		
		
		    }
		
}

