package gui;
//Imports

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static processing.loadImages.imageLoader;

public class userInterface extends Application
{
	
	//Setup display variables
	private File imageLocation;
	private Stage primaryStage;
	private Scene mainScene;
	private BorderPane borderPane;
	private ImageView img1;
	
	public static void main(String args[])
	{
		launch(args);
	}
	
	private void setImageLocation(File location)
	{
		this.imageLocation = location;
		System.out.println(imageLocation);
	}
	
	private void setPrimaryStage(Stage stage)
	{
		this.primaryStage = stage;
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		setPrimaryStage(primaryStage);
		primaryStage.setTitle("Candy AI");
        borderPane = new BorderPane();
        
        
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);
        MenuItem openImage = new MenuItem("Browse Images");
        openImage.setOnAction(e -> browseImages());
        menuFile.getItems().addAll(openImage);
        borderPane.setTop(menuBar);
		
		StackPane root = new StackPane();
		
		Button btn = new Button("Select Random Image");
		btn.setOnAction(e -> selectRandomImage());
		
		btn.setTranslateX(0);
		btn.setTranslateY(0);
		root.getChildren().add(btn);
		
		Button btn2 = new Button("Browse Images");
		btn2.setOnAction(e -> browseImages());
		
		btn.setTranslateX(150);
		btn.setTranslateY(0);
		root.getChildren().add(btn2);
		mainScene = new Scene(root, 700, 700);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
	}
	
	public void browseImages()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image");
		setImageLocation(fileChooser.showOpenDialog(primaryStage));
		displayImage();
	}
	
	public void selectRandomImage()
	{
		try
		{
			setImageLocation(imageLoader());
		} catch( URISyntaxException e )
		{
		} catch( IOException e1 )
		{
		}
		displayImage();
		
	}
	
	public void displayImage()
	{
		img1 = new ImageView();
		try
		{
			Image image = new Image(imageLocation.toURL().toString());
			img1.setImage(image);
		} catch( MalformedURLException e )
		{
			e.printStackTrace();
		}
		
		Group root = new Group();
		Scene scene = new Scene(root, 300, 330);
		
		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		final HBox hBox = new HBox();
		
		hBox.getChildren().add(img1);
		gridpane.add(hBox, 1, 1);
		
		root.getChildren().add(gridpane);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
}
