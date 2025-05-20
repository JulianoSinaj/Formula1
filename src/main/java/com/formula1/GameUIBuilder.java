package com.formula1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameUIBuilder {
    private static final String GAME_TITLE = "Formula 1 Racing Game";
    
    private GameController gameController;
    private Label statusLabel;
    private Label moveCounterLabel;
    private VBox root;
    private Canvas canvas;

    public GameUIBuilder(GameController gameController) {
        this.gameController = gameController;
    }

    public void setupUI() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #f0f0f0, #ffffff);
            -fx-border-color: #e0e0e0;
            -fx-border-width: 1;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);
            """);

        // Title section
        Label titleLabel = new Label(GAME_TITLE);
        titleLabel.setStyle("""
            -fx-font-family: 'Segoe UI', Arial, sans-serif;
            -fx-font-size: 24px;
            -fx-font-weight: bold;
            -fx-text-fill: #2c3e50;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);
            """);

        // Move counter with modern styling
        moveCounterLabel = new Label("Move #0");
        moveCounterLabel.setStyle("""
            -fx-font-family: 'Segoe UI', Arial, sans-serif;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #34495e;
            -fx-padding: 5 10;
            -fx-background-color: #ecf0f1;
            -fx-background-radius: 5;
            """);

        // Game canvas with border and shadow
        canvas = new Canvas(GameRenderer.GRID_WIDTH * GameRenderer.CELL_SIZE, 
                          GameRenderer.GRID_HEIGHT * GameRenderer.CELL_SIZE);
        canvas.setStyle("""
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);
            """);

        // Status label with modern styling
        statusLabel = new Label("Get ready!");
        statusLabel.setStyle("""
            -fx-font-family: 'Segoe UI', Arial, sans-serif;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #2c3e50;
            -fx-padding: 10;
            -fx-background-color: #ecf0f1;
            -fx-background-radius: 5;
            -fx-alignment: center;
            """);
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        // Controls section
        HBox controlsBox = new HBox(15);
        controlsBox.setAlignment(Pos.CENTER);

        // Restart button with modern styling
        Button restartButton = new Button("Restart Game");
        restartButton.setStyle("""
            -fx-font-family: 'Segoe UI', Arial, sans-serif;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 10 30;
            -fx-background-color: #3498db;
            -fx-text-fill: white;
            -fx-background-radius: 5;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 0);
            -fx-cursor: hand;
            """);
        restartButton.setOnMouseEntered(e -> 
            restartButton.setStyle(restartButton.getStyle() + "-fx-background-color: #2980b9;"));
        restartButton.setOnMouseExited(e -> 
            restartButton.setStyle(restartButton.getStyle() + "-fx-background-color: #3498db;"));
        restartButton.setOnAction(e -> gameController.restartGame());

        // Remove legendBox and only add restartButton to controlsBox
        controlsBox.getChildren().addAll(restartButton);

        root.getChildren().addAll(
            titleLabel,
            moveCounterLabel,
            canvas,
            statusLabel,
            controlsBox
        );
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Label getMoveCounterLabel() {
        return moveCounterLabel;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public VBox getRoot() {
        return root;
    }

    public void setGameController(GameController controller) {
        // Update the game controller reference
        this.gameController = controller;
    }
} 