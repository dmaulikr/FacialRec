package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import static gui.trackFaceController.setStringName;

/**
 * Class Details:- Author: Sarhad User: sarha Date: 03-Sep-17 Time : 2:12 PM Project Name: CandyAI Class Name: popup
 */
public class popup
{
	
	@FXML
	public TextArea urlLink;
	@FXML
	public Button insertImage;
	@FXML
	public Button cancel;
	
	/**
	 *
	 * @param actionEvent Action Even
	 */
	@FXML
	protected void insertImage(ActionEvent actionEvent)
	{
		setStringName(urlLink.getText());
		( (Node) ( actionEvent.getSource() ) ).getScene().getWindow().hide();
		
	}
	
	/**
	 * Cancelling popup
	 * @param actionEvent Action event
	 */
	@FXML
	protected void cancel(ActionEvent actionEvent)
	{
		( (Node) ( actionEvent.getSource() ) ).getScene().getWindow().hide();
	}
}