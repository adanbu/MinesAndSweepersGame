package mines;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MinesUI extends Application implements EventHandler<MouseEvent> {
	protected Mines minesGame;
	protected int height = 10;
	protected int width = 10;
	protected int minesNum = 10;
	private GridPane Pane;
	private HBox firstBox;
	private boolean play = true;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			minesGame = new Mines(height, width, minesNum);
			Parent root = FXMLLoader.load(getClass().getResource("minesFX1.fxml"));
			this.Pane = initiateGrid(width, height);
			this.Pane.setAlignment(Pos.CENTER);
			this.firstBox = ((HBox) root);
			VBox xxBox = (VBox) (this.firstBox.getChildren().get(0));
			Button resetButton = (Button) xxBox.getChildren().get(0);
			resetButton.setOnMouseClicked(this);
			resetButton.setAlignment(Pos.CENTER);
			resetButton.setText("Reset");
			((HBox) root).getChildren().add(this.Pane);
			stage.setTitle("The Amazing Mines Sweeper");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GridPane initiateGrid(int width, int height) {
		//initiates grid pane
		//ImageView img;
		GridPane aGridPane = new GridPane();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				buttons b = new buttons(minesGame.get(i, j), i, j);
				b.setOnMouseClicked(this);
				b.setPrefSize(40, 40);
				
					b.setFont(new Font("Arial", 17));
				aGridPane.add(b, i, j);
			}
		}
		return aGridPane;
	}

	public void resetroot() {
		//resets gridPane
		this.Pane = initiateGrid(width, height);
		this.Pane.setAlignment(Pos.CENTER);
		this.firstBox.getChildren().set(1, Pane);
	}

	public void resetGame() {
		//resets game
		width = MyController2.getWidth();
		height = MyController2.getHeight();
		minesNum = MyController2.getMinesNum();
		if ((width <= 0 || height <= 0 || minesNum <= 0)) {
			displayWinOrLose("Wrong input!\nYou need to put in Numbers above zero", "error-icon-4.png", "wrong input");

		} else {
			minesGame = new Mines(width, height, minesNum);
			resetroot();
		}
	}

	private void displayWinOrLose(String message, String imageLocation, String title) {
		//this function opens a window with a text message and a picture																				// with the text message
		Image image = new Image(getClass().getResourceAsStream(imageLocation));

		Stage windowPrompt = new Stage();
		Label promptMessage = new Label(message);
		promptMessage.setTextAlignment(TextAlignment.CENTER);

		Button promptButton = new Button("Exit");
		promptButton.setOnAction(e -> windowPrompt.close());

		VBox promptBox = new VBox(20);
		promptBox.getChildren().addAll(new ImageView(image), promptMessage, promptButton);
		promptBox.setPadding(new Insets(20, 100, 20, 100));
		promptBox.setAlignment(Pos.CENTER);

		windowPrompt.initModality(Modality.APPLICATION_MODAL);
		windowPrompt.setTitle(title);
		windowPrompt.setScene(new Scene(promptBox));
		windowPrompt.showAndWait();
	}

	@Override
	public void handle(MouseEvent event) {
		//handles different events in the game
		if (event.getSource() instanceof buttons) {
			// only the reset button is not instance of button
			buttons b = (buttons) event.getSource();
			if (event.getButton() == MouseButton.PRIMARY) {
				// if the left mouse button was used
				if (!minesGame.open(b.getX(), b.getY()) && play) {
					// the function open returns false only trying to open a mine
					play = false;
					minesGame.setShowAll(true);
					displayWinOrLose("", "gameoversmall.png", "lose");
				} else if (minesGame.isDone() && play) {// in case all the spot that not mine is opened
					play = false;
					minesGame.setShowAll(true);
					displayWinOrLose("", "win.jpg", "won");
				}
			} else if (event.getButton() == MouseButton.SECONDARY) {
				// if the right mouse button was pressed
				minesGame.toggleFlag(b.getX(), b.getY());// put a flag in the spot
			}
			resetroot();
		} else {// if reset button clicked
			play = true;
			resetGame();// reset board
		}

	}

}
