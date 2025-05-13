module wordleGame {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	
	opens wordleApplication to javafx.graphics, javafx.fxml;
}
