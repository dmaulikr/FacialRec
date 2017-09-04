package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	private static String stringName;
	
	/**
	 * More fxml
	 */
	@FXML
	public Button imageURL;
	@FXML
	public Button resetImages;
	
	private Stage primaryStage;
	
	@FXML
	public MenuBar menuBar;
	
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public VBox imageContainer;
	
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public Button randomImages;
	
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public Button cameraButton;
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public ImageView originalFrame;
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public CheckBox lbpClassifier;
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public CheckBox haarClassifier;
	/**
	 * The field below are from the trackFace.fxml file located in res/fxml.
	 */
	@FXML
	public Button browseImages;
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param event Event Handler
	 */
	@FXML
	protected void startCamera(ActionEvent event)
	{
		System.out.println("Camera Intialized");
		webcamSettings();
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param actionEvent Event Handler
	 */
	@FXML
	protected void haarClassifier(ActionEvent actionEvent)
	{
		if( lbpClassifier.isSelected() )
		{
			lbpClassifier.fire();
		}
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param actionEvent Event Handler
	 */
	@FXML
	protected void lbpClassifier(ActionEvent actionEvent)
	{
		if( haarClassifier.isSelected() )
		{
			haarClassifier.fire();
		}
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param actionEvent Event Handler
	 */
	@FXML
	protected void searchImage(ActionEvent actionEvent)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image");
		File image = fileChooser.showOpenDialog(primaryStage);
		
		Image img = new Image("file:"+image.toString());
		setImage(img);
		
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param actionEvent Event Handler
	 */
	@FXML
	protected void randomImage(ActionEvent actionEvent)
	{
		try
		{
			Image image = new Image("file:"+imageLoader().toString());
			setImage(image);
		} catch( URISyntaxException|IOException e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param keyEvent Event Handler
	 */
	@FXML
	protected void handleKeyInput(KeyEvent keyEvent)
	{
	}
	
	/**
	 * The following method are event handlers for different buttons.
	 *
	 * @param actionEvent Event Handler
	 */
	@FXML
	protected void handleAboutAction(ActionEvent actionEvent)
	{
	}
	
	/**
	 * The following method launches a new session.
	 *
	 * @param actionEvent The action Event
	 *
	 * @throws Exception Exception
	 */
	@FXML
	protected void launchNewSession(ActionEvent actionEvent) throws Exception
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/trackFace.fxml"));
			Parent newRoot = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(newRoot));
			stage.show();
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The method exits...
	 * @param actionEvent   action event
	 */
	@FXML
	protected void exit(ActionEvent actionEvent)
	{
		Platform.exit();
	}
	
	/**
	 * Reads from an url and loads image.
	 * @param actionEvent Action Event
	 */
	@FXML
	protected void insertFromUrl(ActionEvent actionEvent)
	{
		try
		{
//			Initiating the popup
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/popup.fxml"));
			Parent newRoot = (Parent) fxmlLoader.load();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(newRoot));
			stage.initStyle(StageStyle.UTILITY);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setAlwaysOnTop(true);
			stage.setTitle("Insert Images using URL");
			stage.showAndWait();
			
//			The get string name is returned and populated by a class inside popup.
			Image image = new Image(getStringName());
			setImage(image);
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete all images showing.
	 * @param actionEvent   Action event
	 */
	@FXML
	protected void resetImages(ActionEvent actionEvent)
	{
		originalFrame.setImage(null);
	}
	
	/**
	 * The starting method.
	 *
	 * @param primaryStage The stage object.
	 *
	 * @throws Exception In case it couldn't load the fxml file.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/trackFace.fxml"));
		Scene scene = new Scene(root, 600, 600);
		primaryStage.setTitle("Candy A.I.");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	/**
	 * Method exists to initialize the gui from an external client/method.
	 *
	 * @param args Default args from main class.
	 */
	public static void launchGUI(String[] args)
	{
		launch(args);
	}
	
	/**
	 * The method is called to launch the webcam when button is pressed.
	 */
	private void webcamSettings()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/webcam.fxml"));
			Parent newRoot = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(newRoot));
			stage.show();
		} catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The method just fits the image into the parent container.
	 *
	 * @param img from the button click file.
	 */
	
	private void setImage(Image img)
	{
		originalFrame.setImage(img);
		originalFrame.fitWidthProperty().bind(imageContainer.widthProperty());
		originalFrame.fitHeightProperty().bind(imageContainer.heightProperty());
	}
	
	/**
	 * Getter for URL Link from popup menu.
	 * @return  String which is a url
	 */
	private String getStringName()
	{
		return stringName;
	}
	
	/**
	 * Setter for url link from popup menu.
	 * @param stringName    String name
	 */
	static void setStringName(String stringName)
	{
		trackFaceController.stringName = stringName;
	}
}
