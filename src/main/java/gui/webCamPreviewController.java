package gui;

import com.github.sarxos.webcam.Webcam;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class that deals with launching webcam stream. The method was copied from the example available on sarxos website.
 */

public class webCamPreviewController extends Application implements Initializable {
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	Button btnStartCamera;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	Button btnStopCamera;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	Button btnDisposeCamera;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	ComboBox<WebCamInfo> cbCameraOptions;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	BorderPane bpWebCamPaneHolder;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	FlowPane fpBottomPane;
	
	/**
	 * The method are field holders from the fxml file located in resources/fxml/webcam.
	 */
	@FXML
	ImageView imgWebCamCapturedImage;
	
	/**
	 * Anonymous class holding webcam information of every single listed webcam. The fields within are self explanatory.
	 */
	private class WebCamInfo {
		
		private String webCamName;
		private int webCamIndex;
		
		String getWebCamName() {
			return webCamName;
		}
		
		void setWebCamName(String webCamName) {
			this.webCamName = webCamName;
		}
		
		int getWebCamIndex() {
			return webCamIndex;
		}
		
		void setWebCamIndex(int webCamIndex) {
			this.webCamIndex = webCamIndex;
		}
		
		@Override
		public String toString() {
			return webCamName;
		}
	}
	
	/**
	 * Different global object holders.
	 */
	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	
	/**
	 * The method to initialize the gui method.
	 * @param arg0  URL
	 * @param arg1  ResBundle
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		fpBottomPane.setDisable(true);
		ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
		int webCamCounter = 0;
		for (Webcam webcam : Webcam.getWebcams()) {
			WebCamInfo webCamInfo = new WebCamInfo();
			webCamInfo.setWebCamIndex(webCamCounter);
			webCamInfo.setWebCamName(webcam.getName());
			options.add(webCamInfo);
			webCamCounter++;
		}
		cbCameraOptions.setItems(options);
		String cameraListPromptText = "Choose Camera";
		cbCameraOptions.setPromptText(cameraListPromptText);
		cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {
			
			@Override
			public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
				if (arg2 != null) {
					System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
					initializeWebCam(arg2.getWebCamIndex());
				}
			}
		});
		Platform.runLater(this::setImageViewSize);
		
	}
	
	/**
	 * Method to set webcam stream to respective sizes.
	 */
	private void setImageViewSize() {
		
		double height = bpWebCamPaneHolder.getHeight();
		double width = bpWebCamPaneHolder.getWidth();
		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		imgWebCamCapturedImage.setPreserveRatio(true);
		
	}
	
	/**
	 * The method to initialize webcam.
	 * @param webCamIndex   Choice of preffered webcam.
	 */
	private void initializeWebCam(final int webCamIndex) {
		
		Task<Void> webCamIntilizer = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				
				if (selWebCam == null) {
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				} else {
					closeCamera();
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				}
				startWebCamStream();
				return null;
			}
			
		};
		
		new Thread(webCamIntilizer).start();
		fpBottomPane.setDisable(false);
		btnStartCamera.setDisable(true);
	}
	
	/**
	 * Webcam stream starter.
	 */
	private void startWebCamStream() {
		
		stopCamera = false;
		Task<Void> task = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				
				while (!stopCamera) {
					try {
						if ((grabbedImage = selWebCam.getImage()) != null) {
							
							Platform.runLater(() -> {
								final Image mainiamge = SwingFXUtils
										.toFXImage(grabbedImage, null);
								imageProperty.set(mainiamge);
							});
							
							grabbedImage.flush();
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return null;
			}
			
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebCamCapturedImage.imageProperty().bind(imageProperty);
		
	}
	
	/**
	 * Close stream.
	 */
	private void closeCamera() {
		if (selWebCam != null) {
			selWebCam.close();
		}
	}
	
	/**
	 * Stop camera
	 * @param event Action event
	 */
	public void stopCamera(ActionEvent event) {
		stopCamera = true;
		btnStartCamera.setDisable(false);
		btnStopCamera.setDisable(true);
	}
	
	/**
	 * Starter
	 * @param   event   Action Event
	 */
	public void startCamera(ActionEvent event) {
		stopCamera = false;
		startWebCamStream();
		btnStartCamera.setDisable(true);
		btnStopCamera.setDisable(false);
	}
	
	/**
	 * Disposer cam
	 * @param event ActionEvent
	 */
	public void disposeCamera(ActionEvent event) {
		stopCamera = true;
		closeCamera();
		btnStopCamera.setDisable(true);
		btnStartCamera.setDisable(true);
	}
	
	/**
	 * the start method.
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage)
	{
		
		Parent root = null;
		try
		{
			root = FXMLLoader.load(getClass().getResource("../fxml/webcam.fxml"));
		} catch( IOException e )
		{
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root, 900, 690);
		
		primaryStage.setTitle("WebCam Detection");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	/**
	 * To launch from within the app. Allowing direct entry to webcam access.
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}