package mines;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class MyController2 extends MinesUI{
	static int width = 10;// set the width,height and the number of mines as 10.
	static int height = 10;
	static int minesNum = 10;
	
	@FXML
    private HBox hbox;
	
    @FXML
    private TextField heightInput;
    
    
    @FXML
    private TextField minesInput;

    
    @FXML
    private static Button resetButton;

    @FXML
    private TextField widthInput;
   
    
    @FXML
    void Resetted(ActionEvent event) {
    	//button reset was pressed
    	super.width = Integer.parseInt(widthInput.getText());
		super.height = Integer.parseInt(heightInput.getText());
		super.minesNum = Integer.parseInt(minesInput.getText());
	}

    @FXML
    void setHeight(ActionEvent event) {
    	//value was entered for height
    	try {
    		super.height=Integer.parseInt(heightInput.getText());;
    		height = Integer.parseInt(heightInput.getText());
    	}
    	catch(NumberFormatException e) {
    		//catches any non numeric inputs
    	}
    	
    }

    @FXML
    void setMines(ActionEvent event) {
    	//value was entered for mines
    	try {
    		super.minesNum=Integer.parseInt(minesInput.getText());
    		minesNum = Integer.parseInt(minesInput.getText());
    	}
    	catch(NumberFormatException e) {
    		//catches any non numeric inputs
    	}
    }

    @FXML
    void setWidth(ActionEvent event) {
    	//value was entered for width
    	try {
    		super.width=Integer.parseInt(widthInput.getText());
    		width = Integer.parseInt(widthInput.getText());
    	}
    	catch(NumberFormatException e) {
    		//catches any non numeric inputs
    	}
    }
    
    public static int getHeight() {return height;}
    public static int getWidth() {return width;}
    public static int getMinesNum() {// this returns the number of mines
		return minesNum;
	}
    public static Button getResetbutton() {// this returns the resetbutton
		return resetButton;
	}
}
