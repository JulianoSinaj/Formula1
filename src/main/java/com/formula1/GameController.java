package com.formula1;

import com.formula1.model.*;
import com.formula1.player.*;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class GameController {
    private static final long COUNTDOWN_DELAY = 1_000_000_000L; // 1 second in nanoseconds
    private static final long FRAME_DELAY = 100_000_000L; // 100ms for better control
    private static final int MAX_MOVES = 50;

    private final Label statusLabel;
    private final Label moveCounterLabel;
    private final GameRenderer renderer;
    private final GameState gameState;
    private GameModel gameModel;
    
    private AnimationTimer gameLoop;
    private AnimationTimer countdownTimer;
    private HumanPlayer humanPlayer;
    private long lastUpdate = 0;
    private long countdownLastUpdate = 0;
    private int countdownNumber = 3;
    private int moveCounter = 0;

    public GameController(Label statusLabel, Label moveCounterLabel, GameRenderer renderer, GameState gameState) {
        this.statusLabel = statusLabel;
        this.moveCounterLabel = moveCounterLabel;
        this.renderer = renderer;
        this.gameState = gameState;
        this.gameModel = new GameModel(createInitialTrack());
    }

    public void startGame() {
        moveCounter = 0;
        moveCounterLabel.setText("Move #0");
        statusLabel.setText("Game started! Make your move.");
        renderer.render(gameModel);
        initializeGame();
        startCountdown();
    }

    private Track createInitialTrack() {
        Vector2D finishPos = generateRandomFinishPosition();
        boolean startOnLeft = finishPos.getX() > GameRenderer.GRID_WIDTH / 2;
        Vector2D startPos = new Vector2D(
            startOnLeft ? 1 : GameRenderer.GRID_WIDTH - 2,
            GameRenderer.GRID_HEIGHT / 2
        );
        return new Track(GameRenderer.GRID_WIDTH, GameRenderer.GRID_HEIGHT, startPos, finishPos);
    }

    private Vector2D generateRandomFinishPosition() {
        java.util.Random random = new java.util.Random();
        boolean onRightSide = random.nextBoolean();
        int x = onRightSide ? GameRenderer.GRID_WIDTH - 2 : 1;
        int minY = GameRenderer.GRID_HEIGHT / 3;
        int maxY = (GameRenderer.GRID_HEIGHT * 2) / 3;
        int y = minY + random.nextInt(maxY - minY);
        return new Vector2D(x, y);
    }

    private void initializeGame() {
        gameState.reset();
        gameModel = new GameModel(createInitialTrack());
        Vector2D startPos = gameModel.getTrack().getStartPosition();

        humanPlayer = new HumanPlayer("Player 1");
        gameModel.addPlayer(humanPlayer, new Vector2D(startPos.getX(), startPos.getY() - 1));
        gameModel.addPlayer(new BotDriver("Bot"), new Vector2D(startPos.getX(), startPos.getY() + 1));
        statusLabel.setText("Human vs Bot Mode - Get ready!");
        gameState.setHumanTurn(true);
        
        renderer.render(gameModel);
    }

    private void startCountdown() {
        gameState.setGameStarted(false);
        countdownNumber = 3;
        statusLabel.setText("Game starting in: 3");
        
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        countdownTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (countdownLastUpdate == 0) {
                    countdownLastUpdate = now;
                    return;
                }

                if (now - countdownLastUpdate >= COUNTDOWN_DELAY) {
                    countdownNumber--;
                    if (countdownNumber > 0) {
                        statusLabel.setText("Game starting in: " + countdownNumber);
                    } else if (countdownNumber == 0) {
                        statusLabel.setText("GO!");
                    } else {
                        gameState.setGameStarted(true);
                        statusLabel.setText("GO!");
                        this.stop();
                        startGameLoop();
                    }
                    countdownLastUpdate = now;
                }
            }
        };
        countdownTimer.start();
    }

    private void startGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        lastUpdate = 0;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameState.isGameStarted()) {
                    return;
                }
                
                if (now - lastUpdate >= FRAME_DELAY) {
                    // Only bot moves automatically when it's bot's turn
                    if (!gameModel.isGameOver() && !gameState.isHumanTurn()) {
                        makeGameMove();
                        lastUpdate = now;
                    }
                    
                    if (gameModel.isGameOver()) {
                        stop();
                        showGameOver();
                    }
                }
            }
        };
        gameLoop.start();
    }

    private void makeGameMove() {
        gameModel.makeMove();
        updateDisplay();
    
        // Print player and bot car info (keep only one set)
        Car playerCar = gameModel.getCars().get(0);
        Car botCar = gameModel.getCars().get(1);
        System.out.println(playerCar.getPlayerName() + " - Pos: " + playerCar.getPosition() + ", Vel: " + playerCar.getVelocity());
        System.out.println(botCar.getPlayerName() + " - Pos: " + botCar.getPosition() + ", Vel: " + botCar.getVelocity());
    
        Car currentCar = gameModel.getCars().get(gameState.isHumanTurn() ? 0 : 1);
        if (currentCar.isCrashed()) {
            statusLabel.setText(gameState.isHumanTurn() ? "Player crashed!" : "Bot crashed!");
            gameModel.setGameOver(true);
            showGameOver();
            return;
        }
    
        if (gameModel.isGameOver()) {
            showGameOver();
            return;
        }
    
        // Switch turn
        gameState.setHumanTurn(!gameState.isHumanTurn());
    
        // Count full round (when human starts again)
        if (gameState.isHumanTurn()) {
            moveCounter++;
            updateMoveCounter();
            if (moveCounter >= MAX_MOVES) {
                gameModel.setGameOver(true);
                statusLabel.setText("Game stopped - max number of moves reached - no winner");
            }
        }
    }
    

    public void handleHumanMove() {
        if (!gameModel.isGameOver() && gameState.isHumanTurn()) {
            makeGameMove();
        }
    }

    public void restartGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        
        gameState.reset();
        moveCounterLabel.setText("Move #0");
        moveCounter = 0;
        updateMoveCounter();
        startGame();
        // Re-request focus for the root node to ensure keyboard input works after restart
        if (moveCounterLabel.getScene() != null && moveCounterLabel.getScene().getRoot() != null) {
            moveCounterLabel.getScene().getRoot().requestFocus();
        }
    }

    private void showGameOver() {
        String winner = gameModel.getWinner();
        String crashInfo = "";
        
        Car firstCar = gameModel.getCars().get(0);
        Car secondCar = gameModel.getCars().get(1);
        
        if (firstCar.isCrashed() && secondCar.isCrashed()) {
            crashInfo = " (Both cars crashed!)";
        } else if (firstCar.isCrashed()) {
            crashInfo = " (Your car crashed)";
        } else if (secondCar.isCrashed()) {
            crashInfo = " (Bot's car crashed)";
        }
        
        statusLabel.setText("Game Over! Winner: " + winner + crashInfo);
    }

    public void updateDisplay() {
        renderer.render(gameModel);
        updateMoveCounter();
    }

    private void updateMoveCounter() {
        moveCounterLabel.setText("Move #" + moveCounter);
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public HumanPlayer getHumanPlayer() {
        return humanPlayer;
    }

    public void stopGame() {
        gameModel.setGameOver(true);
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
} 