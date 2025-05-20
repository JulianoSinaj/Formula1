package com.formula1;

public class GameState {
    private boolean isHumanTurn;
    private boolean gameStarted;
    private int moveCounter;

    public GameState() {
        reset();
    }

    public void reset() {
        moveCounter = 0;
        gameStarted = false;
        isHumanTurn = true;
    }

    public boolean isHumanTurn() {
        return isHumanTurn;
    }

    public void setHumanTurn(boolean humanTurn) {
        isHumanTurn = humanTurn;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean started) {
        gameStarted = started;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void incrementMoveCounter() {
        moveCounter++;
    }
} 