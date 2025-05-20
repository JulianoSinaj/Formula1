package com.formula1.player;

import com.formula1.model.Vector2D;
import com.formula1.model.Car;
import com.formula1.model.Track;
import javafx.scene.input.KeyCode;

public class HumanPlayer implements Player {
    private final String name;
    private KeyCode lastKeyPressed;

    public HumanPlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Vector2D getNextMove(Car car, Track track) {
        if (lastKeyPressed == null) {
            return new Vector2D(0, 0);
        }

        // Debug info
        System.out.println("Processing move for key: " + lastKeyPressed);

        // Return acceleration vector based on key pressed
        switch (lastKeyPressed) {
            case UP:
                return new Vector2D(0, -1);
            case DOWN:
                return new Vector2D(0, 1);
            case LEFT:
                return new Vector2D(-1, 0);
            case RIGHT:
                return new Vector2D(1, 0);
            default:
                return new Vector2D(0, 0);
        }
    }

    public void setLastKeyPressed(KeyCode keyCode) {
        this.lastKeyPressed = keyCode;
    }

    public void clearLastKeyPressed() {
        this.lastKeyPressed = null;
    }
} 