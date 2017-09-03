package gui;

/**
 * Class Details:- Author: Sarhad User: sarha Date: 03-Sep-17 Time : 2:31 AM Project Name: CandyAI Class Name:
 * WebCamPreviewController
 */

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

public class WebCamPreviewController extends Application implements Initializable {
	
	@FXML
	Button btnStartCamera;
	
	@FXML
	Button btnStopCamera;
	
	@FXML
	Button btnDisposeCamera;
	
	@FXML
	ComboBox<WebCamInfo> cbCameraOptions;
	
	@FXML
	BorderPane bpWebCamPaneHolder;
	
	@FXML
	FlowPane fpBottomPane;
	
	@FXML
	ImageView imgWebCamCapturedImage;
	
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
	
	private BufferedImage grabbedImage;
	private Webcam selWebCam = null;
	private boolean stopCamera = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	
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
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				setImageViewSize();
			}
		});
		
	}
	
	private void setImageViewSize() {
		
		double height = bpWebCamPaneHolder.getHeight();
		double width = bpWebCamPaneHolder.getWidth();
		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		imgWebCamCapturedImage.setPreserveRatio(true);
		
	}
	
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
	
	private void startWebCamStream() {
		
		stopCamera = false;
		Task<Void> task = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				
				while (!stopCamera) {
					try {
						if ((grabbedImage = selWebCam.getImage()) != null) {
							
							Platform.runLater(new Runnable() {
								
								@Override
								public void run() {
									final Image mainiamge = SwingFXUtils
											.toFXImage(grabbedImage, null);
									imageProperty.set(mainiamge);
								}
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
	
	private void closeCamera() {
		if (selWebCam != null) {
			selWebCam.close();
		}
	}
	
	public void stopCamera(ActionEvent event) {
		stopCamera = true;
		btnStartCamera.setDisable(false);
		btnStopCamera.setDisable(true);
	}
	
	public void startCamera(ActionEvent event) {
		stopCamera = false;
		startWebCamStream();
		btnStartCamera.setDisable(true);
		btnStopCamera.setDisable(false);
	}
	
	public void disposeCamera(ActionEvent event) {
		stopCamera = true;
		closeCamera();
		btnStopCamera.setDisable(true);
		btnStartCamera.setDisable(true);
	}
	
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
	
	
	public static void main(String[] args)
	{
		launch(args);
	}
}