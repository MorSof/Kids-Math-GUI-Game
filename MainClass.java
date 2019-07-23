
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClass extends Application {

	private Stage stage = new Stage();
	private Pane menuPane = new Pane();
	private Pane pane = new Pane();
	private Scene menuScene = new Scene(menuPane);
	private Scene mainScene = new Scene(pane);

	private HBox topBox = new HBox();
	private HBox mathBox = new HBox();
	private HBox feedbackBox = new HBox();
	private HBox bottomBox = new HBox();

	private MathMenu mathMenu;

	private Text firsNumText = new Text();
	private Text secondNumText = new Text();
	private Text mathSignText = new Text();
	private Text equelSignText = new Text("=");
	private Text feedbackText = new Text();
	private Text playerNameText = new Text();
	private Text scoreText = new Text();

	private TextField answerTextField = new TextField();
	private String[] goodFeedBackArray = { "מצויין!", "יופי!", "עבודה טובה!", "תשובה נכונה!", "מעולה!" };
	private String badFeedback = "תשובה לא נכונה, בוא ננסה שוב:";

	private int realAnswer = 0;
	private int playerAnswer = 0;

	private double SCREEN_WIDTH;
	private double SCREEN_HIGHT;
	private int num1 = 0;
	private int num2 = 0;
	private int score = 0;

	private Rectangle rectangle;

	private Button backToMenuButton = new Button("חזרה לתפריט");
	private Button leftButton = new Button("אופציונלי");
	private Button exitButton = new Button("יציאה");
	private Button insertButton = new Button("הזן תוצאה");

	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
	private double maxXBorder = primaryScreenBounds.getMaxX();
	private double maxYBorder = primaryScreenBounds.getMaxY();
	private final double BUTTON_WIDTH = 120;
	private final double BUTTON_HIGHT = 40;
	private final double BUTTON_TEXT_SIZE = 15;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;
		mathMenu = new MathMenu(menuPane, menuScene, stage);
		playerNameText = mathMenu.getPlayerNameText();
		menuScene = mathMenu.getMenuScene();
		menuPane = mathMenu.getMenuPane();

		SCREEN_WIDTH = mathMenu.getMaxXBorder();
		SCREEN_HIGHT = mathMenu.getMaxYBorder();

		mathMenu.getStartGameButton().setOnAction(e -> {
			mathMenu.setPlayerName(mathMenu.getTextField().getText());
			if (mathMenu.getPlayerName().equals("")) {
				mathMenu.setPlayerName("Player");
			}
			startGame();
		});

	}

	public void startGame() {

		stage.setScene(mainScene);
		stage.setFullScreen(true);
		setPane();
	}

	public void setPane() {

		setMathBox();
		setTopBox();
		setBottomBox();
		getScoreText();
		rectangle = new Rectangle(mathBox.getLayoutX(), mathBox.getLayoutY(), mathBox.getPrefWidth(),
				mathBox.getPrefHeight());
		rectangle.setStroke(Color.ALICEBLUE);
		rectangle.setFill(Color.WHITE);
		System.out.println(rectangle.toString());

		pane.getChildren().addAll(rectangle, mathBox, feedbackBox, topBox, bottomBox, scoreText);

	}

	public void getScoreText() {
		scoreText.setText("נקודות : " + score);
		scoreText.setFont(Font.font(null, FontWeight.BOLD, 40));
		scoreText.setLayoutX(SCREEN_WIDTH * 0.8);
		scoreText.setLayoutY(SCREEN_HIGHT * 0.1);

		scoreText.setFill(Color.RED);
		scoreText.setStyle("-fx-font-weight: bold;");
		scoreText.toFront();
	}

	public void setTopBox() {

		topBox.setPrefWidth(SCREEN_WIDTH);
		topBox.setPrefHeight(SCREEN_HIGHT * 0.2);
		topBox.setLayoutX((SCREEN_WIDTH / 2) - topBox.getPrefWidth() / 2);// 250
		topBox.setLayoutY((SCREEN_HIGHT * 0.1) - topBox.getPrefHeight() / 2);
		topBox.setSpacing(50);
		topBox.setAlignment(Pos.CENTER);

		topBox.getChildren().addAll(playerNameText);

	}

	public void setFeedbackBox() {

		setTextFont(feedbackText);

		feedbackBox.setPrefWidth(SCREEN_WIDTH);
		feedbackBox.setPrefHeight(SCREEN_HIGHT * 0.2);
		feedbackBox.setLayoutX((SCREEN_WIDTH / 2) - feedbackBox.getPrefWidth() / 2);// 250
		feedbackBox.setLayoutY((SCREEN_HIGHT * 0.3) - feedbackBox.getPrefHeight() / 2);
		feedbackBox.setSpacing(50);
		feedbackBox.setAlignment(Pos.CENTER);

		feedbackBox.getChildren().add(feedbackText);
	}

	public void feedBackLogic(boolean rightAnswer) {

		if (rightAnswer) {
			int index = (0 + (int) (Math.random() * goodFeedBackArray.length));
			String chosenFeedback = goodFeedBackArray[index];
			while (chosenFeedback == feedbackText.getText()) {
				index = (0 + (int) (Math.random() * goodFeedBackArray.length));
				chosenFeedback = goodFeedBackArray[index];
			}
			feedbackText.setText(chosenFeedback);
		} else {
			feedbackText.setText(badFeedback);
		}

	}

	public void setMathBox() {

		int numSign = (0 + (int) (Math.random() * 2));
		
		setInsetButton();
		comboDifficultyLogic(numSign);
		setNumText(firsNumText, num1);
		setNumText(secondNumText, num2);
		setMathSignText(numSign);
		setTextField();

		mathBox.setPrefWidth(SCREEN_WIDTH);
		mathBox.setPrefHeight(SCREEN_HIGHT * 0.3);
		mathBox.setLayoutX((SCREEN_WIDTH / 2) - mathBox.getPrefWidth() / 2);// 250
		mathBox.setLayoutY((SCREEN_HIGHT * 0.5) - mathBox.getPrefHeight() / 2);

		mathBox.setSpacing(50);
		mathBox.setAlignment(Pos.CENTER);

		setTextFont(equelSignText);

		mathBox.getChildren().addAll(firsNumText, mathSignText, secondNumText, equelSignText, answerTextField,
				insertButton);

		mathGeneral(numSign);
	}

	public void comboDifficultyLogic(int numSign) {
		if (mathMenu.getComboDifficulty().getValue() == null || mathMenu.getComboDifficulty().getValue() == "") {
			mathMenu.getComboDifficulty().setValue("קל");
		}

		switch (mathMenu.getComboDifficulty().getValue()) {
		case "קל":
			do {
				num1 = (0 + (int) (Math.random() * 10));
				num2 = (0 + (int) (Math.random() * num1));
				System.out.println(num1);
			} while ((num1 + num2 > 10) && numSign == 0);
			break;
		case "בינוני":
			num1 = (0 + (int) (Math.random() * 10));
			num2 = (0 + (int) (Math.random() * num1));
			break;
		case "קשה":
			num1 = (0 + (int) (Math.random() * 100));
			num2 = (0 + (int) (Math.random() * num1));
			break;

		default:
			do {
				num1 = (0 + (int) (Math.random() * 10));
				num2 = (0 + (int) (Math.random() * num1));
			} while (num1 + num2 > 10);
		}

	}

	public void numSignLogic(int numSign) {
		switch (numSign) {
		case 0:
			realAnswer = num1 + num2;
			break;
		case 1:
			realAnswer = num1 - num2;
			break;
		}
	}

	public void mathLogic() {
		if ((!answerTextField.getText().equals("")) && (answerTextField.getText() != null)) {
			System.out.println(answerTextField.getText());
			playerAnswer = Integer.parseInt(answerTextField.getText());
			answerTextField.clear();

			// if the answer is correct
			if (realAnswer == playerAnswer) {
				score += 1;
				getScoreText();
				mathBox.getChildren().clear();
				feedbackBox.getChildren().clear();
				feedbackText.setFill(Color.GREEN);
				feedBackLogic(true);
				setMathBox();
				setFeedbackBox();
				System.out.println("Great !");

				// if the answer is wrong
			} else {
				feedbackBox.getChildren().clear();
				feedbackText.setFill(Color.RED);
				feedBackLogic(false);
				setFeedbackBox();
				System.out.println("Not Good");
			}
		}
	}

	public void mathGeneral(int numSign) {

		numSignLogic(numSign);

		answerTextField.setOnKeyPressed(e -> {

			if ((e.getCode() == KeyCode.ENTER)) {
				mathLogic();
			}
		});
	}

	public void setTextFont(Text text) {
		text.setFont(Font.font(null, FontWeight.BOLD, 50));
	}

	public void setNumText(Text numText, int num) {

		numText.setText("" + num);
		numText.prefWidth(SCREEN_WIDTH);

		setTextFont(numText);

	}

	public void setMathSignText(int numSign) {

		switch (numSign) {
		case 0:
			mathSignText.setText("+");
			break;
		case 1:
			mathSignText.setText("-");
			break;
		}

		setTextFont(mathSignText);

	}

	public void setTextField() {

		final int TEXT_FIELD_LENGTH = 120;
		final int TEXT_FIELD_HIGHT = 100;

		answerTextField.setFont(Font.font(null, FontWeight.BOLD, 50));
		answerTextField.setAlignment(Pos.CENTER);
		answerTextField.textProperty().addListener((observable, old_value, new_value) -> {
			if (new_value.contains(" ") || new_value.length() > 2) {
				// prevents spacing or more than 10 letters name
				answerTextField.setText(old_value);
			}
			if (!new_value.matches("\\d*")) {
				answerTextField.setText(new_value.replaceAll("[^\\d]", ""));
			}
		});
		answerTextField.setPrefWidth(TEXT_FIELD_LENGTH);
		answerTextField.setPrefHeight(TEXT_FIELD_HIGHT);

	}

	public void setInsetButton() {

		insertButton.setTextFill(Color.GREEN);
		insertButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		insertButton.setPrefWidth(BUTTON_WIDTH);
		insertButton.setPrefHeight(BUTTON_HIGHT);
		insertButton.toFront();

		insertButton.setOnAction(e -> {
			mathLogic();
		});

	}

	public void setLeftButton() {

		leftButton.setTextFill(Color.GREEN);
		leftButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		leftButton.setPrefWidth(BUTTON_WIDTH);
		leftButton.setPrefHeight(BUTTON_HIGHT);
		leftButton.toFront();
	}

	public void setBackToMenuButton() {

		backToMenuButton.setTextFill(Color.GREEN);
		backToMenuButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		backToMenuButton.setPrefWidth(BUTTON_WIDTH);
		backToMenuButton.setPrefHeight(BUTTON_HIGHT);
		backToMenuButton.toFront();

		backToMenuButton.setOnAction(e -> {
			clearAll();
			score = 0;
			getScoreText();
			stage.setScene(menuScene);
			stage.setFullScreen(true);
			mathMenu.getTextField().clear();
			mathMenu.getMenuPane().getChildren().add(mathMenu.getPlayerNameText());
			mathMenu.setPlayerNameBox();
		});

	}

	public void setExitButton() {

		exitButton.setTextFill(Color.GREEN);
		exitButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		exitButton.setPrefWidth(BUTTON_WIDTH);
		exitButton.setPrefHeight(BUTTON_HIGHT);
		exitButton.toFront();

		exitButton.setOnAction(e -> {

			System.exit(0);

		});

	}

	public void setBottomBox() {

		setBackToMenuButton();
		setLeftButton();
		setExitButton();

		final double BOTTOM_BOX_SPACING_SIZE = 50;
		final double BOTTOM_BOX_HIGHT_SIZE = 200;
		final double BOTTOM_BOX_WIDTH_SIZE = leftButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE
				+ backToMenuButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE + exitButton.getPrefWidth();

		bottomBox.getChildren().addAll(leftButton, backToMenuButton, exitButton);
		bottomBox.setPrefWidth(BOTTOM_BOX_WIDTH_SIZE);
		bottomBox.setPrefHeight(BOTTOM_BOX_HIGHT_SIZE);
		bottomBox.setLayoutX((maxXBorder / 2) - BOTTOM_BOX_WIDTH_SIZE / 2);
		bottomBox.setLayoutY(maxYBorder * 0.8);
		bottomBox.setSpacing(BOTTOM_BOX_SPACING_SIZE);

	}

	public void clearAll() {
		mathBox.getChildren().clear();
		topBox.getChildren().clear();
		bottomBox.getChildren().clear();
		pane.getChildren().clear();
	}

}
