/**
 * 
 */
package jay;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

 

/**
 * @author jones
 *
 */
public class Main extends Application {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public Nxtapi api = new Nxtapi();
	public static void main(String[] args) throws IOException { 
		launch(args);
		
		
	}
		
		    
		    @Override
		    public void start(Stage primaryStage) {
		    	try {
					Nxtapi.consensus("getAccount", "account=NXT-RJU8-JSNR-H9J4-2KWKY");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        primaryStage.setTitle("Jay Wallet Version 0");
		        GridPane grid = new GridPane();
		        grid.setAlignment(Pos.CENTER);
		        grid.setHgap(10);
		        grid.setVgap(10);
		        grid.setPadding(new Insets(25, 25, 25, 25));

		        Scene scene = new Scene(grid, 400, 275);
		        primaryStage.setScene(scene);
		        
		        Text scenetitle = new Text("Jay ver. 0 send money");
		        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		        grid.add(scenetitle, 0, 0, 2, 1);

		        Label pw = new Label("Passprase:");
		        grid.add(pw, 0, 1);

		        PasswordField pwBox = new PasswordField();
		        grid.add(pwBox, 1, 1);
		        
		        Label userName = new Label("Send to:");
		        grid.add(userName, 0, 2);

		        TextField userTextField = new TextField();
		        grid.add(userTextField, 1, 2);

		        Label amt = new Label("Amount:");
		        grid.add(amt, 0, 3);

		        TextField amtt = new TextField();
		        grid.add(amtt, 1, 3);
		        
		        Button btn = new Button("Send");
		        HBox hbBtn = new HBox(10);
		        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		        hbBtn.getChildren().add(btn);
		        grid.add(hbBtn, 1, 4);
		        
		        final Text actiontarget = new Text();
		        grid.add(actiontarget, 1, 5);
		        
		        btn.setOnAction(new EventHandler<ActionEvent>() {
		        	 
		            @Override
		            public void handle(ActionEvent e) {
		                actiontarget.setFill(Color.FIREBRICK);
		                
		                Account usr = new Account(pwBox.getText());
		                String alr = "";
		                try {
							alr = usr.transaction(userTextField.getText(), amtt.getText());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
		                actiontarget.setText(alr);
		            }
		        });
		        
		        primaryStage.show();
		    }
		
}

