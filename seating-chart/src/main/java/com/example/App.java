package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
    private static final int ROWS = 3;
    private static final int COLS = 3;

    private final Rectangle[][] seats = new Rectangle[ROWS][COLS];
    private final Color[][] seatColors = new Color[ROWS][COLS];
    private int currentRow = 0;
    private int currentCol = 0;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(120));
        gridPane.setHgap(80);
        gridPane.setVgap(80);

        // Creating the seating chart grid
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Rectangle seat = new Rectangle(50, 50);
                seat.setStroke(Color.BLACK);
                seat.setFill(Color.WHITE);
                seats[row][col] = seat;
                seatColors[row][col] = null;

                gridPane.add(seat, col, row);
            }
        }

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        ColorPicker colorPicker = new ColorPicker();
        Button addButton = new Button("Add Student");

        addButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            Color color = colorPicker.getValue();

            if (name.isEmpty()) {
                // Display an error message if the name field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a name.");
                alert.showAndWait();
            } else if (color == null) {
                // Display an error message if no color is chosen
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please choose a color.");
                alert.showAndWait();
            } else if (seatColors[currentRow][currentCol] != null) {
                // Display an error message if the seat is already taken
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("This seat is already taken.");
                alert.showAndWait();
            } else {
                // Assign the chosen color to the seat and move to the next seat
                seats[currentRow][currentCol].setFill(color);
                seatColors[currentRow][currentCol] = color;

                if (++currentCol >= COLS) {
                    currentCol = 0;
                    if (++currentRow >= ROWS) {
                        // Display a completion message if all seats are filled
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Congratulations!");
                        alert.setHeaderText(null);
                        alert.setContentText("Seating arrangement complete!");
                        alert.showAndWait();
                        return;
                    }
                }
            }
        });

        VBox box = new VBox(10, nameLabel, nameField, colorPicker, addButton);
        box.setPadding(new Insets(20));

        Label heading = new Label("Teacher");

        heading.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Pane root = new Pane(gridPane, heading, box);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Set resizable to false to prevent the window from being maximized
        primaryStage.setWidth(500); // Set the window width to  400 pixels
        primaryStage.setHeight(500); // Set the window height to 400 pixels
        primaryStage.show();

        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
