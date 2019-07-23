import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MathMenu {

	private Stage menuStage = new Stage();
	private Pane menuPane = new Pane();
	private Scene menuScene = new Scene(menuPane);

	private HBox buttonBox = new HBox();
	private HBox topBox = new HBox();
	private HBox bottomBox = new HBox();
	private HBox playerNameBox = new HBox();

	private TextField textField = new TextField();
	private Text playerNameText = new Text();
	private Label nameOfGame = new Label("חשבון לבונבון");

	private Button exitButton = new Button("יציאה");
	private Button startGameButton = new Button("התחל");

	private ComboBox<String> comboDifficulty = new ComboBox<String>();
	
	private String playerName;

	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
	private double maxXBorder = primaryScreenBounds.getMaxX();
	private double maxYBorder = primaryScreenBounds.getMaxY();

	public MathMenu(Pane menuPane, Scene menuScene, Stage menuStage) {
		super();
		this.menuPane = menuPane;
		this.menuScene = menuScene;
		this.menuStage = menuStage;

		stageSize();
		setPane();
		exitButtonLogic();
	}

	public void stageSize() {

		menuStage.setAlwaysOnTop(true);
		menuStage.setFullScreen(true);
		menuStage.setScene(menuScene);
		menuStage.show();
		menuPane.setOnMouseClicked(e -> {
			System.out.println("x:" + e.getSceneX());
			System.out.println("y:" + e.getSceneY());

		});

	}

	public void setPane() {

		setBottomBox();
		setTopBox();
		setTextField();
		setPlayerNameBox();
		setDifficultyCombo();


		menuPane.getChildren().addAll(bottomBox, topBox, textField,playerNameBox,comboDifficulty);

	}
	
	public void setDifficultyCombo() {

		comboDifficulty.setStyle("-fx-font-weight: bold;");
		comboDifficulty.setLayoutX(maxXBorder * 0.8);
		comboDifficulty.setLayoutY(maxYBorder*0.4);
		comboDifficulty.setPadding(new Insets(10, 25, 10, 25));
		comboDifficulty.getItems().addAll("קל", "בינוני", "קשה");
		comboDifficulty.setPromptText("רמת קושי");
	
	}

	public void setPlayerNameBox() {

		setPlayerNameText();

		
		playerNameBox.setPrefWidth(maxXBorder);
		playerNameBox.setPrefHeight(maxYBorder * 0.2);
		playerNameBox.setLayoutX((maxXBorder / 2) - playerNameBox.getPrefWidth() / 2);// 250
		playerNameBox.setLayoutY((maxYBorder * 0.4) - playerNameBox.getPrefHeight() / 2);
		playerNameBox.setSpacing(50);
		playerNameBox.setAlignment(Pos.CENTER);
		
		playerNameBox.getChildren().add(playerNameText);
		
	}

	public void setPlayerNameText() {

		playerNameText.setFont(Font.font(null, FontWeight.BOLD, 70));
		playerNameText.setCache(true);
		playerNameText.setFill(Color.BLUE);
		

	}

	public void setTextField() {

		final int TEXT_FIELD_LENGTH = 200;
		final int TEXT_FIELD_HIGHT = 40;

		textField.setPromptText("מה שמך?");
		textField.setAlignment(Pos.CENTER);

		textField.textProperty().addListener((observable, old_value, new_value) -> {
			if (new_value.contains(" ") || new_value.length() >= 10) {
				// prevents spacing or more than 10 letters name
				textField.setText(old_value);
				
			}
			playerNameBox.setAlignment(Pos.CENTER);
			playerNameText.setText(new_value);

		});
		textField.setPrefWidth(TEXT_FIELD_LENGTH);
		textField.setPrefHeight(TEXT_FIELD_HIGHT);
		textField.setLayoutX((maxXBorder / 2) - TEXT_FIELD_LENGTH / 2);
		textField.setLayoutY(maxYBorder * 0.7);// 530
		textField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				playerName = textField.getText();
				if (playerName.equals("") || playerName == null) {
					setPlayerName("Player");
				}
			}
		});

	}

	public void setTopBox() {

		topBox.getChildren().addAll(nameOfGame);

		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		nameOfGame.setEffect(ds);
		nameOfGame.setCache(true);
		nameOfGame.setTextFill(Color.BLUE);
		nameOfGame.setFont(Font.font(null, FontWeight.BOLD, 100));
		nameOfGame.setPrefWidth(maxXBorder);
		nameOfGame.setAlignment(Pos.CENTER);
		topBox.setPrefWidth(nameOfGame.getPrefWidth());

		topBox.setLayoutX((maxXBorder / 2) - topBox.getPrefWidth() / 2);// 250
		topBox.setLayoutY(maxYBorder * 0.);

	}

	public void exitButtonLogic() {

		exitButton.setOnAction(e -> {
			System.exit(0);
		});

	}

	public void setExitButton(Button exitButton) {

		exitButton.setTextFill(Color.BLUE);
		exitButton.setFont(Font.font(null, FontWeight.BOLD, 15));
		// exitButton.setPadding(new Insets(20, 20, 20, 20));
		exitButton.setPrefWidth(120);
		exitButton.setPrefHeight(40);

		this.exitButton = exitButton;
	}

	public void setStartGameButton(Button startGameButton) {

		startGameButton.setTextFill(Color.BLUE);
		startGameButton.setFont(Font.font(null, FontWeight.BOLD, 15));
		// startGameButton.setPadding(new Insets(20, 20, 20, 20));
		startGameButton.setPrefWidth(120);
		startGameButton.setPrefHeight(40);

		this.startGameButton = startGameButton;
	}

	public void setBottomBox() {

		setStartGameButton(startGameButton);
		setExitButton(exitButton);

		final double BOTTOM_BOX_SPACING_SIZE = 50;
		final double BOTTOM_BOX_WIDTH_SIZE = startGameButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE
				+ exitButton.getPrefWidth();
		final double BOTTOM_BOX_HIGHT_SIZE = 200;

		bottomBox.getChildren().addAll(startGameButton, exitButton);
		bottomBox.setPrefWidth(BOTTOM_BOX_WIDTH_SIZE);
		bottomBox.setPrefHeight(BOTTOM_BOX_HIGHT_SIZE);
		bottomBox.setLayoutX((maxXBorder / 2) - BOTTOM_BOX_WIDTH_SIZE / 2);
		bottomBox.setLayoutY(maxYBorder * 0.8);
		bottomBox.setSpacing(BOTTOM_BOX_SPACING_SIZE);

	}

	public Stage getMenuStage() {
		return menuStage;
	}

	public void setMenuStage(Stage menuStage) {
		this.menuStage = menuStage;
	}

	public Pane getMenuPane() {
		return menuPane;
	}

	public void setMenuPane(Pane menuPane) {
		this.menuPane = menuPane;
	}

	public Scene getMenuScene() {
		return menuScene;
	}

	public void setMenuScene(Scene menuScene) {
		this.menuScene = menuScene;
	}

	public HBox getButtonBox() {
		return buttonBox;
	}

	public void setButtonBox(HBox buttonBox) {
		this.buttonBox = buttonBox;
	}

	public HBox getTopBox() {
		return topBox;
	}

	public void setTopBox(HBox topBox) {
		this.topBox = topBox;
	}

	public HBox getBottomBox() {
		return bottomBox;
	}

	public void setBottomBox(HBox bottomBox) {
		this.bottomBox = bottomBox;
	}

	public TextField getTextField() {
		return textField;
	}

	public Label getNameOfGame() {
		return nameOfGame;
	}

	public void setNameOfGame(Label nameOfGame) {
		this.nameOfGame = nameOfGame;
	}

	public Rectangle2D getPrimaryScreenBounds() {
		return primaryScreenBounds;
	}

	public void setPrimaryScreenBounds(Rectangle2D primaryScreenBounds) {
		this.primaryScreenBounds = primaryScreenBounds;
	}

	public double getMaxXBorder() {
		return maxXBorder;
	}

	public void setMaxXBorder(double maxXBorder) {
		this.maxXBorder = maxXBorder;
	}

	public double getMaxYBorder() {
		return maxYBorder;
	}

	public void setMaxYBorder(double maxYBorder) {
		this.maxYBorder = maxYBorder;
	}

	public Button getExitButton() {
		return exitButton;
	}

	public Button getStartGameButton() {
		return startGameButton;
	}

	public String getPlayerName() {
		return playerName;

	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public HBox getPlayerNameBox() {
		return playerNameBox;
	}

	public void setPlayerNameBox(HBox playerNameBox) {
		this.playerNameBox = playerNameBox;
	}

	public Text getPlayerNameText() {
		return playerNameText;
	}

	public void setPlayerNameText(Text playerNameText) {
		this.playerNameText = playerNameText;
	}

	public ComboBox<String> getComboDifficulty() {
		return comboDifficulty;
	}

	public void setComboDifficulty(ComboBox<String> comboDifficulty) {
		this.comboDifficulty = comboDifficulty;
	}

	
	
}
