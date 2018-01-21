package honoursproject.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class GameScreenController {

	
	@FXML
	private AnchorPane gamePane;

	
	public AnchorPane getGamePane(){
		return gamePane;
	}
	
	public void addNode(Node newNode){
		gamePane.getChildren().add(newNode);
	}
	
	public void addNode(ArrayList<Node> nodeList){
		for(int i = 0; i < nodeList.size(); i++){
			addNode(nodeList.get(i));
		}
	}
	
	public void removeNode(Node removableNode){
		gamePane.getChildren().remove(removableNode);
	}
}