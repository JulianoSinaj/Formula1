package com.formula1;

import com.formula1.model.Car;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private final GameController gameController;
    private final Scene gameScene;

    public InputHandler(GameController gameController, Scene gameScene) {
        this.gameController = gameController;
        this.gameScene = gameScene;
        setupKeyboardHandling();
    }

    private void setupKeyboardHandling() {
        gameScene.setOnKeyPressed(this::handleKeyPress);
        gameScene.setOnKeyReleased(this::handleKeyRelease);
    }

    private void handleKeyPress(KeyEvent event) {
        GameState gameState = gameController.getGameState();
        // Only process key events when it's human's turn and game is started
        if (!gameState.isGameStarted() || !gameState.isHumanTurn() || 
            gameController.getGameModel().isGameOver()) {
            return;
        }
        // Get player car to check if crashed
        Car playerCar = gameController.getGameModel().getCars().get(0);
        if (playerCar.isCrashed()) {
            return;
        }

        KeyCode code = event.getCode();
        if (code == KeyCode.UP || code == KeyCode.DOWN || 
            code == KeyCode.LEFT || code == KeyCode.RIGHT) {
            
            gameController.getHumanPlayer().setLastKeyPressed(code);
            gameController.handleHumanMove();
            
            event.consume();
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.UP || code == KeyCode.DOWN || 
            code == KeyCode.LEFT || code == KeyCode.RIGHT) {
            gameController.getHumanPlayer().clearLastKeyPressed();
            event.consume();
        }
    }
} 