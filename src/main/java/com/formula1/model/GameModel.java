package com.formula1.model;

import com.formula1.player.Player;
import java.util.*;

public class GameModel {
    private final Track track;
    private final List<Car> cars;
    private final Map<String, Player> players;
    private int currentPlayerIndex;
    private boolean gameOver;

    public GameModel(Track track) {
        this.track = track;
        this.cars = new ArrayList<>();
        this.players = new HashMap<>();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
    }

    public void addPlayer(Player player, Vector2D startPosition) {
        String playerName = player.getName();
        players.put(playerName, player);
        cars.add(new Car(playerName, startPosition));
    }

    public boolean makeMove() {
        if (gameOver) {
            return false;
        }

        Car currentCar = cars.get(currentPlayerIndex);
        if (currentCar.isCrashed()) {
            nextPlayer();
            return true;
        }

        Player currentPlayer = players.get(currentCar.getPlayerName());
        Vector2D acceleration = currentPlayer.getNextMove(currentCar, track);
        
        // Apply move
        currentCar.move(acceleration);

        // Check if the move is valid
        if (!track.isValidPosition(currentCar.getPosition())) {
            currentCar.setCrashed(true);
        }

        // Check if player has won
        if (track.isFinish(currentCar.getPosition())) {
            gameOver = true;
            return false;
        }

        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % cars.size();
    }

    public Track getTrack() {
        return track;
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    public Car getCurrentCar() {
        return cars.get(currentPlayerIndex);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        if (!gameOver) {
            return null;
        }
        return getCurrentCar().getPlayerName();
    }
} 