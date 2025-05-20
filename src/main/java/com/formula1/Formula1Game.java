package com.formula1;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Formula1Game extends Application {
    private GameController gameController;
    private GameUIBuilder uiBuilder;
    private GameRenderer renderer;
    private GameState gameState;

    @Override
    public void start(Stage primaryStage) {
        // Initialize game state first
        gameState = new GameState();

        // Create UI builder with null controller initially
        uiBuilder = new GameUIBuilder(null);
        uiBuilder.setupUI();

        // Create renderer
        renderer = new GameRenderer(uiBuilder.getCanvas());

        // Create game controller with all dependencies
        gameController = new GameController(
            uiBuilder.getStatusLabel(),
            uiBuilder.getMoveCounterLabel(),
            renderer,
            gameState
        );

        // Set the controller in the UI builder
        uiBuilder.setGameController(gameController);

        // Show the game window
        primaryStage.setTitle("Formula 1 Racing Game");
        // Start the game directly
        gameController.startGame();
        Scene scene = new Scene(uiBuilder.getRoot());
        primaryStage.setScene(scene);
        new InputHandler(gameController, scene);
        uiBuilder.getRoot().requestFocus(); // Ensure keyboard focus
        primaryStage.show();
    }

    @Override
    public void stop() {
        gameController.stopGame();
    }
} 