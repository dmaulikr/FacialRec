package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static processing.loadImages.imageLoader;

/**
 * Class Details:- Author: Sarhad User: sarha Date: 31-Aug-17 Time : 2:45 AM Project Name: CandyAI Class Name:
 * trackFaceController
 */
public class trackFaceController extends Application
{
	
	@FXML
	public Button randomImages;
	private Stage primaryStage;
	//	The below classes are from trackFace.fxml file. Just placeholders
	@FXML
	public Button cameraButton;
	@FXML
	public ImageView originalFrame;
	@FXML
	public CheckBox lbpClassifier;
	@FXML
	public CheckBox haarClassifier;
	@FXML
	public Button browseImages;
	
	//	The event handlers for different buttons
	@FXML
	protected void startCamera(ActionEvent event)
	{
		System.out.println("Button was clicked");
	}
	
	@FXML
	protected void haarClassifier(ActionEvent actionEvent)
	{
		if( lbpClassifier.isSelected() )
		{
			lbpClassifier.fire();
		}
	}
	
	@FXML
	protected void lbpClassifier(ActionEvent actionEvent)
	{
		if( haarClassifier.isSelected() )
		{
			haarClassifier.fire();
		}
	}
	
	@FXML
	protected void searchImage(ActionEvent actionEvent)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image");
		File image = fileChooser.showOpenDialog(primaryStage);
		
		Image img = new Image("file:"+image.toString());
		originalFrame.setImage(img);
	}
	
	@FXML
	protected void randomImage(ActionEvent actionEvent)
	{
		try
		{
			Image image  = new Image("file:"+imageLoader().toString());
			originalFrame.setImage(image);
		} catch( URISyntaxException | IOException e )
		{
			e.printStackTrace();
		}
	}
	
	//	The method to set the stage and start the app.
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/trackFace.fxml"));
		
		Scene scene = new Scene(root, 400, 600);
		
		primaryStage.setTitle("Candy A.I.");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
//	TODO: Alex change this as you like
	
	//	Just a class designed to be accessed from the outside in order to initiate this gui.
	public static void launchGUI(String[] args)
	{
		launch(args);
	}
	
}
